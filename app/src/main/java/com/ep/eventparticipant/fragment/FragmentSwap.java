package com.ep.eventparticipant.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.acker.simplezxing.activity.CaptureActivity;
import com.ep.eventparticipant.Item.Exchangeitem;
import com.ep.eventparticipant.Item.Informention_item;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.activity.AllActivity;
import com.ep.eventparticipant.activity.ExchangeInformation;
import com.ep.eventparticipant.activity.FindThing;
import com.ep.eventparticipant.activity.PublishActivity;
import com.ep.eventparticipant.adapter.ExchangeAdapter;
import com.ep.eventparticipant.other.GlideImageLoader;
import com.ep.eventparticipant.other.AsHttpUtils;


import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.ep.eventparticipant.activity.ExchangeInformation.informention_itemList;


public class FragmentSwap extends Fragment {
    private View view;

    private EditText editText;
    private Banner banner;
    private List<Integer> imgs = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    public static List<Exchangeitem> exchangeitemList = new ArrayList<>();
    private static int i;
    private IntentFilter intentFilter;
    private Recieveboaster recieveboaster;
    private Window window;
    private ImageView saoma;
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private Handler handler = new Handler();

    private ExchangeAdapter adapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.swapmenu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getContext()).inflate(R.layout.fragment_swap, container, false);


        exchangeitemList.clear();


        new Thread(new Runnable() {
            int code;

            @Override
            public void run() {
                code = AsHttpUtils.ExchangeList();
                Log.d("Tag", String.valueOf(code));

            }
        }).start();


        editText = (view).findViewById(R.id.alertDialog_et);
        editText.setFocusable(false);
        editText.getBackground().setAlpha(100);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindThing.class);
                startActivity(intent);
            }
        });
        initdraw();

        LitePal.getDatabase();

        saoma = view.findViewById(R.id.saoma);
        saoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Scan Activity
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }


            }
        });



        intentFilter = new IntentFilter();
        intentFilter.addAction("tuao");
        recieveboaster = new Recieveboaster();
        getActivity().registerReceiver(recieveboaster, intentFilter);

        banner = view.findViewById(R.id.scan);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imgs);
        banner.setBannerTitles(title);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        banner.setDelayTime(2000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        window = getActivity().getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);   //这里动态修改颜色
//        }

        ImageView all = view.findViewById(R.id.yijiaohuan);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private static String[] text = new String[]{"发布我的物品", "2222222", "3333333"

    };
    private static int index = 0;

    static String getext() {
        if (index >= text.length) index = 0;
        return text[index++];

    }

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    private static int[] imageResources = new int[]{R.drawable.ic_storage_black_24dp, R.drawable.ic_terrain_black_24dp};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        //ImageView image_View=(view).findViewById(R.id.xuanku);
        // Glide.with(getContext()).load(R.drawable.tu_8).into(image_View);
        //sendRequestWithOkHttp();
        RecyclerView recyclerView = (view).findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
         adapter = new ExchangeAdapter(exchangeitemList);
        recyclerView.setAdapter(adapter);
        ImageView fabiao = (view).findViewById(R.id.fabiao);
        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageView = (view).findViewById(R.id.jiaohuanxinxi);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExchangeInformation.class);
                startActivity(intent);
            }
        });


