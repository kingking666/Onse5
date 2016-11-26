package com.example.delitto.myapplication;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;


import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.delitto.myapplication.R;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化变量
        initalize();


        //设置bar
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        //DrwaerLayout动画效果
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //navifationView设置点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_money: {
                    }
                    ;
                    case R.id.drawer_publish: {
                    }
                    ;
                    case R.id.drawer_get: {
                    }
                    ;
                    case R.id.seeting: {
                    }
                    ;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        //BottomNavigation
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_store_black_24dp, "列表"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_add_circle_black_24dp, "发布"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_chat_bubble_black_24dp, "消息")
                .setBadgeItem(new BadgeItem().setText("5").setTextColor("#f000ff")));
//        bottomNavigationBar.setFab(floatingActionButton);
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
                        .setBadgeItem(new BadgeItem().setText("5").setTextColor("#f000ff")));
                bottomNavigationBar.setFirstSelectedPosition(position);
                bottomNavigationBar.initialise();
                bottomNavigationBar.show();
                //重新调用onCreateOptionsMenu()方法重新绘制toolbar
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("~statechanged", "+(" + state + ")");
            }
        });
    }

    public void initalize() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingaction_button);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

    }

    //每次viewpager变动后重新绘制toolbar，fab也要动态改变
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        switch (switchPosition) {
            case 0:
                getMenuInflater().inflate(R.menu.sort_setting_menu, menu);
                toolbar.setTitle("万事屋");
                floatingActionButton.setImageResource(R.mipmap.ic_refresh_white_24dp);
                floatingActionButton.show();

                break;
            case 1:
                getMenuInflater().inflate(R.menu.publish_task_menu, menu);
                toolbar.setTitle("发布任务");
                floatingActionButton.setVisibility(View.INVISIBLE);
                break;
            case 2:
                getMenuInflater().inflate(R.menu.seeting_menu, menu);
                toolbar.setTitle("私信");
                floatingActionButton.setImageResource(R.mipmap.ic_add_white_24dp);
                floatingActionButton.show();
                break;
        }
        return true;
    }

}