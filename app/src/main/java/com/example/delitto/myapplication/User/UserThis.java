package com.example.delitto.myapplication.User;

/**
 * Created by ASUS on 2016/12/7.
 */

public class UserThis {
    public static String userPhone;
    public static void setUserPhone(String phone){
        userPhone=phone;
    }
    public static void reDoUserPhone(){
        UserThis.userPhone=null;
    }
}
