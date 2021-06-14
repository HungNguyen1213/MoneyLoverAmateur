package com.example.moneyloveramateur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.api.WalletService;
import com.example.moneyloveramateur.model.Wallet;
import com.example.moneyloveramateur.payload.WalletPayload;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWalletActivity extends AppCompatActivity {
    private EditText etName, etBalance;
    private Button btnAdd, btnBack;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet);
        etBalance = findViewById(R.id.etBalance);
        etName = findViewById(R.id.etName);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWalletActivity.this.finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(etBalance.getText().toString().trim())){
                    Toast.makeText(AddWalletActivity.this, "Các trường không được để trống!", Toast.LENGTH_LONG).show();
                }
                else if(!TextUtils.isDigitsOnly(etBalance.getText().toString().trim())){
                    etBalance.setError("Số dư chỉ chứa chữ số 0-9.");
                }
                else{
                    long balance = Long.parseLong(etBalance.getText().toString().trim());
                    WalletService.walletService
                            .createWallet(new Wallet(balance, name), MainActivity.currentUser.getId())
                            .enqueue(new Callback<WalletPayload>() {
                                @Override
                                public void onResponse(Call<WalletPayload> call, Response<WalletPayload> response) {
                                    WalletPayload walletPayload = response.body();
                                    if(walletPayload.isSuccess()){
                                        Toast.makeText(AddWalletActivity.this, "Tạo ví thành công.", Toast.LENGTH_LONG).show();
                                        AddWalletActivity.this.finish();
                                    }
                                    else{
                                        Toast.makeText(AddWalletActivity.this, "Ví " + name + " đã tồn tại!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<WalletPayload> call, Throwable t) {
                                    Toast.makeText(AddWalletActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}