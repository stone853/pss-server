package com.flong.springboot.base.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.flong.springboot.core.constant.CommonConstant;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author jinshi
 * @Date 2021/3/3 13:57
 * @Version 1.0
 */
public class UserHelper {



    public static String getUserId (String token) {
        if (null !=token && !"".equals(token)) {
            return JWT.decode(token).getAudience().get(0);
        }
        return null;
    }



    public static String getToken(String userId,String password) {
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + CommonConstant.EXPIRE_TIME))
                .withAudience(String.valueOf(userId))
                .sign(Algorithm.HMAC256(password));
    }

    public static String getDateTime () {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getDate () {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 真是请求IP
     *
     * @param request
     * @return
     */
    public static String getRealRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (null != ip && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }


    public static void main (String args[]) {
        System.out.println(UserHelper.getDateTime());
    }
}
