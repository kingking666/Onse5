package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SendHistoryData {
    public int _resouceid;
    public String type;
    public String date;
    public String state;
    public int userid;

    //保存数据
    public SendHistoryData(int _resouceid, String type, String date, String state,int userid) {
        this._resouceid = _resouceid;
        this.type = type;
        this.date = date;
        this.state = state;
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public int get_resouceid() {
        return _resouceid;
    }

    public String gettype() {
        return type;
    }

    public String getdate() {
        return date;
    }

    public String getstate() {
        return state;
    }
}
