package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class GetHistoryData {
    public int resouceid;
    public String assigT;
    public String assigTi;
    public String uN;
    public int uID;
    public int assigID;
    public String assigRM;
    public String assigS;


    public GetHistoryData(int resouceid, String type, String date, String username, int userid,int assigID,String assigRM,String assigS) {
        this.uID = userid;
        this.resouceid = resouceid;
        this.assigT = type;
        this.assigTi = date;
        this.uN = username;
        this.assigID=assigID;
        this.assigRM=assigRM;
        this.assigS=assigS;
    }

    public int get_userid() {
        return uID;
    }

    public void set_userid(int userid) {
        this.uID = userid;
    }

    public int get_resouceid() {
        return resouceid;
    }

    public String get_type() {
        return assigT;
    }

    public String get_date() {
        return assigTi;
    }

    public String get_username() {
        return uN;
    }

    public int get_assigID(){return assigID;}

    public String get_assigRM(){return assigRM;}

    public String get_assigS(){return  assigS;}
}
