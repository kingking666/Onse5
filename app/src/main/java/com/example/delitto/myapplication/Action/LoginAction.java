package com.example.delitto.myapplication.Action;

import httpservice.LoginPost;

/**
 * Created by ASUS on 2016/12/7.
 */

public class LoginAction {
    public static boolean flag;
    public  static void GetLogin(final String phone,final String password){
        new Thread() {
            public void run() {
                final String info = LoginPost.executeHttpPost(phone, password);
                flag = info.equals("login is success!!!!");
            }
        }.start();
    }
    public static boolean getLoginFlag(){
        return flag;
    }
}
