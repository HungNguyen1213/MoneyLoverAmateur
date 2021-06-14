package com.example.moneyloveramateur.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.activity.AddBudgetActivity;
import com.example.moneyloveramateur.activity.MainActivity;
import com.example.moneyloveramateur.adapter.BudgetRecyclerViewAdapter;
import com.example.moneyloveramateur.api.BudgetService;
import com.example.moneyloveramateur.model.ReportBudget;
import com.example.moneyloveramateur.payload.ReportBudgetPayload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvYear, tvMonth, tvDay, tvCountdown;
    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private BudgetRecyclerViewAdapter adapter;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_budget, container, false);
        initView(v);
        setData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(date);
        String[] splitCurrentDate = currentDate.split("/");
        String day = splitCurrentDate[0];
        String month = splitCurrentDate[1];
        String year = splitCurrentDate[2];
        String time = month + "/" + year;

        tvYear.setText("Năm " + year);
        tvDay.setText(day);
        tvMonth.setText("Tháng " + month);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lasDate = c.getTime();
        long getDiff = lasDate.getTime() - date.getTime();
        long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
        tvCountdown.setText("còn lại " + getDaysDiff + " ngày");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new BudgetRecyclerViewAdapter(getContext());

        if(MainActivity.currentWallet!=null){
            BudgetService.budgetService.readBudgetByWalletAndTime(MainActivity.currentWallet.getId(), time).enqueue(new Callback<ReportBudgetPayload>() {
                @Override
                public void onResponse(Call<ReportBudgetPayload> call, Response<ReportBudgetPayload> response) {
                    ReportBudgetPayload reportBudgetPayload = response.body();
                    List<ReportBudget> list = reportBudgetPayload.getListReportBudget();
                    adapter.setData(list);
                }

                @Override
                public void onFailure(Call<ReportBudgetPayload> call, Throwable t) {

                }
            });
        }
        recyclerView.setAdapter(adapter);
    }

    private void initView(View v) {
        tvCountdown = v.findViewById(R.id.tvCountdown);
        tvYear = v.findViewById(R.id.tvYear);
        tvDay = v.findViewById(R.id.tvDay);
        tvMonth = v.findViewById(R.id.tvMonth);
        recyclerView = v.findViewById(R.id.recycleView);
        btnAdd = v.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddBudgetActivity.class);
                getContext().startActivity(i);
            }
        });
    }
}