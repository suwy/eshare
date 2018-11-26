package com.yunde.website.allpay.common.util;

import com.jfinal.base.UserSession;
import com.jfinal.kit.StrKit;
import com.yunde.website.allpay.common.GlobalConfig;
import com.yunde.website.allpay.common.model.SysMenu;
import com.yunde.website.allpay.common.model.SysResource;
import com.yunde.website.allpay.common.model.SysUser;
import com.yunde.website.allpay.web.sys.menu.SysMenuService;
import com.yunde.website.allpay.web.sys.resource.SysResourceService;
import com.yunde.website.allpay.web.sys.user.SysUserService;

import java.util.*;

/**
 * 登陆用户权限管理构造器
 * Created by hang on 2017/3/13 0013.
 */
public class PermissionFactory {
	private static boolean isAdmin(){
		return SysUserService.me.isAdmin(UserSession.get());
	}

	public static boolean hasRes(String resId){
		List<String> resoureIds = PermissionFactory.getLoginUserResourceIds();
		return resoureIds!= null && resoureIds.contains(resId);
	}

	/**
	 * 获取菜单权限
	 * @return
	 */
	public static List<String> getLoginUserMenuIds(){
		SysUser user = UserSession.get();
		if(user == null){
			return new ArrayList<String>();
		}
		if(isAdmin()){
			List<SysMenu> all_menus = getAdminMenus();
			List<String> menus = new ArrayList<String>();
			for(SysMenu menu : all_menus){
				menus.add(menu.getId());
			}
			return menus;
		}
		return SysMenuService.me.getUserMenuIds(user.getId());
	}
	/**
	 * 获取登录用户菜单
	 * @return
	 */
	public static List<SysMenu> getLoginUserMenus(){
		SysUser user = UserSession.get();
		if(user == null){
			return new ArrayList<SysMenu>();
		}
		if(isAdmin()){
			return getAdminMenus();
		}
		return SysMenuService.me.getUserMenus(user.getId());
	}

	/**
	 * 转为树结构
	 * @return
	 */
	public static List<Object> getLoginUserMenusToTree(){
		return NodeKit.buildNodes(getLoginUserMenus());
	}

	public static Map<String, Object> getLoginUserMenuTypes(){
		Set<String> set = new HashSet<>();
		for(SysMenu menu : getLoginUserMenus()){
			String menuType = StrKit.isBlank(menu.getMenuType())?"default":menu.getMenuType();
			set.add(menuType);
		}
		LinkedHashMap<String,Object> map = new LinkedHashMap<>();
		for(Map.Entry<String,Object> entry : JfinalDictTag.MENU_TYPE.entrySet()){
			if(set.contains(entry.getKey())){
				map.put(entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

	/**
	 * 获取资源权限
	 * @return
	 */
	public static List<String> getLoginUserResourceIds(){
		Object object = UserSession.get();
		if(object == null){
			return new ArrayList<String>();
		}

		SysUser user = (SysUser)object;
		if(isAdmin()){
			List<SysResource> all_resources = getAdminResources();
			List<String> resources = new ArrayList<String>();
			for(SysResource resource : all_resources){
				resources.add(resource.getId());
			}
			return resources;
		}
		return SysResourceService.me.getUserResourceIds(user.getId());
	}

	public static List<SysResource> getLoginUserResources(){
		SysUser user = UserSession.get();
		if(user == null){
			return new ArrayList<SysResource>();
		}
		if(isAdmin()){
			return getAdminResources();
		}
		return SysResourceService.me.getUserResources(user.getId());
	}

	public static List<SysResource> getAdminResources(){
		SysUser user = UserSession.get();
		List<SysResource> resources = user.get(GlobalConfig.ADMIN_RESOURCES);
		if(resources == null){
			resources = SysResource.dao.getAll();
			user.put(GlobalConfig.ADMIN_RESOURCES, resources);
		}
		return resources;
	}

	public static List<SysMenu> getAdminMenus(){
		SysUser user = UserSession.get();
		List<SysMenu> menus = user.get(GlobalConfig.ADMIN_MENUS);
		if(menus == null){
			menus = SysMenu.dao.getAll();
			user.put(GlobalConfig.ADMIN_MENUS, menus);
		}
		return menus;
	}
}
