package com.ep.eventparticipant.Item;

import java.net.URL;

public class Exchangeitem {
    private String url=null;
    private String stoptime;
    private int ID;
private String name;
private String place;
private int gujia;
private String phone;
private String userID;
private String miaoshu;
    public String getUrl() {
        return url;
    }

    public String getStoptime() {
        return stoptime;
    }



    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }
    //public  Exchangeitem(String url,String stoptime){
      //  this.url=url;
    //    this.stoptime=stoptime;
  //  }
    public Exchangeitem(String stoptime,String name,String url,String place,int gujia,String phone,String userID,String miaoshu){
        //ID=id;
        this.url=url;
        this.stoptime=stoptime;
        this.name=name;
        this.place=place;
        this.gujia=gujia;
        this.phone=phone;
        this.userID=userID;
        this.miaoshu=miaoshu;
    }
    public Exchangeitem(String stoptime,String name,int ID,String place,int gujia,String phone,String userID,String miaoshu){

        this.ID=ID;
        this.stoptime=stoptime;
        this.name=name;
        this.place=place;
        this.gujia=gujia;
        this.phone=phone;
        this.userID=userID;
        this.miaoshu=miaoshu;
    }

  public int getID() {
      return ID;
   }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public int getGujia() {
        return gujia;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserID() {
        return userID;
    }

    public String getMiaoshu() {
        return miaoshu;
    }
}
