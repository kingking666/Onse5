package com.example.delitto.myapplication.Bean;

import android.widget.Button;

/**
 * Created by Administrator on 2016/12/6.
 */

public class SelectData {
    public int resourceId;
    public String username;
    public String money;
    public String time;
    public String state;
    public int selecter_intent_id;
    public int selecter_id;

    public SelectData(int resourceId,String username,String money,String time,int selecter_intent_id,int selecter_id){
        this.resourceId=resourceId;
        this.username=username;
        this.money=money;
        this.time=time;
        this.selecter_id = selecter_id;
        this.selecter_intent_id = selecter_intent_id;
    }

    public int getresourceId() {
        return resourceId;
    }

    public String getusername() {
        return username;
    }

    public String gettime() {
        return time;
    }

    public String getmoney() {
        return money;
    }

    public String getState() {
        return state;
    }

    public int getSelecter_id() {
        return selecter_id;
    }

    public int getSelecter_intent_id() {
        return selecter_intent_id;
    }
}
