package com.example.delitto.myapplication.Bean;

/**
 * Created by Administrator on 2016/12/5.
 */

public class GetHistoryData {
    public int _resouceid;
    public String _style;
    public String _place;
    public String _time;
    public String _name;

    public GetHistoryData(int _resouceid, String _style, String _place, String _time, String _name) {
        this._resouceid = _resouceid;
        this._style = _style;
        this._place = _place;
        this._time = _time;
        this._name = _name;
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

    public String get_name() {
        return _name;
    }
}
