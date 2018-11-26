package com.yunde.website.allpay.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.kit.ControllerKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.yunde.website.allpay.base.base.AllpayModuleController;
import com.yunde.website.allpay.base.base.BaseAllpayBean;

import java.util.List;

@Before(WebInterceptor.class)
public abstract class BaseAllpayModuleController<M extends BaseAllpayBean> extends AllpayModuleController<M> {
	public Controller setAttr(String name, M m) {
		return super.setAttr(name, JSONObject.parseObject(JsonKit.toJson(m)));
	}

	public Controller setAttr(String name, Page<M> m) {
		return super.setAttr(name, JSONObject.parseObject(JsonKit.toJson(m)));
	}

	public Controller setAttr(String name, List<M> m) {
		return super.setAttr(name, JSONArray.parseArray(JsonKit.toJson(m)));
	}

	/**
	 * 模块首页
	 */
	public void index(){
		ControllerKit.setAttrs(this);
		setAttr("_dataUrl", ControllerKit.controlerUrl(this));
		render("_index.html");
	}

	/**
	 * 分页列表数据
	 */
	@Override
	public void getByPage(){
		setAttr("_page", kit.getPage(getParaMap()));
		render("_page.html");
	}

	/**
	 * 模块详情
	 */
	public void info(){
		view();
		render("_form.html");
	}

	public void view(){
		showPage();
		setAttr("_id", getPara());
		render("_form.html");
	}

	/**
	 * 页面跳转
	 */
	public void showPage(){
		setAttr("_dataUrl", ControllerKit.controlerUrl(this));
		if(!StrKit.isBlank(getPara("id"))){
			setAttr("_id", getPara("id"));
		}
		render(ControllerKit.showPage(this));
	}
}