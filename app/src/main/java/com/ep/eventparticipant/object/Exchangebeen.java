package com.ep.eventparticipant.object;

import android.net.UrlQuerySanitizer;

import java.net.URL;

public class Exchangebeen {
    private String time;
    private String place;
    private String need;
    private String id;
    private String contactWay;
    private URL url;

    public String getContactWay() {
        return contactWay;
    }

    public String getId() {
        return id;
    }

    public String getNeed() {
        return need;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public URL getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContactWay(String contactWay) {

        this.contactWay = contactWay;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
