package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SendHistoryData {
    public int _resouceid;
    public String assigT;
    public String assigTi;
    public String assigS;
    public int uID;
    public int assigID;
    public String assigRM;

    //保存数据
    public SendHistoryData(int _resouceid, String type, String date, String state,int userid,int Taskid,String assigRM) {
        this._resouceid = _resouceid;
        this.assigT = type;
        this.assigTi = date;
        this.assigS = state;
        this.uID = userid;
        this.assigID = Taskid;
        this.assigRM=assigRM;
    }
    public void setUserid(int uID){this.uID=uID;}
    public int getUserid() {
        return uID;
    }
    public void set_resouceid(int _resouceid){this._resouceid=_resouceid;}
    public int get_resouceid() {
        return _resouceid;
    }
    public void settype(String assigT){this.assigT=assigT;}
    public String gettype() {
        return assigT;
    }
    public void setdate(String assigTi){this.assigTi=assigTi;}
    public String getdate() {
        return assigTi;
    }
    public void setstate(String assigS){this.assigS=assigS;}
    public String getstate() {
        return assigS;
    }
    public void setTaskid(int assigID){this.assigID=assigID;}
    public int getTaskid(){ return assigID; }

    public String getContent() {
        return assigRM;
    }

    public void setContent(String assigRM) {
        this.assigRM = assigRM;
    }
}
