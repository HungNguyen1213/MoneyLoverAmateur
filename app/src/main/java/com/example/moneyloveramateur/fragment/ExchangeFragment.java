package com.example.moneyloveramateur.fragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.moneyloveramateur.MyApplication;
import com.example.moneyloveramateur.activity.MainActivity;
import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.activity.SelectWalletActivity;
import com.example.moneyloveramateur.adapter.ExchangeViewPagerAdapter;
import com.example.moneyloveramateur.receiver.NotificationReceiver;
import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExchangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExchangeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 3107;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private ExchangeViewPagerAdapter adapter;
    private Button btnWallet;
    private TextView tvWalletName;
    private TextView tvWalletBalance;

    public ExchangeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExchangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExchangeFragment newInstance(String param1, String param2) {
        ExchangeFragment fragment = new ExchangeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exchange, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.exchange_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navEvent:
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        Intent intent = new Intent(getContext(), NotificationReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 3107, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                        Toast.makeText(getContext(), "Đặt lịch nhắc thành công.", Toast.LENGTH_LONG).show();
                    }
                }, hour, min, true);
                timePickerDialog.show();;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWalletText(){
        if(MainActivity.currentWallet!=null){
            tvWalletName.setText(MainActivity.currentWallet.getName());
            Locale vn = new Locale("vi", "VN");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(vn);
            tvWalletBalance.setText(formatter.format(MainActivity.currentWallet.getBalance()));
        }
        else{
            tvWalletName.setText("Bạn vừa xóa ví này");
            tvWalletBalance.setText("Hãy chọn một ví khác");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setWalletText();
    }

    private void initView(View v) {
        toolbar = v.findViewById(R.id.exchangeToolbar);
        viewPager = v.findViewById(R.id.subViewPager);
        tabLayout = v.findViewById(R.id.tabLayout);
        btnWallet = v.findViewById(R.id.btnWallet);
        tvWalletBalance = v.findViewById(R.id.tvWalletBalance);
        tvWalletName = v.findViewById(R.id.tvWalletName);

        toolbar.setTitle("");
        setWalletText();

        AppCompatActivity temp = (AppCompatActivity) requireActivity();
        temp.setSupportActionBar(toolbar);

        adapter = new ExchangeViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SelectWalletActivity.class);
                ((AppCompatActivity) getContext()).startActivityForResult(i, REQUEST_CODE);
            }
        });
    }
}