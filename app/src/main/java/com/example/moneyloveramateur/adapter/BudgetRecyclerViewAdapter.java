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
import com.example.moneyloveramateur.activity.UpdateRemoveBudgetActivity;
import com.example.moneyloveramateur.model.Budget;
import com.example.moneyloveramateur.model.ReportBudget;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BudgetRecyclerViewAdapter extends RecyclerView.Adapter<BudgetRecyclerViewAdapter.BudgetViewHolder> {
    private Context context;
    private List<ReportBudget> listRb;

    public BudgetRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ReportBudget> list){
        listRb = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_budget_item, parent, false);
        return new BudgetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        ReportBudget rb = listRb.get(position);

        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        holder.tvGroupName.setText(rb.getBudget().getGroup().getName());
        holder.tvBalance.setText(numberFormat.format(rb.getBudget().getBalance()));
        long rest = rb.getBudget().getBalance() - rb.getTotalAmount();
        if(rest>=0){
            holder.tvSpent.setText("Đã chi " + numberFormat.format(rb.getTotalAmount()));
            holder.tvRest.setText("Còn lại " + numberFormat.format(rest));
            holder.tvRest.setTextColor(Color.parseColor("#03A9F4"));
        }
        else{
            holder.tvSpent.setText("");
            holder.tvRest.setText("Bội chi " + numberFormat.format(Math.abs(rest)));
            holder.tvRest.setTextColor(Color.parseColor("#F8BBD0"));
        }
        switch(rb.getBudget().getGroup().getId().intValue()){
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
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateRemoveBudgetActivity.class);
                intent.putExtra("budget", rb);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listRb!=null){
            return listRb.size();
        }
        return 0;
    }

    public class BudgetViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupName, tvBalance, tvRest, tvSpent;
        private ImageView icGroup;
        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            icGroup = itemView.findViewById(R.id.icGroup);
            tvBalance = itemView.findViewById(R.id.tvBalance);
            tvRest = itemView.findViewById(R.id.tvRest);
            tvSpent = itemView.findViewById(R.id.tvSpent);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
        }
    }
}
