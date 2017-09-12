package com.example.delitto.myapplication.Action;

import com.example.delitto.myapplication.User.UserThis;

import httpservice.MindGetAssigPost;

/**
 * Created by ASUS on 2016/12/8.
 */

public class getHistoryAction
{
    public static String flag;
    //  private static Handler mHandler = new Handler();
    public  static void GetHistory(){
        /*new Thread() {
            public void run() {*/
        final String info = MindGetAssigPost.executeHttpPost(UserThis.userPhone);
                /*mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {*/
        flag = info;
    }

    public static String getHistoryFlag(){
        return flag;
    }
}
