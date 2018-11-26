package com.yunde.website.allpay.web.sys.user;

import com.jfinal.aop.Before;
import com.jfinal.base.BaseConfig;
import com.jfinal.base.ReturnResult;
import com.jfinal.ext.kit.ControllerKit;
import com.jfinal.ext.kit.ModelKit;
import com.jfinal.ext.plugin.validate.CheckNotNull;
import com.jfinal.interfaces.ISuccCallback;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.route.ControllerBind;
import com.yunde.website.allpay.common.model.SysUser;
import com.yunde.website.allpay.web.BaseTempleteController;

import java.util.Map;

/**
 * UserController
 * controllerKey	访问某个 Controller 所需要的一个字符串
 * viewPath	Controller 返回的视图的相对路径
 */
@ControllerBind(controllerKey="/sys/user")
public class SysUserController extends BaseTempleteController<SysUser> {
	
	@Override
	public void getByPage() {
		setAttr("_page", SysUserService.me.getByPage(getParaMap(), pageNumber(), pageSize()));
		render("_page.html");
	}
	
	@Override
	public void save(){
		SysUser user = kit.getBean();
		ReturnResult result = SysUserService.me.saveUser(user, getPara("roleIds"));
		renderResult(result);
	}
	
	@Override
	public void update(){
		SysUser user = kit.getBean();
		ReturnResult result = SysUserService.me.updateUser(user, getPara("roleIds"));
		renderResult(result);
	}
	
	@Override
	@Before(Tx.class)
	public void delete(){
		ReturnResult result = SysUserService.me.deleteUsers(new Object[]{getPara()});
		renderResult(result);
	}
	
	@Override
	@Before(Tx.class)
	public void deletes(){
		ReturnResult result = SysUserService.me.deleteUsers(getPara("ids").split(","));
		renderResult(result);
	}
	
	/**
	 * @api {post} /sys/user/loginInfo  获取登录用户信息
	 * @apiName loginInfo
	 * @apiGroup SysUser
	 * @apiVersion 0.0.1
	 * 
	 * @apiSuccessExample 成功返回:  
	 * {"result":{"id":"0ff3f60c542cb3417118d846064ada39","email":null,"nickName":null,"name":"苏巧巧","image":"7cd146fe6b293ed026f0ff602f38c664","account":"suqiaoqiao","mobile":null},"code":"200","msg":"操作成功"}
	 * @apiUse DefError
	 */
	public void loginInfo(){
		SysUser user = ControllerKit.loginUser(this);
		if(user == null){
			renderJson(BaseConfig.notLogin());
			return;
		}
		Map<String, Object> map = ModelKit.toMap(user);
		map.remove(SysUser.c.password);
		renderResult(map);
	}

	/**
	 * @api {post} /sys/user/updatePwd  修改用户密码
	 * @apiName updatePwd
	 * @apiGroup SysUser
	 * @apiVersion 0.0.1
	 * 
	 * @apiparam {String} password 【必填】 旧密码
	 * @apiparam {String} newpassword 【必填】 新密码
	 * 
	 * @apiUse DefSuccess
	 * @apiUse DefError
	 */
	@CheckNotNull({"password","newpassword"})
	public void updatePwd(){
		SysUser user = ControllerKit.loginUser(this);
		if(user == null){
			renderResult(false);
			return;
		}
		ReturnResult result = SysUserService.me.updateUserPwd(user, getPara("password"), getPara("newpassword")).call(new ISuccCallback<ReturnResult>() {
			@Override
			public ReturnResult callback(ReturnResult result) {
				ControllerKit.setLoginUser(SysUserController.this, user);
				return result;
			}
		});
		
		renderResult(result);
	}
	
	
	@CheckNotNull({"id","password","newpassword"})
	public void updatePwdByUserId(){
		SysUser user = SysUser.dao.findById(getPara("id"));
		if(user == null){
			renderResult(false);
			return;
		}
		ReturnResult result = SysUserService.me.updateUserPwd(user, getPara("password"), getPara("newpassword"));
		
		renderResult(result);
	}
	
	
	
	public void updatePwdPage(){
		render("_pwd.html");
	}

}
