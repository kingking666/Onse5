package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class GetHistoryData {
    public int resouceid;
    public String type;
    public String date;
    public String username;
    public int userid;

    public GetHistoryData(int resouceid, String type, String date, String username, int userid) {
        this.userid = userid;
        this.resouceid = resouceid;
        this.type = type;
        this.date = date;
        this.username = username;
    }

    public int get_userid() {
        return userid;
    }

    public void set_userid(int userid) {
        this.userid = userid;
    }

    public int get_resouceid() {
        return resouceid;
    }

    public String get_type() {
        return type;
    }

    public String get_date() {
        return date;
    }

    public String get_username() {
        return username;
    }
}
