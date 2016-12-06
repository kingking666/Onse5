package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class SendHistoryData {
    public int _resouceid;
    public String _style;
    public String _place;
    public String _time;
    public String _state;

    //保存数据
    public SendHistoryData(int _resouceid, String _style, String __place, String _time, String _state) {
        this._resouceid = _resouceid;
        this._style = _style;
        this._place = __place;
        this._time = _time;
        this._state = _state;
    }

    public int get_resouceid() {
        return _resouceid;
    }

    public String get_style() {
        return _style;
    }

    public String get_place() {
        return _place;
    }

    public String get_time() {
        return _time;
    }

    public String get_state() {
        return _state;
    }
}
