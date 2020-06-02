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
import com.example.kouvemobile.Model.DPengadaan;
import com.example.kouvemobile.Model.DTProduk;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataDpAdapter;
import com.example.kouvemobile.RecyclerView.DataTpAdapter;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showTP;
import com.example.kouvemobile.Response.showTransaksiP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class detilTPdkFragment extends Fragment {
    private ApiInterface apiInterface;
    private TransaksiP trp;
    TextView status,kode,total;

    private RecyclerView rvDtp;
    private List<DTProduk> list = new ArrayList<>();
    private DataTpAdapter mDataTpAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_detil_transaksip, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        Button btn_confirm = v.findViewById(R.id.confirm_trp_btn);
        Button btn_cancel = v.findViewById(R.id.cancel_trp_btn);
        Button btn_back = v.findViewById(R.id.back_trp_btn);

        Bundle bundle = getArguments();
        trp = new TransaksiP();
        trp.setId_tproduk(bundle.getInt("id"));
        trp.setKode_tproduk(bundle.getString("kode"));
        trp.setStatus_tproduk(bundle.getString("status"));
        trp.setTotal_tproduk(bundle.getInt("total"));

        status = v.findViewById(R.id.status_dtrp_txt);
        kode = v.findViewById(R.id.id_dtrp_txt);
        total = v.findViewById(R.id.ttl_dtrp_txt);
        status.setText(trp.getStatus_tproduk());
        kode.setText(trp.getKode_tproduk());
        total.setText(String.valueOf(trp.getTotal_tproduk()));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTransaksiP> call = apiInterface.hapusTransaksiP(trp.getId_tproduk(), mnama,mnama,mnama);
                call.enqueue(new Callback<showTransaksiP>() {
                    @Override
                    public void onResponse(Call<showTransaksiP> call, Response<showTransaksiP> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Transaksi Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Transaksi gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new tampilTPdkFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<showTransaksiP> call, Throwable t) {

                    }
                });
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTransaksiP> call = apiInterface.editTransaksiP(trp.getId_tproduk(),mnama,mnama);
                call.enqueue(new Callback<showTransaksiP>() {
                    @Override
                    public void onResponse(Call<showTransaksiP> call, Response<showTransaksiP> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Pengadaan Telah Selesai", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Pengadaan gagal diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new tampilTPdkFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<showTransaksiP> call, Throwable t) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new tampilTPdkFragment()).commit();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rvDtp = v.findViewById(R.id.rv_dtrp);
        rvDtp.setHasFixedSize(true);

        rvDtp.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataTpAdapter = new DataTpAdapter(list, getContext());
        rvDtp.setAdapter(mDataTpAdapter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showTP> call = apiInterface.tampilDTransaksiP(trp.getId_tproduk());
        call.enqueue(new Callback<showTP>() {
            @Override
            public void onResponse(Call<showTP> call, Response<showTP> response) {
                list.addAll(0,response.body().getResult());
                mDataTpAdapter.notifyDataSetChanged();

                Log.e("data pengadaan", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showTP> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

        Button btnAddDtrp = v.findViewById(R.id.createdtrp_btn);
        btnAddDtrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDTransaksipFragment dialog = new addDTransaksipFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", trp.getId_tproduk());
                bundle.putString("kode", trp.getKode_tproduk());
                bundle.putString("status", trp.getStatus_tproduk());
                bundle.putInt("total", trp.getTotal_tproduk());
                dialog.setArguments(bundle);

                dialog.show(getFragmentManager(), "dialog");
            }
        });

        EditText search_etxt = v.findViewById(R.id.search_dtrp_etxt);
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
        List<DTProduk> filteredList = new ArrayList<>();

        for (DTProduk item : list) {
            if (item.getNama_pproduk().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mDataTpAdapter.filterList(filteredList);
    }
}
