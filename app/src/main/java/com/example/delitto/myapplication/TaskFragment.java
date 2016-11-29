package com.example.delitto.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pokedo on 2016/11/18.
 */

public class TaskFragment extends Fragment implements View.OnClickListener {
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText publicEdittext;
    private EditText privateEdittext;
    private BottomNavigationBar bottomNavigationBar;
    private CircleImageView expressCircleImage;
    private CircleImageView takeoutCircleImage;
    private CircleImageView otherCircleImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        publicEdittext = (EditText) view.findViewById(R.id.public_edittext);
        privateEdittext = (EditText) view.findViewById(R.id.private_edittext);
        bottomNavigationBar = (BottomNavigationBar) getActivity().findViewById(R.id.bottom_navigation_bar);
        expressCircleImage = (CircleImageView) view.findViewById(R.id.express_logo);
        takeoutCircleImage = (CircleImageView) view.findViewById(R.id.takeout_logo);
        otherCircleImage = (CircleImageView) view.findViewById(R.id.other_logo);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        String[] ITEMS = {"0.5h", "1h", "1.5h", "2h", "2.5h", "3h", "3.5h", "4h"};
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Edittext设置焦点监听,获取焦点时候隐藏底栏,否则隐藏输入键盘
        publicEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    bottomNavigationBar.hide();
                    Log.d("~focus", bottomNavigationBar.isHidden() + "");
                }
                else{
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                }
                else{
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    bottomNavigationBar.show();
                }
            }
        });

        //监听发布任务类型 选择的图标
        expressCircleImage.setOnClickListener(this);
        takeoutCircleImage.setOnClickListener(this);
        otherCircleImage.setOnClickListener(this);

        return view;
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
    private void setSelectedStyle(CircleImageView cirview){
        cirview.setBorderColor(getResources().getColor(R.color.primary_darker));
        cirview.setBorderWidth(DisplayUtil.dip2px(this,2));
    }

    //未选择logo时的style
    private void setUnselectedStyle(CircleImageView cirview){
        cirview.setBorderColor(getResources().getColor(R.color.border_color));
        cirview.setBorderWidth(DisplayUtil.dip2px(this,1));
    }
}
