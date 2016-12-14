package com.example.delitto.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.*;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.delitto.myapplication.getHistory.getHistoryActivity;
import com.example.delitto.myapplication.profile.ProfileActivity;
import com.example.delitto.myapplication.sendHistory.sendHistoryActivity;
import com.example.delitto.myapplication.setting.SettingActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton floatingActionButton;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private BottomNavigationBar bottomNavigationBar;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<Fragment> fragmentArrayList;
    private ViewPager viewPager;
    private static int switchPosition;

    //本地广播监听
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化变量
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingaction_button);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //注册本地广播
        registerBroadcast();

        //设置toolbar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        //DrwaerLayout动画效果
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //fab点击事件
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //navifationView设置点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Log.d("~start","start");
                switch (item.getItemId()) {
                    case R.id.drawer_send: {
                        Intent intent = new Intent(MainActivity.this,sendHistoryActivity.class);
                        //TODO  获取当前的用户id，在下一个activity中直接利用该id查询记录
                        intent.putExtra("userid","id");
                        startActivity(intent);
                    }
                    break;
                    case R.id.drawer_get: {
                        Intent intent = new Intent(MainActivity.this,getHistoryActivity.class);
                        //TODO 获取当前的用户id，在下一个activity中直接利用该id查询记录
                        intent.putExtra("userid","id");
                        startActivity(intent);
                    }
                    break;
                    case R.id.nav_setting: {
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case R.id.nav_profile:
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        //BottomNavigation
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_store_black_24dp, "列表"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_add_circle_black_24dp, "发布"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_chat_bubble_black_24dp, "消息")
                .setBadgeItem(new BadgeItem().setText("5").setTextColor("#ffffff")));
        bottomNavigationBar.initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

        //ViewPager
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new MainListFragment());
        fragmentArrayList.add(new TaskFragment());
        fragmentArrayList.add(new MessageFragment());
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        //增加viewpager的缓存页面，防止被销毁
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("~Scrolled", "+(" + position + ")");
            }

            //将viewpager移动页面后的整体UI变化进行处理
            @Override
            public void onPageSelected(int position) {
                switchPosition = position;
//                Log.d("~selected", "+(" + position + ")");
                bottomNavigationBar.clearAll();
                bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_store_black_24dp, "列表"));
                bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_add_circle_black_24dp, "发布"));
                bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_chat_bubble_black_24dp, "消息")
                        .setBadgeItem(new BadgeItem().setText("5").setTextColor("#ffffff")));
                bottomNavigationBar.setFirstSelectedPosition(position);
                bottomNavigationBar.initialise();
                bottomNavigationBar.show();
                //重新调用onCreateOptionsMenu()方法重新绘制toolbar
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("~statechanged", "+(" + state + ")");
            }
        });
    }

    //每次viewpager变变页数0,1,2后重新绘制toolbar，fab也要动态改变
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        switch (switchPosition) {
            case 0:
                getMenuInflater().inflate(R.menu.sort_menu, menu);
                toolbar.setTitle("万事屋");
                floatingActionButton.setImageResource(R.mipmap.ic_refresh_white_24dp);
                floatingActionButton.show();
                //settag特殊标识，方便各点击事件处理
                floatingActionButton.setTag("refresh");
                break;
            case 1:
                //在fragment中生成菜单，并监听事件,解耦处理
                getMenuInflater().inflate(R.menu.publish_task_menu, menu);
                toolbar.setTitle("发布任务");
                floatingActionButton.hide();
                break;
            case 2:
                getMenuInflater().inflate(R.menu.more_menu, menu);
                toolbar.setTitle("私信");
                floatingActionButton.setImageResource(R.mipmap.ic_add_white_24dp);
                floatingActionButton.show();
                floatingActionButton.setTag("add");
                break;
        }
        return true;
    }

    //Toolbar item选项设置点击监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.item_change_theme:
                return true;
            case R.id.item_seeting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //注册本地广播
    public void registerBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.delitto.myapplication.MAINACT");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    //接收广播并重写实现方法
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("type").equals("change_page"))
                //TODO 当发送完成任务后，如果同时刷新和更改页面，TitleDecoration会抛出异常，故延迟一秒执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(0);
                        myFragmentPagerAdapter.notifyDataSetChanged();
                    }
                }, 1000);
        }
    }

    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(localReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

}