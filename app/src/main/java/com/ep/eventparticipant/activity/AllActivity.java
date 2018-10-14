package com.ep.eventparticipant.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.fragment.BookFragment;
import com.ep.eventparticipant.fragment.PhoneFragment;
import com.ep.eventparticipant.fragment.digitalFragment;
import com.ep.eventparticipant.fragment.furnitureFragment;

public class AllActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView book,furniture,digitaldimentia,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        book=(TextView)findViewById(R.id.book);
        furniture=(TextView)findViewById(R.id.furniture);
        digitaldimentia=(TextView)findViewById(R.id.digitaldementia);
        phone=(TextView)findViewById(R.id.phone);
        phone.setBackgroundColor(Color.parseColor("#FFFFFF"));
        book.setOnClickListener(this);
        furniture.setOnClickListener(this);
        digitaldimentia.setOnClickListener(this);
        phone.setOnClickListener(this);
        replaceFragment(new PhoneFragment());
        ImageView imageView=(ImageView)findViewById(R.id.finish);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phone:  replaceFragment(new PhoneFragment());runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phone.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    book.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    digitaldimentia.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    furniture.setBackgroundColor(Color.parseColor("#d3cfcf"));

                }
            });break;
            case R.id.book: replaceFragment(new BookFragment());runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phone.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    book.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    digitaldimentia.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    furniture.setBackgroundColor(Color.parseColor("#d3cfcf"));
                }
            });break;
            case R.id.digitaldementia:replaceFragment(new digitalFragment()); runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phone.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    book.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    digitaldimentia.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    furniture.setBackgroundColor(Color.parseColor("#d3cfcf"));

                }
            });break;
            case R.id.furniture:replaceFragment(new furnitureFragment());runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    phone.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    book.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    digitaldimentia.setBackgroundColor(Color.parseColor("#d3cfcf"));
                    furniture.setBackgroundColor(Color.parseColor("#FFFFFF"));

                }
            });break;
        }

    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.all_fragment,fragment);
        transaction.commit();
    }
}
