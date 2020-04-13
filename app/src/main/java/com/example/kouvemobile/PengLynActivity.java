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
import com.example.kouvemobile.Frag.addLayananFragment;
import com.example.kouvemobile.Model.Layanan;
import com.example.kouvemobile.RecyclerView.DataLayananAdapter;
import com.example.kouvemobile.Response.showLayanan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengLynActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView rvLayanan;
    private List<Layanan> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataLayananAdapter mDataLayananAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_lyn);

        rvLayanan = findViewById(R.id.rv_layanan);
        rvLayanan.setHasFixedSize(true);


        rvLayanan.setLayoutManager(new LinearLayoutManager(this));
        mDataLayananAdapter = new DataLayananAdapter(list, PengLynActivity.this);
        rvLayanan.setAdapter(mDataLayananAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengLynActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showLayanan> call = apiInterface.tampilLayanan();
        call.enqueue(new Callback<showLayanan>() {
            @Override
            public void onResponse(Call<showLayanan> call, Response<showLayanan> response) {
//                list.addAll(DataPegawai.getListData());
                list.addAll(response.body().getResult());
                mDataLayananAdapter.notifyDataSetChanged();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showLayanan> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataPegawai.getListData());
        EditText search_etxt = findViewById(R.id.search_lyn_etxt);
        search_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        Button btnAddSupp = findViewById(R.id.createlyn_btn);
        btnAddSupp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addLayananFragment dialog = new addLayananFragment();
        dialog.show(getSupportFragmentManager(), "dialog2");
    }

    private void filter(String text){
        List<Layanan> filteredList = new ArrayList<>();

        for (Layanan item : list) {
            if (item.getNama_layanan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataLayananAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

//        Toast.makeText(this, "test on finish", Toast.LENGTH_SHORT).show();

        Call<showLayanan> call = apiInterface.tampilLayanan();
        call.enqueue(new Callback<showLayanan>() {
            @Override
            public void onResponse(Call<showLayanan> call, Response<showLayanan> response) {
                mDataLayananAdapter.notifyDataSetChanged();
                mDataLayananAdapter = new DataLayananAdapter(response.body().getResult(), PengLynActivity.this);
                rvLayanan.setAdapter(mDataLayananAdapter);

            }

            @Override
            public void onFailure(Call<showLayanan> call, Throwable t) {

            }
        });
    }
}
