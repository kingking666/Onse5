package com.example.delitto.myapplication.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delitto.myapplication.Bean.MainListData;
import com.example.delitto.myapplication.Animation.PopupDialog;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.Tools.AppTools;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by pokedo on 2016/11/14.
 */
class MainListAdapter extends RecyclerView.Adapter {
    private static final String TAG = "~MainListAdapter";

    private Fragment fragment;
    private ArrayList<MainListData> list_data;
    private PopupDialog popupDialog;

    private final int SHOW_TIME = 0;
    private final int HIDE_TIME = 1;
    private String lastTime;

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

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return SHOW_TIME;

        MainListData mData = list_data.get(position);
        MainListData mLastData = list_data.get(position - 1);
        String currentTime = mData.getDate();
        String lastTime = mLastData.getDate();
        if (currentTime.equals(lastTime))
            return HIDE_TIME;
        else
            return SHOW_TIME;
    }

    //自定义viewHolder ,获取每个子视图对象的里面的控件
    class RequireViewHolder extends RecyclerView.ViewHolder {
        private View mTimeLinearLayout;
        private TextView mTimeTextView;
        private TextView typeview1;
        private TextView contentview1;
        private ImageView imageView1;
        private ImageView morebutton;

        public RequireViewHolder(View itemView) {
            super(itemView);
            mTimeLinearLayout = itemView.findViewById(R.id.main_list_time_linearlayout);
            mTimeTextView = (TextView) itemView.findViewById(R.id.main_list_time);
            typeview1 = (TextView) itemView.findViewById(R.id.main_list_type);
            contentview1 = (TextView) itemView.findViewById(R.id.main_list_content);
            imageView1 = (ImageView) itemView.findViewById(R.id.require_image);
            morebutton = (ImageView) itemView.findViewById(R.id.main_list_more);
        }

        public void showTime() {
            mTimeLinearLayout.setVisibility(View.VISIBLE);
        }

        public void hideTime() {
            mTimeLinearLayout.setVisibility(View.GONE);
        }
    }


    //创建视图部分
    //实例化展示的View并实例化viewHolder,返回一个viewHolder对象
    //根据item类别加载不同ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_cardview,
                parent, false);
        RequireViewHolder viewHolder = new RequireViewHolder(itemview);
        if (viewType == SHOW_TIME) {
            viewHolder.showTime();
        } else {
            viewHolder.hideTime();
        }
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

        ((RequireViewHolder) holder).mTimeTextView.setText(data.getDate());
        ((RequireViewHolder) holder).typeview1.setText(data.getType());
        ((RequireViewHolder) holder).contentview1.setText(data.getContent());
        ((RequireViewHolder) holder).imageView1.setImageResource(AppTools.getPhotoResourseId());
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
