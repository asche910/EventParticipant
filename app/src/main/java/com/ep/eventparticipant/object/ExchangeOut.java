package com.ep.eventparticipant.object;

import android.provider.ContactsContract;
import android.widget.Button;

public class ExchangeOut {
    private String name;
    private int ImageId;
//
    public  ExchangeOut(String name,int ImageId) {
        this.name =name;
        this.ImageId = ImageId;
    }

    public int getImageId() {
        return ImageId;
    }

    public String getName() {
        return name;
    }
}
