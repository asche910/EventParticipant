package com.ep.eventparticipant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.UserExchangeOutAdapter;
import com.ep.eventparticipant.object.ExchangeOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ep.eventparticipant.MyApplication.getContext;
import static com.ep.eventparticipant.activity.MainActivity.resourceIdToUri;

public class exchangeout extends AppCompatActivity {
    public static List<ExchangeOut> exchangeOuts = new ArrayList<>();
    private Button return_;

    static {
     /*   for (int i =0 ;i<2;i++) {
            ExchangeOut exchangeOut1 = new ExchangeOut("索尼vaio笔记本电脑", R.drawable.computer + "");
            exchangeOuts.add(exchangeOut1);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangeout);
        inits();
        return_ = findViewById(R.id.return_);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.exchangeout_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UserExchangeOutAdapter adapter = new UserExchangeOutAdapter(exchangeOuts);
        recyclerView.setAdapter(adapter);
    }
    private void inits(){
        //第一个参数为id是随机数，到时候对应也是随机数对应

    }
}
