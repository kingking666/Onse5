package com.example.delitto.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.delitto.myapplication.MainListAdapter;
import com.example.delitto.myapplication.Bean.MainListData;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;
import com.example.delitto.myapplication.decoration.TitleItemDecoration;

import java.util.ArrayList;


/**
 * Created by pokedo on 2016/11/18.
 */

public class MainListFragment extends Fragment {

    private RecyclerView recyclerView;

    private ArrayList<MainListData> list_data;

    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingaction_button);

        list_data = new ArrayList<MainListData>();
        iniArrayList();

        //RecyclerView控件
        recyclerView = (RecyclerView) view.findViewById(R.id.require_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //数据适配器 MyAdapter
        final MainListAdapter requireListAdapter = new MainListAdapter(this,list_data);
        recyclerView.setAdapter(requireListAdapter);


        //绘制title   decoration
        recyclerView.addItemDecoration(new TitleItemDecoration(this.getContext(), list_data));
        //绘制item间隔
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST));

//        addItem = (Button) findViewById(R.id.add_item);
//        deleteItem = (Button) findViewById(R.id.delete_item);
//        addItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myAdapter.addItem(R.mipmap.logo, "new Item");
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

        //fab根据页面滑动 隐藏和显示
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0) {
                    floatingActionButton.hide();
                }
                else if(dy<0){
                    floatingActionButton.show();
                }
            }
        });

        //myAdapter设置自定义接口监听者,重写OnItemClickLIstener的方法
        requireListAdapter.setOnItemClickListener(new MainListAdapter.OnItemClickListener() {

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

    //初始化数据
    public void iniArrayList() {
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "今天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "明天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));
        list_data.add(new MainListData(R.mipmap.logo, new String[]{"类型", "金额"}, "谁在校门口帮我拿一个快递...", "后天"));

    }
}






