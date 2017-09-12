package com.example.delitto.myapplication.Bean;

import android.widget.Button;

/**
 * Created by Administrator on 2016/12/6.
 */

public class SelectData {
    public int resourceId;
    public String uN;
    public String assigM;
    public String uT;
    public String state;
    public int uID;
    public int selecter_intent_id;


    public SelectData(int resourceId,String username,String money,String time,int selecter_intent_id,int selecter_id){
        this.resourceId=resourceId;
        this.uN=username;
        this.assigM=money;
        this.uT=time;
        this.uID = selecter_id;
        this.selecter_intent_id = selecter_intent_id;
    }

    public int getresourceId() {
        return resourceId;
    }
    public int getSelecter_id() {
        return uID;
    }

    public String getusername() {
        return uN;
    }

    public String gettime() {
        return uT;
    }

    public String getmoney() {
        return assigM;
    }

    public String getState() {
        return state;
    }



    public int getSelecter_intent_id() {
        return selecter_intent_id;
    }
}
