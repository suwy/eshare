package com.yunde.website.allpay.common.util;

import com.jfinal.ext.kit.DateKit;
import com.jfinal.kit.StrKit;

import java.util.Calendar;
import java.util.Date;

public class JfinalTag {
	public String now(){
		return DateKit.getDateTimeStr();
	}
	
	public String now(String format){
		return DateKit.toStr(new Date(), format);
	}
	
	public String dateFormat(String dateStr, String format){
		return dateFormat(DateKit.toDate(dateStr), format);
	}
	
	public String dateFormat(Date date, String format){
		return DateKit.toStr(date, format);
	}
	
	public String nowAdd(String type, int number){
		return nowAdd(type, number, DateKit.dateTimeFormat);
	}
	
	public String nowAdd(String type, int number, String format){
		return dateAdd(new Date(), type, number, format);
	}
	
	public String dateAdd(String dateStr, String type, int number, String format){
		return dateAdd(DateKit.toDate(dateStr), type, number, format);
	}
	public String dateAdd(Date date, String type, int number, String format){
		switch (type) {
		case "year":
			date = DateKit.addYear(date, number);
			break;
		case "month":
			date = DateKit.addMonth(date, number);
			break;
		case "day":
			date = DateKit.add(date, Calendar.DAY_OF_MONTH, number);
			break;
		case "hour":
			date = DateKit.addHour(date, number);
			break;
		case "minute":
			date = DateKit.addMinute(date, number);
			break;
		case "second":
			date = DateKit.addSecond(date, number);
			break;
		default:
			break;
		}
		return DateKit.toStr(date, format);
	}
	
	public boolean hasPermission(String permissionId){
		if(permissionId.startsWith("res:")){
			return PermissionFactory.hasRes(permissionId.replaceFirst("res:",""));
		}
		return false;
	}
	
	public String htmlEncode(String str) { 
	    if (str == null || "".equals(str)) 
	    	return ""; 
	    str = str.replace(">", "&gt;"); 
	    str = str.replace("<", "&lt;"); 
	    str = str.replace(" ", "&nbsp;"); 
	    str = str.replace("  ", " &nbsp;"); 
	    str = str.replace("\"", "&quot;"); 
	    str = str.replace("\'", "&#39;"); 
	    str = str.replace("\n", " <br/> "); 
	    return str; 
	}

	public boolean startWith(String s, String w) {
		if(StrKit.isBlank(s) || StrKit.isBlank(w)){
			return false;
		}
		return s.startsWith(w);
	}

}
