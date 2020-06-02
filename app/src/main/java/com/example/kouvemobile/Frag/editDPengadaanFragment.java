package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.DPengadaan;
import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.PengHewActivity;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showPengadaan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class editDPengadaanFragment extends DialogFragment {
    EditText jml_etxt;
    TextView hrg_txt;
    Integer hrg_pdk;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private DPengadaan dpgd;
    private Produk pdk;
    private Pengadaan pgd;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editdetilpengadaan, container, false);
        jml_etxt = v.findViewById(R.id.jmled_dpgd_etxt);
        hrg_txt = v.findViewById(R.id.hrged_dpgd_txt);

        Bundle bundle = getArguments();
        pgd = new Pengadaan();
        pgd.setId_pengadaan(bundle.getInt("id"));
        pgd.setKode_pengadaan(bundle.getString("kode"));
        pgd.setStatus_PO(bundle.getString("status"));
        pgd.setTotal_pengadaan(bundle.getInt("total"));

        Bundle b = getArguments();
        dpgd = new DPengadaan();
        dpgd.setId_detil_pengadaan(b.getInt("iddp"));
        dpgd.setJml_produk(b.getInt("jml"));
        dpgd.setHarga_produk(b.getInt("harga"));

        jml_etxt.setText(String.valueOf(dpgd.getJml_produk()));
        hrg_txt.setText(String.valueOf(dpgd.getHarga_produk()));

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("idp",0);

        jml_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchdpgd_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    jml_etxt.setEnabled(true);
                    jml_etxt.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            hrg_pdk=dpgd.getHarga_produk();
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
                } else {
                    jml_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletedpgd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showDP> call = apiInterface.hapusDPengadaan(dpgd.getId_detil_pengadaan());
                call.enqueue(new Callback<showDP>() {
                    @Override
                    public void onResponse(Call<showDP> call, Response<showDP> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Produk Dihapus", Toast.LENGTH_SHORT).show();
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
                        else {
                            Toast.makeText(getContext(), "Produk gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }

                        //((PengHewActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showDP> call, Throwable t) {

                    }
                });

            }
        });

        v.findViewById(R.id.editdpgd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showDP> call = apiInterface.editDPengadaan(dpgd.getId_detil_pengadaan(),
                        Integer.parseInt(jml_etxt.getText().toString()),
                        Integer.parseInt(hrg_txt.getText().toString()),mnama,mnama);


                call.enqueue(new Callback<showDP>() {
                    @Override
                    public void onResponse(Call<showDP> call, Response<showDP> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Produk Diperbaharui", Toast.LENGTH_SHORT).show();
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
                        else
                        {
                            Toast.makeText(getContext(), "Produk gagal Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showDP> call, Throwable t) {

                    }
                });
            }
        });

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


    @Override
    public void onPause() {
        super.onPause();
        detilPgdFragment detilpgd = new detilPgdFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", pgd.getId_pengadaan());
        bundle1.putString("kode", pgd.getKode_pengadaan());
        bundle1.putString("status", pgd.getStatus_PO());
        bundle1.putInt("total", pgd.getTotal_pengadaan());
        detilpgd.setArguments(bundle1);


        ((PengadaanActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detilpgd).commit();
    }
}
