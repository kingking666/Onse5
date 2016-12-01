package com.example.delitto.myapplication;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by pokedo on 2016/11/30.
 */

//导入这句
//import android.support.v7.preference.PreferenceFragmentCompat;
public class SettingFragment extends PreferenceFragmentCompat{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_general);
    }
}
