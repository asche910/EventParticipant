package com.ep.eventparticipant.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.Item.Exchangeitem;
import com.ep.eventparticipant.MyApplication;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.activity.OtherActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ViewHolder> {
    private List<Exchangeitem> exchangeitemList;
    private Bitmap bitmap;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thing;
        TextView information;
        Button button;
        public ViewHolder (View view){
            super(view);
            thing=(ImageView)view.findViewById(R.id.item_view);
            information=(TextView)view.findViewById(R.id.item_text);
            button=(Button)view.findViewById(R.id.item_button);
        }
    }
    public ExchangeAdapter(List<Exchangeitem> exchangeitemList){
        this.exchangeitemList=exchangeitemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.exchange_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        if (context==null){
            context=parent.getContext();
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("tuao");
                MyApplication.getContext().sendBroadcast(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Exchangeitem exchangeitem=exchangeitemList.get(position);
        Glide.with(context).load(exchangeitem.getID()).into(holder.thing);
        //holder.thing.setImageBitmap(returnBitMap(exchangeitem.getUrl()));
        holder.information.setText(exchangeitem.getStoptime());
        holder.thing.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), OtherActivity.class);
                intent.putExtra("a",position);
                v.getContext().startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation((Activity)v.getContext(),v,"sharedView").toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return exchangeitemList.size();
    }
    public Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }

}
