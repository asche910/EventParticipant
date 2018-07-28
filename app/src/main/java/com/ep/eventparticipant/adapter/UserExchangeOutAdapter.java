package com.ep.eventparticipant.adapter;

import android.os.Build;
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
import com.ep.eventparticipant.object.ExchangeOut;

import java.util.List;

public class UserExchangeOutAdapter extends RecyclerView.Adapter<UserExchangeOutAdapter.ViewHolder> {
    private List<ExchangeOut> exchangeOuts;
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Button btn;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.exchange_out_image);
            textView = (TextView)itemView.findViewById(R.id.exchange_out_name);
            btn = (Button)itemView.findViewById(R.id.exchange_out_btn);
        }
    }
    public UserExchangeOutAdapter(List<ExchangeOut> exchangeOuts){
        this.exchangeOuts = exchangeOuts;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_exchangeout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ExchangeOut exchangeOut =  exchangeOuts.get(position);
        holder.imageView.setImageResource(exchangeOut.getImageId());
        holder.textView.setText(exchangeOut.getName());
    }

    @Override
    public int getItemCount() {
        return exchangeOuts.size();
    }

}
