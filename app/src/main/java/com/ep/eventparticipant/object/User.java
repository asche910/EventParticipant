package com.ep.eventparticipant.object;

import android.provider.ContactsContract;

public class User {
    private int id;
    private String username;
    private String password;
    private String Name;
    private String Signature;
    private String Phone;
    private String ImageUrl;
    public User(){
        this.id = id;
        this.Name = Name;
        this.Signature = Signature;
        this.Phone = Phone;
        this.ImageUrl = ImageUrl;
        }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return Name;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getPhone() {
        return Phone;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
