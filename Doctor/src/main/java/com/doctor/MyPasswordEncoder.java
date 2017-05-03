package com.doctor;


import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Created by free on 2016/11/19.
 */
@Component("passwordEncoder")
public class MyPasswordEncoder implements PasswordEncoder
{
    public MyPasswordEncoder() {
        super();
    }

    public String encodePassword(String rawPass, Object salt) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        String mergestr = "admin"+rawPass;
        String mergestr = salt+rawPass;
        return MyMd5.stringMD5(mergestr);
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        String pass1 = "" + encPass;
        String pass2 = this.encodePassword(rawPass, salt);
        return MyPasswordEncoderUtils.equals(pass1, pass2);
    }
}