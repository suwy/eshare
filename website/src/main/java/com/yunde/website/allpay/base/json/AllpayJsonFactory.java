package com.yunde.website.allpay.base.json;

import com.jfinal.json.IJsonFactory;
import com.jfinal.json.Json;

/**
 * IJsonFactory 的 allpayjson 实现.
 */
public class AllpayJsonFactory implements IJsonFactory {

    private static final AllpayJsonFactory me = new AllpayJsonFactory();
	
	public static AllpayJsonFactory me() {
		return me;
	}
	
	public Json getJson() {
		return new AllpayJson();
	}
}