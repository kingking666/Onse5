package com.example.delitto.myapplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.delitto.myapplication.Tools.ECTools;
import com.example.delitto.myapplication.runtimepermissions.PermissionsResultAction;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import com.hyphenate.easeui.controller.EaseUI;

import java.util.Iterator;
import java.util.List;
import com.example.delitto.myapplication.runtimepermissions.PermissionsManager;


/**
 * Created by Delitto on 2016/12/4.
 */

public class ECApplication extends Application {
    private static final String TAG = "ECApplication";
    @Override
            public void onCreate(){

        super.onCreate();

        initECeasemob();

    }
    public void initECeasemob(){
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);

        //初始化
        EMClient.getInstance().init(ECApplication.this, options);
        EaseUI.getInstance().init(ECApplication.this,options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
   //     EMClient.getInstance().setDebugMode(true);
    }



}
