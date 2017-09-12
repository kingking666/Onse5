package com.example.delitto.myapplication.Action;

import httpservice.FinishassigPost;

/**
 * Created by ASUS on 2016/12/9.
 */

public class FinishAssigAction
{
    public static boolean flag;
    public  static void GetFinshAssiginfo(final String assigID){
        new Thread() {
            public void run() {
                final String info = FinishassigPost.executeHttpPost(assigID);
                flag = info.equals("Mission complete!!!!");
            }
        }.start();
    }
    public static boolean getFinshAssigFlag(){
        return flag;
    }
}
