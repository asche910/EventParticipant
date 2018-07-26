package com.ep.eventparticipant;

import android.widget.ImageView;

import org.litepal.crud.DataSupport;

public class User extends DataSupport{
    public int id;
    public String username;
    public String Password;
    public String Name;
    public String Signature;
    public String Phone;
    public String Imageurl;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public String getPhone() {
        return Phone;
    }

    public String getSignature() {
        return Signature;
    }

    public String getUsername() {
        return username;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
