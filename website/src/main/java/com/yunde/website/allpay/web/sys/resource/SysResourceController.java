package com.yunde.website.allpay.web.sys.resource;

import com.jfinal.aop.Enhancer;
import com.jfinal.base.ReturnResult;
import com.jfinal.ext.plugin.validate.CheckNotNull;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.route.ControllerBind;
import com.yunde.website.allpay.common.model.SysResource;
import com.yunde.website.allpay.common.util.PermissionFactory;
import com.yunde.website.allpay.web.BaseTempleteController;

import java.util.Arrays;
import java.util.List;

@ControllerBind(controllerKey = "/sys/resource")
public class SysResourceController extends BaseTempleteController<SysResource> {

	private List<SysResource> getLoginUserResources() {
		return PermissionFactory.getLoginUserResources();
	}

	/**
	 * 获取登录用户资源列表
	 */
	public void getResources() {
		// 获取登录用户资源列表
		List<SysResource> loginUserResources = getLoginUserResources();
		renderResult(loginUserResources);
	}

	/**
	 * 获取user和role资源权限
	 */
	@CheckNotNull({ "userId" })
	public void getUserResources() {
		List<SysResource> userResourcesList = SysResourceService.me.getUserResources(getPara("userId"));
		renderResult(userResourcesList);
	}

	/**
	 * 获取role资源权限
	 */
	@CheckNotNull({ "roleId" })
	public void getRoleResources() {
		// 获取指定用户资源列表
		List<SysResource> userResourcesList = SysResourceService.me.getSingleRoleResources(getPara("roleId"));
		renderResult(userResourcesList);
	}

	/**
	 * 获取role资源权限,编辑时，应该过滤掉当前登陆者不存在的资源
	 */
	@CheckNotNull({ "roleId" })
	public void getRoleResourcesForUpdate() {
		// 获取指定用户资源列表
		List<SysResource> roleResourcesList = SysResourceService.me.getSingleRoleResources(getPara("roleId"));
		List<SysResource> loginUserResourcesList = getLoginUserResources();
		renderResult(roleResourcesList.retainAll(loginUserResourcesList));
	}

	/**
	 * 角色资源授权
	 */
	@CheckNotNull({ "roleId", "resourcesIds" })
	public void grantResourcesToRole() {
		List<String> resourcesIdsIds = Arrays.asList(getPara("resourcesIds").split(","));

		// 授权
		ReturnResult result = Enhancer.enhance(SysResourceService.class, Tx.class).grantResourcesToRole(getPara("roleId"), resourcesIdsIds);
		renderResult(result);
	}

	public void add() {
		render("_add.html");
	}
}
