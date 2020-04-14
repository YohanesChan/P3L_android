package com.example.kouvemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Frag.addCustomerFragment;
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.RecyclerView.DataCustomerAdapter;
import com.example.kouvemobile.Response.showCustomer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengCustActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvCustomer;
    private List<Customer> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataCustomerAdapter mDataCustomerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_cust);

        rvCustomer = findViewById(R.id.rv_customer);
        rvCustomer.setHasFixedSize(true);


        rvCustomer.setLayoutManager(new LinearLayoutManager(this));
        mDataCustomerAdapter = new DataCustomerAdapter(list, PengCustActivity.this);
        rvCustomer.setAdapter(mDataCustomerAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengCustActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showCustomer> call = apiInterface.tampilCustomer();
        call.enqueue(new Callback<showCustomer>() {
            @Override
            public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                list.addAll(response.body().getResult());
                mDataCustomerAdapter.notifyDataSetChanged();

                Log.e("data Customer", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showCustomer> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataCustomer.getListData());
        EditText search_etxt = findViewById(R.id.search_cst_etxt);
        search_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btnAddCustomer = findViewById(R.id.createcst_btn);
        btnAddCustomer.setOnClickListener(this);
        onFinishDialog();
    }

    @Override
    public void onClick(View v) {
        addCustomerFragment dialog = new addCustomerFragment();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void filter(String text){
        List<Customer> filteredList = new ArrayList<>();

        for (Customer item : list) {
            if (item.getNama_customer().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataCustomerAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

        Call<showCustomer> call = apiInterface.tampilCustomer();
        call.enqueue(new Callback<showCustomer>() {
            @Override
            public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                list = response.body().getResult();
                mDataCustomerAdapter.notifyDataSetChanged();
                mDataCustomerAdapter = new DataCustomerAdapter(list, PengCustActivity.this);
                rvCustomer.setAdapter(mDataCustomerAdapter);

            }

            @Override
            public void onFailure(Call<showCustomer> call, Throwable t) {

            }
        });
//        mDataCustomerAdapter.notifyDataSetChanged();
    }

}
