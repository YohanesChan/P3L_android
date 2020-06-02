package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.kouvemobile.Model.DTProduk;
import com.example.kouvemobile.Model.TransaksiP;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showTP;
import com.example.kouvemobile.Response.showTransaksiP;
import com.example.kouvemobile.TransaksiPActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editDTransaksipFragment extends DialogFragment {
    EditText jml_etxt;
    TextView hrg_txt;
    Integer hrg_pdk;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private DTProduk dtrp;
    TransaksiP tr;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editdetiltransaksip, container, false);
        jml_etxt = v.findViewById(R.id.jmled_dtrp_etxt);
        hrg_txt = v.findViewById(R.id.hrged_dtrp_txt);

        Bundle bundle = getArguments();
        dtrp = new DTProduk();
        dtrp.setId_pproduk(bundle.getInt("idtp"));
        dtrp.setJml_pproduk(bundle.getInt("jml"));
        dtrp.setHarga_pproduk(bundle.getInt("harga"));

        Bundle b = getArguments();
        tr = new TransaksiP();
        tr.setId_tproduk(b.getInt("id"));
        tr.setKode_tproduk(b.getString("kode"));
        tr.setStatus_tproduk(b.getString("status"));
        tr.setTotal_tproduk(b.getInt("total"));

        jml_etxt.setText(String.valueOf(dtrp.getJml_pproduk()));
        hrg_txt.setText(String.valueOf(dtrp.getHarga_pproduk()));


        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("idp",0);

        jml_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchdtrp_btn);
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
                            hrg_pdk=dtrp.getHarga_pproduk();
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

        v.findViewById(R.id.deletedtrp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTP> call = apiInterface.hapusDTransaksiP(dtrp.getId_pproduk());
                call.enqueue(new Callback<showTP>() {
                    @Override
                    public void onResponse(Call<showTP> call, Response<showTP> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Produk Dihapus", Toast.LENGTH_SHORT).show();
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
                        else {
                            Toast.makeText(getContext(), "Produk gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }

                        //((PengHewActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showTP> call, Throwable t) {

                    }
                });

            }
        });

        v.findViewById(R.id.editdtrp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTP> call = apiInterface.editDTransaksiP(dtrp.getId_pproduk(),
                        Integer.parseInt(jml_etxt.getText().toString()),
                        Integer.parseInt(hrg_txt.getText().toString()),mnama,mnama);


                call.enqueue(new Callback<showTP>() {
                    @Override
                    public void onResponse(Call<showTP> call, Response<showTP> response) {
                        if (response.isSuccessful())
                        {

                            Toast.makeText(getContext(), "Produk Diperbaharui", Toast.LENGTH_SHORT).show();
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
                        else
                        {
                            Toast.makeText(getContext(), "Produk gagal Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showTP> call, Throwable t) {

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
        detilTPdkFragment detilTpdk = new detilTPdkFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", tr.getId_tproduk());
        bundle1.putString("kode", tr.getKode_tproduk());
        bundle1.putString("status", tr.getStatus_tproduk());
        bundle1.putInt("total", tr.getTotal_tproduk());
        detilTpdk.setArguments(bundle1);


        ((TransaksiPActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detilTpdk).commit();
    }
}
