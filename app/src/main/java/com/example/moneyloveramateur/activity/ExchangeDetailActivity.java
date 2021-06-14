package com.example.moneyloveramateur.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.api.ExchangeService;
import com.example.moneyloveramateur.api.WalletService;
import com.example.moneyloveramateur.model.Exchange;
import com.example.moneyloveramateur.model.Wallet;
import com.example.moneyloveramateur.payload.ExchangePayload;
import com.example.moneyloveramateur.payload.WalletPayload;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeDetailActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1080;
    private Toolbar toolbar;
    private ImageView icGroup;
    private TextView tvGroupName, tvNote, tvCost, tvDate;
    private Exchange exchange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_detail);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exchange_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            exchange = (Exchange) data.getSerializableExtra("exchange");
            tvDate.setText(exchange.getDate());
            tvNote.setText(exchange.getNote());

            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            tvCost.setText(format.format(exchange.getCost()));

            String groupName = "";
            String[] groupList = getResources().getStringArray(R.array.listGroup);
            switch (exchange.getGroup().getId().intValue()){
                case 1:
                    groupName = groupList[0];
                    break;
                case 2:
                    groupName =  groupList[1];
                    break;
                case 3:
                    groupName =  groupList[2];
                    break;
                case 4:
                    groupName =  groupList[3];
                    break;
                case 5:
                    groupName =  groupList[4];
                    break;
                case 6:
                    groupName =  groupList[5];
                    break;
                case 7:
                    groupName =  groupList[6];
                    break;
                case 8:
                    groupName =  groupList[7];
                    break;
                case 9:
                    groupName =  groupList[8];
                    break;
                case 10:
                    groupName =  groupList[9];
                    break;
            }
            tvGroupName.setText(groupName);

            switch(exchange.getGroup().getId().intValue()){
                case 1:
                    icGroup.setImageResource(R.drawable.ic_category_foodndrink);
                    break;
                case 2:
                    icGroup.setImageResource(R.drawable.ic_category_transport);
                    break;
                case 3:
                    icGroup.setImageResource(R.drawable.ic_category_family);
                    break;
                case 4:
                    icGroup.setImageResource(R.drawable.ic_living);
                    break;
                case 5:
                    icGroup.setImageResource(R.drawable.ic_category_doctor);
                    break;
                case 6:
                    icGroup.setImageResource(R.drawable.ic_category_shopping);
                    break;
                case 7:
                    icGroup.setImageResource(R.drawable.ic_relax);
                    break;
                case 8:
                    icGroup.setImageResource(R.drawable.ic_category_give);
                    break;
                case 9:
                    icGroup.setImageResource(R.drawable.ic_category_salary);
                    break;
                case 10:
                    icGroup.setImageResource(R.drawable.ic_take);
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navUpdate:
                Intent i = new Intent(ExchangeDetailActivity.this, UpdateExchangeActivity.class);
                i.putExtra("exchange", exchange);
                ExchangeDetailActivity.this.startActivityForResult(i, REQUEST_CODE);
                break;
            case R.id.navDelete:
                getDeleteExchange();
                break;
            case R.id.navClose:
                ExchangeDetailActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDeleteExchange() {
        ExchangeService.exchangeService.deleteExchange(exchange.getId()).enqueue(new Callback<ExchangePayload>() {
            @Override
            public void onResponse(Call<ExchangePayload> call, Response<ExchangePayload> response) {
                ExchangePayload exchangePayload = response.body();
                if(exchangePayload.isSuccess()){
                    Toast.makeText(ExchangeDetailActivity.this, "Đã xóa giao dịch.", Toast.LENGTH_LONG).show();
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
                    ExchangeDetailActivity.this.finish();
                }
                else{
                    Toast.makeText(ExchangeDetailActivity.this, "Xóa thất bại;", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ExchangePayload> call, Throwable t) {
                Toast.makeText(ExchangeDetailActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        icGroup = findViewById(R.id.icGroup);
        tvCost = findViewById(R.id.tvCost);
        tvNote = findViewById(R.id.tvNote);
        tvGroupName = findViewById(R.id.tvGroupName);
        tvDate = findViewById(R.id.tvDate);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        exchange = (Exchange) i.getSerializableExtra("exchange");

        tvDate.setText(exchange.getDate());
        tvNote.setText(exchange.getNote());

        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        tvCost.setText(format.format(exchange.getCost()));
        tvGroupName.setText(exchange.getGroup().getName());

        switch(exchange.getGroup().getId().intValue()){
            case 1:
                icGroup.setImageResource(R.drawable.ic_category_foodndrink);
                break;
            case 2:
                icGroup.setImageResource(R.drawable.ic_category_transport);
                break;
            case 3:
                icGroup.setImageResource(R.drawable.ic_category_family);
                break;
            case 4:
                icGroup.setImageResource(R.drawable.ic_living);
                break;
            case 5:
                icGroup.setImageResource(R.drawable.ic_category_doctor);
                break;
            case 6:
                icGroup.setImageResource(R.drawable.ic_category_shopping);
                break;
            case 7:
                icGroup.setImageResource(R.drawable.ic_relax);
                break;
            case 8:
                icGroup.setImageResource(R.drawable.ic_category_give);
                break;
            case 9:
                icGroup.setImageResource(R.drawable.ic_category_salary);
                break;
            case 10:
                icGroup.setImageResource(R.drawable.ic_take);
                break;
        }
    }
}