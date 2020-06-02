package com.example.kouvemobile.Frag;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataPengadaanAdapter;
import com.example.kouvemobile.Response.showPengadaan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class tampilPgdFragment extends Fragment {
    private RecyclerView rvPengadaan;
    private List<Pengadaan> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataPengadaanAdapter mDataPengadaanAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tampilpgd, container, false);

        rvPengadaan = v.findViewById(R.id.rv_pgd);
        rvPengadaan.setHasFixedSize(true);

        rvPengadaan.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataPengadaanAdapter = new DataPengadaanAdapter(list, getContext());
        rvPengadaan.setAdapter(mDataPengadaanAdapter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showPengadaan> call = apiInterface.tampilPengadaan();
        call.enqueue(new Callback<showPengadaan>() {
            @Override
            public void onResponse(Call<showPengadaan> call, Response<showPengadaan> response) {
                list.addAll(0,response.body().getResult());
                mDataPengadaanAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showPengadaan> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

        EditText search_etxt = v.findViewById(R.id.search_pgd_etxt);
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
        Button btnAddPgd = v.findViewById(R.id.createpgd_btn);
        btnAddPgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPengadaanFragment dialog = new addPengadaanFragment();
                dialog.show(getFragmentManager(), "dialog");
            }
        });

        return v;
    }

    private void filter(String text){
        List<Pengadaan> filteredList = new ArrayList<>();

        for (Pengadaan item : list) {
            if (item.getKode_pengadaan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mDataPengadaanAdapter.filterList(filteredList);
    }

}
