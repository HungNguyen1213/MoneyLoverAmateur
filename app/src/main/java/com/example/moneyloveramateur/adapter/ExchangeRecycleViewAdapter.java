package com.example.moneyloveramateur.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.activity.ExchangeDetailActivity;
import com.example.moneyloveramateur.model.Exchange;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExchangeRecycleViewAdapter extends RecyclerView.Adapter<ExchangeRecycleViewAdapter.ExchangeViewHolder> {
    private Context context;
    private List<Exchange> exchangeList;

    public ExchangeRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Exchange> list){
        exchangeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_exchange_item, parent, false);
        return new ExchangeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeViewHolder holder, int position) {
        Exchange e = exchangeList.get(position);
        Locale vn = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(vn);
        holder.tvCost.setText(numberFormat.format(e.getCost()));
        if(e.getGroup().getName().equals("Lương")){
            holder.tvCost.setTextColor(Color.parseColor("#03A9F4"));
        }
        else if(e.getGroup().getName().equals("Khoản thu khác")){
            holder.tvCost.setTextColor(Color.parseColor("#03A9F4"));
        }
        else{
            holder.tvCost.setTextColor(Color.parseColor("#F8BBD0"));
        }
        holder.tvGroupName.setText(e.getGroup().getName());

        switch(e.getGroup().getId().intValue()){
            case 1:
                holder.icGroup.setImageResource(R.drawable.ic_category_foodndrink);
                break;
            case 2:
                holder.icGroup.setImageResource(R.drawable.ic_category_transport);
                break;
            case 3:
                holder.icGroup.setImageResource(R.drawable.ic_category_family);
                break;
            case 4:
                holder.icGroup.setImageResource(R.drawable.ic_living);
                break;
            case 5:
                holder.icGroup.setImageResource(R.drawable.ic_category_doctor);
                break;
            case 6:
                holder.icGroup.setImageResource(R.drawable.ic_category_shopping);
                break;
            case 7:
                holder.icGroup.setImageResource(R.drawable.ic_relax);
                break;
            case 8:
                holder.icGroup.setImageResource(R.drawable.ic_category_give);
                break;
            case 9:
                holder.icGroup.setImageResource(R.drawable.ic_category_salary);
                break;
            case 10:
                holder.icGroup.setImageResource(R.drawable.ic_take);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ExchangeDetailActivity.class);
                i.putExtra("exchange", e);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(exchangeList!=null){
            return exchangeList.size();
        }
        return 0;
    }

    public class ExchangeViewHolder extends RecyclerView.ViewHolder {
        private ImageView icGroup;
        private TextView tvGroupName, tvCost;
        public ExchangeViewHolder(@NonNull View itemView) {
            super(itemView);
            icGroup = itemView.findViewById(R.id.icGroup);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvCost = itemView.findViewById(R.id.tvCost);
        }
    }
}
