package com.anchor.Service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

public interface WxPayService {

	public Integer transferPay(HttpServletRequest request,
			@Param("openid") String openid,
			@Param("tradeNo") String tradeNo,
			@Param("orderAmount") String orderAmount);
}
