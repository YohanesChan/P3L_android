package com.example.kouvemobile.Frag;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.MainActivity;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.Model.ProdukShow;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.ShowProdukAdapter;
import com.example.kouvemobile.Response.tampilProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukFragment extends Fragment {
    private RecyclerView rvProduk;
    private List<ProdukShow> list = new ArrayList<>();
    private List<ProdukShow> mlistProduk;
    private ApiInterface apiInterface;
    private ShowProdukAdapter mShowProdukAdapter;
    Spinner mSpinner;
    private Produk pdk;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_produk, container, false);

        Button btn = v.findViewById(R.id.menu2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openside();
            }
        });

        rvProduk = v.findViewById(R.id.rvs_produk);
        rvProduk.setHasFixedSize(true);

        rvProduk.setLayoutManager(new GridLayoutManager(getContext(),3));
        mShowProdukAdapter = new ShowProdukAdapter(list,getContext());
        rvProduk.setAdapter(mShowProdukAdapter);

//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Loading");
//        progressDialog.show();
//
//        Call<tampilProduk> call = apiInterface.produkShow();
//        call.enqueue(new Callback<tampilProduk>() {
//            @Override
//            public void onResponse(Call<tampilProduk> call, Response<tampilProduk> response) {
//
//                list.addAll(response.body().getResult());
//                mShowProdukAdapter.notifyDataSetChanged();
//
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<tampilProduk> call, Throwable t) {
//
//                progressDialog.dismiss();
//            }
//        });

        mSpinner = (Spinner) v.findViewById(R.id.sort_spn);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    list.clear();
                    mShowProdukAdapter.notifyDataSetChanged();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<tampilProduk> call = apiInterface.produkShowPD();
                    call.enqueue(new Callback<tampilProduk>() {
                        @Override
                        public void onResponse(Call<tampilProduk> call, Response<tampilProduk> response) {

                            list.addAll(response.body().getResult());
                            mShowProdukAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<tampilProduk> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });

                }else if(position == 1){
                    list.clear();
                    mShowProdukAdapter.notifyDataSetChanged();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<tampilProduk> call = apiInterface.produkShowPA();
                    call.enqueue(new Callback<tampilProduk>() {
                        @Override
                        public void onResponse(Call<tampilProduk> call, Response<tampilProduk> response) {

                            list.addAll(response.body().getResult());
                            mShowProdukAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<tampilProduk> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });

                }else if(position == 2){
                    list.clear();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<tampilProduk> call = apiInterface.produkShowSD();
                    call.enqueue(new Callback<tampilProduk>() {
                        @Override
                        public void onResponse(Call<tampilProduk> call, Response<tampilProduk> response) {

                            list.addAll(response.body().getResult());
                            mShowProdukAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<tampilProduk> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });
                }else if(position == 3){
                    list.clear();
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    Call<tampilProduk> call = apiInterface.produkShowSA();
                    call.enqueue(new Callback<tampilProduk>() {
                        @Override
                        public void onResponse(Call<tampilProduk> call, Response<tampilProduk> response) {

                            list.addAll(response.body().getResult());
                            mShowProdukAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<tampilProduk> call, Throwable t) {

                            progressDialog.dismiss();
                        }
                    });
                    mShowProdukAdapter.notifyDataSetChanged();
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.sort,
                        android.R.layout.simple_spinner_item);

        //how the spinner will look when it drop downs on click
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adapter to spinner
        mSpinner.setAdapter(adapter);

        return v;
    }
}
