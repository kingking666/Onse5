package com.example.delitto.myapplication.getHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delitto.myapplication.Bean.GetHistoryData;
import com.example.delitto.myapplication.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/12/5.
 */

class getHistoryAdapter extends RecyclerView.Adapter{
    private Context _context;
    private ArrayList<GetHistoryData> _listData;

    public getHistoryAdapter(Context _context,ArrayList<GetHistoryData> _listData){
        this._context=_context;
        this._listData=_listData;
    }


    private OnItemClickListener _onItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(View _view, int _position);
        void onItemLongClick(View _view, int _position);
    }


    public void setOnItemClickListener(OnItemClickListener _listener){
        this._onItemClickListener=_listener;
    }


    class getHistoryViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView _circleView;
        private TextView _type;
        private TextView _username;
        private TextView _date;

        public getHistoryViewHolder(View _itemView){
            super(_itemView);
            _circleView=(CircleImageView)_itemView.findViewById(R.id.get_image);
            _type=(TextView)_itemView.findViewById(R.id.type_get);
            _username=(TextView)_itemView.findViewById(R.id.get_username);
            _date=(TextView)_itemView.findViewById(R.id.date_get);
        }
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup _parent,int _viewType){
        View _itemview= LayoutInflater.from(_parent.getContext()).inflate(R.layout.get_history_item_view,_parent,false);
        getHistoryViewHolder _viewHolder=new getHistoryViewHolder(_itemview);
        return _viewHolder;
    }


    public void onBindViewHolder(final RecyclerView.ViewHolder _holder,int _position){
        GetHistoryData _data=_listData.get(_position);
        ((getHistoryViewHolder)_holder)._circleView.setImageResource(_data.get_resouceid());
        ((getHistoryViewHolder)_holder)._type.setText(_data.get_type());
        ((getHistoryViewHolder)_holder)._username.setText(_data.get_username());
        ((getHistoryViewHolder)_holder)._date.setText(_data.get_date());


        _holder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(_onItemClickListener != null){
                    int _pos=_holder.getLayoutPosition();
                    _onItemClickListener.onItemClick(_holder.itemView,_pos);
                }
            }
        });

        _holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                if(_onItemClickListener!=null){
                    int _pos=_holder.getLayoutPosition();
                    _onItemClickListener.onItemClick(_holder.itemView,_pos);
                }
                return true;
            }
        });
    }


    public int getItemCount(){
        return _listData.size();
    }
}
