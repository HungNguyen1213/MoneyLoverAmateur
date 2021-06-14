package com.example.moneyloveramateur.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.activity.MainActivity;
import com.example.moneyloveramateur.adapter.ReportRecyclerViewAdapter;
import com.example.moneyloveramateur.api.ReportService;
import com.example.moneyloveramateur.model.Report;
import com.example.moneyloveramateur.payload.ListReportPayload;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportSubPrevFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportSubPrevFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ReportRecyclerViewAdapter adapter;
    private TextView tvCashIn, tvCashOut, tvBalance;

    public ReportSubPrevFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportSubPrevFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportSubPrevFragment newInstance(String param1, String param2) {
        ReportSubPrevFragment fragment = new ReportSubPrevFragment();
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
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_report_sub_prev, container, false);
        recyclerView = v.findViewById(R.id.recycleView);
        tvCashIn = v.findViewById(R.id.tvCashIn);
        tvCashOut = v.findViewById(R.id.tvCashOut);
        tvBalance = v.findViewById(R.id.tvBalance);
        setData();
        return v;
    }

    private void setData() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = formatter.format(date);
        String[] splitCurrentDate = currentDate.split("/");
        int m = Integer.parseInt(splitCurrentDate[1]) - 1;
        int y = Integer.parseInt(splitCurrentDate[2]);
        if(m==0){
            m = 12;
            y -=1;
        }
        String monthPrev;
        if(m<10){
            monthPrev = "0" + String.valueOf(m);
        }
        else{
            monthPrev = String.valueOf(m);
        }
        String time = monthPrev + "/" + y;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new ReportRecyclerViewAdapter(getContext());
        if(MainActivity.currentWallet!=null){
            ReportService.reportService.readReportAllGroup(MainActivity.currentWallet.getId(), time).enqueue(new Callback<ListReportPayload>() {
                @Override
                public void onResponse(Call<ListReportPayload> call, Response<ListReportPayload> response) {
                    ListReportPayload listReportPayload = response.body();
                    List<Report> list = listReportPayload.getListReport();
                    adapter.setData(list);
                    long cashIn = 0;
                    long cashOut = 0;
                    Locale locale = new Locale("vi", "VN");
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
                    for(Report report : list){
                        if(report.getGroupId()==9 || report.getGroupId()==10){
                            try {
                                cashIn += (long)numberFormat.parse(report.getTotalAmount());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            try {
                                cashOut += (long)numberFormat.parse(report.getTotalAmount());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    tvCashIn.setText(numberFormat.format(cashIn));
                    tvCashOut.setText(numberFormat.format(cashOut));
                    tvBalance.setText(numberFormat.format(cashIn - cashOut));
                }

                @Override
                public void onFailure(Call<ListReportPayload> call, Throwable t) {

                }
            });
        }
        recyclerView.setAdapter(adapter);
    }
}