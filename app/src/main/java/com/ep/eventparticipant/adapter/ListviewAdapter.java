package com.ep.eventparticipant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.Item.Informention_item;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.others.HttpUtil;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ep.eventparticipant.activity.ExchangeInformation.informention_itemList;

public class ListviewAdapter extends ArrayAdapter<Informention_item> {
    private int imageID;
    public ListviewAdapter(Context context, int textID, List<Informention_item> objects){
        super(context,textID,objects);
        imageID=textID;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Informention_item informention_item=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(imageID,parent,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.image);
        Button button=(Button)view.findViewById(R.id.querenjiaoyi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client=new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("status", "1").build();
                            Request request = new Request.Builder().url(String.format("http://120.79.137.167:8080/firstProject/exchange/confirm_status.do?userId=%s&exchangeId=%s",informention_item.getUserID(),"1")).
                                    post(requestBody).build();
                            Response response=client.newCall(request).execute();
                            String responsedata=response.body().string();


                        } catch (IOException e) {
                            Toast.makeText(v.getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(v.getContext(),"交易成功",Toast.LENGTH_SHORT).show();
                informention_itemList.remove(informention_item);





            } });
        TextView textView=(TextView)view.findViewById(R.id.name);
        if (informention_item.getUrl()!=null){
        Glide.with(getContext()).load(informention_item.getUrl()).into(imageView);}
        else{
            Glide.with(getContext()).load(informention_item.getID()).into(imageView);
        }
        textView.setText(informention_item.getName());
        TextView textView1=(TextView)view.findViewById(R.id.xinxi);
        textView1.setText("时间:"+informention_item.getShijian()+"\n"+"地点:"+informention_item.getPlace()+"\n"+"交易人ID:"+informention_item.getUserID());
        return view;
    }
    class  GameThread implements Runnable{
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }

            }
        }
    }
}
