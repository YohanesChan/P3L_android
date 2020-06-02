package com.example.kouvemobile.Frag;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.TransaksiL;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataTransaksilAdapter;
import com.example.kouvemobile.Response.showTransaksiL;
import com.example.kouvemobile.TransaksiLActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tampilTLynFragment extends Fragment {
    private RecyclerView rvTlayanan;
    private List<TransaksiL> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataTransaksilAdapter mDataTransaksilAdapter;
    Spinner mSpinner;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tampiltlyn, container, false);

        rvTlayanan = v.findViewById(R.id.rv_tlyn);
        rvTlayanan.setHasFixedSize(true);

        rvTlayanan.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataTransaksilAdapter = new DataTransaksilAdapter(list, getContext());
        rvTlayanan.setAdapter(mDataTransaksilAdapter);
//
//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();
//
//        Call<showTransaksiL> call = apiInterface.tampilTransaksiL();
//        call.enqueue(new Callback<showTransaksiL>() {
//            @Override
//            public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {
//                list.addAll(0,response.body().getResult());
//                mDataTransaksilAdapter.notifyDataSetChanged();
//
//                Log.e("data pegawai", response.body().getResult().toString());
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<showTransaksiL> call, Throwable t) {
//
//                progressDialog.dismiss();
//            }
//        });

        final EditText search_etxt = v.findViewById(R.id.search_tlyn_etxt);
        search_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                onFinishDialog();
                filter(s.toString());
                if(s.toString().length() == 0){
                    search_etxt.clearFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button btnAddTLyn = v.findViewById(R.id.createtlyn_btn);
        btnAddTLyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTLynFragment dialog = new addTLynFragment();
                dialog.show(getFragmentManager(), "dialog");
            }
        });

        mSpinner = (Spinner) v.findViewById(R.id.filter_spn);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    list.clear();
                    mDataTransaksilAdapter.notifyDataSetChanged();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<showTransaksiL> call = apiInterface.tampilTransaksiL();
                    call.enqueue(new Callback<showTransaksiL>() {
                        @Override
                        public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {

                            list.addAll(response.body().getResult());
                            mDataTransaksilAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<showTransaksiL> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });

                }else if(position == 1){
                    list.clear();

                    mDataTransaksilAdapter.notifyDataSetChanged();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<showTransaksiL> call = apiInterface.tampilTransaksiL_proccess();
                    call.enqueue(new Callback<showTransaksiL>() {
                        @Override
                        public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {

                            list.addAll(response.body().getResult());
                            mDataTransaksilAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<showTransaksiL> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });

                }else if(position == 2){
                    list.clear();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<showTransaksiL> call = apiInterface.tampilTransaksiL_finih();
                    call.enqueue(new Callback<showTransaksiL>() {
                        @Override
                        public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {

                            list.addAll(response.body().getResult());
                            mDataTransaksilAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<showTransaksiL> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.filter,
                        android.R.layout.simple_spinner_item);

        //how the spinner will look when it drop downs on click
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adapter to spinner
        mSpinner.setAdapter(adapter);
        
        return v;
    }

    private void filter(String text){
        List<TransaksiL> filteredList = new ArrayList<>();

        for (TransaksiL item : list) {
            if (item.getKode_tlayanan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mDataTransaksilAdapter.filterList(filteredList);
    }
    public void onPause() {
        super.onPause();

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new tampilTLynFragment()).commit();
    }
}
