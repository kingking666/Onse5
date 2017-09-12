package com.example.delitto.myapplication.select;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.delitto.myapplication.Action.FinishAssigAction;
import com.example.delitto.myapplication.Action.XuanrenAction;
import com.example.delitto.myapplication.Bean.SelectData;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.decoration.DividerItemDecoration;
import com.example.delitto.myapplication.util.NetworkState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.delitto.myapplication.Action.FinishAssigAction.GetFinshAssiginfo;
import static com.example.delitto.myapplication.Action.FinishAssigAction.getFinshAssigFlag;
import static com.example.delitto.myapplication.Action.XuanrenAction.GetXuanreninfo;
import static com.example.delitto.myapplication.Action.XuanrenAction.getXuanrenFlag;
import static com.example.delitto.myapplication.Action.YiXiangAction.GetYiXiang;
import static com.example.delitto.myapplication.Action.YiXiangAction.getYiXiangFlag;



/**
 * Created by Administrator on 2016/12/5.
 */

public class selectActivity extends AppCompatActivity {
    private Toolbar _toolbar;
    private ArrayList<SelectData> _listdata;
    private RecyclerView _recyclerView;
    private selectAdapter _adapter;
    private LinearLayoutManager _layoutManager;
    private Button _button;
    private SwipeRefreshLayout refreshLayout;
    private Context mContext;
    private Gson gson;
    private AlertDialog.Builder _dialog;
    private Intent _getintent;
    public final static int LOAD_SUCCESS = -1;
    public final static int NETWORK_ERROE = -2;
    public final static int CONNECT_ERROR = -3;
    private int assigUSerID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        _listdata = new ArrayList<>();

        mContext = selectActivity.this;
        _getintent = getIntent();
        gson = new Gson();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        _recyclerView = (RecyclerView) findViewById(R.id.select_fragment);
        _toolbar = (Toolbar) findViewById(R.id.toolbar1);
        _button = (Button) findViewById(R.id.select_btn);

        _layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _recyclerView.setLayoutManager(_layoutManager);

        //绘制item间隔
        _recyclerView.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));

//        //选择按钮的时间处理
//        _adapter.setOnClickListener(new selectAdapter.OnClickListener() {
//            @Override
//            public void onClick(View _view, int _position) {
//                Toast.makeText(getApplicationContext(), "you had select" + _position, Toast.LENGTH_SHORT).show();
//            }
//        });
        //设置toolbar
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    /*String url = "url";
                    //工具类HttpUtil，可直接解析url
                    HttpUtil.sendHttpRequest("http://10.0.2.2/select_data.json", new HttpCallbackListener() {
                                @Override

                                @Override
                                public int onError(Exception e) {
                                    return CONNECT_ERROR;
                                }
                            }
                    );*/
                    int assigID = _getintent.getIntExtra("assigID",-1);
                    GetYiXiang((String.valueOf(assigID)));
                    String jsonresult= getYiXiangFlag();
                    onFinish(jsonresult);
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
                            _adapter = new selectAdapter(mContext, _listdata);
                            _adapter.setOnItemClickListener(new selectAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
//                                   Intent intent = new Intent(mContext,selectActivity.class);
//                                  携带当前点击任务的id,可利用查询当前任务的接单的用户列表
//                                   intent.putExtra("uN",_listdata.get(position).getusername());
 //                                  intent.putExtra("assigM",_listdata.get(position).getmoney());
 //                                  intent.putExtra("uT",_listdata.get(position).gettime());
  //                                  intent.putExtra("uID",_listdata.get(position).getSelecter_id());


  //                                startActivity(intent);
                                }

                                @Override
                                public void onItemLongClick(View view, int position) {
                                }

                                @Override
                                public void onButtonClick(Button button, int _position) {
                                    SelectData _data = _listdata.get(_position);
                                    assigUSerID=_data.getSelecter_id();


                                    selecting();
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
    public int onFinish(String response) {
        //respone解析为list<MainListData>，遍历list,将每个元素添加到_listdata
        try {
            _listdata.clear();
            //遍历json数组
            ArrayList<SelectData> list = gson.fromJson(response, new
                    TypeToken<ArrayList<SelectData>>() {
                    }.getType());
            for (SelectData item : list) {
                _listdata.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CONNECT_ERROR;
        }
        return LOAD_SUCCESS;
    }

    public void selecting() {
        _dialog = new AlertDialog.Builder(mContext);
        _dialog.setTitle("确定");
        _dialog.setMessage("确认选择此用户？");
        _dialog.setCancelable(true);
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                final ProgressDialog _progressDialog = new ProgressDialog(mContext);   //弹出进度条
                _progressDialog.setTitle("正在确认");
                _progressDialog.setMessage("请稍后..");
                _progressDialog.show();
                int assigID = _getintent.getIntExtra("assigID",-1);


                //3秒后返回首页
                GetXuanreninfo((String.valueOf(assigUSerID)),(String.valueOf(assigID)));

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                //FIXME 这里进行上传数据处理,监听上传成功和失败的情况回调函数
                                boolean flag= getXuanrenFlag();
                             if(flag) {
                                    _selectSuccess();
                                    _progressDialog.dismiss();
                                }
                              /* else {
                                    _confirmFail();
                                    _progressDialog.dismiss();
                                }*/
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

    public void _selectSuccess() {
        _dialog = new AlertDialog.Builder(mContext);
        _dialog.setMessage("已经选择此用户为接单人");
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                load();
            }
        });
        _dialog.show();
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