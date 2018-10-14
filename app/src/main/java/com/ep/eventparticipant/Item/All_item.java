package com.ep.eventparticipant.Item;

public class All_item {
    private String name;
    private int id;
    private String time;
    private String address;
    private String expect;
    private int price;
    String imageurl;
    public All_item(){}

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public All_item(String name,int id){
        this.id=id;
        this.name=name;
    }

    public int getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public String getExpect() {
        return expect;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getTime() {
        return time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
