package com.example.delitto.myapplication.Action;

import com.example.delitto.myapplication.User.UserThis;

import httpservice.YiXiangPost;

/**
 * Created by ASUS on 2016/12/8.
 */

public class detailAction
{
    public static boolean flag;
    public  static void Getdetailinfo(final String assigID, final String assigM, final String uT){
        new Thread() {
            public void run() {
                final String info = YiXiangPost.executeHttpPost(assigID,assigM,uT,UserThis.userPhone);
                if(info.equals("YiXiang is success!!!!")) {
                    flag = true;
                }
                else if(info.equals("YiXiang is fail!!!")){
                    flag = false;
                }
            }
        }.start();
    }
    public static boolean getdetailinfoFlag(){
        return flag;
    }
}
