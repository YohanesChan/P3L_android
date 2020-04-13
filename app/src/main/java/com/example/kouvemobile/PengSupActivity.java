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
import com.example.kouvemobile.Frag.addSupplierFragment;
import com.example.kouvemobile.Model.Supplier;
import com.example.kouvemobile.RecyclerView.DataSupplierAdapter;
import com.example.kouvemobile.Response.showSupplier;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengSupActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvSupplier;
    private List<Supplier> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataSupplierAdapter mDataSupplierAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_sup);

        rvSupplier = findViewById(R.id.rv_supplier);
        rvSupplier.setHasFixedSize(true);


        rvSupplier.setLayoutManager(new LinearLayoutManager(this));
        mDataSupplierAdapter = new DataSupplierAdapter(list, PengSupActivity.this);
        rvSupplier.setAdapter(mDataSupplierAdapter);
//-----------------------------------------------------------------------------------------------------sini tampil
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final ProgressDialog progressDialog = new ProgressDialog(PengSupActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Call<showSupplier> call = apiInterface.tampilSupplier();
        call.enqueue(new Callback<showSupplier>() {
            @Override
            public void onResponse(Call<showSupplier> call, Response<showSupplier> response) {
//                list.addAll(DataPegawai.getListData());
                list.addAll(response.body().getResult());
                mDataSupplierAdapter.notifyDataSetChanged();

                Log.e("data pegawai", response.body().getResult().toString());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<showSupplier> call, Throwable t) {

                progressDialog.dismiss();
            }
        });
//-------------------------------------------------------------------------------------------------------sampe sini
//        list.addAll(DataPegawai.getListData());
        EditText search_etxt = findViewById(R.id.search_sup_etxt);
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
        Button btnAddSupp = findViewById(R.id.createsup_btn);
        btnAddSupp.setOnClickListener(this);
        onFinishDialog();
    }

    @Override
    public void onClick(View v) {
        addSupplierFragment dialog = new addSupplierFragment();
        dialog.show(getSupportFragmentManager(), "dialog2");
    }

    private void filter(String text){
        List<Supplier> filteredList = new ArrayList<>();

        for (Supplier item : list) {
            if (item.getNama_supplier().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mDataSupplierAdapter.filterList(filteredList);
    }

    public void onFinishDialog() {

//        Toast.makeText(this, "test on finish", Toast.LENGTH_SHORT).show();

        Call<showSupplier> call = apiInterface.tampilSupplier();
        call.enqueue(new Callback<showSupplier>() {
            @Override
            public void onResponse(Call<showSupplier> call, Response<showSupplier> response) {
                list = response.body().getResult();
                mDataSupplierAdapter.notifyDataSetChanged();
                mDataSupplierAdapter = new DataSupplierAdapter(list, PengSupActivity.this);
                rvSupplier.setAdapter(mDataSupplierAdapter);

                Log.e("data pegawai onfinish", response.body().getResult().toString());

            }

            @Override
            public void onFailure(Call<showSupplier> call, Throwable t) {

            }
        });
//        mDataPegawaiAdapter.notifyDataSetChanged();
    }


}
