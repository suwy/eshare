package com.yunde.website.allpay.web.sys.menu;

import com.jfinal.base.ReturnResult;
import com.jfinal.ext.plugin.sql.Cnd;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.yunde.website.allpay.common.GlobalConfig;
import com.yunde.website.allpay.common.model.SysMenu;
import com.yunde.website.allpay.common.model.SysRoleMenu;
import com.yunde.website.allpay.common.model.SysUserMenu;
import com.yunde.website.allpay.common.util.PermissionFactory;
import com.yunde.website.allpay.web.sys.user.SysUserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SysMenuService {
	public static final SysMenuService me = new SysMenuService();
	
	public void setUserMenusToRedis(String userId, List<String> menus){
//		RedisKeyKit.set(RedisKey.SysUserEnum.menus, userId, menus);
	}
	
	public void setRoleMenusToRedis(String roleId, List<String> menus){
//		RedisKeyKit.set(RedisKey.SysRoleEnum.menus, roleId, menus);
	}
	
	/**
	 * 获取单纯用户菜单ID集合
	 * @return
	 */
	public List<String> getSingleUserMenuIds(String userId){
//		return RedisKeyKit.getSet(RedisKey.SysUserEnum.menus, userId, new IDataLoader() {
//			@Override
//			public Object load() {
//				return getSingleUserMenuIdsFromDb(userId);
//			}
//		});
		return null;
	}
	private List<String> getSingleUserMenuIdsFromDb(String userId){
		Cnd cnd = Cnd.$select()
				.select(String.format("select menuId from %s", SysUserMenu.dao.getTableName()))
				.where(SysUserMenu.c.userId, userId)
				.build();
		
		List<String> menuIds = Db.query(cnd.getSql(), cnd.getParas());
		return menuIds;
	}
	
	/**
	 * 获取单纯角色菜单ID集合
	 * @return
	 */
	public List<String> getSingleRoleMenuIds(String roleId){
//		return RedisKeyKit.getSet(RedisKey.SysRoleEnum.menus, roleId, new IDataLoader() {
//			@Override
//			public Object load() {
//				return getSingleRoleMenuIdsFromDb(roleId);
//			}
//		});
		return null;
	}
	private List<String> getSingleRoleMenuIdsFromDb(String roleId){
		Cnd cnd = Cnd.$select()
				.select(String.format("select menuId from %s", SysRoleMenu.dao.getTableName()))
				.where(SysRoleMenu.c.roleId, roleId)
				.build();
		
		List<String> menuIds = Db.query(cnd.getSql(), cnd.getParas());
		return menuIds;
	}
	
	/**
	 * 获取用户菜单ID集合
	 * @return
	 */
	public List<String> getUserMenuIds(String userId){
		Set<String> set = new HashSet<String>();
		set.addAll(getSingleUserMenuIds(userId));
		
		for(String roleId : SysUserService.me.getUserRoleIds(userId)){
			set.addAll(getSingleRoleMenuIds(roleId));
		}
		return new ArrayList<String>(set);
	}
	
	/**
	 * 获取单纯角色菜单列表
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getSingleRoleMenus(String roleId) {
		List<String> roleMenus = getSingleRoleMenuIds(roleId);
		Set<SysMenu> menus = new HashSet<SysMenu>();
		for(String menuId : roleMenus){
			SysMenu menu = SysMenu.dao.findById(menuId);
			if(menu != null && "y".equals(menu.getState())){
				menus.add(menu);
			}
		}
		return new ArrayList<SysMenu>(menus);

	} 
	
	/**
	 * 获取单纯用户菜单列表
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getSingleUserMenus(String roleId) {
		List<String> roleMenus = getSingleUserMenuIds(roleId);
		Set<SysMenu> menus = new HashSet<SysMenu>();
		for(String menuId : roleMenus){
			SysMenu menu = SysMenu.dao.findById(menuId);
			if(menu != null && "y".equals(menu.getState())){
				menus.add(menu);
			}
		}
		return new ArrayList<SysMenu>(menus);

	}
	
	/**
	 * 获取用户菜单列表
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getUserMenus(String userId) {
		// 返回菜单
		Set<SysMenu> menus = new HashSet<SysMenu>();
		for(String menuId : getUserMenuIds(userId)){
			SysMenu menu = SysMenu.dao.findById(menuId);
			if(menu != null){
				menus.add(menu);
			}
		}
		return new ArrayList<SysMenu>(menus);

	} 
	
	/**
	 * 角色授权
	 * Enhancer.enhance(SysMenuService.class, Tx.class).grantToRole(roleId,menuIds)
	 */
	public ReturnResult grantToRole(String roleId, List<String> menuIds){
		if(menuIds == null){
			return ReturnResult.failure();
		}
		//可操作的ID集合
		List<String> loginUserMenuIds = PermissionFactory.getLoginUserMenuIds();
		//选择角色拥有的权限
		List<String> singleRoleMenuIds = getSingleRoleMenuIds(roleId);
		
		//这种情况是清空所有授权
		int menuIdSize = menuIds.size();
		if(menuIdSize == 0){
			singleRoleMenuIds.retainAll(loginUserMenuIds);
			SysRoleMenu.dao.delete(new SysRoleMenu().setRoleId(roleId).put(SysRoleMenu.c.menuId, singleRoleMenuIds));
			setRoleMenusToRedis(roleId, new ArrayList<String>());
		} else {
			menuIds = new ArrayList<String>(menuIds);
			//剔除没有权限操作的
			menuIds.retainAll(loginUserMenuIds);
			if(menuIds.size() == 0 || menuIds.size() != menuIdSize){
				return GlobalConfig.ErrorCode.sys_no_permission_by_grant.render();
			}
			
			//新增的权限
			List<String> add_menus = new ArrayList<String>(menuIds);
			add_menus.removeAll(singleRoleMenuIds);
			for(String menuId : add_menus){
				new SysRoleMenu().setId(StrKit.uuidNoneLine()).setRoleId(roleId).setMenuId(menuId).save();
			}
			
			//删除的权限
			singleRoleMenuIds.removeAll(menuIds);
			if(singleRoleMenuIds.size() > 0){
				SysRoleMenu.dao.delete(new SysRoleMenu().setRoleId(roleId).put(SysRoleMenu.c.menuId, singleRoleMenuIds));
			}
			setRoleMenusToRedis(roleId, getSingleRoleMenuIdsFromDb(roleId));
		}
		return ReturnResult.success();
	}
	
	/**
	 * 用户授权
	 * Enhancer.enhance(SysMenuService.class, Tx.class).grantToUser(userId,menuIds)
	 */
	public ReturnResult grantToUser(String userId, List<String> menuIds){
		if(menuIds == null){
			return ReturnResult.failure();
		}
		//可操作的ID集合
		List<String> loginUserMenuIds = PermissionFactory.getLoginUserMenuIds();
		//选择用户拥有的权限
		List<String> singleUserMenuIds = getSingleUserMenuIds(userId);
		
		//这种情况是清空所有授权
		int menuIdSize = menuIds.size();
		if(menuIdSize == 0){
			singleUserMenuIds.retainAll(loginUserMenuIds);
			SysUserMenu.dao.delete(new SysUserMenu().setUserId(userId).put(SysUserMenu.c.menuId, singleUserMenuIds));
			setUserMenusToRedis(userId, new ArrayList<String>());
		} else {
			menuIds = new ArrayList<String>(menuIds);
			//剔除没有权限操作的
			menuIds.retainAll(loginUserMenuIds);
			if(menuIds.size() == 0 || menuIds.size() != menuIdSize){
				return GlobalConfig.ErrorCode.sys_no_permission_by_grant.render();
			}
			
			//新增的权限
			List<String> add_menus = new ArrayList<>(menuIds);
			List<SysUserMenu> add_models = new ArrayList<>();
			add_menus.removeAll(singleUserMenuIds);
			for(String menuId : add_menus){
				SysUserMenu temp = new SysUserMenu().setId(StrKit.uuidNoneLine()).setUserId(userId).setMenuId(menuId);
				temp.save();
				add_models.add(temp);
			}
			
			//删除的权限
			List<Object> del_menus = new ArrayList<>(singleUserMenuIds);
			del_menus.removeAll(menuIds);
			if(del_menus.size() > 0){
				SysUserMenu.dao.delete(new SysUserMenu().setUserId(userId).put(SysUserMenu.c.menuId, del_menus));
			}

			setUserMenusToRedis(userId, getSingleUserMenuIdsFromDb(userId));
		}
		return ReturnResult.success();
	}
}
