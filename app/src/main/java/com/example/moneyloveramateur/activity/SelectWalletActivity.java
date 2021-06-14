package com.example.moneyloveramateur.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.adapter.ListWalletViewAdapter;
import com.example.moneyloveramateur.adapter.SelectWalletViewAdapter;
import com.example.moneyloveramateur.api.WalletService;
import com.example.moneyloveramateur.payload.ListWalletPayload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectWalletActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SelectWalletViewAdapter adapter;
    private FloatingActionButton btnAdd;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_wallet);
        recyclerView = findViewById(R.id.recycleView);
        btnAdd = findViewById(R.id.btnAdd);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new SelectWalletViewAdapter(this);
        setDataRecyclerView();
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectWalletActivity.this, AddWalletActivity.class);
                SelectWalletActivity.this.startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_wallet_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navUpdate:
                Intent i = new Intent(SelectWalletActivity.this, ManageWalletActivity.class);
                SelectWalletActivity.this.startActivity(i);
                break;
            case R.id.navClose:
                SelectWalletActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataRecyclerView();
    }

    private void setDataRecyclerView(){
        WalletService.walletService.findWalletByOwner(MainActivity.currentUser.getId()).enqueue(new Callback<ListWalletPayload>() {
            @Override
            public void onResponse(Call<ListWalletPayload> call, Response<ListWalletPayload> response) {
                ListWalletPayload listWalletPayload = response.body();
                adapter.setData(listWalletPayload.getListWallet());
            }

            @Override
            public void onFailure(Call<ListWalletPayload> call, Throwable t) {
                Toast.makeText(SelectWalletActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
            }
        });
    }
}