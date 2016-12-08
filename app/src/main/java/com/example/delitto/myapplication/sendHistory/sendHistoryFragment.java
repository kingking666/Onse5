package com.example.delitto.myapplication.sendHistory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.delitto.myapplication.Bean.SendHistoryData;
import com.example.delitto.myapplication.Listener.HttpCallbackListener;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;
import com.example.delitto.myapplication.other.WrapContentLinearLayoutManager;
import com.example.delitto.myapplication.select.selectActivity;
import com.example.delitto.myapplication.util.HttpUtil;
import com.example.delitto.myapplication.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by pokedo on 2016/12/7.
 */

public class sendHistoryFragment extends Fragment {

    private Toolbar _toolbar;
    private ArrayList<SendHistoryData> _listdata;
    private RecyclerView _recyclerView;
    private sendHistoryAdapter _adapter;
    private WrapContentLinearLayoutManager _layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private Context mContext;
    private Gson gson;
    private AlertDialog.Builder _dialog;
    public final static int LOAD_SUCCESS = -1;
    public final static int NETWORK_ERROE = -2;
    public final static int CONNECT_ERROR = -3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_history_fragment,container,false);

        _listdata = new ArrayList<>();

        mContext = sendHistoryFragment.this.getContext();

        gson = new Gson();

        _recyclerView = (RecyclerView) view.findViewById(R.id.send_historyfragment);
        _toolbar = (Toolbar) view.findViewById(R.id.toolbar1);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        _layoutManager = new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        _recyclerView.setLayoutManager(_layoutManager);

        //绘制item间隔
        _recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        //初始化refresh
        inirefresh();
        load();

        //只能监听手势的刷新事件
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
        return view;
    }

    public void load() {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected void onPreExecute() {
                refreshLayout.setRefreshing(true);
            }

            @Override
            protected Integer doInBackground(Void... params) {
                if (NetworkState.networkConnected(mContext)) {

                    //TODO 编写url获取用户所有已发布任务的历史记录
                    String url = "url";
                    //工具类HttpUtil，可直接解析url
                    HttpUtil.sendHttpRequest("http://10.0.2.2/send_history.json", new HttpCallbackListener() {
                                @Override
                                public int onFinish(String response) {
                                    //respone解析为list<MainListData>，遍历list,将每个元素添加到_listdata
                                    try {
                                        _listdata.clear();
                                        //遍历json数组
                                        ArrayList<SendHistoryData> list = gson.fromJson(response, new
                                                TypeToken<ArrayList<SendHistoryData>>() {
                                                }.getType());
                                        for (SendHistoryData item : list) {
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
                            _adapter = new sendHistoryAdapter(mContext, _listdata);
                            _adapter.setOnItemClickListener(new sendHistoryAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //TODO 启动活动，对应选人页面正在编写
                                    Intent intent = new Intent(mContext,selectActivity.class);
//                                  携带当前点击任务的id,可利用查询当前任务的接单的用户列表
                                    intent.putExtra("taskid",_listdata.get(position).getTaskid());
//                                    intent.putExtra("state",_listdata.get(position).getstate());
                                    startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {
                                }

                                @Override
                                public void onButtonClick(Button button, int _position) {
                                    confirming();
                                }
                            });
                            _recyclerView.setAdapter(_adapter);
                        }
                         /*调用adapter.notifyDataSetChanged与listView.setAdapter函数都会引起界面重绘，
                         区别是前者会保留原有位置、数据信息，后者是回到初始状态。
                        TODO 如果用adapter.notifydatasetchange(),确认按钮有时候会显示错位*/
                        else {
                            _recyclerView.setAdapter(_adapter);
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

    public void confirming() {
        _dialog = new AlertDialog.Builder(mContext);
        _dialog.setTitle("确定");
        _dialog.setMessage("是否确认完成任务？");
        _dialog.setCancelable(true);
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                final ProgressDialog _progressDialog = new ProgressDialog(mContext);   //弹出进度条
                _progressDialog.setTitle("正在确认完成");
                _progressDialog.setMessage("请稍后..");
                _progressDialog.show();

                //3秒后返回首页
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                //FIXME 这里进行上传数据处理,监听上传成功和失败的情况回调函数
//                                if(success)
                                _confirmSuccess();
//                                else
//                                _confirmFail();
                                _progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });
        _dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
            }
        });
        _dialog.show();
    }

    public void _confirmSuccess() {
        _dialog = new AlertDialog.Builder(mContext);
        _dialog.setMessage("已确认完成任务");
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                //确认后刷新数据
                load();
            }
        });
        _dialog.show();
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
