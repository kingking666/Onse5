package com.example.delitto.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delitto.myapplication.Bean.SendHistoryData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/12/5.
 */

class sendHistoryAdapter extends RecyclerView.Adapter{
    private Context _context;
    private ArrayList<SendHistoryData> _listData;

    //重写父类的一组构造函数，实例化时传入数据
    public sendHistoryAdapter(Context _context,ArrayList<SendHistoryData> _listData){
        this._context=_context;
        this._listData=_listData;
    }

    //自定义接口的一个对象
    private OnItemClickListener _onItemClickListener;

    //写为一个自定义接口，在主activity代码中添加具体的实现内容
    //再在myAdapter中传入对应被Click时每项的参数，并调用对应方法
    public interface OnItemClickListener{
        void onItemClick(View _view, int _position);
        void onItemLongClick(View _view, int _position);
    }

    //从主代码传入_onItemClick onItemLongClick方法的具体实现内容
    public void setOnItemClickListener(OnItemClickListener _listener){
        this._onItemClickListener=_listener;
    }

    //自定义viewHolder，获取每个子视图对象的里面的控件
    class sendHistoryViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView _circleView;
        private TextView _style;
        private TextView _state;
        private TextView _time;

        public sendHistoryViewHolder(View _itemView){
            super(_itemView);
            _circleView=(CircleImageView)_itemView.findViewById(R.id.send_image);
            _style=(TextView)_itemView.findViewById(R.id.style_send);
            _state=(TextView)_itemView.findViewById(R.id.state_send);
            _time=(TextView)_itemView.findViewById(R.id.time_send);
        }
    }

    //创建视图部分
    //实例化展示的View并实例化viewHolder,返回一个viewHolder对象
    //根据item类别加载不同ViewHolder
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup _parent,int _viewType){
        View _itemview= LayoutInflater.from(_parent.getContext()).inflate(R.layout.send_history_item_view,_parent,false);
        sendHistoryViewHolder _viewHolder=new sendHistoryViewHolder(_itemview);
        return _viewHolder;
    }
    //视图绑定数据
    //对viewHolder里面的控件进行数据绑定
    public void onBindViewHolder(final RecyclerView.ViewHolder _holder,int _position){
        SendHistoryData _data=_listData.get(_position);
        ((sendHistoryViewHolder)_holder)._circleView.setImageResource(_data._resouceid);
        ((sendHistoryViewHolder)_holder)._style.setText(_data._style);
        ((sendHistoryViewHolder)_holder)._state.setText(_data._state);
        ((sendHistoryViewHolder)_holder)._time.setText(_data._time);

        //实际的itemView监听者
        _holder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //如果自定义接口存在
                if(_onItemClickListener != null){
                    int _pos=_holder.getLayoutPosition();
                    //每次绑定holder时
                    //自定义接口的onItemClick方法传入被Click项的对应参数，方法被调用
                    _onItemClickListener.onItemClick(_holder.itemView,_pos);
                }
            }
        });

        _holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                if(_onItemClickListener!=null){
                    int _pos=_holder.getLayoutPosition();
                    //调用自定义接口的onItemLongClick方法传入被Long Click项的对应参数，方法被调用
                    _onItemClickListener.onItemClick(_holder.itemView,_pos);
                }
                return true;
            }
        });
    }


    //数据条数
    public int getItemCount(){
        return _listData.size();
    }
}
