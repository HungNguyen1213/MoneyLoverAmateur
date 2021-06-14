package com.example.moneyloveramateur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.api.ExchangeService;
import com.example.moneyloveramateur.api.WalletService;
import com.example.moneyloveramateur.model.Exchange;
import com.example.moneyloveramateur.model.Group;
import com.example.moneyloveramateur.model.ItemSpinner;
import com.example.moneyloveramateur.payload.ExchangePayload;
import com.example.moneyloveramateur.payload.WalletPayload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateExchangeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etCost, etNote, etDate;
    private Spinner spGroup;
    private Button btnUpdate, btnBack;
    private ImageView btnDatePicker;
    private SpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exchange);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        etCost = findViewById(R.id.etCost);
        etNote = findViewById(R.id.etNote);
        etDate = findViewById(R.id.etDate);
        spGroup = findViewById(R.id.spGroup);
        btnDatePicker = findViewById(R.id.btnDatePicker);

        etDate.setEnabled(false);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnDatePicker = findViewById(R.id.btnDatePicker);

        Intent i = getIntent();
        Exchange e = (Exchange) i.getSerializableExtra("exchange");

        etCost.setText(String.valueOf(e.getCost()));
        etNote.setText(e.getNote());
        etDate.setText(e.getDate());

        adapter = new com.example.moneyloveramateur.adapter.SpinnerAdapter(this, R.layout.item_group_selected, getListItem());
        spGroup.setAdapter(adapter);
        switch (e.getGroup().getId().intValue()){
            case 1:
                spGroup.setSelection(0);
                break;
            case 2:
                spGroup.setSelection(1);
                break;
            case 3:
                spGroup.setSelection(2);
                break;
            case 4:
                spGroup.setSelection(3);
                break;
            case 5:
                spGroup.setSelection(4);
                break;
            case 6:
                spGroup.setSelection(5);
                break;
            case 7:
                spGroup.setSelection(6);
                break;
            case 8:
                spGroup.setSelection(7);
                break;
            case 9:
                spGroup.setSelection(8);
                break;
            case 10:
                spGroup.setSelection(9);
                break;
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateExchangeActivity.this.finish();
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            String date = e.getDate();
            String[] splitDate = date.split("/");
            int currentDay = Integer.parseInt(splitDate[0]);
            int currentMonth = Integer.parseInt(splitDate[1])-1;
            int currentYear = Integer.parseInt(splitDate[2]);
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateExchangeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String sDay = String.valueOf(dayOfMonth);
                        if(dayOfMonth<10){
                            sDay = "0" + sDay;
                        }
                        String sMonth = String.valueOf(month + 1);
                        if((month + 1)<10){
                            sMonth = "0" + sMonth;
                        }
                        etDate.setText(sDay + "/" + sMonth + "/" + year);
                    }
                }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = etNote.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                if(TextUtils.isEmpty(date) || TextUtils.isEmpty(etCost.getText().toString().trim())){
                    Toast.makeText(UpdateExchangeActivity.this, "Số tiền và ngày GD không được để trống!", Toast.LENGTH_LONG).show();
                }
                else if(!TextUtils.isDigitsOnly(etCost.getText().toString().trim())){
                    etCost.setError("Số tiền chỉ chứa chữ số 0-9");
                }
                else if(Long.parseLong(etCost.getText().toString().trim()) == 0){
                    etCost.setError("Số tiền phải lớn hơn 0");
                }
                else if(compareToPresentDate(date)){
                    etDate.setError("Ngày giao dịch phải trước ngày hôm nay");
                    Toast.makeText(UpdateExchangeActivity.this, "Ngày giao dịch phải trước ngày hôm nay", Toast.LENGTH_LONG).show();
                }
                else{
                    long cost = Long.parseLong(etCost.getText().toString().trim());
                    Group g = new Group();
                    Long groupId = (long) spGroup.getSelectedItemPosition() + 1;
                    g.setId(groupId);
                    e.setCost(cost);
                    e.setDate(date);
                    e.setNote(note);
                    e.setGroup(g);
                    ExchangeService.exchangeService.updateExchange(e).enqueue(new Callback<ExchangePayload>() {
                        @Override
                        public void onResponse(Call<ExchangePayload> call, Response<ExchangePayload> response) {
                            ExchangePayload exchangePayload = response.body();
                            if(exchangePayload.isSuccess()){
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("exchange", e);
                                setResult(RESULT_OK, returnIntent);
                                WalletService.walletService.readWalletById(MainActivity.currentWallet.getId()).enqueue(new Callback<WalletPayload>() {
                                    @Override
                                    public void onResponse(Call<WalletPayload> call, Response<WalletPayload> response) {
                                        WalletPayload walletPayload = response.body();
                                        MainActivity.currentWallet.setBalance(walletPayload.getWallet().getBalance());
                                    }

                                    @Override
                                    public void onFailure(Call<WalletPayload> call, Throwable t) {

                                    }
                                });
                                UpdateExchangeActivity.this.finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ExchangePayload> call, Throwable t) {
                            Toast.makeText(UpdateExchangeActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private List<ItemSpinner> getListItem() {
        List<ItemSpinner> rs = new ArrayList<>();
        String[] listGroup = getResources().getStringArray(R.array.listGroup);
        for(int i=0; i<=9; i++){
            ItemSpinner itemSpinner = new ItemSpinner();
            itemSpinner.setItemName(listGroup[i]);
            switch (i){
                case 0:
                    itemSpinner.setItemIcon(R.drawable.ic_category_foodndrink);
                    break;
                case 1:
                    itemSpinner.setItemIcon(R.drawable.ic_category_transport);
                    break;
                case 2:
                    itemSpinner.setItemIcon(R.drawable.ic_category_family);
                    break;
                case 3:
                    itemSpinner.setItemIcon(R.drawable.ic_living);
                    break;
                case 4:
                    itemSpinner.setItemIcon(R.drawable.ic_category_doctor);
                    break;
                case 5:
                    itemSpinner.setItemIcon(R.drawable.ic_category_shopping);
                    break;
                case 6:
                    itemSpinner.setItemIcon(R.drawable.ic_relax);
                    break;
                case 7:
                    itemSpinner.setItemIcon(R.drawable.ic_category_give);
                    break;
                case 8:
                    itemSpinner.setItemIcon(R.drawable.ic_category_salary);
                    break;
                case 9:
                    itemSpinner.setItemIcon(R.drawable.ic_take);
                    break;
            }
            rs.add(itemSpinner);
        }
        return rs;
    }

    private boolean compareToPresentDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date input = null;
        try {
            input = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date presentDate = new Date();
        int rsCompare = input.compareTo(presentDate);
        if(rsCompare>0){
            return true;
        }
        return false;
    }
}