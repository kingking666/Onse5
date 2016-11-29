package com.example.delitto.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import java.util.zip.Inflater;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by pokedo on 2016/11/27.
 */

public class PopupDialog extends BasePopupWindow implements View.OnClickListener {

    private View popupView;

    //初始化popupDialog，传入context，设置popupDialog的长宽属性
    public PopupDialog(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bindEvent();
    }

    //设置显示动画
    @Override
    protected Animation initShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    //从res中实例化popupView
    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.popupmenu_layout);
        return popupView;
    }

    //设置需要应用动画的资源
    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_layout);
    }

    //popupView的 子view的按键触发处理
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unfavorite_button:
                Toast.makeText(view.getContext(), "unfavorite", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.favorite_button:
                Toast.makeText(view.getContext(), "favorite", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    //设定popupwindow的显示位置
    public void showPopupWindow(View v) {
        setRelativeToAnchorView(true);
        setRelativePivot(RelativePivot.RIGHT);
        setOffsetX(-v.getWidth());
        super.showPopupWindow(v);
    }

    //popupView里面绑定子view并设置监听者
    private void bindEvent() {
        if (popupView != null) {
            popupView.findViewById(R.id.favorite_button).setOnClickListener(this);
            popupView.findViewById(R.id.unfavorite_button).setOnClickListener(this);
        }
    }

    //第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
    //第二个参数toX为动画结束时 X坐标上的伸缩尺寸
    //第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
    //第四个参数toY为动画结束时Y坐标上的伸缩尺寸
    /*说明:
                        以上四种属性值
                        0.0表示收缩到没有
                        1.0表示正常无伸缩
                        值小于1.0表示收缩
                        值大于1.0表示放大
    */
    //第五个参数pivotXType为动画在X轴相对于物件位置类型
    //第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
    //第七个参数pivotXType为动画在Y轴相对于物件位置类型
    //第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
    protected Animation getDefaultScaleAnimation() {
        Animation myAnimation_Scale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        //持续时间
        myAnimation_Scale.setDuration(500);
        return myAnimation_Scale;
    }

}
