package com.ep.eventparticipant.been;

import org.litepal.crud.DataSupport;

public class Information extends DataSupport {
    private int ID;
    private String imageurl;
    private String time;
    private String userid;
    private String  place;
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getUserid() {
        return userid;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
