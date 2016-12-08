package com.example.delitto.myapplication.getHistory;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.delitto.myapplication.Bean.GetHistoryData;
import com.example.delitto.myapplication.Listener.HttpCallbackListener;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;
import com.example.delitto.myapplication.other.WrapContentLinearLayoutManager;
import com.example.delitto.myapplication.util.HttpUtil;
import com.example.delitto.myapplication.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/5.
 */

public class getHistoryActivity extends AppCompatActivity {
    private Toolbar _toolbar;
    private ArrayList<GetHistoryData> _listdata;
    private RecyclerView _recyclerView;
    private getHistoryAdapter _adapter;
    private WrapContentLinearLayoutManager _layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private Context mContext;
    private Gson gson;
    public final static int LOAD_SUCCESS = -1;
    public final static int NETWORK_ERROE = -2;
    public final static int CONNECT_ERROR = -3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_history);

        _listdata = new ArrayList<>();

        mContext = getHistoryActivity.this;

        gson = new Gson();

        _recyclerView = (RecyclerView) findViewById(R.id.get_historyfragment);
        _toolbar = (Toolbar) findViewById(R.id.toolbar1);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        _layoutManager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _recyclerView.setLayoutManager(_layoutManager);

        _recyclerView.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));

        //设置toolbar
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //初始化refresh
        inirefresh();
        load();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //当上拉刷新时
            @Override
            public void onRefresh() {
                load();
            }
        });

        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = _layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = _layoutManager.getItemCount();
                    // 判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        Toast.makeText(mContext, "没有更多了!", Toast.LENGTH_SHORT).show();
                    }
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }
        });
    }

    public void load() {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected Integer doInBackground(Void... params) {
                if (NetworkState.networkConnected(mContext)) {

                    //利用当前自己用户的id查询被选择作为接单人id的任务
                    //TODO 编写url获取用户所有已领取任务的历史记录
                    String url = "url";
                    //工具类HttpUtil，可直接解析url
                    HttpUtil.sendHttpRequest("http://10.0.2.2/get_history.json", new HttpCallbackListener() {
                                @Override
                                public int onFinish(String response) {
                                    //respone解析为list<MainListData>，遍历list,将每个元素添加到_listdata
                                    try {
                                        _listdata.clear();
                                        //遍历json数组
                                        ArrayList<GetHistoryData> list = gson.fromJson(response, new
                                                TypeToken<ArrayList<GetHistoryData>>() {
                                                }.getType());
                                        for (GetHistoryData item : list) {
                                            _listdata.add(item);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return CONNECT_ERROR;
                                    }
                                    return LOAD_SUCCESS;
                                }

                                @Override
                                public int onError(Exception e) {
                                    return CONNECT_ERROR;
                                }
                            }
                    );
                } else {
                    return NETWORK_ERROE;
                }
                //提示missing return method
                return -1;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            /**
             * @param code LOAD_SUCCESS:成功获取数据
             *             CONNECT_ERROR:连接数据库失败
             *             NETWORK_ERROE:网络错误
             */
            @Override
            protected void onPostExecute(Integer code) {
                switch (code) {
                    case LOAD_SUCCESS:
                        if (_adapter == null) {
                            _adapter = new getHistoryAdapter(mContext, _listdata);
                            _adapter.setOnItemClickListener(new getHistoryAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //TODO 启动活动 ?
//                                    Intent intent = new Intent(mContext, detailActivity.class);
//                                    intent.putExtra("type", _listdata.get(position).getType());
//                                    intent.putExtra("content", _listdata.get(position).getContent());
//                                    intent.putExtra("time", _listdata.get(position).getTime());
//                                    intent.putExtra("username", _listdata.get(position).getUsername());
//                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {
                                }
                            });
                            _recyclerView.setAdapter(_adapter);
                        } else {
                            _adapter.notifyDataSetChanged();
                        }
                        break;
                    case CONNECT_ERROR:
                        Toast.makeText(mContext, "加载失败!", Toast.LENGTH_SHORT).show();
                        break;
                    case NETWORK_ERROE:
                        Toast.makeText(mContext, "当前没有网络连接!", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }
        }.execute();
    }


    //设置对toolbar的按钮监听
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void inirefresh() {
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.primary_dark, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
    }


}
