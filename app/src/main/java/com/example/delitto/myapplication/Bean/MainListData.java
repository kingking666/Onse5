package com.example.delitto.myapplication.Bean;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pokedo on 2016/11/14.
 */

public class MainListData {
    public int resourceid;
    public String type;
    public String content;
    public String time;
    public String date;
    public String username;

    public MainListData(int resourceid, String type, String content, String time) {
        this.resourceid = resourceid;
        this.type = type;
        this.content = content;
        this.time = time;
        this.date = date;
        this.username =username;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
