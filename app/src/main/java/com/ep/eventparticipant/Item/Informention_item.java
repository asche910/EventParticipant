package com.ep.eventparticipant.Item;

public class Informention_item {
    private String name;
   private int ID;
    private String url;
    private String shijian,place,userID;
    private int gujia;

   // public int getID() {
     //   return ID;
   // }

    public String getName() {
        return name;
    }
    public Informention_item(String name, String url,String shijian,String place,String userID,int gujia){
        this.name=name;
        this.url=url;
        this.gujia=gujia;
        this.place=place;
        this.shijian=shijian;
        this.userID=userID;
    }
    public Informention_item(String name, int ID,String shijian,String place,String userID,int gujia){
        this.name=name;
        this.ID=ID;
        this.gujia=gujia;
        this.place=place;
        this.shijian=shijian;
        this.userID=userID;
    }

    public String getUrl() {
        return url;
    }

    public String getUserID() {
        return userID;
    }

    public int getGujia() {
        return gujia;
    }

    public String getPlace() {
        return place;
    }

    public String getShijian() {
        return shijian;
    }

    public int getID() {
        return ID;
    }
}
