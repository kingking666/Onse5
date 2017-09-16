package com.example.delitto.myapplication.detail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delitto.myapplication.Action.detailAction;
import com.example.delitto.myapplication.Activity.MainActivity;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.Tools.AppTools;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;
import function.getTime;

/**
 * Created by Administrator on 2016/11/30.
 */

public class detailActivity extends AppCompatActivity {

    private Toolbar _toolbal;
    private AlertDialog.Builder _dialog;
    private ProgressDialog _progressDialog;
    private Intent _intent;
    private MaterialSpinner _mspinner;
    private MaterialSpinner _tspinner;
    private ArrayAdapter<String> adapter;
    private CircleImageView _circle;
    private TextView _nametextview;
    private TextView _detailtextview;
    private TextView _timetextview;
    private Button _button;
    private static Handler mHandler = new Handler();
    private Intent _getintent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_task);

        _toolbal = (Toolbar) findViewById(R.id.toolbar1);
        _mspinner = (MaterialSpinner) findViewById(R.id.money_spinner);
        _tspinner = (MaterialSpinner) findViewById(R.id.time_spinner);
        _circle = (CircleImageView) findViewById(R.id.circle_image);
        _nametextview = (TextView) findViewById(R.id.name_detail);
        _detailtextview = (TextView) findViewById(R.id.detail_task);
        _timetextview = (TextView) findViewById(R.id.detail_time);
        _button = (Button) findViewById(R.id.style_detail);

        //设置toolbal
        setSupportActionBar(_toolbal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //设置后退键
//
        //设置点击图片时显示用户信息
        _circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "你点击了头像", Toast.LENGTH_SHORT).show();
                //.....
                //.....
            }
        });
        _getintent = getIntent();
        //获取图片头像
        _circle.setImageResource(AppTools.getPhotoResourseId());
        iniData();

        //设置完成时间
        final String[] TITEMS = {"5分钟以内", "15分钟以内", "30分钟以内", "一个小时以内"};
        adapter = new ArrayAdapter<String>(detailActivity.this, android.R.layout.simple_spinner_item, TITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _tspinner.setAdapter(adapter);
        //设置完成赏金
        String[] MITEMS = {"1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10"};
        adapter = new ArrayAdapter<String>(detailActivity.this, android.R.layout.simple_spinner_item, MITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _mspinner.setAdapter(adapter);
    }

    //关联对应Toolbar和menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    //设置对Toolbar的按钮监听
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_send:        //按发送键时弹出选择
                switch (verify()) {
                    case 1:
                        _tspinner.setError("请选择该下拉项!");
                        return true;
                    case 2:
                        _mspinner.setError("请选择该下拉项!");
                        return true;
                    case 3:
                        _tspinner.setError("请选择该下拉项!");
                        _mspinner.setError("请选择该下拉项!");
                        return true;
                    default:
                        break;
                }
                _dialog = new AlertDialog.Builder(detailActivity.this);
                _dialog.setTitle("确定");
                _dialog.setMessage("领取任务之后不可取消，是否确认？");
                _dialog.setCancelable(false);
                _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface _dialog, int which) {
                        _progressDialog = new ProgressDialog(detailActivity.this);   //弹出进度条
                        _progressDialog.setTitle("正在确认接收");
                        _progressDialog.setMessage("请稍后..");
                        _progressDialog.show();
                        final String time=new getTime().gettime((String)_tspinner.getSelectedItem());
                        int assigID = _getintent.getIntExtra("assigID",-1);
//                        Log.d("~newuserid",userid+"");
                        detailAction.Getdetailinfo((String.valueOf(assigID)),(String)_mspinner.getSelectedItem(),time);
                        //3秒后返回首页
                        mHandler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        //FIXME 这里进行上传数据处理,监听上传成功和失败的情况回调函数
                                        if(detailAction.getdetailinfoFlag()) {
                                            _getSuccess();
                                            _progressDialog.dismiss();
                                        }
                                        else {
                                            _getFail();
                                            _progressDialog.dismiss();
                                        }
                                    }
                                }, 3000);
                    }
                });
                _dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface _dialog, int which) {
                    }
                });
                _dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //设置数据
    public void iniData() {
        _button.setText(_getintent.getStringExtra("assigT"));
        _detailtextview.setText(_getintent.getStringExtra("assigRM"));
        _timetextview.setText(_getintent.getStringExtra("assigTi"));
        _nametextview.setText(_getintent.getStringExtra("uN"));
    }

    //验证发送信息
    public int verify() {
        if (_mspinner.getSelectedItem().equals(_mspinner.getHint()) &&
                _tspinner.getSelectedItem().equals(_tspinner.getHint()))
            return 3;
        else if (_tspinner.getSelectedItem().equals(_tspinner.getHint()))
            return 1;
        else if (_mspinner.getSelectedItem().equals(_mspinner.getHint()))
            return 2;
        else return -1;
    }

    //领取成功时
    public void _getSuccess() {
        _dialog = new AlertDialog.Builder(detailActivity.this);
        _dialog.setMessage("领取任务成功！");
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                _intent = new Intent(detailActivity.this, MainActivity.class);
                startActivity(_intent);
                finish();
                //发送广播
                Intent intent = new Intent("com.example.delitto.myapplication.TASK");
                intent.putExtra("type","detail_task");
                LocalBroadcastManager.getInstance(detailActivity.this).sendBroadcast(intent);
            }
        });
        _dialog.show();
    }

    //领取失败时
    public void _getFail() {
        _dialog = new AlertDialog.Builder(detailActivity.this);
        _dialog.setMessage("抱歉亲爱的用户，任务已被领取或取消");
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                _intent = new Intent(detailActivity.this, MainActivity.class);
                startActivity(_intent);
                finish();
            }
        });
         _dialog.show();
    }
}
