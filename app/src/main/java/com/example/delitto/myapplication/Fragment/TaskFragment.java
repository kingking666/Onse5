package com.example.delitto.myapplication.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.delitto.myapplication.Action.PublishAction;
import com.example.delitto.myapplication.Activity.MainActivity;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.User.UserThis;
import com.example.delitto.myapplication.util.DisplayUtil;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ganfra.materialspinner.MaterialSpinner;
import function.getTime;
import httpservice.assigPost;

/**
 * Created by pokedo on 2016/11/18.
 */

public class TaskFragment extends Fragment implements View.OnClickListener {
    private MaterialSpinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText publicEdittext;
    private EditText privateEdittext;
    private EditText inputphone;
    private BottomNavigationBar bottomNavigationBar;
    private CircleImageView expressCircleImage;
    private CircleImageView takeoutCircleImage;
    private CircleImageView otherCircleImage;
    private Context mContext;
    private AlertDialog.Builder _dialog;
    private static Handler mHandler = new Handler();
    private String type1="快递";
    private String type2="外卖";
    private String type3="其它";
    private String type;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1120/2025.html
        //Fragment中控制外部的optionmenu
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        publicEdittext = (EditText) view.findViewById(R.id.public_edittext);
        privateEdittext = (EditText) view.findViewById(R.id.private_edittext);
        inputphone = (EditText) view.findViewById(R.id.input_contact_phone);
        bottomNavigationBar = (BottomNavigationBar) getActivity().findViewById(R.id.bottom_navigation_bar);
        expressCircleImage = (CircleImageView) view.findViewById(R.id.express_logo);
        takeoutCircleImage = (CircleImageView) view.findViewById(R.id.takeout_logo);
        otherCircleImage = (CircleImageView) view.findViewById(R.id.other_logo);

        mContext = TaskFragment.this.getContext();

        //设置下拉选择框的数据
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        String[] ITEMS = {"0.5h", "1h", "1.5h", "2h", "2.5h", "3h", "3.5h", "4h"};

        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Edittext设置焦点监听,获取焦点时候隐藏底栏,否则隐藏输入键盘
        hideWidget();
        //解决外层Scrollview和Edittext滚动冲突问题
        scrollConflict();

        //监听发布任务类型 选择的图标
        expressCircleImage.setOnClickListener(this);
        takeoutCircleImage.setOnClickListener(this);
        otherCircleImage.setOnClickListener(this);

