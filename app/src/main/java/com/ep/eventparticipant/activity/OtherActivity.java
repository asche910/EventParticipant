package com.ep.eventparticipant.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.Item.All_item;
import com.ep.eventparticipant.Item.Exchangeitem;
import com.ep.eventparticipant.R;

import static com.ep.eventparticipant.activity.FindThing.allItems;
import static com.ep.eventparticipant.fragment.FragmentSwap.exchangeitemList;
import static com.ep.eventparticipant.others.AsHttpUtils.all_items;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ImageView imageView=(ImageView)findViewById(R.id.image_view_2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(OtherActivity.this);
            }
        });
        TextView textView=(TextView)findViewById(R.id.yonghuID);
        TextView textView1=(TextView)findViewById(R.id.jiaoyididian);
        TextView textView2=(TextView)findViewById(R.id.wupinmiaoshu);
        TextView textView3=(TextView)findViewById(R.id.gujia);
        TextView textView4=(TextView)findViewById(R.id.lianxifangsi);
        Intent intent=getIntent();
        int i=intent.getIntExtra("a",0);
        int j=intent.getIntExtra("b",-1);
if(j==-1){
        Exchangeitem exchangeitem=exchangeitemList.get(i);
        String userID=exchangeitem.getUserID();
        String place=exchangeitem.getPlace();
        String miaoshu=exchangeitem.getMiaoshu();
        int gujia=exchangeitem.getGujia();
        String phone=exchangeitem.getPhone();
        if (exchangeitem.getUrl()!=null){
        Glide.with(OtherActivity.this).load(exchangeitem.getUrl()).into(imageView);}
        else {
            Glide.with(OtherActivity.this).load(exchangeitem.getID()).into(imageView);
        }
        textView.setText(exchangeitem.getUserID());
        textView1.setText(exchangeitem.getPlace());
        textView2.setText(exchangeitem.getMiaoshu());
        textView3.setText(exchangeitem.getGujia()+"");
        textView4.setText(exchangeitem.getPhone());
    }
    else{
    if(allItems!=null){
        try {


    All_item all_item= allItems.get(j);
    textView.setText("12543");
    textView1.setText(all_item.getAddress());
    textView2.setText(all_item.getExpect());
    textView3.setText(all_item.getPrice());
    textView4.setText("1010");
    Glide.with(this).load(all_item.getImageurl()).into(imageView);}
    catch (Exception e){Toast.makeText(this,"加载失败",Toast.LENGTH_LONG).show();}}
    else{

    }
    }
    }



        }




