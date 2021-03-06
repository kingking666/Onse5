package com.example.delitto.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.delitto.myapplication.Listener.HttpCallbackListener;
import com.example.delitto.myapplication.Bean.MainListData;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;
import com.example.delitto.myapplication.decoration.TitleItemDecoration;
import com.example.delitto.myapplication.detail.detailActivity;
import com.example.delitto.myapplication.other.WrapContentLinearLayoutManager;
import com.example.delitto.myapplication.util.HttpUtil;
import com.example.delitto.myapplication.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * Created by pokedo on 2016/11/18.
 */

public class MainListFragment extends Fragment {
    public final static int LOAD_SUCCESS = -1;
    public final static int NETWORK_ERROE = -2;
    public final static int CONNECT_ERROR = -3;

    private RecyclerView recyclerView;

    private ArrayList<MainListData> list_data;

    private FloatingActionButton floatingActionButton;

    private SwipeRefreshLayout refreshLayout;

    private WrapContentLinearLayoutManager layoutManager;

    //数据适配器 MyAdapter
    private MainListAdapter adapter;

    private Gson gson = new Gson();

    private int currentPage;

    private int totalPage;

    private Context mContext;

    private ArrayList<MainListData> list;

    //本地广播监听
    private LocalReceiver localReceiver;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;

    private int returncode;

    private int begin;
    private int end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.floatingaction_button);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.require_recycler_view);

        //保存任务列表的数组
        list_data = new ArrayList<MainListData>();

        mContext = MainListFragment.this.getContext();

        //RecyclerView控件
        layoutManager = new WrapContentLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //初始化refresh
        inirefresh();

        //首次初始化fragment时候刷新一次数据
        load(true);
        currentPage = 1;

        //绘制title   decoration
        recyclerView.addItemDecoration(new TitleItemDecoration(this.getContext(), list_data));
//        绘制item间隔
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST));

        //注册广播
        registerBroadcast();

//        deleteItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myAdapter.deleteItem();
//                linearLayoutManager.scrollToPosition(0);
//            }
//        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //当上拉刷新时
            @Override
            public void onRefresh() {
                load(true);
            }
        });

        //fab根据页面滑动 隐藏和显示
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Log.d("~newState", "没动" + newState);
                    // 获取最后一个完全显示的itemposition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();

                    // 判断是否滚动到底部并且是向下滑动
