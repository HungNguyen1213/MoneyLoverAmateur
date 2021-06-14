package com.example.moneyloveramateur.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.adapter.MainViewPagerAdapter;
import com.example.moneyloveramateur.api.WalletService;
import com.example.moneyloveramateur.model.User;
import com.example.moneyloveramateur.model.Wallet;
import com.example.moneyloveramateur.payload.ListWalletPayload;
import com.example.moneyloveramateur.payload.WalletPayload;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static  User currentUser;
    public static Wallet currentWallet;
    private ViewPager viewPager;
    private MainViewPagerAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.mainViewPager);
        bottomNavigationView = findViewById(R.id.bottomNav);
        setupViewPager();
        setupBottomNav();
    }

    private void setupBottomNav() {
        bottomNavigationView.setSelectedItemId(0);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navExchange:
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.navReport:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.navBudget:
                        viewPager.setCurrentItem(2, true);
                        break;
                    case R.id.navUser:
                        viewPager.setCurrentItem(3, true);
                        break;
                }
                return true;
            }
        });
    }

    private void setupViewPager() {
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navExchange).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navReport).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navBudget).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navUser).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}