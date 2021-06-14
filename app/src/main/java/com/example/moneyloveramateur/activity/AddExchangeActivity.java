package com.example.moneyloveramateur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.adapter.SpinnerAdapter;
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

public class AddExchangeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etCost, etNote, etDate;
    private Spinner spGroup;
    private Button btnAdd, btnBack;
    private ImageView btnDatePicker;
    private SpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exchange);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        etCost = findViewById(R.id.etCost);
        etNote = findViewById(R.id.etNote);
        etDate = findViewById(R.id.etDate);
        spGroup = findViewById(R.id.spGroup);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        toolbar = findViewById(R.id.toolbar);

        etDate.setEnabled(false);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        btnDatePicker = findViewById(R.id.btnDatePicker);

        adapter = new SpinnerAdapter(this, R.layout.item_group_selected, getListItem());
        spGroup.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExchangeActivity.this.finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = etNote.getText().toString().trim();
                String date = etDate.getText().toString().trim();
                if(TextUtils.isEmpty(date) || TextUtils.isEmpty(etCost.getText().toString().trim())){
                    Toast.makeText(AddExchangeActivity.this, "Số tiền và ngày GD không được để trống!", Toast.LENGTH_LONG).show();
                }
                else if(!TextUtils.isDigitsOnly(etCost.getText().toString().trim())){
                    etCost.setError("Số tiền chỉ chứa chữ số 0-9");
                }
                else if(Long.parseLong(etCost.getText().toString().trim()) == 0){
                    etCost.setError("Số tiền phải lớn hơn 0");
                }
                else if(compareToPresentDate(date)){
                    etDate.setError("Ngày giao dịch phải trước ngày hôm nay");
                    Toast.makeText(AddExchangeActivity.this, "Ngày giao dịch phải trước ngày hôm nay", Toast.LENGTH_LONG).show();
                }
                else{
                    long cost = Long.parseLong(etCost.getText().toString().trim());
                    Group g = new Group();
                    Long groupId = (long) spGroup.getSelectedItemPosition() + 1;
                    g.setId(groupId);
                    Exchange exchange = new Exchange(cost, note, date, g);
                    ExchangeService.exchangeService.createExchange(exchange, MainActivity.currentWallet.getId()).enqueue(new Callback<ExchangePayload>() {
                        @Override
                        public void onResponse(Call<ExchangePayload> call, Response<ExchangePayload> response) {
                            ExchangePayload exchangePayload = response.body();
                            if(exchangePayload.isSuccess()){
                                Toast.makeText(AddExchangeActivity.this, "Tạo giao dịch thành công.", Toast.LENGTH_LONG).show();
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
                                AddExchangeActivity.this.finish();
                            }
                            else{
                                Toast.makeText(AddExchangeActivity.this, "Tạo giao dịch thất bại.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ExchangePayload> call, Throwable t) {
                            Toast.makeText(AddExchangeActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            int currentMonth = cal.get(Calendar.MONTH);
            int currentDay = cal.get(Calendar.DATE);
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddExchangeActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    private boolean compareToPresentDate(String date){
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