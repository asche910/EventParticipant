package com.ep.eventparticipant.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.UserExchangeInAdapter;
import com.ep.eventparticipant.object.ExchangeIn;
import com.ep.eventparticipant.object.ExchangeOut;

import java.util.ArrayList;
import java.util.List;

public class exchangein extends AppCompatActivity {
    private List<ExchangeIn> exchangeIns = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangein);
        inits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.exchangein_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UserExchangeInAdapter adapter = new UserExchangeInAdapter(exchangeIns);
        recyclerView.setAdapter(adapter);
    }
    private  void inits(){
        for(int i=0;i<5;i++){
            ExchangeIn exchangein = new ExchangeIn("索尼vaio笔记本电脑",R.drawable.computer);
            exchangeIns.add(exchangein);
        }
    }
}
