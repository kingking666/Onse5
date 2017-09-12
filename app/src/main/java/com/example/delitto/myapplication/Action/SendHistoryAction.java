package com.example.delitto.myapplication.Action;

import com.example.delitto.myapplication.User.UserThis;

import httpservice.MindAssigPost;

/**
 * Created by ASUS on 2016/12/8.
 */

public class SendHistoryAction
{
    public static String flag;
    //  private static Handler mHandler = new Handler();
    public  static void GetSendHistory(){
        /*new Thread() {
            public void run() {*/
        final String info = MindAssigPost.executeHttpPost(UserThis.userPhone);
                /*mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {*/
        flag = info;
    }

    public static String getSendHistoryFlag(){
        return flag;
    }
}
