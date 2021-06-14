package com.example.moneyloveramateur.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.activity.MainActivity;
import com.example.moneyloveramateur.activity.SelectWalletActivity;
import com.example.moneyloveramateur.model.Wallet;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SelectWalletViewAdapter extends RecyclerView.Adapter<SelectWalletViewAdapter.WalletViewHolder> {
    private Context context;
    private List<Wallet> walletList;

    public SelectWalletViewAdapter(Context context) {
        this.context = context;
    }

    public  void setData(List<Wallet> list){
        walletList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_wallet_item, parent, false);
        return new WalletViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        Wallet wallet = walletList.get(position);

        holder.icWallet.setImageResource(R.drawable.ic_wallet);
        holder.tvName.setText(wallet.getName());
        Locale vn = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(vn);
        holder.tvBalance.setText(numberFormat.format(wallet.getBalance()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.currentWallet = wallet;
                ((SelectWalletActivity) context).setResult(Activity.RESULT_OK);
                ((SelectWalletActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(walletList!=null){
            return walletList.size();
        }
        return 0;
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder{
        ImageView icWallet;
        TextView tvName, tvBalance;
        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            icWallet = itemView.findViewById(R.id.icWallet);
            tvBalance = itemView.findViewById(R.id.tvWalletBalance);
            tvName = itemView.findViewById(R.id.tvWalletName);
        }
    }
}
