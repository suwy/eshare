package com.yunde.website.allpay.base.plugin.allpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.base.ReturnResult;
import com.jfinal.interfaces.ISuccCallback;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class AllpayResult {
	private final static String DATA = "data";
	private final static String CODE = "code";
	protected JSONObject attrs;
	protected String str;
	
	public AllpayResult(String str) {
		this.str = str;
		try {
			this.attrs = JSONObject.parseObject(str);
		} catch (Exception e) {
			this.attrs = new JSONObject(){{
				put(CODE, "SYS_ERROR");
				put(DATA, e.getMessage());
			}};
		}
	}
	
	public static AllpayResult create(String str) {
		return new AllpayResult(str);
	}

	public static AllpayResult failure(String msg){
		JSONObject json = new JSONObject();
		json.put(CODE, "SYS_ERROR");
		json.put(DATA, msg);

		return new AllpayResult(json.toJSONString());
	}

	public String getStr() {
		return str;
	}
	
	@Override
	public String toString() {
		return getStr();
	}
	
	public boolean isSucceed() {
		String errorCode = getStr(CODE);
		return (errorCode != null && "SUCCESS".equals(errorCode));
	}
	
	public String getErrorMsg() {
		if(isSucceed()){
			return "";
		}
		String msg = getStr("errMsg");
		if(!StrKit.isBlank(msg)){
			return msg;
		}
		msg = getStr(DATA);
		if(!StrKit.isBlank(msg)){
			return msg;
		}
		return getStr(CODE);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T)attrs.get(name);
	}

	public String getStr(String name) {
		return attrs.getString(name);
	}

	public Integer getInteger(String name) {return attrs.getInteger(name);}

	public Long getLong(String name) {return attrs.getLong(name);}

	public BigInteger getBigInteger(String name) {
		return attrs.getBigInteger(name);
	}

	public Double getDouble(String name) {return attrs.getDouble(name);}

	public Float getFloat(String name){return attrs.getFloat(name);}

	public BigDecimal getBigDecimal(String name) {
		return attrs.getBigDecimal(name);
	}

	public Boolean getBoolean(String name) {
		return attrs.getBoolean(name);
	}

	@SuppressWarnings("rawtypes")
	public <T> List<T> getList(String name, Class<T> clazz) {
		JSONArray array = getJsonArray(name);
		if(array != null){
			return array.toJavaList(clazz);
		}
		return null;
	}

	public <T> T getType(String name, Class<T> clazz){
		JSONObject json = getJson(name);
		if(json != null){
			return json.toJavaObject(clazz);
		}
		return null;
	}

	public JSONObject getJson(String name){
		Object object = get(name);
		if(object == null){
			return null;
		}
		if(object instanceof String){
			return JSONObject.parseObject((String) object);
		}
		if(object instanceof List){
			JSONArray array = getJsonArray(name);
			return array != null && array.size() > 0 ? array.getJSONObject(0):null;
		}
		return (JSONObject) JSON.toJSON(object);
	}

	public JSONArray getJsonArray(String name){
		Object object = get(name);
		if(object == null){
			return null;
		}
		if(object instanceof String){
			return JSONArray.parseArray((String) object);
		}
		return (JSONArray) JSON.toJSON(object);
	}

	public <T> Page<T> getPage(String name, Class<T> clazz){
		JSONObject json = getJson(name);
		if(json != null){
			Page<T> page = new Page<T>(json.getJSONArray("list").toJavaList(clazz),
					json.getInteger("pageNumber"),
					json.getInteger("pageSize"),
					json.getInteger("totalPage"),
					json.getInteger("totalRow")
			);
			attrs.put(DATA, page);
			return page;
		}
		return null;
	}

	public String dataToStr(){ return dataToStr(true); }
	public String dataToStr(boolean result){
		return check(result)? getStr(DATA) : null;
	}

	public Float datoToFloat(){ return dataToFloat(true); }
	public Float dataToFloat(boolean result){
		return check(result)? getFloat(DATA) : null;
	}

	public Double dataToDouble(){
		return dataToDouble(true);
	}
	public Double dataToDouble(boolean result){
		return check(result)? getDouble(DATA) : null;
	}

	public BigInteger dataToBigInteger(){
		return dataToBigInteger(true);
	}
	public BigInteger dataToBigInteger(boolean result){
		return check(result)? getBigInteger(DATA) : null;
	}

	public BigDecimal dataToBigDecimal(){
		return dataToBigDecimal(true);
	}
	public BigDecimal dataToBigDecimal(boolean result){
		return check(result)? getBigDecimal(DATA) : null;
	}

	public Integer dataToInteger(){return dataToInteger(true);}
	public Integer dataToInteger(boolean result){
		return check(result)? getInteger(DATA) : null;
	}

	public Long dataToLong(){
		return dataToLong(true);
	}
	public Long dataToLong(boolean result){
		return check(result)? getLong(DATA) : null;
	}

	public Boolean dataToBoolean(){
		return dataToBoolean(true);
	}
	public Boolean dataToBoolean(boolean result){
		return check(result)? getBoolean(DATA) : null;
	}

	public JSONObject dataToJson(){
		return dataToJson(true);
	}
	public JSONObject dataToJson(boolean result){
		return check(result)? getJson(DATA) : null;
	}

	public <T> List<T> dataToList(Class<T> clazz){
		return dataToList(clazz, true);
	}
	public <T> List<T> dataToList(Class<T> clazz, boolean result){
		return check(result)? getList(DATA, clazz) : null;
	}

	public JSONArray dataToJsonArray(){
		return dataToJsonArray(true);
	}
	public JSONArray dataToJsonArray(boolean result){
		return check(result)? getJsonArray(DATA) : null;
	}

	public <T> Page<T> dataToPage(Class<T> clazz){
		return dataToPage(clazz, true);
	}
	public <T> Page<T> dataToPage(Class<T> clazz, boolean result){
		return check(result)? getPage(DATA, clazz) : null;
	}

	public <T> T dataToType(Class<T> clazz){
		return dataToType(clazz, true);
	}
	public <T> T dataToType(Class<T> clazz, boolean result){
		return check(result)? getType(DATA, clazz) : null;
	}

	public AllpayResult call(ISuccCallback<AllpayResult> call){
		if(call == null || !this.isSucceed()){
    		return this;
    	}

		AllpayResult result = call.callback(this);
		if(!result.isSucceed()){
			return result;
		}
		return this;
	}

	private boolean check(boolean result){
		return result == isSucceed();
	}

	public ReturnResult toReturnResult(){
		if(isSucceed()){
			Object object = get(DATA);
			return ReturnResult.success(object);
		}
		return ReturnResult.failure(getErrorMsg());
	}
}