package com.example.moneyloveramateur.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moneyloveramateur.fragment.BudgetFragment;
import com.example.moneyloveramateur.fragment.ExchangeFragment;
import com.example.moneyloveramateur.fragment.ReportFragment;
import com.example.moneyloveramateur.fragment.UserFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 1:
                return new ReportFragment();
            case 2:
                return new BudgetFragment();
            case 3:
                return new UserFragment();
            case 0:
            default:
                return new ExchangeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
