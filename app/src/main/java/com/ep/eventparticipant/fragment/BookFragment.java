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

public class BookFragment extends Fragment {
    //public static List<All_item> all_items=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bookfragment,container,false);

        all_items.clear();
        initAll();
        RecyclerView recyclerView=view.findViewById(R.id.book_recyclerview);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        AllAdapter allAdapter=new AllAdapter(all_items);
        recyclerView.setAdapter(allAdapter);

        return view;
    }
    public static void initAll(){
        all_items.add(new All_item("十宗罪",R.drawable.book_1));
        all_items.add(new All_item("TROUBLES",R.drawable.book_2));
        all_items.add(new All_item("情商高",R.drawable.book_3));
    }
}
