package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SendHistoryData {
    public int resouceid;
    public String type;
    public String date;
    public String state;
    public int userid;
    public int taskid;

    //保存数据
    public SendHistoryData(int resouceid, String type, String date, String state,int userid,int taskid) {
        this.resouceid = resouceid;
        this.type = type;
        this.date = date;
        this.state = state;
        this.userid = userid;
        this.taskid = taskid;
    }

    //得到当前任务id，可利用查询接单的用户列表
    public int getTaskid() {
        return taskid;
    }

    //得到当前用户id，可利用查询当前用户发布任务列表
    public int getUserid() {
        return userid;
    }

    public int getresouceid() {
        return resouceid;
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
