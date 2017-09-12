package com.example.delitto.myapplication.Action;

import httpservice.FindYixiangRenPost;
import httpservice.XuanRenPost;

/**
 * Created by ASUS on 2016/12/8.
 */

public class YiXiangAction
{
    public static String flag;
    //  private static Handler mHandler = new Handler();
    public  static void GetYiXiang(final String assigID){
        /*new Thread() {
            public void run() {*/
        final String info = FindYixiangRenPost.executeHttpPost(assigID);
                /*mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {*/
        flag = info;
    }

    public static String getYiXiangFlag(){
        return flag;
    }
}
