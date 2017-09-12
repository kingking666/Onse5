package com.example.delitto.myapplication.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by pokedo on 2016/11/18.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
