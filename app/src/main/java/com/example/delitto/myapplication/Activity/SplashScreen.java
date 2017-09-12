package com.example.delitto.myapplication.Activity;


import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.User.UserThis;
import com.example.delitto.myapplication.util.PreferenceUtils;

import java.lang.reflect.Field;

public class SplashScreen extends AppCompatActivity {
    @Override
    //通过Bundle 传输信息（暂定）
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.activity_splashscreen);


        //更改启动页面logo
        int logo_number=(int)(Math.random()*20);
        String number ="logo"+logo_number;
        ImageView img=(ImageView)findViewById(R.id.logo_splashscreen);
        img.setImageResource(getResource(number));
   //     img.invalidate();


        //已经安装的APP版本信息
        //显示版本信息
        /*
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.example.delitto.myapplication", 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }*/
        TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
        versionNumber.setText("Version " + this.getString(R.string.versionNumber));
        //显示2.9秒闪屏后进入Login页面（暂定）
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent;
                if(PreferenceUtils.getPrefInt(SplashScreen.this,"Ones5",0)==1) {
                    UserThis.setUserPhone(PreferenceUtils.getPrefString(SplashScreen.this,"Ones5User",null));
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                else{
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, 2900); //2.9秒

    }
    //通过反射根据资源名获取资源ID
    public int  getResource(String imageName){
        Class drawable = R.drawable.class;
        try {
            Field field = drawable.getField(imageName);
            int resId = field.getInt(imageName);
            return resId;
        } catch (NoSuchFieldException e) {//如果没有找到imageName,将会返回logo1;
            return R.drawable.logo1;
        } catch (IllegalAccessException e) {
            return R.drawable.logo3;
        } catch (NullPointerException e) {
            return R.drawable.logo2;
        }
     }
}

