package com.ep.eventparticipant.object;

import android.widget.ArrayAdapter;

public class ExchangeIn{
    private String name;
    private int ImageId;
    public ExchangeIn(String name,int imageId){
        this.name = name;
        this.ImageId =imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return ImageId;
    }

}
