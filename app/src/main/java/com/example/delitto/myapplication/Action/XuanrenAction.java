package com.example.delitto.myapplication.Action;

import httpservice.XuanRenPost;

/**
 * Created by ASUS on 2016/12/9.
 */

public class XuanrenAction
{
    public static boolean flag;
    public  static void GetXuanreninfo(final  String assigSUserID,final String assigID){
        new Thread() {
            public void run() {
                final String info = XuanRenPost.executeHttpPost(assigSUserID,assigID);
                flag = info.equals("xuanren is success!!!!");
            }
        }.start();
    }
    public static boolean getXuanrenFlag(){
        return flag;
    }
}
