package com.example.delitto.myapplication.Tools;

import android.app.Activity;
import android.content.Intent;

import com.example.delitto.myapplication.R;

import java.lang.reflect.Field;

/**
 * Created by Delitto on 2016/12/7.
 */

public class AppTools {
    public static Intent GotoActivity(Activity activity, Class cls){
        Intent intent = new Intent(activity,cls);
        return intent;
    }
    public static int getPhotoResourseId(){
        String logoName = new String("logo");
        int x=1+(int)(Math.random()*20);
        logoName = logoName+x;
            Class mipmap = R.mipmap.class;
            try {
                Field field = mipmap.getField(logoName);
                int resId = field.getInt(logoName);
                return resId;
            } catch (NoSuchFieldException e) {//如果没有找到imageName,将会返回logo1;
                return R.mipmap.logo1;
            } catch (IllegalAccessException e) {
                return R.mipmap.logo3;
            } catch (NullPointerException e) {
                return R.mipmap.logo2;
            }
        }
    }

