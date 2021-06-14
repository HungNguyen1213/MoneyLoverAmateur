package com.example.moneyloveramateur.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.model.ItemSpinner;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<ItemSpinner> {
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ItemSpinner> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_selected, parent, false);
        ImageView icGroup = convertView.findViewById(R.id.icGroup);
        TextView tvGroupName = convertView.findViewById(R.id.tvGroupName);
        ItemSpinner itemSpinner = getItem(position);
        if(itemSpinner!=null){
            icGroup.setImageResource(itemSpinner.getItemIcon());
            tvGroupName.setText(itemSpinner.getItemName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group_item, parent, false);
        ImageView icGroup = convertView.findViewById(R.id.icGroup);
        TextView tvGroupName = convertView.findViewById(R.id.tvGroupName);
        ItemSpinner itemSpinner = getItem(position);
        if(itemSpinner!=null){
            icGroup.setImageResource(itemSpinner.getItemIcon());
            tvGroupName.setText(itemSpinner.getItemName());
        }
        return convertView;
    }
}
