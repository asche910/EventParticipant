package com.ep.eventparticipant.activity;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ep.eventparticipant.Item.Informention_item;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.ListviewAdapter;
import com.ep.eventparticipant.been.Information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ExchangeInformation extends AppCompatActivity {
    public static List<Informention_item> informention_itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_information);
        //informention_itemList.clear();
        ListviewAdapter adapter = new ListviewAdapter(ExchangeInformation.this, R.layout.informention_item, informention_itemList);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        ImageView back = (ImageView) findViewById(R.id.back_2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Information> information = DataSupport.findAll(Information.class);
//////
        for (Information information1 : information) {
            if (information1.getImageurl() != null) {
                String url = information1.getImageurl();
                String place = information1.getPlace();
                String userid = information1.getUserid();
                String time = information1.getTime();
                int price = information1.getPrice();
                String name = information1.getName();
                informention_itemList.add(new Informention_item(name, url, time, place, userid, price));
            } else {
                String place = information1.getPlace();
                String userid = information1.getUserid();
                String time = information1.getTime();
                int price = information1.getPrice();
                String name = information1.getName();
                int id = information1.getID();
                informention_itemList.add(new Informention_item(name, id, time, place, userid, price));
            }
        }
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }//这样可以在主线程中发起网络亲求

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void parseJSON(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            //  for (int i=0;i<jsonArray.length();i++){
            //  JSONObject jsonObject=jsonArray.getJSONObject(i);

            int code = jsonArray.getJSONObject(0).getInt("status");
            JSONArray js = jsonArray.getJSONArray(1);
            JSONArray ja = js.getJSONArray(8);
            //int code=jsonObject.getInt("status");
            //JSONArray ja = jsonObject.getJSONObject("data").getJSONArray("list");
            if (code == 0) {
                // informention_itemList.clear();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject object = ja.getJSONObject(i);
                    String imageUrl = null;
                    imageUrl = object.getString("imageUrl");

                    String name = object.getString("name");
                    String time = object.getString("time");
                    // String address="武汉科技大学";
                    String address = object.getString("address");
                    String phone = object.getString("phone");
                    String expect = object.getString("expect");
                    informention_itemList.add(new Informention_item(name, imageUrl, time, address, "123", 100));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String exchangeinformation;
                    Request request = new Request.Builder().url("http://120.79.137.167:8080/firstProject/exchange/list.do").build();
                    Response response = client.newCall(request).execute();
                    exchangeinformation = response.body().string();
//                    Toast.makeText(ExchangeInformation.this,"hahaah",Toast.LENGTH_LONG).show();
                    parseJSON(exchangeinformation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
