package com.espmail.utils;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

	public static final String getIp(HttpServletRequest req) {
        String ip = req.getHeader("HTTP_X_FORWARDED_FOR");

        if (ip == null) {
            ip = req.getRemoteAddr();
        }
        
        return ip;
	}
}