//                    Log.d("~isSlidingToLast", "isSlidingToLast" + isSlidingToLast);
//                    Log.d("~lastVisibleItem", "lastVisibleItem:" + lastVisibleItem);
//                    Log.d("~totalItemCount", "totalItemCount:" + totalItemCount);
//                    Log.d("~底部", "底部" + (lastVisibleItem == (totalItemCount - 1)));
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {

                        //滚动到最后一页底部时，提示没有更多数据
                        if (currentPage == totalPage)
                            Toast.makeText(mContext, "没有更多了!", Toast.LENGTH_SHORT).show();
                            //当前页！=总页数时，继续加载
                        else
                            load(false);
                    }
                    //FIXME 有时候会出现totalItemCount已加载而lastVisibleItem未加载的情况，估计为adapter未通知recyclerview更新
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
                //上下隐藏fab
                if (dy > 0) {
                    floatingActionButton.hide();
                } else if (dy < 0) {
                    floatingActionButton.show();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当为"刷新"fab时候
                if (floatingActionButton.getTag().equals("refresh")) {
                    //点击fab加载
                    load(true);
                    floatingActionButton.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.rote));
                }
            }
        });
        return view;
    }


    /**
     * @param clearing true:清除list的数据，重新加载第一页的任务列表信息，并添加进list
     *                 false:查询下一任务列表的信息，并添加进list
     */
    public void load(final boolean clearing) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected void onPreExecute() {
                Animation anil = AnimationUtils.loadAnimation(mContext,R.anim.rote);
                floatingActionButton.startAnimation(anil);
                //当刷新时，当前页为1，处于刷新状态
                if (clearing) {
                    currentPage = 1;
                    refreshLayout.setRefreshing(true);
                } //加载更多时，page++
                else {
                    currentPage++;
                }
            }

            @Override
            protected Integer doInBackground(Void... params) {
                if (NetworkState.networkConnected(mContext)) {

                    //指定每次获取任务条数
                    int per = 8;
                    //TODO count:获取数据库总用户发布任务数量
                    //TODO getcount():返回所有任务列表总条数
                    int count = 9;
//                    if (getCount() >= 0)
//                        count = getCount();
//                    else
//                        return CONNECT_ERROR;
                    totalPage = (int) Math.ceil((double) count / per);
                    Log.d("~total", totalPage + "");

                    //查询当前页数 至 下一页数的 任务列表
                    if (currentPage != totalPage ) {
                        begin = (currentPage - 1) * per;
                        end = currentPage * per-1;
                    } else {
                        begin = (currentPage-1) * per;
                        end = count-1;
                    }
                    Log.d("~current",currentPage+"");
                    Log.d("~begin",begin+"");
                    Log.d("~end",end+"");

                    //TODO 编写url获取指定条数的任务列表，返回查询的url
                    String url = selectUrl(begin, end);
                    //工具类HttpUtil，可直接解析url
                    HttpUtil.sendHttpRequest("http://10.0.2.2/get_data.json", new HttpCallbackListener() {
                                @Override
                                public int onFinish(String response) {
                                    //respone解析为list<MainListData>，遍历list,将每个元素添加到list_data
                                    try {
                                        //解析json
                                        list = gson.fromJson(response, new
                                                TypeToken<ArrayList<MainListData>>() {
                                                }.getType());
                                        Log.d("~response", response);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("~exception", "exception");
                                        return returncode = CONNECT_ERROR;
                                    }
                                    Log.d("~LOAD_SUCCESS", "LOAD_SUCCESS");
                                    return returncode = LOAD_SUCCESS;
                                }

                                @Override
                                public int onError(Exception e) {
                                    return CONNECT_ERROR;
                                }
                            }
                    );
                    return returncode;
                } else {
                    return NETWORK_ERROE;
                }
                //提示missing return method
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
                Log.d("~code", code + "");
                switch (code) {
                    case LOAD_SUCCESS:
                        //每次刷新都清除list_data的数据项，重新加载解析的元素
                        if (clearing) {
                            list_data.clear();
                        }
                        //遍历json数组
                        addJson(begin, end);
                        if (adapter == null) {
                            adapter = new MainListAdapter(MainListFragment.this, list_data);
                            adapter.setOnItemClickListener(new MainListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //启动活动
                                    Intent intent = new Intent(mContext, detailActivity.class);
                                    intent.putExtra("type", list_data.get(position).getType());
                                    intent.putExtra("content", list_data.get(position).getContent());
                                    intent.putExtra("time", list_data.get(position).getTime());
                                    intent.putExtra("username", list_data.get(position).getUsername());
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        } else {
                            //通知recyclerview更新数据
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case CONNECT_ERROR:
                        Toast.makeText(mContext, "加载失败!", Toast.LENGTH_SHORT).show();
                        break;
                    case NETWORK_ERROE:
                        Toast.makeText(mContext, "当前没有网络连接!", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
                floatingActionButton.clearAnimation();
            }
        }.execute();
    }


    //TODO 返回所有任务列表总条数，返回一个数值
    public int getCount() {
//        HttpUtil.sendHttpRequest("url", new HttpCallbackListener() {
//            @Override
//            public int onFinish(String response) {
//            public int onFinish(String response) {
//                //解析成int类型
////                int count = gson.fromJson(response, Integer);
//                int count = 30;
//                return count;
//            }
//            @Override
//            public int onError(Exception e) {
//                return CONNECT_ERROR;
//            }
//        });
        return -1;
    }

    //TODO 编写url获取指定条数的任务列表，返回查询的url
    public String selectUrl(int begin, int end) {
        return "url" + "begin to end";
    }

    //从begin到end部分遍历数组
    public void addJson(int begin, int end) {
        for (int i = begin; i <= end; i++){
            MainListData item =list.get(i);
            list_data.add(item);
        }
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(localReceiver);
        super.onDestroy();
    }

    //注册本地广播
    public void registerBroadcast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.delitto.myapplication.TASK");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    //接收广播并重写实现方法
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //从detail_activity发送过来的本地广播
            if (intent.getStringExtra("type").equals("detail_task"))
                load(true);
            else if (intent.getStringExtra("type").equals("send_task"))
                load(true);
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






