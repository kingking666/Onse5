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

import com.example.delitto.myapplication.Bean.GetHistoryData;
import com.example.delitto.myapplication.Bean.SendHistoryData;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/5.
 */

public class getHistoryActivity extends AppCompatActivity {
    private Toolbar _toolbar;
    private ArrayList<GetHistoryData> _listdata;
    private RecyclerView _recyclerView;
    private getHistoryAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_historyfragment);

        _listdata = new ArrayList<>();
        iniArrayList();

        _recyclerView = (RecyclerView) findViewById(R.id.get_historyfragment);
        _toolbar = (Toolbar) findViewById(R.id.toolbar1);


        _layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _recyclerView.setLayoutManager(_layoutManager);
        _adapter = new getHistoryAdapter(getHistoryActivity.this, _listdata);
        _recyclerView.setAdapter(_adapter);

        _recyclerView.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));


        _adapter.setOnItemClickListener(new getHistoryAdapter.OnItemClickListener() {
            public void onItemClick(View _view, int _position) {
                Toast.makeText(getApplicationContext(), "You click item" + _position, Toast.LENGTH_SHORT).show();
            }

            public void onItemLongClick(View _view, int _position) {
                Toast.makeText(getApplicationContext(), "You click item" + _position, Toast.LENGTH_SHORT).show();
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
        _listdata.add(new GetHistoryData(R.mipmap.logo, "快递", "南苑", "刚刚", "用户昵称"));
        _listdata.add(new GetHistoryData(R.mipmap.logo, "其他", "北苑", "刚刚", "用户昵称"));
        _listdata.add(new GetHistoryData(R.mipmap.logo, "其他", "北苑", "刚刚", "用户昵称"));
    }
}