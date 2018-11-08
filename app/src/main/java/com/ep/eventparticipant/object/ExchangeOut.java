package com.ep.eventparticipant.object;

import android.provider.ContactsContract;
import android.widget.Button;

public class ExchangeOut {
    private String name;
    private String ImageId;

    public ExchangeOut(String name, String imageId) {
        this.name = name;
        ImageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }
}
