package com.example.kouvemobile.Frag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.Model.DPengadaan;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.PengPdkActivity;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataDpAdapter;
import com.example.kouvemobile.RecyclerView.DataProdukAdapter;
import com.example.kouvemobile.Response.showCustomer;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class addDPengadaanFragment extends DialogFragment {
    EditText jml_etxt;
    TextView hrg_txt;
    Spinner mSpinner;
    private DataDpAdapter mDataDpAdapter;
    private List<DPengadaan> list2 = new ArrayList<>();
    private ApiInterface apiInterface;
    List<Produk> produkList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    Integer selected,hrg_pdk;
    String namaProduk;
    private Pengadaan pgd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adddetilpgd, container, false);

        jml_etxt = v.findViewById(R.id.jml_dpgd_etxt);
        hrg_txt = v.findViewById(R.id.hrg_dpgd_txt);
        mSpinner = (Spinner) v.findViewById(R.id.pdk_dpgd_spn);

        Bundle bundle = getArguments();
        pgd = new Pengadaan();
        pgd.setId_pengadaan(bundle.getInt("id"));
        pgd.setKode_pengadaan(bundle.getString("kode"));
        pgd.setStatus_PO(bundle.getString("status"));
        pgd.setTotal_pengadaan(bundle.getInt("total"));


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<showProduk> call = apiInterface.tampilProduk();
        call.enqueue(new Callback<showProduk>() {
            @Override
            public void onResponse(Call<showProduk> call, Response<showProduk> response) {
                produkList = response.body().getResult();
                for (Produk temp : produkList) {
                    Log.e("temp", temp.getNama_produk());
                    list.add(temp.getNama_produk());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showProduk> call, Throwable t) {

            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = produkList.get(position).getId_produk();
                namaProduk = produkList.get(position).getNama_produk();
                hrg_pdk = produkList.get(position).getHarga_produk();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jml_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0 )
                {
                    Integer jml = Integer.parseInt(jml_etxt.getText().toString());
                    Integer temp;
                    temp = jml*hrg_pdk;
                    String inputText = temp.toString();
                    hrg_txt.setText(inputText);
                }
                else {
                    Integer temp;
                    temp = 0*hrg_pdk;
                    String inputText = temp.toString();
                    hrg_txt.setText(inputText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("idp",0);

        v.findViewById(R.id.adddpgd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinner.getSelectedItem().toString().isEmpty() || jml_etxt.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showDP> call = apiInterface.regisDPengadaan(namaProduk,
                            Integer.parseInt(jml_etxt.getText().toString()),
                            Integer.parseInt(hrg_txt.getText().toString()),
                            mid,
                            selected,
                            mnama);

                    call.enqueue(new Callback<showDP>() {
                        @Override
                        public void onResponse(Call<showDP> call, Response<showDP> response) {
                            if (response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Produk Ditambah", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getContext(), "Produk gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showDP> call, Throwable t) {
                            dismiss();
                        }
                    });
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<showPengadaan> call1 = apiInterface.totalPengadaan(mid,mnama,mnama);
                    call1.enqueue(new Callback<showPengadaan>() {
                        @Override
                        public void onResponse(Call<showPengadaan> call, Response<showPengadaan> response) {

                        }

                        @Override
                        public void onFailure(Call<showPengadaan> call, Throwable t) {

                        }
                    });
                }
            }
        });
//-----------------------------------------------------------------------------------sini

//-----------------------------------------------------------------------------------------sini
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    public void onPause() {
        super.onPause();

        detilPgdFragment detilpgd = new detilPgdFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", pgd.getId_pengadaan());
        bundle.putString("kode", pgd.getKode_pengadaan());
        bundle.putString("status", pgd.getStatus_PO());
        bundle.putInt("total", pgd.getTotal_pengadaan());
        detilpgd.setArguments(bundle);


        ((PengadaanActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detilpgd).commit();
    }
}
