package com.example.delitto.myapplication.Action;

import android.os.Handler;

import httpservice.AssigCountPost;

/**
 * Created by ASUS on 2016/12/8.
 */

public class AssigCountAction {
    public static int flag;

    public  static void GetAssigCount(){

                final int info = Integer.parseInt(AssigCountPost.executeHttpPost());
                flag = info;


    }
    public static int getAssigCountFlag(){
        return flag;
    }
}