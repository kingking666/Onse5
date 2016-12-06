package com.example.delitto.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.delitto.myapplication.Bean.SendHistoryData;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class sendHistoryActivity extends AppCompatActivity {
    private Toolbar _toolbar;
    private ArrayList<SendHistoryData> _listdata;
    private RecyclerView _recyclerView;
    private sendHistoryAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_historyfrgment);

        _listdata=new ArrayList<>();
        iniArrayList();

        _recyclerView=(RecyclerView)findViewById(R.id.send_historyfragment);
        _toolbar = (Toolbar) findViewById(R.id.toolbar1);


        _layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _recyclerView.setLayoutManager(_layoutManager);
        //数据适配器sendHistoryAdapter
        _adapter = new sendHistoryAdapter(sendHistoryActivity.this,_listdata);
        _recyclerView.setAdapter(_adapter);

        //绘制item间隔
        _recyclerView.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));

        //事件处理
        _adapter.setOnItemClickListener(new sendHistoryAdapter.OnItemClickListener(){
            public void onItemClick(View _view,int _position){
               Toast.makeText(getApplicationContext(),"You click item"+_position,Toast.LENGTH_SHORT).show();
            }
            public void onItemLongClick(View _view,int _position){
                Toast.makeText(getApplicationContext(),"You click item"+_position,Toast.LENGTH_SHORT).show();
            }
        });

        //设置toolbar
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //设置对toolbar的按钮监听
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //初始化数据
    public void iniArrayList() {
        _listdata.add(new SendHistoryData(R.mipmap.logo, "快递", "南苑", "刚刚", "任务状态"));
        _listdata.add(new SendHistoryData(R.mipmap.logo, "其他", "北苑", "刚刚", "任务状态"));
        _listdata.add(new SendHistoryData(R.mipmap.logo, "其他", "北苑", "刚刚", "任务状态"));
    }
}