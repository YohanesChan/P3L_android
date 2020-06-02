package com.example.kouvemobile.Frag;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataTransaksipAdapter;
import com.example.kouvemobile.Response.showTransaksiP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tampilTPdkFragment extends Fragment {
    private RecyclerView rvTproduk;
    private List<TransaksiP> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataTransaksipAdapter mDataTransaksipAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tampiltpdk, container, false);

        rvTproduk = v.findViewById(R.id.rv_tpdk);
        rvTproduk.setHasFixedSize(true);

        rvTproduk.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataTransaksipAdapter = new DataTransaksipAdapter(list, getContext());
        rvTproduk.setAdapter(mDataTransaksipAdapter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showTransaksiP> call = apiInterface.tampilTransaksiP();
        call.enqueue(new Callback<showTransaksiP>() {
            @Override
            public void onResponse(Call<showTransaksiP> call, Response<showTransaksiP> response) {
                list.addAll(0,response.body().getResult());
                mDataTransaksipAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showTransaksiP> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

        EditText search_etxt = v.findViewById(R.id.search_tpdk_etxt);
        search_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                onFinishDialog();
                filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btnAddTPdk = v.findViewById(R.id.createtpdk_btn);
        btnAddTPdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTPdkFragment dialog = new addTPdkFragment();
                dialog.show(getFragmentManager(), "dialog");
            }
        });

        return v;
    }

    private void filter(String text){
        List<TransaksiP> filteredList = new ArrayList<>();

        for (TransaksiP item : list) {
            if (item.getKode_tproduk().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mDataTransaksipAdapter.filterList(filteredList);
    }
}
