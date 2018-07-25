package com.ep.eventparticipant.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.eventparticipant.MyApplication;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.activity.EventActivity;
import com.ep.eventparticipant.adapter.HomePopularAdapter;
import com.ep.eventparticipant.adapter.HomeSuggestAdapter;
import com.ep.eventparticipant.object.EventBean;
import com.ep.eventparticipant.other.MyChangeScrollView;
import com.ep.eventparticipant.other.ViewPagerClass;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.constraint.Constraints.TAG;
import static com.ep.eventparticipant.activity.MainActivity.resourceIdToUri;

public class FragmentHome extends Fragment implements View.OnClickListener{
    ViewPagerClass viewPagerClass;
    public static List<View> viewList = new ArrayList<>();

    ScrollView scrollView;

    public static FloatingActionsMenu fAM_stable;
    public static FloatingActionsMenu fAM_scroll;
    FloatingActionButton fabScrollNew, fabStableNew;
    ImageView imgSearch;
    public static EditText editTitle;
    TextView textTitle;
    private boolean isEditFocus;

    //监听 -> floatBtn 的悬浮
    RelativeLayout layout;
    MyChangeScrollView myChangeScrollView;

    public static InputMethodManager inputMethodManager;

    RecyclerView recycPopular, recycSuggest;
    LinearLayoutManager layoutPopularManager;
    GridLayoutManager layoutSuggestManager;
    HomePopularAdapter homePopularAdapter;
    HomeSuggestAdapter homeSuggestAdapter;


    public static List<EventBean> eventBeanList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (viewList.isEmpty()) {
            init();
        }
        return  inflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPagerClass = getActivity().findViewById(R.id.viewpager_home);
        viewPagerClass.setViewPagerViews(viewList);

        scrollView = getActivity().findViewById(R.id.scrollView_home);
        fAM_stable = getActivity().findViewById(R.id.float_stable);
        fAM_scroll = getActivity().findViewById(R.id.float_scroll);
        fabScrollNew = getActivity().findViewById(R.id.fab_scroll_new);
        fabStableNew = getActivity().findViewById(R.id.fab_stable_new);

        imgSearch = getActivity().findViewById(R.id.home_img_search);
        textTitle = getActivity().findViewById(R.id.home_text_title);
        editTitle = getActivity().findViewById(R.id.home_edit_title);

        layout = getActivity().findViewById(R.id.home_layout_top);
        myChangeScrollView = getActivity().findViewById(R.id.myscroll);

        recycPopular = getActivity().findViewById(R.id.recyclerview_home_popular);
        recycSuggest = getActivity().findViewById(R.id.recyclerview_home_suggest);
        layoutPopularManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutSuggestManager = new GridLayoutManager(getContext(), 2);
        homePopularAdapter = new HomePopularAdapter(eventBeanList);
        homeSuggestAdapter = new HomeSuggestAdapter(eventBeanList);
        recycPopular.setLayoutManager(layoutPopularManager);
        recycSuggest.setLayoutManager(layoutSuggestManager);
        recycPopular.setAdapter(homePopularAdapter);
        recycSuggest.setAdapter(homeSuggestAdapter);

        myChangeScrollView.setupTitleView(layout);

        fabScrollNew.setOnClickListener(this);
        fabStableNew.setOnClickListener(this);

        homePopularAdapter.setOnClickListener(new HomePopularAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), EventActivity.class);
                startActivity(intent);
            }
        });

        imgSearch.setOnClickListener(this);

        editTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Toast.makeText(getContext(), "获得了焦点！", Toast.LENGTH_SHORT).show();
                    isEditFocus = true;
                }else{
                    Toast.makeText(getContext(), "失去了焦点！", Toast.LENGTH_SHORT).show();
                    isEditFocus = false;
                    textTitle.setVisibility(View.VISIBLE);
                    editTitle.setVisibility(View.GONE);
                }
            }
        });

    }


    private  void init(){

        ImageView img_1 = new ImageView(getContext());
        img_1.setBackgroundResource(R.drawable.bg_home_1);

        ImageView img_2 = new ImageView(getContext());
        img_2.setBackgroundResource(R.drawable.bg_home_2);

        ImageView img_3 = new ImageView(getContext());
        img_3.setBackgroundResource(R.drawable.bg_home_3);

        ImageView img_4 = new ImageView(getContext());
        img_4.setBackgroundResource(R.drawable.bg_home_4);

        ImageView img_5 = new ImageView(getContext());
        img_5.setBackgroundResource(R.drawable.bg_home_5);

        viewList.add(img_1);
        viewList.add(img_2);
        viewList.add(img_3);
        viewList.add(img_4);
        viewList.add(img_5);

        EventBean eventBean_1 = new EventBean(new Random(System.currentTimeMillis()).nextInt(), "BIBF国际绘本展门票及会员卡",
                "2018.7.25", "2018.07.30", "上海浦东", "note......", resourceIdToUri(getContext(), R.drawable.bg_home_2).toString(),
                666, 99, "Lihua, Asche_, Job", false);

        EventBean eventBean_2 = new EventBean(new Random().nextInt(), "BIBF国际绘本展门票及会员卡",
                "2018.7.25", "2018.07.30", "上海浦东", "note......", resourceIdToUri(getContext(), R.drawable.bg_home_3).toString(),
                666, 99, "Lihua, Asche_, Job", false);
        eventBeanList.add(eventBean_1);
        eventBeanList.add(eventBean_2);

        inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_stable_new:
            case R.id.fab_scroll_new:
                Intent intent = new Intent(getContext(), EventActivity.class);
                startActivity(intent);
                break;
            case R.id.home_img_search:
                if (!isEditFocus) {
                    textTitle.setVisibility(View.GONE);
                    editTitle.setVisibility(View.VISIBLE);

                    editTitle.setFocusable(true);
                    editTitle.setFocusableInTouchMode(true);
                    editTitle.requestFocus();
                    inputMethodManager.showSoftInput(editTitle, 0);
                }else{
                    if(editTitle.getText().toString().equals("")){
                        Toast.makeText(MyApplication.getContext(), "请输入要搜索的内容！", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "搜索中， 请等待...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
