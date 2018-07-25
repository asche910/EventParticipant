package com.ep.eventparticipant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.EventBean;

import java.util.List;

public class HomeSuggestAdapter extends RecyclerView.Adapter<HomeSuggestAdapter.ViewHolder> {

    private List<EventBean> list;
    private Context context;
    private OnClickListener onClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView textName;
        TextView textWhere;
        TextView textTime;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_img_sug);
            textName = itemView.findViewById(R.id.item_name_sug);
            textWhere = itemView.findViewById(R.id.item_where_sug);
            textTime = itemView.findViewById(R.id.item_time_sug);
        }
    }

    public HomeSuggestAdapter(List<EventBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HomeSuggestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_suggest, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSuggestAdapter.ViewHolder holder, final int position) {
        EventBean eventBean = list.get(position);

        Glide.with(context)
                .load(eventBean.getImgUri())
                .into(holder.img);
        holder.textName.setText(eventBean.getName());
        holder.textWhere.setText(eventBean.getWhere());
        holder.textTime.setText(eventBean.getStartTime());

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
