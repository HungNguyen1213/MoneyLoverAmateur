package com.example.moneyloveramateur.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.activity.AddExchangeActivity;
import com.example.moneyloveramateur.activity.MainActivity;
import com.example.moneyloveramateur.adapter.ExchangeOndayRecyclerViewAdapter;
import com.example.moneyloveramateur.api.ExchangeService;
import com.example.moneyloveramateur.model.Exchange;
import com.example.moneyloveramateur.model.ListExchangeOnday;
import com.example.moneyloveramateur.payload.ListExchangePayload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExchangeSubPresentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExchangeSubPresentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvCashIn, tvCashOut, tvBalance;
    private RecyclerView recyclerView;
    private ExchangeOndayRecyclerViewAdapter adapter;
    private FloatingActionButton btnAdd;

    public ExchangeSubPresentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExchangeSubPresentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExchangeSubPresentFragment newInstance(String param1, String param2) {
        ExchangeSubPresentFragment fragment = new ExchangeSubPresentFragment();
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
        View v = inflater.inflate(R.layout.fragment_exchange_sub_present, container, false);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new ExchangeOndayRecyclerViewAdapter(getContext());
        if(MainActivity.currentWallet!=null){
            ExchangeService.exchangeService.readExchangeByWalletAndTime(MainActivity.currentWallet.getId(), time)
                    .enqueue(new Callback<ListExchangePayload>() {
                        @Override
                        public void onResponse(Call<ListExchangePayload> call, Response<ListExchangePayload> response) {
                            ListExchangePayload listExchangePayload = response.body();
                            List<Exchange> list = listExchangePayload.getListExchange();
                            List<ListExchangeOnday> listExchangeOnday = handleListExchange(day, month, year, list);
                            adapter.setData(listExchangeOnday);
                            long cashIn = 0;
                            long cashOut = 0;
                            for(Exchange e : list){
                                if(e.getGroup().getName().equals("Lương")){
                                    cashIn += e.getCost();
                                }
                                else if(e.getGroup().getName().equals("Khoản thu khác")){
                                    cashIn += e.getCost();
                                }
                                else {
                                    cashOut += e.getCost();
                                }
                            }
                            Locale vn = new Locale("vi", "VN");
                            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(vn);
                            tvCashIn.setText(numberFormat.format(cashIn));
                            tvCashOut.setText(numberFormat.format(cashOut));
                            tvBalance.setText(numberFormat.format(cashIn - cashOut));
                        }

                        @Override
                        public void onFailure(Call<ListExchangePayload> call, Throwable t) {
                            Toast.makeText(getContext(), "Lỗi mạng!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        recyclerView.setAdapter(adapter);
    }

    private List<ListExchangeOnday> handleListExchange(String day, String month, String year, List<Exchange> list){
        int dayIntValue = Integer.parseInt(day);
        List<ListExchangeOnday> rs = new ArrayList<>();
        for(int i = dayIntValue; i >= 1; i--){
            String dayStringValue;
            if(i>=10){
                dayStringValue = String.valueOf(i);
            }
            else{
                dayStringValue = "0" + i;
            }
            ListExchangeOnday temp = new ListExchangeOnday();
            List<Exchange> listOnDay = filterByDay(dayStringValue, list);
            if(listOnDay.isEmpty()){
                continue;
            }
            temp.setExchangeList(listOnDay);
            temp.setYear(year);
            temp.setMonth(month);
            temp.setDay(dayStringValue);
            long amount = 0;
            for(Exchange e : listOnDay){
                if(e.getGroup().getName().equals("Lương")){
                    amount += e.getCost();
                }
                else if(e.getGroup().getName().equals("Khoản thu khác")){
                    amount += e.getCost();
                }
                else {
                    amount -= e.getCost();
                }
            }
            temp.setAmount(amount);
            rs.add(temp);
        }
        return rs;
    }

    private List<Exchange> filterByDay(String day, List<Exchange> list){
        List<Exchange> rs = new ArrayList<>();
        for(Exchange e : list){
            String eDay = e.getDate().split("/")[0];
            if(day.equals(eDay)){
                rs.add(e);
            }
        }
        return rs;
    }

    private void initView(View v) {
        tvBalance = v.findViewById(R.id.tvBalance);
        tvCashIn = v.findViewById(R.id.tvCashIn);
        tvCashOut = v.findViewById(R.id.tvCashOut);
        recyclerView = v.findViewById(R.id.recycleView);
        btnAdd = v.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddExchangeActivity.class);
                getContext().startActivity(i);
            }
        });
    }
}