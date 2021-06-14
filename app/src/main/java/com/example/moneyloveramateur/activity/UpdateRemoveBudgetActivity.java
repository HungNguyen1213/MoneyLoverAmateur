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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.api.BudgetService;
import com.example.moneyloveramateur.model.Budget;
import com.example.moneyloveramateur.model.Group;
import com.example.moneyloveramateur.model.ReportBudget;
import com.example.moneyloveramateur.payload.BudgetPayload;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRemoveBudgetActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etBalance;
    private TextView tvGroup;
    private Button btnUpdate, btnDelete;
    private ImageView icGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_remove_budget);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.close_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navClose:
                UpdateRemoveBudgetActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        etBalance = findViewById(R.id.etBalance);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        tvGroup = findViewById(R.id.tvGroup);
        icGroup = findViewById(R.id.icGroup);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        ReportBudget reportBudget = (ReportBudget) intent.getSerializableExtra("budget");
        Budget budget = reportBudget.getBudget();

        etBalance.setText(String.valueOf(budget.getBalance()));

        String[] listBudget = getResources().getStringArray(R.array.listBudget);
        int index = budget.getGroup().getId().intValue() - 1;
        tvGroup.setText(listBudget[index]);
        switch(index){
            case 0:
                icGroup.setImageResource(R.drawable.ic_category_foodndrink);
                break;
            case 1:
                icGroup.setImageResource(R.drawable.ic_category_transport);
                break;
            case 2:
                icGroup.setImageResource(R.drawable.ic_category_family);
                break;
            case 3:
                icGroup.setImageResource(R.drawable.ic_living);
                break;
            case 4:
                icGroup.setImageResource(R.drawable.ic_category_doctor);
                break;
            case 5:
                icGroup.setImageResource(R.drawable.ic_category_shopping);
                break;
            case 6:
                icGroup.setImageResource(R.drawable.ic_relax);
                break;
            case 7:
                icGroup.setImageResource(R.drawable.ic_category_give);
                break;
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetService.budgetService.deleteBudget(budget.getId()).enqueue(new Callback<BudgetPayload>() {
                    @Override
                    public void onResponse(Call<BudgetPayload> call, Response<BudgetPayload> response) {
                        if (response.body().isSuccess()) {
                            Toast.makeText(UpdateRemoveBudgetActivity.this, "Đã xóa ngân sách.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateRemoveBudgetActivity.this, "Thất bại.", Toast.LENGTH_LONG).show();
                        }
                        UpdateRemoveBudgetActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<BudgetPayload> call, Throwable t) {
                        Toast.makeText(UpdateRemoveBudgetActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etBalance.getText().toString().trim())){
                    Toast.makeText(UpdateRemoveBudgetActivity.this, "Số tiền không được để trống!", Toast.LENGTH_LONG).show();
                }
                else if(!TextUtils.isDigitsOnly(etBalance.getText().toString().trim())){
                    etBalance.setError("Số tiền chỉ chứa chữ số 0-9");
                }
                else if(Long.parseLong(etBalance.getText().toString().trim()) == 0){
                    etBalance.setError("Số tiền phải lớn hơn 0");
                }
                else{
                    long balance = Long.parseLong(etBalance.getText().toString().trim());
                    budget.setBalance(balance);
                    BudgetService.budgetService.updateBudget(budget).enqueue(new Callback<BudgetPayload>() {
                        @Override
                        public void onResponse(Call<BudgetPayload> call, Response<BudgetPayload> response) {
                            if(response.body().isSuccess()){
                                Toast.makeText(UpdateRemoveBudgetActivity.this, "Sửa thông tin thành công.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(UpdateRemoveBudgetActivity.this, "Tồn tại ngân sách thuộc nhóm này rồ!.", Toast.LENGTH_LONG).show();
                            }
                            UpdateRemoveBudgetActivity.this.finish();
                        }

                        @Override
                        public void onFailure(Call<BudgetPayload> call, Throwable t) {
                            Toast.makeText(UpdateRemoveBudgetActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}