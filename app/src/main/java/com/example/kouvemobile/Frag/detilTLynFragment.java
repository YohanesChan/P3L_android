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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.DTLayanan;
import com.example.kouvemobile.Model.TransaksiL;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataTlAdapter;
import com.example.kouvemobile.Response.showTL;
import com.example.kouvemobile.Response.showTransaksiL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class detilTLynFragment extends Fragment {
    private ApiInterface apiInterface;
    private TransaksiL trl;
    TextView status,kode,total;

    private RecyclerView rvDtl;
    private List<DTLayanan> list = new ArrayList<>();
    private DataTlAdapter mDataTlAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_detil_transaksil, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        Button btn_confirm = v.findViewById(R.id.confirm_trl_btn);
        Button btn_cancel = v.findViewById(R.id.cancel_trl_btn);
        Button btn_back = v.findViewById(R.id.back_trl_btn);

        Bundle bundle = getArguments();
        trl = new TransaksiL();
        trl.setId_tlayanan(bundle.getInt("id"));
        trl.setKode_tlayanan(bundle.getString("kode"));
        trl.setStatus_tlayanan(bundle.getString("status"));
        trl.setTotal_tlayanan(bundle.getInt("total"));

        status = v.findViewById(R.id.status_dtrl_txt);
        kode = v.findViewById(R.id.id_dtrl_txt);
        total = v.findViewById(R.id.ttl_dtrl_txt);
        status.setText(trl.getStatus_tlayanan());
        kode.setText(trl.getKode_tlayanan());
        total.setText(String.valueOf(trl.getTotal_tlayanan()));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTransaksiL> call = apiInterface.hapusTransaksiL(trl.getId_tlayanan(), mnama,mnama,mnama);
                call.enqueue(new Callback<showTransaksiL>() {
                    @Override
                    public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Transaksi Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Transaksi gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new tampilTLynFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<showTransaksiL> call, Throwable t) {

                    }
                });
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTransaksiL> call = apiInterface.editTransaksiL(trl.getId_tlayanan(),mnama,mnama);
                call.enqueue(new Callback<showTransaksiL>() {
                    @Override
                    public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Pengadaan Telah Selesai", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Pengadaan gagal diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new tampilTLynFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<showTransaksiL> call, Throwable t) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new tampilTLynFragment()).commit();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rvDtl = v.findViewById(R.id.rv_dtrl);
        rvDtl.setHasFixedSize(true);

        rvDtl.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataTlAdapter = new DataTlAdapter(list, getContext());
        rvDtl.setAdapter(mDataTlAdapter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showTL> call = apiInterface.tampilDTransaksiL(trl.getId_tlayanan());
        call.enqueue(new Callback<showTL>() {
            @Override
            public void onResponse(Call<showTL> call, Response<showTL> response) {
                list.addAll(0,response.body().getResult());
                mDataTlAdapter.notifyDataSetChanged();

                Log.e("data pengadaan", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showTL> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

        Button btnAddDtrl = v.findViewById(R.id.createdtrl_btn);
        btnAddDtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDTransaksilFragment dialog = new addDTransaksilFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", trl.getId_tlayanan());
                bundle.putString("kode", trl.getKode_tlayanan());
                bundle.putString("status", trl.getStatus_tlayanan());
                bundle.putInt("total", trl.getTotal_tlayanan());
                dialog.setArguments(bundle);

                dialog.show(getFragmentManager(), "dialog");
            }
        });

        EditText search_etxt = v.findViewById(R.id.search_dtrl_etxt);
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

        return v;
    }

    private void filter(String text){
        List<DTLayanan> filteredList = new ArrayList<>();

        for (DTLayanan item : list) {
            if (item.getNama_playanan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mDataTlAdapter.filterList(filteredList);
    }
}
