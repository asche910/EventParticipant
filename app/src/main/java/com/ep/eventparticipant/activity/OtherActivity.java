package com.ep.eventparticipant.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.Item.Exchangeitem;
import com.ep.eventparticipant.R;

import static com.ep.eventparticipant.fragment.FragmentSwap.exchangeitemList;

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
        Intent intent=getIntent();
        int i=intent.getIntExtra("a",0);
        Exchangeitem exchangeitem=exchangeitemList.get(i);
        Glide.with(OtherActivity.this).load(exchangeitem.getID()).into(imageView);
    }
}
