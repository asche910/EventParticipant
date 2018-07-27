package com.ep.eventparticipant.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.ep.eventparticipant.Item.Exchangeitem;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.activity.FindThing;
import com.ep.eventparticipant.activity.MainActivity;
import com.ep.eventparticipant.activity.PublishActivity;
import com.ep.eventparticipant.adapter.ExchangeAdapter;


import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class FragmentSwap extends Fragment {
private View view;
private EditText editText;
private Banner banner;
private List<Integer> imgs=new ArrayList<>();
private List<String> title=new ArrayList<>();
public static List<Exchangeitem> exchangeitemList=new ArrayList<>();
private static int i;
private IntentFilter intentFilter;
private Recieveboaster recieveboaster;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.swapmenu,menu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.from(getContext()).inflate(R.layout.fragment_swap, container, false);
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

//banner=(view).findViewById(R.id.banner);
//banner.setImages(imgs);
//banner.setBannerTitles(title);
//banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//banner.setImageLoader(new GlideImageLoader());
//banner.setBannerAnimation(Transformer.Tablet);
//banner.isAutoPlay(true);
//banner.setDelayTime(1500);
//banner.setIndicatorGravity(BannerConfig.CENTER);
//banner.start();




        //Boom菜单设置

//      BoomMenuButton boomMenuButton = (BoomMenuButton) view.findViewById(R.id.bmb);
//        for (  i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
//            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
//                    .listener(new OnBMClickListener() {
//                        @Override
//                        public void onBoomButtonClick(int index) {
//                           if (index!=0){Toast.makeText(getActivity(), "Clicked " + index, Toast.LENGTH_SHORT).show();}
//                        else{  Intent intent=new Intent(getActivity(), PublishActivity.class);
//                           getActivity().startActivity(intent);}
//                        }
//                    })
//                    .normalImageRes(getImageResource())
//                    .normalText(getext());
//            boomMenuButton.addBuilder(builder);
//        }


        intentFilter=new IntentFilter();
        intentFilter.addAction("tuao");
        recieveboaster=new Recieveboaster();
        getActivity().registerReceiver(recieveboaster,intentFilter);





        return view;
    }
    private static String [] text = new String[]{"发布我的物品","2222222","3333333"

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











    private static int[] imageResources=new int[]{R.drawable.ic_storage_black_24dp,R.drawable.ic_terrain_black_24dp};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        //sendRequestWithOkHttp();
        RecyclerView recyclerView=(view).findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ExchangeAdapter adapter=new ExchangeAdapter(exchangeitemList);
        recyclerView.setAdapter(adapter);
        ImageView fabiao=(view).findViewById(R.id.fabiao);
        fabiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PublishActivity.class);
                startActivity(intent);
            }
        });






    }
    /*---- 后面要散的---*/
    private void initview(){
        for(int i=0;i<=0;i++){
            exchangeitemList.add(new Exchangeitem(R.drawable.tu8,"截至时间:\n 2018/12/12"));
            exchangeitemList.add(new Exchangeitem(R.drawable.tu9,"截止时间:\n 2019/11/23"));
            exchangeitemList.add(new Exchangeitem(R.drawable.tu10,"截止时间:\n 2020/3/13"));
        }
    }
    private void initdraw(){


imgs.add(R.drawable.tu_4);
imgs.add(R.drawable.tu_6);
imgs.add(R.drawable.tu_5);
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
    class Recieveboaster extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("确定与他进行交换?");
        builder.setCancelable(false);
        builder.setMessage("你将与他交换你的物品");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"即将进入交换页面",Toast.LENGTH_SHORT).show();
            }
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


}
