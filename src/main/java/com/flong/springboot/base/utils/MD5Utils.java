package com.flong.springboot.base.utils;

import com.flong.springboot.modules.task.MD5;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static MD5Utils md5 = null;
    private static final String ENCODING = "UTF-8";

    private MD5Utils(){};

    public static MD5Utils getInstance(){
        if(md5 == null)
            md5 = new MD5Utils();
        return md5;
    }

    /**
     * MD5进行数据加密
     */
    public static String  encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes(ENCODING));
            BigInteger bint = new BigInteger(1, bytes);
            return bint.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * MD5加密，前面encrypt方法可能会出现加密缺少前置0的情况
     */
    public String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 密码校验
     * inPassword：用户请求密码
     * dbPassword：数据库存储密码
     */
    public boolean compare(String inPassword,String dbPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(encrypt(inPassword).equals(dbPassword))
            return true;
        return false;
    }

    public static void main (String args[]){
        System.out.println(MD5Utils.encrypt("123456"));
    }
}