//发送网络请求
//        HttpUtil.sendHttpRequest("http://120.79.137.167:8080/firstProject/exchange/list.do",new okhttp3.Callback(){
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responsesData=response.body().string();
//                //Toast.makeText(getContext(),"成功",Toast.LENGTH_SHORT).show();
//                parseJSONWithJsonObject(responsesData);
//            }
//        });


    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void parseJSONWithJsonObject(String jsonData) {////*****
        try {
//            JSONArray jsonArray=new JSONArray(jsonData);
            //for(int i=0;i<jsonArray.length();i++){
            //  JSONObject jsonObject=jsonArray.getJSONObject(i);
            JSONObject jsonObject = new JSONObject(jsonData);
            String imageUrl = null;
            imageUrl = jsonObject.getString("imageUrl");

            String name = jsonObject.getString("name");
            String time = jsonObject.getString("time");
            // String address="武汉科技大学";
            String address = jsonObject.getString("address");
            // String phone=jsonObject.getString("phone");
            String expect = jsonObject.getString("expect");
            // int price=100;
            int price = jsonObject.getInt("price");
            exchangeitemList.add(new Exchangeitem(time, name, imageUrl, address, price, "110", "21324", expect));

            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*---- 后面要散的---*/
    private void initview() {
        exchangeitemList.add(new Exchangeitem("2018年5月20日", "笔记本", R.drawable.tu8, "武汉科技大学", 200, "10086", "110", "不建议使用"));
        //exchangeitemList.add(new Exchangeitem("截止时间:\n2018/2/2","电脑","http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png","武科大",90,"10086","110","非常好用"));
        // exchangeitemList.add(new Exchangeitem(R.drawable.tu9,"截止时间:\n 2019/11/23","一体机"));
        //exchangeitemList.add(new Exchangeitem(R.drawable.tu10,"截止时间:\n 2020/3/13","小米MAX3"));
        exchangeitemList.add(new Exchangeitem("2019年2月3日", "一体机", R.drawable.tu9, "领航工作室", 2000000, "10010", "119", "保证让你生不如死"));
        exchangeitemList.add(new Exchangeitem("2020年9月1号", "小米MAX3", R.drawable.tu10, "领航工作室", 1500, "42052040086", "120", "别做梦了"));

    }

    private void initdraw() {


        imgs.add(R.drawable.scanner_1);
        imgs.add(R.drawable.scanner_2);
        imgs.add(R.drawable.scanner_3);
        title.add("1");
        title.add("2");
        title.add("3");
    }
    // private void initview(){

    // }
    private String reponsedata;

    //    private void sendRequestWithOkHttp(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client=new OkHttpClient();
//
//                RequestBody requestBody =  FormBody.create(MediaType.parse("application/json; charset=utf-8"),getJSON("酒店",
//                            "f7146138c94846d7ad978a65a8470e31","123456"));
//                    /*后面要改*/              Request request=new Request.Builder().url("http://openapi.tuling123.com/openapi/api/v2").post(requestBody).build();
//                    Response response=client.newCall(request).execute();
//                    reponsedata=response.body().string();
//
//                  //  parseJSONWithJSONObject(reponsedata);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
    /*后面要改*/
//  private void parseJSONWithJSONObject(String jsonData){
//        try{
//            JSONArray jsonArray=new JSONArray(jsonData);
//            for (int i=0;i<jsonArray.length();i++){
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//       /*---*/         String url=jsonObject.getString("pictural");
//       String information=jsonObject.getString("information");
//       exchangeitemList.add(new Exchangeitem(url,information));
//
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//}
//private static String getJSON(String input,String APIkey,String userId) throws JSONException {
//      JSONObject userInfo=new JSONObject();
//      userInfo.put("apikey",APIkey);
//      userInfo.put("userid",userId);
//     JSONObject inputText=new JSONObject();
//     inputText.put("text",input);
//     JSONObject perception=new JSONObject();
//     perception.put("inputtext",inputText);
//     JSONObject jsonObject=new JSONObject();
//     jsonObject.put("userInfo",userInfo);
//     jsonObject.put("perception",perception);
//     return jsonObject.toString();
//
//
//}
    public static class Recieveboaster extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("确定进行交换?");
            builder.setCancelable(false);
            builder.setMessage("你将与他交换你的物品");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "交换申请中", Toast.LENGTH_SHORT).show();
                    int i = intent.getIntExtra("tupian", 0);
                    int j = intent.getIntExtra("tupian2", -1);

                    String name = exchangeitemList.get(i).getName();
                    //int ID=exchangeitemList.get(i).getID();
                    String url = exchangeitemList.get(i).getUrl();
                    String jiezishijian = exchangeitemList.get(i).getStoptime();
                    String place = exchangeitemList.get(i).getPlace();
                    int gujia = exchangeitemList.get(i).getGujia();
                    String phone = exchangeitemList.get(i).getPhone();
                    String userId = exchangeitemList.get(i).getUserID();
                    int ID = exchangeitemList.get(i).getID();
                    if (url != null) {
                        informention_itemList.add(new Informention_item(name, url, jiezishijian, place, userId, gujia));
                    } else {
                        informention_itemList.add(new Informention_item(name, ID, jiezishijian, place, userId, gujia));
                    }
                }

//                Information information=new Information();
//                if(url!=null){
//                    information.setImageurl(url);
//                    information.setID(-1);
//                    information.setPlace(place);
//                    information.setTime(jiezishijian);
//                    information.setUserid(userId);
//                    information.setName(name);
//                    information.setPrice(gujia);
//                    information.save();
//                }
//                else{
//                    information.setImageurl(null);
//                    information.setName(name);
//                    information.setPrice(gujia);
//                    information.setID(ID);
//                    information.setPlace(place);
//                    information.setTime(jiezishijian);
//                    information.setUserid(userId);
//                    information.save();
//                }
                //informention_itemList.add(new Informention_item("电脑包",exchangeitemList.get(intent.getIntExtra("tipian",0)).getID()));

            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(recieveboaster);
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        bundle.putBoolean(CaptureActivity.KEY_SCAN_AREA_FULL_SCREEN, CaptureActivity.VALUE_SCAN_AREA_FULL_SCREEN);
        bundle.putBoolean(CaptureActivity.KEY_NEED_SCAN_HINT_TEXT, CaptureActivity.VALUE_SCAN_HINT_TEXT);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);

        startActivityForResult(intent, CaptureActivity.REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        Toast.makeText(getContext(), data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT), Toast.LENGTH_SHORT).show();
                        // tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));  //or do sth

                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            // tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    Toast.makeText(getContext(), "You must agree the camera permission request before you use the code scan function", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }


}
