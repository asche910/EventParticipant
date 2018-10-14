package com.ep.eventparticipant.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import static com.ep.eventparticipant.fragment.PhoneFragment.all_items;

public class digitalFragment extends Fragment {
   // public static List<All_item> all_items=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.digitalfragment,container,false);
        all_items.clear();
        initAll();
        RecyclerView recyclerView=view.findViewById(R.id.digital_recyclerview);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        AllAdapter allAdapter=new AllAdapter(all_items);
        recyclerView.setAdapter(allAdapter);
        return view;
    }
    public static void initAll(){
        all_items.add(new All_item("收纳袋",R.drawable.digital));
        all_items.add(new All_item("收纳包",R.drawable.digital_2));
        all_items.add(new All_item("耳机",R.drawable.digital_3));
    }
}
