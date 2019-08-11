package com.gz.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

public class CheckUtil {

	public static boolean checkSignature(String signature, String timestamp, String nonce,String token) {
		String[] arr = new String[] { token, timestamp, nonce };
		Arrays.sort(arr);// 排序
		// 生成字符串
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		String temp = getSHA1String(content.toString());// sha1加密
		return temp.equals(signature); // 与微信传递过来的签名进行比较
	}

	private static String getSHA1String(String data) {
		return DigestUtils.sha1Hex(data); // 使用commons codec生成sha1字符串
	}
}
