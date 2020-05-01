package com.example.kouvemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Frag.HomeFragment;
import com.example.kouvemobile.Frag.addCustomerFragment;
import com.example.kouvemobile.Frag.addPengadaanFragment;
import com.example.kouvemobile.Frag.tampilPgdFragment;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.RecyclerView.DataPengadaanAdapter;
import com.example.kouvemobile.RecyclerView.DataPengadaanAdapter;
import com.example.kouvemobile.RecyclerView.DataSupplierAdapter;
import com.example.kouvemobile.Response.showHewan;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showPengadaan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengadaanActivity extends AppCompatActivity{

    private RecyclerView rvPengadaan;
    private List<Pengadaan> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataPengadaanAdapter mDataPengadaanAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengadaan);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new tampilPgdFragment()).commit();
    }

    public void onFinishDialog() {

        Call<showPengadaan> call = apiInterface.tampilPengadaan();
        call.enqueue(new Callback<showPengadaan>() {
            @Override
            public void onResponse(Call<showPengadaan> call, Response<showPengadaan> response) {
                mDataPengadaanAdapter.notifyDataSetChanged();
                mDataPengadaanAdapter = new DataPengadaanAdapter(response.body().getResult(), PengadaanActivity.this);
                rvPengadaan.setAdapter(mDataPengadaanAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<showPengadaan> call, Throwable t) {

            }
        });
    }
}
