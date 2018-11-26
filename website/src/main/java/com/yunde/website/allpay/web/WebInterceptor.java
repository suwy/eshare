package com.yunde.website.allpay.web;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.base.UserSession;
import com.jfinal.core.Controller;
import com.jfinal.ext.kit.ControllerKit;
import com.yunde.website.allpay.common.model.SysUser;

public class WebInterceptor implements Interceptor {
	@Override
	public void intercept(Invocation ai) {
		if (!login(ai)) {
			return;
		}
		ai.invoke();
	}

	// 登录拦截判断
	private boolean login(Invocation ai) {
		Controller controller = ai.getController();
		String actionKey = ai.getActionKey();
		// 登录拦截判断
		SysUser user = ControllerKit.loginUser(controller);
		if (!"/login".equals(actionKey) && !"/wechatLogin".equals(actionKey) && !"/doLogin".equals(actionKey) && user == null) {
			controller.renderNull();
			controller.redirect("/login");
			return false;
		}
		UserSession.set(user);
		return true;
	}
}
