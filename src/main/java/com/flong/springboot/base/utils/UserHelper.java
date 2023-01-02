package com.flong.springboot.base.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.flong.springboot.core.constant.CommonConstant;


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



    public static String getToken(String mobile,String password) {
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + CommonConstant.EXPIRE_TIME))
                .withAudience(String.valueOf(mobile))
                .sign(Algorithm.HMAC256(password));
    }

    public static String getDateTime () {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public static String getDate () {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
