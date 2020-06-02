package com.example.kouvemobile;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Frag.tampilTPdkFragment;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.RecyclerView.DataTransaksipAdapter;
import com.example.kouvemobile.Response.showTransaksiP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPActivity extends AppCompatActivity {

    private RecyclerView rvTransaksiP;
    private List<TransaksiP> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataTransaksipAdapter mDataTransaksiPAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksip);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new tampilTPdkFragment()).commit();
    }

    public void onFinishDialog() {

        Call<showTransaksiP> call = apiInterface.tampilTransaksiP();
        call.enqueue(new Callback<showTransaksiP>() {
            @Override
            public void onResponse(Call<showTransaksiP> call, Response<showTransaksiP> response) {
                mDataTransaksiPAdapter.notifyDataSetChanged();
                mDataTransaksiPAdapter = new DataTransaksipAdapter(response.body().getResult(), TransaksiPActivity.this);
                rvTransaksiP.setAdapter(mDataTransaksiPAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());
            }

            @Override
            public void onFailure(Call<showTransaksiP> call, Throwable t) {

            }
        });
    }
}
