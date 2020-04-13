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
import com.example.kouvemobile.Frag.addPegawaiFragment;
import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.RecyclerView.DataPegawaiAdapter;
import com.example.kouvemobile.Response.showPegawai;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengPgwActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvPegawai;
    private List<Pegawai> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataPegawaiAdapter mDataPegawaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_pgw);

        rvPegawai = findViewById(R.id.rv_pegawai);
        rvPegawai.setHasFixedSize(true);


        rvPegawai.setLayoutManager(new LinearLayoutManager(this));
        mDataPegawaiAdapter = new DataPegawaiAdapter(list, PengPgwActivity.this);
        rvPegawai.setAdapter(mDataPegawaiAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengPgwActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showPegawai> call = apiInterface.tampilPegawai();
        call.enqueue(new Callback<showPegawai>() {
            @Override
            public void onResponse(Call<showPegawai> call, Response<showPegawai> response) {
//                list.addAll(DataPegawai.getListData());
                list.addAll(response.body().getResult());
                mDataPegawaiAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showPegawai> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataPegawai.getListData());
        EditText search_etxt = findViewById(R.id.search_pgw_etxt);
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
        Button btnAddPegawai = findViewById(R.id.createpgw_btn);
        btnAddPegawai.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addPegawaiFragment dialog = new addPegawaiFragment();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void filter(String text){
        List<Pegawai> filteredList = new ArrayList<>();

        for (Pegawai item : list) {
            if (item.getNama_pegawai().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataPegawaiAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

        Call<showPegawai> call = apiInterface.tampilPegawai();
        call.enqueue(new Callback<showPegawai>() {
            @Override
            public void onResponse(Call<showPegawai> call, Response<showPegawai> response) {

                mDataPegawaiAdapter.notifyDataSetChanged();
                mDataPegawaiAdapter = new DataPegawaiAdapter(response.body().getResult(), PengPgwActivity.this);
                rvPegawai.setAdapter(mDataPegawaiAdapter);

            }

            @Override
            public void onFailure(Call<showPegawai> call, Throwable t) {

            }
        });
//        mDataPegawaiAdapter.notifyDataSetChanged();
    }

}
