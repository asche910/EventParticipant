package com.ep.eventparticipant.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.eventparticipant.Item.All_item;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.AllAdapter;
import com.ep.eventparticipant.fragment.BookFragment;
import com.ep.eventparticipant.fragment.FragmentSwap;
import com.ep.eventparticipant.fragment.PhoneFragment;
import com.ep.eventparticipant.fragment.digitalFragment;
import com.ep.eventparticipant.fragment.furnitureFragment;
import com.ep.eventparticipant.other.AsHttpUtils;

import java.util.ArrayList;
import java.util.List;
import static com.ep.eventparticipant.fragment.PhoneFragment.all_items;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;


public class FindThing extends AppCompatActivity  implements android.widget.SearchView.OnQueryTextListener{

        private final String[] mStrings = {"手机", "图书", "数码电器", "家具"};
        private ListView listView;
        //private List<All_item> all_items=new ArrayList<>();
        private FragmentSwap.Recieveboaster recieveboaster;
        private static int code = 1;
        public static List<All_item> allItems = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_find_thing);
            listView = (ListView) findViewById(R.id.find_listview);
            //StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            //listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings));
            listView.setTextFilterEnabled(true);
            RecyclerView recyclerView;
            // recyclerView.setLayoutManager(layoutManager);
            android.widget.SearchView searchView = (android.widget.SearchView) findViewById(R.id.searchview);
            searchView.setOnQueryTextListener(this);
            searchView.setIconifiedByDefault(false);
            searchView.setSubmitButtonEnabled(true);
            Intent intent = getIntent();
            searchView.setQueryHint("请输入关键字");
            String keytext = intent.getStringExtra("key");
            searchView.setQuery(keytext, true);

//        searchView.setOnClickBack(new bCallBack() {
//            @Override
//            public void BackAciton() {
//                finish();
//            }
//        });
//        searchView.setOnClickSearch(new ICallBack() {
//            @Override
//            public void SearchAciton(String string) {
//                Toast.makeText(FindThing.this,"正在搜索中",Toast.LENGTH_SHORT).show();
//            }
//        });
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("tuao");
            recieveboaster = new FragmentSwap.Recieveboaster();
            registerReceiver(recieveboaster, intentFilter);
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                listView.clearTextFilter();
            }
            // else{listView.setFilterText(newText);}
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(final String query) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    code = AsHttpUtils.searchExchange(query, false);
                    if (code != 0) {
//                        Toast.makeText(FindThing.this, "未搜索到想换物品", Toast.LENGTH_LONG).show();

                    }
                }
            }).start();

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.find_recyclerview);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            allItems = AsHttpUtils.all_items;
            AllAdapter allAdapter = new AllAdapter(allItems);
            if (code == 0) {
                recyclerView.setAdapter(allAdapter);
            } else {
                Toast.makeText(FindThing.this, "搜索失败", Toast.LENGTH_LONG).show();
//
//           switch (query.charAt(0)){
//               case '手':case '小': PhoneFragment.initAll();AllAdapter allAdapter0=new AllAdapter(PhoneFragment.all_items);recyclerView.setAdapter(allAdapter0);break;
//               case '家':case '具':furnitureFragment.initAll();AllAdapter allAdapter1=new AllAdapter(furnitureFragment.all_items);recyclerView.setAdapter(allAdapter1);break;
//               case '电': case '数':digitalFragment.initAll();AllAdapter allAdapter2=new AllAdapter(digitalFragment.all_items);recyclerView.setAdapter(allAdapter2);break;
//               case '图': case '书':BookFragment.initAll();AllAdapter allAdapter3=new AllAdapter(BookFragment.all_items);recyclerView.setAdapter(allAdapter3);break;
//               default: Toast.makeText(this,"未搜索到",Toast.LENGTH_LONG).show();break;
            }


            return true;
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            unregisterReceiver(recieveboaster);
        }
    }
