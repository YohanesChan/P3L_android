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
import com.example.kouvemobile.Frag.addJenisFragment;
import com.example.kouvemobile.Model.Jenis;
import com.example.kouvemobile.Model.Jenis;
import com.example.kouvemobile.RecyclerView.DataJenisAdapter;
import com.example.kouvemobile.RecyclerView.DataJenisAdapter;
import com.example.kouvemobile.Response.showJenis;
import com.example.kouvemobile.Response.showJenis;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengJnsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvJenis;
    private List<Jenis> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataJenisAdapter mDataJenisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_jns);

        rvJenis = findViewById(R.id.rv_jenis);
        rvJenis.setHasFixedSize(true);


        rvJenis.setLayoutManager(new LinearLayoutManager(this));
        mDataJenisAdapter = new DataJenisAdapter(list, PengJnsActivity.this);
        rvJenis.setAdapter(mDataJenisAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengJnsActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showJenis> call = apiInterface.tampilJenis();
        call.enqueue(new Callback<showJenis>() {
            @Override
            public void onResponse(Call<showJenis> call, Response<showJenis> response) {
//                list.addAll(DataPegawai.getListData());
                list.addAll(response.body().getResult());
                mDataJenisAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showJenis> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataPegawai.getListData());
        EditText search_etxt = findViewById(R.id.search_jns_etxt);
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
        Button btnAddJns = findViewById(R.id.createjns_btn);
        btnAddJns.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addJenisFragment dialog = new addJenisFragment();
        dialog.show(getSupportFragmentManager(), "dialog2");
    }

    private void filter(String text){
        List<Jenis> filteredList = new ArrayList<>();

        for (Jenis item : list) {
            if (item.getNama_jenis_hewan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataJenisAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

//        Toast.makeText(this, "test on finish", Toast.LENGTH_SHORT).show();

        Call<showJenis> call = apiInterface.tampilJenis();
        call.enqueue(new Callback<showJenis>() {
            @Override
            public void onResponse(Call<showJenis> call, Response<showJenis> response) {
//                list.addAll(DataPegawai.getListData());
//                list.clear();
//                list.addAll(response.body().getResult());
                mDataJenisAdapter.notifyDataSetChanged();
                mDataJenisAdapter = new DataJenisAdapter(response.body().getResult(), PengJnsActivity.this);
                rvJenis.setAdapter(mDataJenisAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<showJenis> call, Throwable t) {

            }
        });
//        mDataPegawaiAdapter.notifyDataSetChanged();
    }

}
