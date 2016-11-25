package com.example.delitto.myapplication.Bean;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pokedo on 2016/11/14.
 */

public class MainListData {
    public int resourceid;
    public String[]title;
    public String content;
    public String time;

    public MainListData(int resourceid, String[]title, String content, String time) {
        this.resourceid = resourceid;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public void addItem(){


    }
}
