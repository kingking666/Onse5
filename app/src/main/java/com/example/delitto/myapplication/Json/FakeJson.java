package com.example.delitto.myapplication.Json;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.delitto.myapplication.Activity.MainActivity;
import com.example.delitto.myapplication.Bean.MainListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by pokedo on 2017/9/15.
 */

public class FakeJson {
    private static final String TAG = "~FakeJson";

    private static String FAKE_JSON;

    public static int getFakeJsonCount() {
        ArrayList<MainListData> list = fromFakeJson();
        Log.d(TAG, "getFakeJsonCount: " + list);
        return list.size();
    }

    public static ArrayList<MainListData> fromFakeJson() {
        Gson gson = new Gson();
        ArrayList<MainListData> list = gson.fromJson(FAKE_JSON, new
                TypeToken<ArrayList<MainListData>>() {
                }.getType());
        Log.d(TAG, "fromFakeJson: ");
        return list;
    }

    public static String readJson(Context context) { //从文档中读取json
        StringBuilder str = new StringBuilder();
        try {
            AssetManager manager = context.getAssets();
            InputStream is = manager.open("Json.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bfReader = new BufferedReader(reader);
            String line;
            while ((line = bfReader.readLine()) != null) {
                str.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "readJson: false");
        }
        FAKE_JSON = str.toString();
        return FAKE_JSON;
    }

    public static String getFakeJson() {
        return FAKE_JSON;
    }

}
