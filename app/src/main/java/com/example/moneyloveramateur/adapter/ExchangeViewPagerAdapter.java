package com.example.moneyloveramateur.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moneyloveramateur.fragment.ExchangeSubPresentFragment;
import com.example.moneyloveramateur.fragment.ExchangeSubPrevFragment;

public class ExchangeViewPagerAdapter extends FragmentStatePagerAdapter {
    public ExchangeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ExchangeSubPrevFragment();
            case 1:
            default:
                return new ExchangeSubPresentFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Tháng trước";
            case 1:
                return "Tháng này";
            default:
                return "";
        }
    }
}
