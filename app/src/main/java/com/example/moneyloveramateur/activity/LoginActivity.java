package com.example.moneyloveramateur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.api.UserService;
import com.example.moneyloveramateur.api.WalletService;
import com.example.moneyloveramateur.model.Wallet;
import com.example.moneyloveramateur.payload.ListWalletPayload;
import com.example.moneyloveramateur.payload.UserPayload;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setUpButtonOnclick();
    }

    private void setUpButtonOnclick() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Các trường không được để trống", Toast.LENGTH_LONG).show();
                }
                else {
                    UserService.userService.checkLogin(username, password).enqueue(new Callback<UserPayload>() {
                        @Override
                        public void onResponse(Call<UserPayload> call, Response<UserPayload> response) {
                            UserPayload userPayload = response.body();
                            if (userPayload.isSuccess()){
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, AfterLoginActivity.class);
                                MainActivity.currentUser = userPayload.getUser();
                                LoginActivity.this.startActivity(i);
                                LoginActivity.this.finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserPayload> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Lỗi mạng.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
    }
}