package com.flong.springboot.base.utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author jinshi
 * @Date 2021/3/16 15:16
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptTest {

    @Test
    public void contextLoads() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)，随便写
        textEncryptor.setPassword("G0CvDz7oJn6");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("jinshi");
        String password1 = textEncryptor.encrypt("Jin87520024");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        System.out.println("password1:"+password1);

        //username:kT2BrE32MuY2ZlCWGWAX4g==
        //password:wBRBfk5vLUXsCn0QqPyCiQ==
        //password1:5peX8ZYZ1pM8q41fAYJ+zvj90JgoS5gy
    }
}
