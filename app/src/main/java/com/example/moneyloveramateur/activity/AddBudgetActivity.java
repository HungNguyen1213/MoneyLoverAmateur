package com.example.moneyloveramateur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneyloveramateur.R;
import com.example.moneyloveramateur.adapter.SpinnerAdapter;
import com.example.moneyloveramateur.api.BudgetService;
import com.example.moneyloveramateur.model.Budget;
import com.example.moneyloveramateur.model.Group;
import com.example.moneyloveramateur.model.ItemSpinner;
import com.example.moneyloveramateur.payload.BudgetPayload;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBudgetActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etBalance;
    private Spinner spGroup;
    private Button btnAdd, btnBack;
    private SpinnerAdapter adapter;

    public AddBudgetActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        etBalance = findViewById(R.id.etBalance);
        spGroup = findViewById(R.id.spGroup);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        adapter = new SpinnerAdapter(this, R.layout.item_group_selected, getListItem());
        spGroup.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBudgetActivity.this.finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etBalance.getText().toString().trim())){
                    Toast.makeText(AddBudgetActivity.this, "Số tiền không được để trống!", Toast.LENGTH_LONG).show();
                }
                else if(!TextUtils.isDigitsOnly(etBalance.getText().toString().trim())){
                    etBalance.setError("Số tiền chỉ chứa chữ số 0-9");
                }
                else if(Long.parseLong(etBalance.getText().toString().trim()) == 0){
                    etBalance.setError("Số tiền phải lớn hơn 0");
                }
                else{
                    long balance = Long.parseLong(etBalance.getText().toString().trim());
                    Group g = new Group();
                    Long groupId = (long) spGroup.getSelectedItemPosition() + 1;
                    g.setId(groupId);
                    Budget budget = new Budget(balance, g, null);
                    if(MainActivity.currentWallet!=null){
                        BudgetService.budgetService.createBudget(budget, MainActivity.currentWallet.getId()).enqueue(new Callback<BudgetPayload>() {
                            @Override
                            public void onResponse(Call<BudgetPayload> call, Response<BudgetPayload> response) {
                                BudgetPayload budgetPayload = response.body();
                                if(budgetPayload.isSuccess()){
                                    Toast.makeText(AddBudgetActivity.this, "Tạo ngân sách thành công.", Toast.LENGTH_LONG).show();
                                    AddBudgetActivity.this.finish();
                                }
                                else {
                                    Toast.makeText(AddBudgetActivity.this, "Tồn tại ngân sách thuộc nhóm này rồi!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BudgetPayload> call, Throwable t) {
                                Toast.makeText(AddBudgetActivity.this, "Lỗi mạng!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private List<ItemSpinner> getListItem() {
        List<ItemSpinner> rs = new ArrayList<>();
        String[] listGroup = getResources().getStringArray(R.array.listBudget);
        for(int i=0; i<=7; i++){
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
            }
            rs.add(itemSpinner);
        }
        return rs;
    }
}