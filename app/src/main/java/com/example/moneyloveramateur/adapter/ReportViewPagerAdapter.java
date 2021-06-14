package com.example.moneyloveramateur.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.moneyloveramateur.fragment.ReportSubPresentFragment;
import com.example.moneyloveramateur.fragment.ReportSubPrevFragment;

public class ReportViewPagerAdapter extends FragmentStatePagerAdapter {
    public ReportViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ReportSubPrevFragment();
            case 1:
            default:
                return new ReportSubPresentFragment();
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
