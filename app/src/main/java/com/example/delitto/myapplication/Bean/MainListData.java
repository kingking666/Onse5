package com.example.delitto.myapplication.Bean;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pokedo on 2016/11/14.
 */

public class MainListData {
    public int resourceid;
    public Integer uID;
    public String assigCPN;
    public int assigID;
    public String assigT;
    public String assigRM;
    public String assigTi;
    public String uN;

    public MainListData(Integer uID,String assigCPN,int assigID,int resourceid, String assigT, String assigRM, String assigTi,String uN) {
        this.resourceid = resourceid;
        this.assigT = assigT;
        this.assigRM = assigRM;
        this.assigTi = assigTi;
        this.uN =uN;
        this.assigCPN=assigCPN;
        this.uID=uID;
        this.assigID=assigID;
    }
    public Integer getuID(){return  uID;}
    public void setuID(Integer uID){this.uID=uID;}
    public String getAssigCPN()
    {
        return assigCPN;
    }
    public void setAssigCPN(String assigCPN)
    {
        this.assigCPN=assigCPN;
    }
    public Integer getAssigID()
    {
        return assigID;
    }
    public void setAssigID(int assigID)
    {
        this.assigID= assigID;
    }
    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getType() {
        return assigT;
    }

    public void setType(String assigT) {
        this.assigT = assigT;
    }

    public String getContent() {
        return assigRM;
    }

    public void setContent(String assigRM) {
        this.assigRM = assigRM;
    }



    public String getDate() {
        return assigTi;
    }

    public void setDate(String date) {
        this.assigTi = date;
    }

    public void setUsername(String uN) {
        this.uN = uN;
    }

    public String getUsername() {
        return uN;
    }
}
