package com.flong.springboot.base.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.flong.springboot.core.constant.CommonConstant;


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



    public static String getToken(String userId,String mobile) {
        return JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + CommonConstant.EXPIRE_TIME))
                .withAudience(String.valueOf(userId))
                .sign(Algorithm.HMAC256(mobile));
    }
}
