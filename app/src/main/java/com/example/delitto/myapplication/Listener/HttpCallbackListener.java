package com.example.delitto.myapplication.Listener;

public interface HttpCallbackListener {
    int onFinish(String response);

    int onError(Exception e);
}