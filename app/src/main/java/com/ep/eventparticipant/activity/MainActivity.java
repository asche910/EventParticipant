package com.ep.eventparticipant.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ep.eventparticipant.MyApplication;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.FragAdapter;
import com.ep.eventparticipant.fragment.FragmentHome;
import com.ep.eventparticipant.fragment.FragmentSwap;
import com.ep.eventparticipant.fragment.FragmentUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ep.eventparticipant.fragment.FragmentHome.editTitle;
import static com.ep.eventparticipant.fragment.FragmentHome.inputMethodManager;
import static com.ep.eventparticipant.fragment.FragmentHome.isShouldHideInput;
import static com.ep.eventparticipant.fragment.FragmentHome.viewList;

/**
 * @author As_
 * @since 2018/07/24
 * @email apknet@163.com
 * @github https://github.com/apknet
 *
 */

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    List<Fragment> fragmentList = new ArrayList<>();
    FragAdapter fragAdapter;
    BottomNavigationBar bottomNavigationBar;
    BottomNavigationItem bottomNavigationItemHome;
    BottomNavigationItem bottomNavigationItemSwap;
    BottomNavigationItem bottomNavigationItemUser;

    //从资源文件获得对应Uri
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

//            1.登陆
    public static final String LOGIN_URL = "http://120.79.137.167:8080/firstProject/user/login.do";
//            2.注册
    public static final String LOGUP_URL = "http://120.79.137.167:8080/firstProject/user/register.do";


//    <<<-- - - - - - - - - - - - - - - - - - - -分割线 - - - - - - - - - -  - - - - - - - - -->>>>


//            1、发布活动
    public static final String CREATE_ACTIVITY = "http://120.79.137.167:8080/firstProject/activity/release.do";
//            2、删除活动
    public static final String DELETE_ACTIVITY = "http://120.79.137.167:8080/firstProject/activity/delete.do";
//            3、搜索活动
    public static final String SEARCH_ACTIVITY = "http://120.79.137.167:8080/firstProject/search.do";
//            4、活动列表
    public static final String ALL_ACTIVITY = "http://120.79.137.167:8080/firstProject/activity/List.do";
//            5、更新活动
    public static final String UPDATE_ACTIVITY = "http://120.79.137.167:8080/firstProject/activity/update_information.do";
//            6、报名活动
    public static final String JOIN_ACTIVITY = "http://120.79.137.167:8080/firstProject/activity_member/sign_up.do";
//            7、取消报名
    public static final String CANCEL_ACTIVITY  = " http://120.79.137.167:8080/firstProject/activity_member/cancel_sign_up.do";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            |  View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.WHITE);

            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        viewPager = findViewById(R.id.viewpager_main);
        fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentSwap());
        fragmentList.add(new FragmentUser());
        fragAdapter = new FragAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragAdapter);
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setMode(BottomNavigationBar.MODE_SHIFTING);
//        此处会修改字体颜色
//                .setBarBackgroundColor("#1ccbae");
        bottomNavigationItemHome = new BottomNavigationItem(R.drawable.ic_nav_home_press, "Home")
                .setActiveColor(Resources.getSystem().getColor(android.R.color.holo_blue_light))
                .setInactiveIconResource(R.drawable.ic_nav_home);
        bottomNavigationItemSwap = new BottomNavigationItem(R.drawable.ic_nav_swap_press, "Swap")
                .setActiveColor(Resources.getSystem().getColor(android.R.color.holo_green_light))
                .setInactiveIconResource(R.drawable.ic_nav_swap);
        bottomNavigationItemUser = new BottomNavigationItem(R.drawable.ic_nav_user_press, "User")
                .setActiveColor(Color.parseColor("#FF01B5AC"))
                .setInactiveIconResource(R.drawable.ic_nav_user);

        bottomNavigationBar.addItem(bottomNavigationItemHome);
        bottomNavigationBar.addItem(bottomNavigationItemSwap);
        bottomNavigationBar.addItem(bottomNavigationItemUser);

        bottomNavigationBar.initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                switch (i) {
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                }
            }
            @Override
            public void onTabUnselected(int i) {
            }
            @Override
            public void onTabReselected(int i) {
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                editTitle.setFocusable(false);
                editTitle.setFocusableInTouchMode(false);
                editTitle.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


    public static int getRandomId(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        //随机生6位数Id
        for(int i = 0; i< 6; i++){
            stringBuilder.append(random.nextInt(10));
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 2:
                    bottomNavigationBar.selectTab(2);
                    break;
                case 1:
                    bottomNavigationBar.selectTab(1);
                    break;
                case 0:
                    bottomNavigationBar.selectTab(0);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
