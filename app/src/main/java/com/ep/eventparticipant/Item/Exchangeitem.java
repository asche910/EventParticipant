package com.ep.eventparticipant.Item;

import java.net.URL;

public class Exchangeitem {
    //private String url;
    private String stoptime;
    private int ID;

   // public String getUrl() {
    //    return url;
  //  }

    public String getStoptime() {
        return stoptime;
    }

   //    this.url = url;
   // }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }
    //public  Exchangeitem(String url,String stoptime){
      //  this.url=url;
    //    this.stoptime=stoptime;
  //  }
    public Exchangeitem(int id,String stoptime){
        ID=id;
        this.stoptime=stoptime;
    }

    public int getID() {
        return ID;
    }
}
