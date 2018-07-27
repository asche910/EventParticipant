package com.ep.eventparticipant.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.EventBean;

import java.util.ArrayList;
import java.util.List;

import static com.ep.eventparticipant.fragment.FragmentHome.CREATED_LIST;
import static com.ep.eventparticipant.fragment.FragmentHome.EVENT_LIST;
import static com.ep.eventparticipant.fragment.FragmentHome.JOINED_LIST;
import static com.ep.eventparticipant.fragment.FragmentHome.SEARCH_LIST;
import static com.ep.eventparticipant.fragment.FragmentHome.eventBeanList;
import static com.ep.eventparticipant.fragment.FragmentHome.myCreatedList;
import static com.ep.eventparticipant.fragment.FragmentHome.myJoinedList;
import static com.ep.eventparticipant.fragment.FragmentHome.mySearchList;

public class EventActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textName, textCount, textTime, textWhere, textNote, textOrgName, textOrgTel, textOrgNote;
    ImageView imgEvent, imgHeader;
    Button btnJoin, btnBack, btnShare;

    private int index;
    private int listType;
    private List<EventBean> list = new ArrayList<>();
    private EventBean eventBean;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();

        if(index != -1){
            eventBean = list.get(index);
            Glide.with(getApplicationContext())
                    .load(eventBean.getImgUri())
                    .apply(new RequestOptions()
                            .centerCrop())
                    .into(imgEvent);

            Glide.with(getApplicationContext())
                    .load(eventBean.getOrganizerHeader())
                    .into(imgHeader);

            textName.setText(eventBean.getName());
            textCount.setText("已参加人数" + eventBean.getPersonCount());
            textTime.setText(eventBean.getStartTime() + " - " + eventBean.getEndTime());
            textWhere.setText(eventBean.getWhere());
            textNote.setText(eventBean.getNote());
            textOrgName.setText(eventBean.getOrganizerName());
            textOrgTel.setText(eventBean.getOrganizerTel());
            textOrgNote.setText(eventBean.getOrganizerNote());
        }

    }

    void init(){
        textName = findViewById(R.id.event_text_name);
        textCount = findViewById(R.id.event_text_count);
        textTime = findViewById(R.id.event_text_time);
        textWhere = findViewById(R.id.event_text_where);
        textNote = findViewById(R.id.event_text_note);
        textOrgName = findViewById(R.id.event_text_org_name);
        textOrgTel = findViewById(R.id.event_text_org_tel);
        textOrgNote = findViewById(R.id.event_text_org_note);

        imgEvent = findViewById(R.id.event_img);
        imgHeader = findViewById(R.id.event_img_header);

        btnBack = findViewById(R.id.event_btn_back);
        btnShare = findViewById(R.id.event_btn_share);
        btnJoin = findViewById(R.id.event_btn_join);

        btnBack.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnJoin.setOnClickListener(this);

        index = getIntent().getIntExtra("Position", -1);
        listType = getIntent().getIntExtra("ListType", EVENT_LIST);
        switch (listType){
            case SEARCH_LIST:
                list = mySearchList;
                break;
            case JOINED_LIST:
                list = myJoinedList;
                break;
            case CREATED_LIST:
                list = myCreatedList;
                break;
            case EVENT_LIST:
                list = eventBeanList;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.event_btn_join:
                Toast.makeText(this, "报名成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.event_btn_share:
                Toast.makeText(this, "正在分享中......", Toast.LENGTH_SHORT).show();
                break;
            case R.id.event_btn_back:
                finish();
                break;
        }
    }
}
