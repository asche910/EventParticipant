package com.ep.eventparticipant.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ep.eventparticipant.Item.All_item;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.adapter.AllAdapter;

import java.util.ArrayList;
import java.util.List;

public class PhoneFragment extends Fragment {
    public static List<All_item> all_items=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_all,container,false);
        all_items.clear();
        initAll();
        RecyclerView recyclerView=view.findViewById(R.id.all_recyclerview);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        AllAdapter allAdapter=new AllAdapter(all_items);
        recyclerView.setAdapter(allAdapter);
        return  view;
    }
    public static void initAll(){

            All_item all_item=new All_item("小米",R.drawable.phone);
            all_items.add(all_item);
            All_item all_item1=new All_item("华为荣耀",R.drawable.phone_2);
            all_items.add(all_item1);
            All_item all_item2=new All_item("OPPO",R.drawable.phone_3);
            all_items.add(all_item2);

    }
}
