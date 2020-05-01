package com.example.kouvemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Frag.addProdukFragment;
import com.example.kouvemobile.Frag.addProdukFragment;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.RecyclerView.DataProdukAdapter;
import com.example.kouvemobile.RecyclerView.DataProdukAdapter;
import com.example.kouvemobile.Response.showProduk;
import com.example.kouvemobile.Response.showProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengPdkActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView rvProduk;
    private List<Produk> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataProdukAdapter mDataProdukAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_pdk);

        rvProduk = findViewById(R.id.rv_produk);
        rvProduk.setHasFixedSize(true);


        rvProduk.setLayoutManager(new LinearLayoutManager(this));
        mDataProdukAdapter = new DataProdukAdapter(list, PengPdkActivity.this);
        rvProduk.setAdapter(mDataProdukAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengPdkActivity.this);
        progressDialog.show();

        Call<showProduk> call = apiInterface.tampilProduk();
        call.enqueue(new Callback<showProduk>() {
            @Override
            public void onResponse(Call<showProduk> call, Response<showProduk> response) {
//                list.addAll(DataPegawai.getListData());
                list.addAll(response.body().getResult());
                mDataProdukAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showProduk> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataPegawai.getListData());
        EditText search_etxt = findViewById(R.id.search_pdk_etxt);
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
                filter(s.toString());
            }
        });
        Button btnAddSupp = findViewById(R.id.createpdk_btn);
        btnAddSupp.setOnClickListener(this);
        onFinishDialog();
    }

    @Override
    public void onClick(View v) {
        addProdukFragment dialog = new addProdukFragment();
        dialog.show(getSupportFragmentManager(), "dialog2");
    }

    private void filter(String text){
        List<Produk> filteredList = new ArrayList<>();

        for (Produk item : list) {
            if (item.getNama_produk().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataProdukAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

        Call<showProduk> call = apiInterface.tampilProduk();
        call.enqueue(new Callback<showProduk>() {
            @Override
            public void onResponse(Call<showProduk> call, Response<showProduk> response) {
                mDataProdukAdapter.notifyDataSetChanged();
                mDataProdukAdapter = new DataProdukAdapter(response.body().getResult(), PengPdkActivity.this);
                rvProduk.setAdapter(mDataProdukAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<showProduk> call, Throwable t) {

            }
        });
//        mDataPegawaiAdapter.notifyDataSetChanged();
    }
}
