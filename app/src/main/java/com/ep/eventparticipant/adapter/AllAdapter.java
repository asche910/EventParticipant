package com.ep.eventparticipant.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.Item.All_item;
import com.ep.eventparticipant.Item.Informention_item;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.activity.AllActivity;
import com.ep.eventparticipant.activity.FindThing;
import com.ep.eventparticipant.activity.OtherActivity;

import java.util.List;

import static com.ep.eventparticipant.activity.ExchangeInformation.informention_itemList;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {
    public static List<All_item> mall_items;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView xiangqin;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.all_imageview);
            textView = (TextView) view.findViewById(R.id.all_text_view);
            xiangqin = (TextView) view.findViewById(R.id.xiangqin);
        }
    }

    public AllAdapter(List<All_item> all_items) {
        mall_items = all_items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        if (context == null) {
            context = parent.getContext();
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int position = holder.getAdapterPosition();
                    All_item all_item = mall_items.get(position);

                    if (context.getClass().equals(FindThing.class)) {
                        Intent intent = new Intent("tuao");
                        intent.putExtra("tupian2", position);
                        v.getContext().sendBroadcast(intent);
                    } else {

                        Intent intent = new Intent(context, FindThing.class);
                        intent.putExtra("key", all_item.getName());
                        Toast.makeText(context, "您需要搜索的是 " + all_item.getName(), Toast.LENGTH_LONG).show();
                        context.startActivity(intent);
                        // informention_itemList.add(new Informention_item(all_item.getName(),all_item.getId(),"","","",100));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "出现未知错误！ ", Toast.LENGTH_LONG).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final All_item all_item = mall_items.get(position);
        holder.textView.setText(all_item.getName());
        Glide.with(context).load(all_item.getId()).into(holder.imageView);
        final String name = all_item.getName();
        holder.xiangqin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OtherActivity.class);
                intent.putExtra("b", position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.getContext().startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(), v, "sharedView").toBundle());
                }
            }
        });
        // holder.imageView.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //  public void onClick(View v) {

        //informention_itemList.add(new Informention_item(all_item.getName(),all_item.getId(),"","","",100));
        //      }
        //   });


    }

    @Override
    public int getItemCount() {
        return mall_items.size();
    }
}
