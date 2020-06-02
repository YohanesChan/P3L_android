package com.example.kouvemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.RecyclerView.DataProdukAdapter;
import com.example.kouvemobile.Response.showProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.app.NotificationCompat.*;

public class PdkHabisActivity extends AppCompatActivity{

    private RecyclerView rvProduk;
    private List<Produk> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataProdukAdapter mDataProdukAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdk_habis);

        rvProduk = findViewById(R.id.rv_pdk_habis);
        rvProduk.setHasFixedSize(true);


        rvProduk.setLayoutManager(new LinearLayoutManager(this));
        mDataProdukAdapter = new DataProdukAdapter(list, PdkHabisActivity.this);
        rvProduk.setAdapter(mDataProdukAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PdkHabisActivity.this);
        progressDialog.show();

        Call<showProduk> call = apiInterface.tampilProdukHabis();
        call.enqueue(new Callback<showProduk>() {
            @Override
            public void onResponse(Call<showProduk> call, Response<showProduk> response) {
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
    }
}
