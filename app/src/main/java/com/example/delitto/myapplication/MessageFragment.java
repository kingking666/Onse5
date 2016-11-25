package com.example.delitto.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.delitto.myapplication.Bean.MessageListData;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by pokedo on 2016/11/18.
 */

public class MessageFragment extends Fragment {

    private ArrayList<MessageListData> list_data;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);

        list_data = new ArrayList<>();
        iniArrayList();

        //RecyclerView控件
        recyclerView = (RecyclerView) view.findViewById(R.id.message_recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //数据适配器 MyAdapter
        final MessageListAdapter messageListAdapter = new MessageListAdapter(this.getContext(), list_data);
        recyclerView.setAdapter(messageListAdapter);

        //绘制item间隔
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST));

//        addItem = (Button) findViewById(R.id.add_item);
//        deleteItem = (Button) findViewById(R.id.delete_item);
//        addItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myAdapter.addItem(R.mipmap.ic_launcher, "new Item");
//                linearLayoutManager.scrollToPosition(0);
//            }
//        });
//        deleteItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myAdapter.deleteItem();
//                linearLayoutManager.scrollToPosition(0);
//            }
//        });

        //myAdapter设置自定义接口监听者,重写OnItemClickLIstener的方法
        messageListAdapter.setOnItemClickListener(new MessageListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "You click item" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(), "You click item" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void iniArrayList() {
        list_data.add(new MessageListData(R.mipmap.logo, "Onse消息助手", "亲爱的xxx,你好。\n你于2016年10月28日 12:54分所发布的任务...", "刚刚"));
        list_data.add(new MessageListData(R.mipmap.logo, "Onse消息助手", "亲爱的xxx,你好。\n你于2016年10月28日 12:54分所发布的任务...", "刚刚"));
        list_data.add(new MessageListData(R.mipmap.logo, "Onse消息助手", "亲爱的xxx,你好。\n你于2016年10月28日 12:54分所发布的任务...", "刚刚"));
        list_data.add(new MessageListData(R.mipmap.logo, "Onse消息助手", "亲爱的xxx,你好。\n你于2016年10月28日 12:54分所发布的任务...", "刚刚"));

    }

}
