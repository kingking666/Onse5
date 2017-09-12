package com.example.delitto.myapplication.Fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delitto.myapplication.Bean.MessageListData;
import com.example.delitto.myapplication.R;
import com.example.delitto.myapplication.Tools.ECTools;

import java.util.ArrayList;

/**
 * Created by pokedo on 2016/11/14.
 */
class MessageListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<MessageListData> list_data;
    protected final int MSG_REFRESH_ADAPTER_DATA = 0;

    //实例化时传入一个Context
    public MessageListAdapter(Context mContext, ArrayList<MessageListData> list_data) {
        this.mContext = mContext;
        this.list_data = list_data;
    }


    //自定义接口的一个对象
    private OnItemClickListener onItemClickListener;

    //写为一个自定义接口，在主acitiity代码中添加具体的实现内容
    //再在myAdapter中传入对应被Click时每项的参数，并调用对用方法
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    //从主代码传入 onItemClick onItemLongClick方法的具体实现内容
    public void setOnItemClickListener(MessageListAdapter.OnItemClickListener listener) {
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
    class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView titleview2;
        private TextView contentview2;
        private TextView timeview;
        private ImageView imageView2;

        public MessageViewHolder(View itemView) {
            super(itemView);
            titleview2 = (TextView) itemView.findViewById(R.id.message_title);
            contentview2 = (TextView) itemView.findViewById(R.id.message_content);
            timeview = (TextView) itemView.findViewById(R.id.message_timeview);
            imageView2 = (ImageView) itemView.findViewById(R.id.message_image);
        }

    }

    //创建视图部分
    //实例化展示的View并实例化viewHolder,返回一个viewHolder对象
    //根据item类别加载不同ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_view,
                parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(itemview);
        return viewHolder;
    }

    //视图绑定数据
    //对viewHolder里面的控件进行数据绑定
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        ((MyViewHolder) holder).textView.setText("item" + position);
//        ((MyViewHolder) holder).imageView.setImageResource(R.mipmap.ic_launcher);
        MessageListData data = list_data.get(position);
        ((MessageViewHolder) holder).titleview2.setText(data.title);
        ((MessageViewHolder) holder).contentview2.setText(data.content);
        ((MessageViewHolder) holder).imageView2.setImageResource(data.resourceid);
        ((MessageViewHolder) holder).timeview.setText(data.time);

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
