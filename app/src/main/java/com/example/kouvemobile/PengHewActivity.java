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
import com.example.kouvemobile.Frag.addHewanFragment;
import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.RecyclerView.DataHewanAdapter;
import com.example.kouvemobile.Response.showHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengHewActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvHewan;
    private List<Hewan> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataHewanAdapter mDataHewanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_hew);

        rvHewan = findViewById(R.id.rv_hew);
        rvHewan.setHasFixedSize(true);


        rvHewan.setLayoutManager(new LinearLayoutManager(this));
        mDataHewanAdapter = new DataHewanAdapter(list, PengHewActivity.this);
        rvHewan.setAdapter(mDataHewanAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengHewActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showHewan> call = apiInterface.tampilHewan();
        call.enqueue(new Callback<showHewan>() {
            @Override
            public void onResponse(Call<showHewan> call, Response<showHewan> response) {
                list.addAll(response.body().getResult());
                mDataHewanAdapter.notifyDataSetChanged();

                Log.e("data Hewan", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showHewan> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataHewan.getListData());
        EditText search_etxt = findViewById(R.id.search_hew_etxt);
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
        Button btnAddHewan = findViewById(R.id.createhew_btn);
        btnAddHewan.setOnClickListener(this);
        onFinishDialog();
    }

    @Override
    public void onClick(View v) {
        addHewanFragment dialog = new addHewanFragment();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void filter(String text){
        List<Hewan> filteredList = new ArrayList<>();

        for (Hewan item : list) {
            if (item.getNama_hewan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataHewanAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

        Call<showHewan> call = apiInterface.tampilHewan();
        call.enqueue(new Callback<showHewan>() {
            @Override
            public void onResponse(Call<showHewan> call, Response<showHewan> response) {
                list = response.body().getResult();
                mDataHewanAdapter.notifyDataSetChanged();
                mDataHewanAdapter = new DataHewanAdapter(list, PengHewActivity.this);
                rvHewan.setAdapter(mDataHewanAdapter);

            }

            @Override
            public void onFailure(Call<showHewan> call, Throwable t) {

            }
        });
    }

}
