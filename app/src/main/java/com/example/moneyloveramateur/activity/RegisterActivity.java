package com.example.moneyloveramateur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.api.UserService;
import com.example.moneyloveramateur.model.User;
import com.example.moneyloveramateur.payload.UserPayload;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFullName, etUsername, etPassword, etConfirmPassword;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setUpOnclickButton();
    }

    private void setUpOnclickButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                if(TextUtils.isEmpty(fullName)
                    || TextUtils.isEmpty(username)
                    || TextUtils.isEmpty(password)
                    || TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Các trường không được để trống!", Toast.LENGTH_LONG).show();
                }
                else {
                    if(password.equals(confirmPassword)){
                        User user = new User(fullName, username, password);
                        UserService.userService.register(user).enqueue(new Callback<UserPayload>() {
                            @Override
                            public void onResponse(Call<UserPayload> call, Response<UserPayload> response) {
                                UserPayload userPayload = response.body();
                                if(userPayload.isSuccess()){
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công.", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Tài khoản đã được sử dụng.", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<UserPayload> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Lỗi mạng.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initView() {
        etFullName = findViewById(R.id.etFullname);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConformPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
    }
}