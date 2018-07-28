package com.ep.eventparticipant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.ExchangeIn;

import java.util.List;

public class UserExchangeInAdapter extends RecyclerView.Adapter<UserExchangeInAdapter.ViewHolder> {
    private List<ExchangeIn> exchangeIns;
    @NonNull
    @Override
    public UserExchangeInAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_exchangein,parent,false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserExchangeInAdapter.ViewHolder holder, int position) {
        ExchangeIn exchangeIn = exchangeIns.get(position);
        holder.UserImage.setImageResource(exchangeIn.getImageId());
        holder.UserTextView.setText(exchangeIn.getName());
    }

    @Override
    public int getItemCount() {
        return exchangeIns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView UserImage;
            TextView UserTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            UserImage = (ImageView)itemView.findViewById(R.id.exchange_in_image);
            UserTextView = (TextView)itemView.findViewById(R.id.exchange_in_name);
        }
    }
    public UserExchangeInAdapter(List<ExchangeIn> exchangeInList){
        exchangeIns = exchangeInList;
    }
}
