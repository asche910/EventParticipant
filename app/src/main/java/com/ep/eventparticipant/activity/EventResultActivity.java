package com.ep.eventparticipant.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.HomePopularAdapter;
import com.ep.eventparticipant.object.EventBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

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

/**
 * @author As_
 * @since 2018/07/24
 * @email apknet@163.com
 * @github https://github.com/apknet
 *
 */


public class EventResultActivity extends AppCompatActivity {

    TextView textTitle, textEmpty;
    Button btnBack;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    HomePopularAdapter adapter;

    private int listType;
    private List<EventBean> list = new ArrayList<>();
    private List<EventBean> visibleList = new ArrayList<>();
    // 当前显示多少条
    private int index = 0;

    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_result);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        textTitle = findViewById(R.id.result_text_title);
        textEmpty = findViewById(R.id.result_text_empty);
        btnBack = findViewById(R.id.result_btn_back);
        recyclerView = findViewById(R.id.recyclerview_result);
        refreshLayout = findViewById(R.id.refreshLayout_event);

        listType = getIntent().getIntExtra("ListType", EVENT_LIST);
        switch (listType){
            case SEARCH_LIST:
                list = mySearchList;
                textTitle.setText("搜索结果");
                break;
            case JOINED_LIST:
                list = myJoinedList;
                textTitle.setText("参加的活动");
                break;
            case CREATED_LIST:
                list = myCreatedList;
                break;
            case EVENT_LIST:
                list = eventBeanList;
                break;
        }

        if (index + 10 >= list.size()) {
            visibleList.addAll(list.subList(index, list.size()));
            index = list.size();
        }else{
            visibleList.addAll(list.subList(index, (index += 10)));
        }

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new HomePopularAdapter(visibleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new HomePopularAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {

                if(listType == CREATED_LIST){
                    Intent intent_ = new Intent(getApplicationContext(), EventNewActivity.class);
                    intent_.putExtra("Position", position);
                    startActivity(intent_);
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                intent.putExtra("ListType", listType);
                intent.putExtra("Position", position);
                startActivity(intent);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(list.isEmpty()){
            textEmpty.setVisibility(View.VISIBLE);
        }

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(200);

                if (index + 1 >= list.size()){
                    refreshLayout.finishLoadMore(true);
                    return;
                }

                if (index + 10 >= list.size()) {
                    visibleList.addAll(list.subList(index, list.size()));
                }else {
                    visibleList.addAll(list.subList(index, (index + 10)));
                }

                adapter.notifyItemRangeInserted(index, (index += 10));
            }
        });
    }
}
