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
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.Model.DPengadaan;
import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.PengHewActivity;
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataDpAdapter;
import com.example.kouvemobile.RecyclerView.DataPengadaanAdapter;
import com.example.kouvemobile.RecyclerView.DataSupplierAdapter;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showSupplier;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class detilPgdFragment extends Fragment {
    private ApiInterface apiInterface;
    private Pengadaan pgd;
    TextView status,kode,total;
    String value;
    private RecyclerView rvDp;
    private List<DPengadaan> list = new ArrayList<>();
    private DataDpAdapter mDataDpAdapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_detil_pengadaan, container, false);
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        Button btn_confirm = v.findViewById(R.id.confirm_pgd_btn);
        Button btn_cancel = v.findViewById(R.id.cancel_pgd_btn);
        Button btn_back = v.findViewById(R.id.back_pgd_btn);

        Bundle bundle = getArguments();
        pgd = new Pengadaan();
        pgd.setId_pengadaan(bundle.getInt("id"));
        pgd.setKode_pengadaan(bundle.getString("kode"));
        pgd.setStatus_PO(bundle.getString("status"));
        pgd.setTotal_pengadaan(bundle.getInt("total"));

        status = v.findViewById(R.id.status_dpgd_txt);
        kode = v.findViewById(R.id.id_dpgd_txt);
        total = v.findViewById(R.id.ttl_dpgd_txt);
        status.setText(pgd.getStatus_PO());
        kode.setText(pgd.getKode_pengadaan());
        total.setText(pgd.getTotal_pengadaan().toString());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showPengadaan> call = apiInterface.hapusPengadaan(pgd.getId_pengadaan(), mnama,mnama,mnama);
                call.enqueue(new Callback<showPengadaan>() {
                    @Override
                    public void onResponse(Call<showPengadaan> call, Response<showPengadaan> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Pengadaan Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Pengadaan gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new tampilPgdFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<showPengadaan> call, Throwable t) {

                    }
                });
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showPengadaan> call = apiInterface.editPengadaan(pgd.getId_pengadaan(),mnama,mnama);
                call.enqueue(new Callback<showPengadaan>() {
                    @Override
                    public void onResponse(Call<showPengadaan> call, Response<showPengadaan> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Pengadaan Telah Selesai", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Pengadaan gagal diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new tampilPgdFragment()).commit();
                    }

                    @Override
                    public void onFailure(Call<showPengadaan> call, Throwable t) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new tampilPgdFragment()).commit();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rvDp = v.findViewById(R.id.rv_dpgd);
        rvDp.setHasFixedSize(true);

        rvDp.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataDpAdapter = new DataDpAdapter(list, getContext());
        rvDp.setAdapter(mDataDpAdapter);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showDP> call = apiInterface.tampilDPengadaan(pgd.getId_pengadaan());
        call.enqueue(new Callback<showDP>() {
            @Override
            public void onResponse(Call<showDP> call, Response<showDP> response) {
                list.addAll(0,response.body().getResult());
                mDataDpAdapter.notifyDataSetChanged();

                Log.e("data pengadaan", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showDP> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

        Button btnAddDpgd = v.findViewById(R.id.createdpgd_btn);
        btnAddDpgd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDPengadaanFragment dialog = new addDPengadaanFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", pgd.getId_pengadaan());
                bundle.putString("kode", pgd.getKode_pengadaan());
                bundle.putString("status", pgd.getStatus_PO());
                bundle.putInt("total", pgd.getTotal_pengadaan());
                dialog.setArguments(bundle);

                dialog.show(getFragmentManager(), "dialog");
            }
        });

        EditText search_etxt = v.findViewById(R.id.search_dpgd_etxt);
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
        List<DPengadaan> filteredList = new ArrayList<>();

        for (DPengadaan item : list) {
            if (item.getNama_produk().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mDataDpAdapter.filterList(filteredList);
    }
}
