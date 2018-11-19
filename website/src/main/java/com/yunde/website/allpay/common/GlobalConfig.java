package com.yunde.website.allpay.common;

import com.jfinal.base.ReturnResult;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;

/**
 * 全局参数配置
 * @author hang
 *
 */
public class GlobalConfig{
	//一些常量配置
	public final static String aes_key = "7048643781382613";//这个必须16位
	public final static String ADMIN_MENUS = "admin_menus";
	public final static String ADMIN_RESOURCES = "admin_resources";

	public static boolean isProduction(){
		return "production".equals(PropKit.get("env", "dev"));
	}
	public static DbPro getReadDb(){
		if(isProduction()){
			return Db.use("read");
		}
		return Db.use();
	}

	/**
	 * 全局错误码处理
	 */
	public enum ErrorCode {
		user_not_exist("用户不存在"),
		user_is_disabled("用户被禁用"),
		password_error("密码错误"),

		sys_no_permission_by_grant("授权失败，用户权限不足"),
		;

		private String errorMsg;
		public String getErrorMsg() {
			return errorMsg;
		}
		ErrorCode(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public ReturnResult render(){
			return ReturnResult.failure(this.errorMsg, this.name());
		}
	}

	public enum PlatType {
		wechat("1", "微信"),
		alipay("2", "支付宝");

		private String code;
		private String msg;

		PlatType(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}
		public String getMsg() {
			return msg;
		}
	}
}
