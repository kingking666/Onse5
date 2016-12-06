package com.example.delitto.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.delitto.myapplication.Bean.MainListData;

import java.util.ArrayList;

/**
 * Created by pokedo on 2016/11/14.
 */
class MainListAdapter extends RecyclerView.Adapter {

    private Fragment fragment;
    private ArrayList<MainListData> list_data;
    private PopupDialog popupDialog;

    //实例化时传入一个Context
    public MainListAdapter(Fragment fragment, ArrayList<MainListData> list_data) {
        this.fragment = fragment;
        this.list_data = list_data;
    }


    //自定义接口的一个对象
    private MainListAdapter.OnItemClickListener onItemClickListener;

    //写为一个自定义接口，在主acitiity代码中添加具体的实现内容
    //再在myAdapter中传入对应被Click时每项的参数，并调用对用方法
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    //从主代码传入 onItemClick onItemLongClick方法的具体实现内容
    public void setOnItemClickListener(MainListAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


//    public void addItem(int id, String[] title, String content) {
//        list_data.add(0, new ListData(id, title, content));
//        notifyItemInserted(0);
//    }
//
//    public void deleteItem() {
//        if (!list_data.isEmpty())
//            list_data.remove(0);
//        notifyItemRemoved(0);
//    }


    //自定义viewHolder ,获取每个子视图对象的里面的控件
    class RequireViewHolder extends RecyclerView.ViewHolder {
        private TextView titleview1;
        private TextView contentview1;
        private ImageView imageView1;
        private ImageView morebutton;

        public RequireViewHolder(View itemView) {
            super(itemView);
            titleview1 = (TextView) itemView.findViewById(R.id.require_title);
            contentview1 = (TextView) itemView.findViewById(R.id.require_content);
            imageView1 = (ImageView) itemView.findViewById(R.id.require_image);
            morebutton = (ImageView) itemView.findViewById(R.id.require_more_horiz);
        }
    }


    //创建视图部分
    //实例化展示的View并实例化viewHolder,返回一个viewHolder对象
    //根据item类别加载不同ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_view,
                parent, false);
        RequireViewHolder viewHolder = new RequireViewHolder(itemview);
        return viewHolder;
    }

    //视图绑定数据
    //对viewHolder里面的控件进行数据绑定
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        ((MyViewHolder) holder).textView.setText("item" + position);
//        ((MyViewHolder) holder).imageView.setImageResource(R.mipmap.ic_launcher);
        MainListData data = list_data.get(position);
        //初始化 触发morebutton后弹出的popupDialog
        popupDialog = new PopupDialog((Activity) fragment.getContext());
        ((RequireViewHolder) holder).titleview1.setText(data.getTitle());
        ((RequireViewHolder) holder).contentview1.setText(data.getContent());
        ((RequireViewHolder) holder).imageView1.setImageResource(data.getResourceid());
        ((RequireViewHolder) holder).morebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每次触发morebuttonhou，调用方法显示popupDialog，并传入触发的view从而确定popupDialog的显示位置
                popupDialog.showPopupWindow(((RequireViewHolder) holder).morebutton);
            }
        });

        //实际的itemView监听者
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果自定义接口存在
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    //每次绑定holder时，
                    //自定义接口的onItemClick方法传入被Clik项的对应参数,方法被调用
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    //调用自定义接口的onItemLongClick方法传入被Long Clik项的对应参数,方法被调用
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                return true;
            }
        });
    }

    //数据条数
    @Override
    public int getItemCount() {
        return list_data.size();
    }

}
