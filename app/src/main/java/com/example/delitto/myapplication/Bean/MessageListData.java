package com.example.delitto.myapplication.Bean;

/**
 * Created by pokedo on 2016/11/21.
 */

public class MessageListData {
    public int resourceid;
    public String title;
    public String content;
    public String time;

    public MessageListData(int resourceid, String title, String content, String time) {
        this.resourceid = resourceid;
        this.title = title;
        this.content = content;
        this.time = time;

    }

}
