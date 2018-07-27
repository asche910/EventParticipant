package com.ep.eventparticipant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.EventBean;

import java.util.List;

public class HomePopularAdapter extends RecyclerView.Adapter<HomePopularAdapter.ViewHolder> {

    private List<EventBean> list;
    private Context context;
    private OnClickListener onClickListener;


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView textName;
        TextView textWhere;
        TextView textTime;
        Button btn;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_img);
            textName = itemView.findViewById(R.id.item_name);
            textWhere = itemView.findViewById(R.id.item_where);
            textTime = itemView.findViewById(R.id.item_time);
            btn = itemView.findViewById(R.id.item_btn);

        }
    }

    public HomePopularAdapter(List<EventBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_popular, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        EventBean eventBean = list.get(position);

        Glide.with(context)
                .load(eventBean.getImgUri())
                .into(holder.img);
        holder.textName.setText(eventBean.getName());
        holder.textWhere.setText(eventBean.getWhere());
        holder.textTime.setText(eventBean.getStartTime());


        if(eventBean.isJoin()){
            holder.btn.setText("已报名");
        }
        if(onClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}
