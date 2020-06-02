package com.example.kouvemobile;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Frag.tampilTLynFragment;
import com.example.kouvemobile.Frag.tampilTPdkFragment;
import com.example.kouvemobile.Model.TransaksiL;
import com.example.kouvemobile.RecyclerView.DataTransaksilAdapter;
import com.example.kouvemobile.Response.showTransaksiL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiLActivity extends AppCompatActivity {

    private RecyclerView rvTransaksiL;
    private List<TransaksiL> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataTransaksilAdapter mDataTransaksilAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksil);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new tampilTLynFragment()).commit();
    }

    public void onFinishDialog() {

        Call<showTransaksiL> call = apiInterface.tampilTransaksiL();
        call.enqueue(new Callback<showTransaksiL>() {
            @Override
            public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {
                mDataTransaksilAdapter.notifyDataSetChanged();
                mDataTransaksilAdapter = new DataTransaksilAdapter(response.body().getResult(), TransaksiLActivity.this);
                rvTransaksiL.setAdapter(mDataTransaksilAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<showTransaksiL> call, Throwable t) {

            }
        });
    }
}
