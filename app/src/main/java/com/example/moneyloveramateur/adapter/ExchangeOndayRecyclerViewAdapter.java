package com.example.moneyloveramateur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.model.ListExchangeOnday;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExchangeOndayRecyclerViewAdapter extends RecyclerView.Adapter<ExchangeOndayRecyclerViewAdapter.ExchangeOndayViewHolder> {
    private Context context;
    List<ListExchangeOnday> list;

    public ExchangeOndayRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ListExchangeOnday> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExchangeOndayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_exchange_onday_item, parent, false);
        return new ExchangeOndayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeOndayViewHolder holder, int position) {
        ListExchangeOnday listExchangeOnday = list.get(position);

        Locale vn = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(vn);
        holder.tvAmount.setText(numberFormat.format(listExchangeOnday.getAmount()));
        holder.tvYear.setText("Năm " + listExchangeOnday.getYear());
        holder.tvMonth.setText("Tháng " + listExchangeOnday.getMonth());
        holder.tvDay.setText(listExchangeOnday.getDay());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        holder.recyclerView.setFocusable(false);
        ExchangeRecycleViewAdapter adapter = new ExchangeRecycleViewAdapter(context);
        adapter.setData(listExchangeOnday.getExchangeList());
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class ExchangeOndayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay, tvMonth, tvYear, tvAmount;
        private RecyclerView recyclerView;
        public ExchangeOndayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            recyclerView = itemView.findViewById(R.id.recycleView);
        }
    }
}
