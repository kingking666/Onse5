package com.example.delitto.myapplication.Action;

import com.example.delitto.myapplication.User.UserThis;

import httpservice.AssigCountPost;
import httpservice.addInformationPost;

/**
 * Created by ASUS on 2016/12/7.
 */

public class RegistinfoAction
{
    public static boolean flag;
    public  static void GetRegistinfo(final String uname,final String sex,final String signature){
        new Thread() {
            public void run() {
                final String info = addInformationPost.executeHttpPost(uname,sex,signature,UserThis.userPhone);
                flag = info.equals("Update is success!!!!");
            }
        }.start();
    }
    public static boolean getRegistinfoFlag(){
        return flag;
    }
}
