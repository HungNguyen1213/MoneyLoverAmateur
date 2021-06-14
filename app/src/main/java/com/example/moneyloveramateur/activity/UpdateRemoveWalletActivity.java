package com.example.moneyloveramateur.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

public class UpdateRemoveWalletActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etName;
    private Button btnUpdate, btnRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_remove_wallet);
        toolbar = findViewById(R.id.toolbar);
        etName = findViewById(R.id.etName);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnRemove = findViewById(R.id.btnRemove);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Wallet walet = (Wallet) i.getSerializableExtra("wallet");
        etName.setText(walet.getName());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(UpdateRemoveWalletActivity.this, "Các trường không được để trống!", Toast.LENGTH_LONG).show();
                }
                else{
                    walet.setName(name);
                    WalletService.walletService.updateWallet(walet).enqueue(new Callback<WalletPayload>() {
                        @Override
                        public void onResponse(Call<WalletPayload> call, Response<WalletPayload> response) {
                            if(MainActivity.currentWallet!=null){
                                if(MainActivity.currentWallet.getId().equals(walet.getId())){
                                    MainActivity.currentWallet.setName(name);
                                }
                            }
                            Toast.makeText(UpdateRemoveWalletActivity.this, "Sửa thông tin thành công.", Toast.LENGTH_LONG).show();
                            UpdateRemoveWalletActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Call<WalletPayload> call, Throwable t) {
                            Toast.makeText(UpdateRemoveWalletActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletService.walletService.deleteWallet(walet.getId()).enqueue(new Callback<WalletPayload>() {
                    @Override
                    public void onResponse(Call<WalletPayload> call, Response<WalletPayload> response) {
                        if(MainActivity.currentWallet!=null){
                            if(MainActivity.currentWallet.getId().equals(walet.getId())){
                                MainActivity.currentWallet = null;
                            }
                            UpdateRemoveWalletActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<WalletPayload> call, Throwable t) {
                        Toast.makeText(UpdateRemoveWalletActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navClose :
                UpdateRemoveWalletActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}