        return view;
    }

    //在fragment设置菜单监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_publish) {
            //验证
            if (!verify())
                return true;
            _dialog = new AlertDialog.Builder(mContext);
            _dialog.setTitle("确定");
            _dialog.setMessage("发布任务之后不可取消，是否确认？");
            _dialog.setCancelable(false);
            _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface _dialog, int which) {
                    final ProgressDialog _progressDialog = new ProgressDialog(mContext);   //弹出进度条
                    _progressDialog.setTitle("正在确认发布");
                    _progressDialog.setMessage("请稍后..");
                    _progressDialog.show();
                    if(expressCircleImage.getBorderColor()!=getResources().getColor(R.color.border_color))
                    {
                        type=type1;
                    }
                    else if( takeoutCircleImage.getBorderColor()!=getResources().getColor(R.color.border_color))
                    {
                        type=type2;
                    }
                    else if(otherCircleImage.getBorderColor()!=getResources().getColor(R.color.border_color))
                    {
                        type=type3;
                    }
                    final String time=new getTime().gettime((String)spinner.getSelectedItem());
                   PublishAction.GetPublish(type, privateEdittext.getText().toString(), publicEdittext.getText().toString(),time,inputphone.getText().toString());
                    //FIXME 这里进行上传数据处理,监听上传成功和失败的情况回调函数
                    //3秒后返回首页

                            mHandler.postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run()
                                        {
                                            if(PublishAction.getPublishFlag()){
                                                _sendSuccess();
                                                _progressDialog.dismiss();
                                            }
                                            else{
                                                _progressDialog.dismiss();
                                                return;
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
        }
        return super.onOptionsItemSelected(item);
    }

    //验证
    public boolean verify() {
        boolean temp = true;
        if (spinner.getSelectedItem().equals(spinner.getHint())) {
            spinner.setError("请选择该下拉栏");
            temp = false;
        }
        if (publicEdittext.getText().length() < 5) {
            publicEdittext.setError("字符长度必须大于5");
            temp = false;
        }
        if (privateEdittext.getText().length() < 5) {
            privateEdittext.setError("字符长度必须大于5");
            temp = false;
        }
        if (inputphone.getText().length() != 11) {
            inputphone.setError("手机号码长度为11位");
            temp = false;
        }
        int border_color = getResources().getColor(R.color.border_color);
        if (expressCircleImage.getBorderColor() == border_color && otherCircleImage.getBorderColor()
                == border_color && takeoutCircleImage.getBorderColor() == border_color) {
            Toast.makeText(mContext, "请选择任务类型!", Toast.LENGTH_SHORT).show();
            temp = false;
        }
//        Log.d("~verify", temp + "");
//        Log.d("~color", getResources().getColor(R.color.border_color) + "");
//        Log.d("~color", expressCircleImage.getBorderColor() + "");
//        Log.d("~color", otherCircleImage.getBorderColor() + "");
        if (temp)
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CircleImageView) {
            changeStyle(view);
        }
    }

    //改变Logo的Style，每次click重新设置三个logo的属性
    private void changeStyle(View view) {
        CircleImageView cirview = (CircleImageView) view;
        switch (view.getId()) {
            case R.id.express_logo:
                setSelectedStyle(expressCircleImage);
                setUnselectedStyle(takeoutCircleImage);
                setUnselectedStyle(otherCircleImage);
                break;
            case R.id.takeout_logo:
                setSelectedStyle(takeoutCircleImage);
                setUnselectedStyle(expressCircleImage);
                setUnselectedStyle(otherCircleImage);
                break;
            case R.id.other_logo:
                setSelectedStyle(otherCircleImage);
                setUnselectedStyle(takeoutCircleImage);
                setUnselectedStyle(expressCircleImage);
                break;
        }
    }

    //选择logo时的style
    private void setSelectedStyle(CircleImageView cirview) {
        cirview.setBorderColor(getResources().getColor(R.color.primary_darker));
        cirview.setBorderWidth(DisplayUtil.dip2px(this, 2));
    }

    //未选择logo时的style
    private void setUnselectedStyle(CircleImageView cirview) {
        cirview.setBorderColor(getResources().getColor(R.color.border_color));
        cirview.setBorderWidth(DisplayUtil.dip2px(this, 1));
    }

    public void _sendSuccess() {
        _dialog = new AlertDialog.Builder(mContext);
        _dialog.setMessage("发布任务成功！");
        _dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface _dialog, int which) {
                Intent _intent = new Intent(mContext, MainActivity.class);
                startActivity(_intent);
                //intent1 : 刷新数据
                Intent intent1 = new Intent("com.example.delitto.myapplication.TASK");
                intent1.putExtra("type","send_task");
                //intent2 : 更改viewpager页面
                Intent intent2 = new Intent("com.example.delitto.myapplication.MAINACT");
                intent2.putExtra("type","change_page");
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mContext);
                manager.sendBroadcast(intent1);
                manager.sendBroadcast(intent2);
                reload();
            }
        });
        _dialog.show();
    }

    //重新加载视图
    public void reload(){
        spinner.setSelection(0);
        setUnselectedStyle(takeoutCircleImage);
        setUnselectedStyle(otherCircleImage);
        setUnselectedStyle(expressCircleImage);
        publicEdittext.setText("");
        privateEdittext.setText("");
        inputphone.setText("");
    }

    //解决外层Scrollview和Edittext滚动冲突问题
    protected void scrollConflict() {
        publicEdittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.public_edittext) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        privateEdittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.private_edittext) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    //Edittext设置焦点监听,获取焦点时候隐藏底栏,否则隐藏输入键盘
    protected void hideWidget() {
        publicEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    bottomNavigationBar.hide();
                    Log.d("~focus", bottomNavigationBar.isHidden() + "");
                } else {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    bottomNavigationBar.show();
                }
            }
        });
        privateEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    bottomNavigationBar.hide();
                    Log.d("~focus", bottomNavigationBar.isHidden() + "");
                } else {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    bottomNavigationBar.show();
                }
            }
        });

        inputphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    bottomNavigationBar.hide();
                    Log.d("~focus", bottomNavigationBar.isHidden() + "");
                } else {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    bottomNavigationBar.show();
                }
            }
        });
    }
}
