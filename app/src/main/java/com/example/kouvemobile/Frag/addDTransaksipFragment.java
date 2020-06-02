package com.example.kouvemobile.Frag;

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
import com.example.kouvemobile.Model.DPengadaan;
import com.example.kouvemobile.Model.DTProduk;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataDpAdapter;
import com.example.kouvemobile.RecyclerView.DataTpAdapter;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showProduk;
import com.example.kouvemobile.Response.showTP;
import com.example.kouvemobile.Response.showTransaksiP;
import com.example.kouvemobile.TransaksiPActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addDTransaksipFragment extends DialogFragment {
    EditText jml_etxt;
    TextView hrg_txt;
    Spinner mSpinner;
    private DataTpAdapter mDataTpAdapter;
    private List<DTProduk> list2 = new ArrayList<>();
    private ApiInterface apiInterface;
    List<Produk> produkList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    Integer selected,hrg_pdk;
    String namaProduk;
    private TransaksiP trp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adddetiltrp, container, false);

        jml_etxt = v.findViewById(R.id.jml_dtrp_etxt);
        hrg_txt = v.findViewById(R.id.hrg_dtrp_txt);
        mSpinner = (Spinner) v.findViewById(R.id.pdk_dtrp_spn);

        Bundle bundle = getArguments();
        trp = new TransaksiP();
        trp.setId_tproduk(bundle.getInt("id"));
        trp.setKode_tproduk(bundle.getString("kode"));
        trp.setStatus_tproduk(bundle.getString("status"));
        trp.setTotal_tproduk(bundle.getInt("total"));


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

        v.findViewById(R.id.adddtrp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinner.getSelectedItem().toString().isEmpty() || jml_etxt.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showTP> call = apiInterface.regisDTransaksiP(namaProduk,
                            Integer.parseInt(jml_etxt.getText().toString()),
                            Integer.parseInt(hrg_txt.getText().toString()),
                            mid,
                            selected,
                            mnama);

                    call.enqueue(new Callback<showTP>() {
                        @Override
                        public void onResponse(Call<showTP> call, Response<showTP> response) {
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
                        public void onFailure(Call<showTP> call, Throwable t) {
                            dismiss();
                        }
                    });
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<showTransaksiP> call1 = apiInterface.totalTransaksiP(mid,mnama,mnama);
                    call1.enqueue(new Callback<showTransaksiP>() {
                        @Override
                        public void onResponse(Call<showTransaksiP> call, Response<showTransaksiP> response) {

                        }

                        @Override
                        public void onFailure(Call<showTransaksiP> call, Throwable t) {

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
        detilTPdkFragment detiltpdk = new detilTPdkFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", trp.getId_tproduk());
        bundle.putString("kode", trp.getKode_tproduk());
        bundle.putString("status", trp.getStatus_tproduk());
        bundle.putInt("total", trp.getTotal_tproduk());
        detiltpdk.setArguments(bundle);


        ((TransaksiPActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detiltpdk).commit();
    }
}
