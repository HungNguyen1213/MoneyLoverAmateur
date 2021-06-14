package com.example.moneyloveramateur.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.model.Report;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ReportRecyclerViewAdapter extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ReportViewHolder> {
    private Context context;
    private List<Report> reportList;

    public ReportRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Report> list){
        reportList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_report_item, parent, false);
        return new ReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reportList.get(position);

        holder.tvGroupName.setText(report.getGroupName());
        holder.tvAmount.setText(report.getTotalAmount());
        if(report.getGroupId()==9 || report.getGroupId()==10){
            holder.tvAmount.setTextColor(Color.parseColor("#03A9F4"));
        }
        else holder.tvAmount.setTextColor(Color.parseColor("#F8BBD0"));

        switch (report.getGroupId().intValue()){
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
    }

    @Override
    public int getItemCount() {
        if(reportList!=null){
            return reportList.size();
        }
        return 0;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private ImageView icGroup;
        private TextView tvGroupName, tvAmount;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            icGroup = itemView.findViewById(R.id.icGroup);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
        }
    }
}
