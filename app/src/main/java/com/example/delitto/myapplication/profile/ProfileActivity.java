package com.example.delitto.myapplication.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.delitto.myapplication.MainListFragment;
import com.example.delitto.myapplication.MessageFragment;
import com.example.delitto.myapplication.MyFragmentPagerAdapter;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.TaskFragment;
import com.example.delitto.myapplication.getHistory.getHistoryFragment;
import com.example.delitto.myapplication.sendHistory.sendHistoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pokedo on 2016/12/7.
 */

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ArrayList<Fragment> fragmentArrayList;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.pro_viewpager);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.tab);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager(viewPager);

//        fragmentArrayList = new ArrayList<>();
//        fragmentArrayList.add(new MessageFragment());
//        fragmentArrayList.add(new TaskFragment());
//        fragmentArrayList.add(new MessageFragment());
//        myFragmentPagerAdapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(), fragmentArrayList);
//        viewPager.setAdapter(myFragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new sendHistoryFragment(), "发布");
        adapter.addFragment(new getHistoryFragment(), "领取");
        adapter.addFragment(new sendHistoryFragment(), "联系");
        //增加viewpager的缓存页面，防止被销毁
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
