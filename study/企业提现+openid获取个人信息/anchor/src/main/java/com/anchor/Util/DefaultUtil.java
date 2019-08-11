package com.anchor.Util;

public class DefaultUtil {

	
	/**
     * 正则表达式：验证手机号
     */
    public static final String phone = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    
    
    /**
     * 正则表达式：验证汉字
     */
    public static final String name = "^([\\u4e00-\\u9fa5]{1,20}|[a-zA-Z\\.\\s]{1,20})$";
    
    
    
	public static final Integer defaultPage=9;
}
