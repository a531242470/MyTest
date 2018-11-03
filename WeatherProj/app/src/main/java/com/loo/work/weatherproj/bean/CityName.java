package com.loo.work.weatherproj.bean;

public class CityName {
    private String _id;
    private String id;
    private String pid;
    private String city_code;
    private String city_name;

    public CityName(String _id, String id, String pid, String city_code, String city_name) {
        this._id = _id;
        this.id = id;
        this.pid = pid;
        this.city_code = city_code;
        this.city_name = city_name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
