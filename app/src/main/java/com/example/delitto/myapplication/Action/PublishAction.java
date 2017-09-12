package com.example.delitto.myapplication.Action;

import com.example.delitto.myapplication.User.UserThis;

import httpservice.LoginPost;
import httpservice.assigPost;

/**
 * Created by ASUS on 2016/12/7.
 */

public class PublishAction {
    public static boolean flag;
    public  static void GetPublish(final String type,final String xxxx,final String dgxx,final String time,final String phone){
        new Thread() {
            public void run() {
                final String info= assigPost.executeHttpPost(type, xxxx, dgxx,time,phone, UserThis.userPhone);
                flag = info.equals("putassig is success!!!!");
            }
        }.start();
    }
    public static boolean getPublishFlag(){
        return flag;
    }
}
