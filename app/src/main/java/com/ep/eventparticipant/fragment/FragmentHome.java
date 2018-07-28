package com.ep.eventparticipant.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
import com.ep.eventparticipant.activity.EventNewActivity;
import com.ep.eventparticipant.activity.EventResultActivity;
import com.ep.eventparticipant.adapter.HomePopularAdapter;
import com.ep.eventparticipant.adapter.HomeSuggestAdapter;
import com.ep.eventparticipant.object.EventBean;
import com.ep.eventparticipant.other.AsHttpUtils;
import com.ep.eventparticipant.other.MyChangeScrollView;
import com.ep.eventparticipant.other.ViewPagerClass;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;

import static android.support.constraint.Constraints.TAG;
import static com.ep.eventparticipant.activity.MainActivity.getRandomId;
import static com.ep.eventparticipant.activity.MainActivity.resourceIdToUri;
import static com.zhihu.matisse.internal.utils.PathUtils.getDataColumn;
import static com.zhihu.matisse.internal.utils.PathUtils.isDownloadsDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isExternalStorageDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isMediaDocument;

/**
 * @author As_
 * @since 2018/07/24
 * @email apknet@163.com
 * @github https://github.com/apknet
 *
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    ViewPagerClass viewPagerClass;
    public static List<View> viewList = new ArrayList<>();

    ScrollView scrollView;

    public static FloatingActionsMenu fAM_stable;
    public static FloatingActionsMenu fAM_scroll;
    FloatingActionButton fabScrollNew, fabStableNew, fabScrollCreated, fabStableCreated, fabScrollJoin, fabStableJoin;
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
    public static List<EventBean> myCreatedList = new ArrayList<>();
    public static List<EventBean> myJoinedList = new ArrayList<>();
    public static List<EventBean> mySearchList = new ArrayList<>();

    //分别对应上述四个list
    public static final int EVENT_LIST = 0;
    public static final int CREATED_LIST = 1;
    public static final int JOINED_LIST = 2;
    public static final int SEARCH_LIST = 3;

    //判断标题输入内容
    boolean isNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (viewList.isEmpty()) {
            init();
        }
        return inflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);
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
        fabScrollCreated = getActivity().findViewById(R.id.fab_scroll_created);
        fabStableCreated = getActivity().findViewById(R.id.fab_stable_created);
        fabScrollJoin = getActivity().findViewById(R.id.fab_scroll_join);
        fabStableJoin = getActivity().findViewById(R.id.fab_stable_join);

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
        fabScrollCreated.setOnClickListener(this);
        fabStableCreated.setOnClickListener(this);
        fabScrollJoin.setOnClickListener(this);
        fabStableJoin.setOnClickListener(this);

        homePopularAdapter.setOnClickListener(new HomePopularAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), EventActivity.class);
                intent.putExtra("ListType", EVENT_LIST);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });
        homeSuggestAdapter.setOnClickListener(new HomeSuggestAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), EventActivity.class);
                intent.putExtra("ListType", EVENT_LIST);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });

        imgSearch.setOnClickListener(this);

        editTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(getContext(), "获得了焦点！", Toast.LENGTH_SHORT).show();
                    isEditFocus = true;
                } else {
                    Toast.makeText(getContext(), "失去了焦点！", Toast.LENGTH_SHORT).show();
                    isEditFocus = false;
                    textTitle.setVisibility(View.VISIBLE);
                    editTitle.setVisibility(View.GONE);
                }
            }
        });

    }

    private void init() {

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

        EventBean eventBean_1 = new EventBean(getRandomId(), "BIBF国际绘本展门票及会员卡",
                "2018.7.25", "2018.07.30", "上海浦东", "北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心",
                resourceIdToUri(getContext(), R.drawable.bg_home_2).toString(),
                666, resourceIdToUri(getContext(), R.drawable.bg_home_2).toString(), "AFIH组委会_1", "电话：10086100861", "北京城市艺术博览会\\nAFIH重点在于建设一个不同以往的博览会模式，准备...",
                99, "Lihua, Asche_, Job", false);

        EventBean eventBean_2 = new EventBean(getRandomId(), "BIBF国际绘本展门票及会员卡",
                "2018.7.25", "2018.07.30", "上海浦东", "北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心",
                resourceIdToUri(getContext(), R.drawable.bg_home_3).toString(),
                666, resourceIdToUri(getContext(), R.drawable.bg_home_2).toString(), "AFIH组委会_2", "电话：10086100861", "北京城市艺术博览会\\nAFIH重点在于建设一个不同以往的博览会模式，准备...",
                99, "Lihua, Asche_, Job", false);

        EventBean eventBean_3 = new EventBean(getRandomId(), "BIBF国际绘本展门票及会员卡",
                "2018.7.25", "2018.07.30", "上海浦东", "北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心",
                resourceIdToUri(getContext(), R.drawable.bg_home_1).toString(),
                666,resourceIdToUri(getContext(), R.drawable.bg_home_2).toString(), "AFIH组委会_3", "电话：10086100861", "北京城市艺术博览会\\nAFIH重点在于建设一个不同以往的博览会模式，准备...",
                99, "Lihua, Asche_, Job", false);

        EventBean eventBean_4 = new EventBean(getRandomId(), "BIBF国际绘本展门票及会员卡",
                "2018.7.25", "2018.07.30", "上海浦东", "北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心北京顺义京新国际展览中心",
                resourceIdToUri(getContext(), R.drawable.bg_home_5).toString(),
                666,resourceIdToUri(getContext(), R.drawable.bg_home_2).toString(),  "AFIH组委会_4", "电话：10086100861", "北京城市艺术博览会\\nAFIH重点在于建设一个不同以往的博览会模式，准备...",
                99, "Lihua, Asche_, Job", false);


        eventBeanList.add(eventBean_1);
        eventBeanList.add(eventBean_2);
        eventBeanList.add(eventBean_3);
        eventBeanList.add(eventBean_4);

        myCreatedList.addAll(eventBeanList);
        myCreatedList.remove(3);
        myCreatedList.remove(2);

        myJoinedList.addAll(eventBeanList);
        myJoinedList.remove(3);


        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_stable_join:
            case R.id.fab_scroll_join:
                Intent intent_ = new Intent(getContext(), EventResultActivity.class);
                intent_.putExtra("ListType", JOINED_LIST);
                startActivity(intent_);
                break;
            case R.id.fab_stable_created:
            case R.id.fab_scroll_created:
                Intent intent_1 = new Intent(getContext(), EventResultActivity.class);
                intent_1.putExtra("ListType", CREATED_LIST);
                startActivity(intent_1);
                break;
            case R.id.fab_stable_new:
            case R.id.fab_scroll_new:
                Intent intent = new Intent(getContext(), EventNewActivity.class);
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
                } else {
                    if (editTitle.getText().toString().equals("")) {
                        Toast.makeText(MyApplication.getContext(), "请输入要搜索的内容！", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(getContext(), "搜索中， 请等待...", Toast.LENGTH_SHORT).show();

                        try {
                            Integer.parseInt(editTitle.getText().toString());
                            isNum = true;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            isNum = false;
                        }

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AsHttpUtils.searchActivity(editTitle.getText().toString(), isNum);
                            }
                        });
                        thread.start();
                        try {
                            thread.join(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent_3 = new Intent(getContext(), EventResultActivity.class);
                        intent_3.putExtra("ListType", SEARCH_LIST);
                        startActivity(intent_3);

                    }
                }
                break;
        }
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
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


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id
                        = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

}
