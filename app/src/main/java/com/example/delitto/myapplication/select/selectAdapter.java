package com.example.delitto.myapplication.select;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.delitto.myapplication.Bean.SelectData;
import com.example.delitto.myapplication.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/12/5.
 */

class selectAdapter extends RecyclerView.Adapter {
    private Context _context;
    private ArrayList<SelectData> _listData;

    //重写父类的一组构造函数，实例化时传入数据
    public selectAdapter(Context _context, ArrayList<SelectData> _listData) {
        this._context = _context;
        this._listData = _listData;
    }

    //自定义接口的一个对象
    private OnItemClickListener _onItemClickListener;
    private OnClickListener _onClickListenr;

    //写为一个自定义接口，在主activity代码中添加具体的实现内容
    //再在myAdapter中传入对应被Click时每项的参数，并调用对应方法
    public interface OnItemClickListener {
        void onItemClick(View _view, int _position);

        void onItemLongClick(View _view, int _position);

        void onButtonClick(Button button, int _position);

    }

    //从主代码传入_onItemClick onItemLongClick方法的具体实现内容
    public void setOnItemClickListener(OnItemClickListener _listener) {
        this._onItemClickListener = _listener;
    }

    //自定义接口，在主activity代码中添加具体的实现内容
    //再在myAdapter中传入对应被Click时每项Button的参数，并调用对应方法
    public interface OnClickListener {
        void onClick(View _view, int _position);
    }

    //从主代码传入_onClick方法的具体实现内容
    public void setOnClickListener(OnClickListener _listener) {
        this._onClickListenr = _listener;
    }

    //自定义viewHolder，获取每个子视图对象的里面的控件
    class selectViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView _circleView;
        private TextView _name;
        private TextView _money;
        private TextView _date;
        private Button _selectbtn;

        public selectViewHolder(View _itemView) {
            super(_itemView);
            _circleView = (CircleImageView) _itemView.findViewById(R.id.select_image);
            _name = (TextView) _itemView.findViewById(R.id.select_name);
            _money = (TextView) _itemView.findViewById(R.id.select_money);
            _date = (TextView) _itemView.findViewById(R.id.select_time);
            _selectbtn = (Button) _itemView.findViewById(R.id.select_btn);
        }
    }

    //创建视图部分
    //实例化展示的View并实例化viewHolder,返回一个viewHolder对象
    //根据item类别加载不同ViewHolder
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {
        View _itemview = LayoutInflater.from(_parent.getContext()).inflate(R.layout.select_item_view, _parent, false);
        selectViewHolder _viewHolder = new selectViewHolder(_itemview);
        return _viewHolder;
    }

    //视图绑定数据
    //对viewHolder里面的控件进行数据绑定
    public void onBindViewHolder(final RecyclerView.ViewHolder _holder, int _position) {
        SelectData _data = _listData.get(_position);
        ((selectViewHolder) _holder)._circleView.setImageResource(_data.getresourceId());
        ((selectViewHolder) _holder)._name.setText(_data.getusername());
        ((selectViewHolder) _holder)._money.setText(_data.getmoney());
        ((selectViewHolder) _holder)._date.setText(_data.gettime());
        String state = _data.getState();
        Button btn = ((selectViewHolder) _holder)._selectbtn;
        if (state.equals("正在进行中")||state.equals("已完成")) {
            if (_data.getSelecter_id() == _data.getSelecter_intent_id()) {
                btn.setText("已选择");
                btn.setEnabled(false);
            }
            else
                btn.setVisibility(View.INVISIBLE);
        }else if(state.equals("已过时间期限"))
            btn.setVisibility(View.INVISIBLE);
        else if(state.equals("可接"))

        //实际的itemView监听者
        _holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //如果自定义接口存在
                if (_onItemClickListener != null) {
                    int _pos = _holder.getLayoutPosition();
                    //每次绑定holder时
                    //自定义接口的onItemClick方法传入被Click项的对应参数，方法被调用
                    _onItemClickListener.onItemClick(_holder.itemView, _pos);
                }
            }
        });

        _holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (_onItemClickListener != null) {
                    int _pos = _holder.getLayoutPosition();
                    //调用自定义接口的onItemLongClick方法传入被Long Click项的对应参数，方法被调用
                    _onItemClickListener.onItemClick(_holder.itemView, _pos);
                }
                return true;
            }
        });
        ((selectViewHolder) _holder)._selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_onClickListenr != null) {
                    int _pos = _holder.getLayoutPosition();
                    _onClickListenr.onClick(_holder.itemView, _pos);
                }
            }
        });

        ((selectViewHolder) _holder)._selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_onItemClickListener != null) {
                    //传入button
                    int _pos = _holder.getLayoutPosition();
                    _onItemClickListener.onButtonClick(((selectViewHolder) _holder)._selectbtn, _pos);
                }
            }
        });
    }

    //数据条数
    public int getItemCount() {
        return _listData.size();
    }
}
