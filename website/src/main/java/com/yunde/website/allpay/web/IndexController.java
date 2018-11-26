package com.yunde.website.allpay.web;

import com.jfinal.base.BaseConfig;
import com.jfinal.base.ReturnResult;
import com.jfinal.ext.kit.ControllerKit;
import com.jfinal.ext.plugin.validate.CheckNotNull;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.route.ControllerBind;
import com.yunde.website.allpay.common.GlobalConfig;
import com.yunde.website.allpay.common.enums.State;
import com.yunde.website.allpay.common.model.SysMenu;
import com.yunde.website.allpay.common.model.SysResource;
import com.yunde.website.allpay.common.model.SysUser;
import com.yunde.website.allpay.common.util.PermissionFactory;
import com.yunde.website.allpay.web.sys.user.SysUserService;

@ControllerBind(controllerKey="/")
public class IndexController<M extends Model<M>> extends BaseTempleteController<M> {
	/**
	 * 首页页面
	 */
	@Override
	public void index(){
		setAttr("user", ControllerKit.loginUser(this));
		setAttr("userMenuTypes", PermissionFactory.getLoginUserMenuTypes());
		renderForMobileAndPC("index.html");
	}
	
	/**
	 * 登录页面
	 */
	public void login(){
		if(ControllerKit.loginUser(this) != null){
			redirect("/");
		}
		else{
			renderForMobileAndPC("login.html");
		}
	}
	
	/**
	 * 登录方法
	 */
	@CheckNotNull({"user.account", "user.password"})
	public ReturnResult doLogin(){
		getRequest().getSession().invalidate();
		SysUser info = getModel(SysUser.class, "user");
		return login(info.getAccount(), info.getPassword());
	}
	
	/**
	 * 系统级用户登录
	 * @param account
	 * @param password
	 * @return
	 */
	private ReturnResult login(String account, String password){
		SysUser user = SysUser.dao.getFirstByWhat("account", account);
		if(user==null){
			return GlobalConfig.ErrorCode.user_not_exist.render();
		}
		if(!State.Default.y.name().endsWith(user.getState())){
			return GlobalConfig.ErrorCode.user_is_disabled.render();
		}
		
		if(!user.getPassword().equals(SysUserService.me.encryptPws(password, user.getId()))){
			return GlobalConfig.ErrorCode.password_error.render();
		}
		if(SysUserService.me.isAdmin(user)){
			user.put(GlobalConfig.ADMIN_MENUS, SysMenu.dao.getAll());
			user.put(GlobalConfig.ADMIN_RESOURCES, SysResource.dao.getAll());
		}
		setSessionAttr(BaseConfig.loginUserSessionAttr, user);
		ControllerKit.setLoginUser(this, user);
		return ReturnResult.success();
	}
	
	/**
	 * 登出页面
	 */
	public void logout(){
		ControllerKit.clearLoginUser(this);
		renderForMobileAndPC("login.html");
	}
}
