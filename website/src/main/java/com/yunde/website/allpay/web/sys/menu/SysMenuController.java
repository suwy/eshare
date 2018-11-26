package com.yunde.website.allpay.web.sys.menu;

import com.jfinal.aop.Enhancer;
import com.jfinal.base.ReturnResult;
import com.jfinal.ext.plugin.validate.CheckNotNull;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.route.ControllerBind;
import com.yunde.website.allpay.common.model.SysMenu;
import com.yunde.website.allpay.common.util.NodeKit;
import com.yunde.website.allpay.common.util.PermissionFactory;
import com.yunde.website.allpay.web.BaseTempleteController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SysMenuController
 */
@ControllerBind(controllerKey="/sys/menu")
public class SysMenuController extends BaseTempleteController<SysMenu> {
	/**
	 * 获取登录用户菜单
	 */
	public void getMenus() {
		renderResult(PermissionFactory.getLoginUserMenusToTree());
	}

	@CheckNotNull({"menuType"})
	public List<Object> getMenusByType() {
		String menuType = getPara("menuType");
		List<SysMenu> menus = new ArrayList<SysMenu>();
		List<SysMenu> userMenus = PermissionFactory.getLoginUserMenus();
		for (SysMenu menu : userMenus) {
			if(StrKit.valueEquals(menuType, menu.getMenuType())) {
				menus.add(menu);
			}
		}
		return NodeKit.buildNodes(menus);
	}

	/**
	 * 获取角色拥有的菜单
	 */
	@CheckNotNull({"roleId"})
	public void getRoleMenus(){
		renderResult(SysMenuService.me.getSingleRoleMenus(getPara("roleId")));
	}
	
	/**
	 * 用户菜单授权
	 */
	@CheckNotNull({"userId","menuIds"})
	public void grantToUser(){
		String userId = getPara("userId");
		List<String> menuIds = Arrays.asList(getPara("menuIds").split(","));
		
		//授权
		ReturnResult result = Enhancer.enhance(SysMenuService.class, Tx.class).grantToUser(userId, menuIds);
		renderResult(result);
	}
	
	/**
	 * 角色菜单授权
	 */
	@CheckNotNull({"roleId","menuIds"})
	public void grantToRole(){
		String roleId = getPara("roleId");
		List<String> menuIds = Arrays.asList(getPara("menuIds").split(","));
		
		//授权
		ReturnResult result = Enhancer.enhance(SysMenuService.class, Tx.class).grantToRole(roleId, menuIds);
		renderResult(result);
	}
	
	@Override
	@CheckNotNull({"name","link"})
	public void save(){
		super.save();
	}
}
