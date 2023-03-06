package com.flong.springboot.modules.task;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
//    private static MD5 md5 = null;
//    private final String ENCODING = "UTF-8";
//
//    private MD5(){};
//
//    public static MD5 getInstance(){
//        if(md5 == null)
//            md5 = new MD5();
//        return md5;
//    }
//
//    /**
//     * MD5进行数据加密
//     */
//    public String  encrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        byte[] bytes = md.digest(password.getBytes(ENCODING));
//        BigInteger bint = new BigInteger(1, bytes);
//        return bint.toString(16);
//
//    }
//    /**
//     * MD5加密，前面encrypt方法可能会出现加密缺少前置0的情况
//     */
//    public String getMd5(String plainText) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(plainText.getBytes());
//            byte b[] = md.digest();
//
//            int i;
//
//            StringBuffer buf = new StringBuffer("");
//            for (int offset = 0; offset < b.length; offset++) {
//                i = b[offset];
//                if (i < 0)
//                    i += 256;
//                if (i < 16)
//                    buf.append("0");
//                buf.append(Integer.toHexString(i));
//            }
//            //32位加密
//            return buf.toString();
//            // 16位的加密
//            //return buf.toString().substring(8, 24);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    /**
//     * 密码校验
//     * inPassword：用户请求密码
//     * dbPassword：数据库存储密码
//     */
//    public boolean compare(String inPassword,String dbPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
//        if(encrypt(inPassword).equals(dbPassword))
//            return true;
//        return false;
//    }
}
