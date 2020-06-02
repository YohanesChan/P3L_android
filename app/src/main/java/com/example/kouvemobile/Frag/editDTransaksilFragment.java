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
import com.example.kouvemobile.Model.DTLayanan;
import com.example.kouvemobile.Model.TransaksiL;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showTL;
import com.example.kouvemobile.Response.showTransaksiL;
import com.example.kouvemobile.TransaksiLActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editDTransaksilFragment extends DialogFragment {
    EditText jml_etxt;
    TextView hrg_txt;
    Integer hrg_lyn;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private DTLayanan dtrl;
    TransaksiL tr;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editdetiltransaksil, container, false);
        jml_etxt = v.findViewById(R.id.jmled_dtrl_etxt);
        hrg_txt = v.findViewById(R.id.hrged_dtrl_txt);

        Bundle bundle = getArguments();
        dtrl = new DTLayanan();
        dtrl.setId_playanan(bundle.getInt("idtp"));
        dtrl.setJml_playanan(bundle.getInt("jml"));
        dtrl.setHarga_playanan(bundle.getInt("harga"));

        Bundle b = getArguments();
        tr = new TransaksiL();
        tr.setId_tlayanan(b.getInt("id"));
        tr.setKode_tlayanan(b.getString("kode"));
        tr.setStatus_tlayanan(b.getString("status"));
        tr.setTotal_tlayanan(b.getInt("total"));

        jml_etxt.setText(String.valueOf(dtrl.getJml_playanan()));
        hrg_txt.setText(String.valueOf(dtrl.getHarga_playanan()));


        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("idp",0);

        jml_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchdtrl_btn);
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
                            hrg_lyn=dtrl.getHarga_playanan();
                            if(s.toString().length() > 0 )
                            {
                                Integer jml = Integer.parseInt(jml_etxt.getText().toString());
                                Integer temp;
                                temp = jml*hrg_lyn;
                                String inputText = temp.toString();
                                hrg_txt.setText(inputText);
                            }
                            else {
                                Integer temp;
                                temp = 0*hrg_lyn;
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

        v.findViewById(R.id.deletedtrl_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTL> call = apiInterface.hapusDTransaksiL(dtrl.getId_playanan());
                call.enqueue(new Callback<showTL>() {
                    @Override
                    public void onResponse(Call<showTL> call, Response<showTL> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Produk Dihapus", Toast.LENGTH_SHORT).show();
                            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<showTransaksiL> call1 = apiInterface.totalTransaksiL(mid,mnama,mnama);
                            call1.enqueue(new Callback<showTransaksiL>() {
                                @Override
                                public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {

                                }

                                @Override
                                public void onFailure(Call<showTransaksiL> call, Throwable t) {

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
                    public void onFailure(Call<showTL> call, Throwable t) {

                    }
                });

            }
        });

        v.findViewById(R.id.editdtrl_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showTL> call = apiInterface.editDTransaksiL(dtrl.getId_playanan(),
                        Integer.parseInt(jml_etxt.getText().toString()),
                        Integer.parseInt(hrg_txt.getText().toString()),mnama,mnama);


                call.enqueue(new Callback<showTL>() {
                    @Override
                    public void onResponse(Call<showTL> call, Response<showTL> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Layanan Diperbaharui", Toast.LENGTH_SHORT).show();
                            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<showTransaksiL> call1 = apiInterface.totalTransaksiL(mid,mnama,mnama);
                            call1.enqueue(new Callback<showTransaksiL>() {
                                @Override
                                public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {

                                }

                                @Override
                                public void onFailure(Call<showTransaksiL> call, Throwable t) {

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
                    public void onFailure(Call<showTL> call, Throwable t) {

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
        detilTLynFragment detilTlyn = new detilTLynFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("id", tr.getId_tlayanan());
        bundle1.putString("kode", tr.getKode_tlayanan());
        bundle1.putString("status", tr.getStatus_tlayanan());
        bundle1.putInt("total", tr.getTotal_tlayanan());
        detilTlyn.setArguments(bundle1);


        ((TransaksiLActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detilTlyn).commit();
    }
}
