package com.example.delitto.myapplication.Bean;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pokedo on 2016/11/14.
 */

public class MainListData {
    public int resourceid;
    public String title;
    public String content;
    public String time;

    public MainListData(int resourceid, String title, String content, String time) {
        this.resourceid = resourceid;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void addItem() {


    }
}
