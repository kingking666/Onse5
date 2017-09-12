package com.example.delitto.myapplication.Action;

import android.os.Handler;

import httpservice.DisplayAssigPost;

/**
 * Created by ASUS on 2016/12/8.
 */

public class DisplayAssigAction {
    public static String flag;
  //  private static Handler mHandler = new Handler();
    public  static void GetDisplayAssig(){
        /*new Thread() {
            public void run() {*/
                final String info = DisplayAssigPost.executeHttpPost("可接");

                /*mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {*/
                        flag = info;
                    }

    public static String getDisplayAssigFlag(){
        return flag;
    }
}
