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
import com.example.kouvemobile.Frag.addUkuranFragment;
import com.example.kouvemobile.Model.Ukuran;
import com.example.kouvemobile.Model.Ukuran;
import com.example.kouvemobile.RecyclerView.DataUkuranAdapter;
import com.example.kouvemobile.RecyclerView.DataUkuranAdapter;
import com.example.kouvemobile.Response.showUkuran;
import com.example.kouvemobile.Response.showUkuran;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengUkActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvUkuran;
    private List<Ukuran> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataUkuranAdapter mDataUkuranAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_uk);

        rvUkuran = findViewById(R.id.rv_ukuran);
        rvUkuran.setHasFixedSize(true);


        rvUkuran.setLayoutManager(new LinearLayoutManager(this));
        mDataUkuranAdapter = new DataUkuranAdapter(list, PengUkActivity.this);
        rvUkuran.setAdapter(mDataUkuranAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengUkActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showUkuran> call = apiInterface.tampilUkuran();
        call.enqueue(new Callback<showUkuran>() {
            @Override
            public void onResponse(Call<showUkuran> call, Response<showUkuran> response) {
//                list.addAll(DataPegawai.getListData());
                list.addAll(response.body().getResult());
                mDataUkuranAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showUkuran> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataPegawai.getListData());
        EditText search_etxt = findViewById(R.id.search_uk_etxt);
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
        Button btnAddUk = findViewById(R.id.createuk_btn);
        btnAddUk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addUkuranFragment dialog = new addUkuranFragment();
        dialog.show(getSupportFragmentManager(), "dialog2");
    }

    private void filter(String text){
        List<Ukuran> filteredList = new ArrayList<>();

        for (Ukuran item : list) {
            if (item.getNama_ukuran_hewan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataUkuranAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

//        Toast.makeText(this, "test on finish", Toast.LENGTH_SHORT).show();

        Call<showUkuran> call = apiInterface.tampilUkuran();
        call.enqueue(new Callback<showUkuran>() {
            @Override
            public void onResponse(Call<showUkuran> call, Response<showUkuran> response) {
//                list.addAll(DataPegawai.getListData());
//                list.clear();
//                list.addAll(response.body().getResult());
                mDataUkuranAdapter.notifyDataSetChanged();
                mDataUkuranAdapter = new DataUkuranAdapter(response.body().getResult(), PengUkActivity.this);
                rvUkuran.setAdapter(mDataUkuranAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<showUkuran> call, Throwable t) {

            }
        });
//        mDataPegawaiAdapter.notifyDataSetChanged();
    }
}
