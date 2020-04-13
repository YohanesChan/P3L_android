package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.PengPdkActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showProduk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editProdukFragment extends DialogFragment {
    EditText name_etxt, harga_etxt, stok_etxt, mstok_etxt, no_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Produk pdk;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editproduk, container, false);
        no_etxt = v.findViewById(R.id.noed_pdk_etxt);
        name_etxt = v.findViewById(R.id.namaed_pdk_etxt);
        harga_etxt = v.findViewById(R.id.hargaed_pdk_etxt);
        stok_etxt = v.findViewById(R.id.stoked_pdk_etxt);
        mstok_etxt = v.findViewById(R.id.stokmined_pdk_etxt);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        Bundle bundle = getArguments();
        pdk = new Produk();
        pdk.setId_produk(bundle.getInt("id"));
        pdk.setNama_produk(bundle.getString("nama"));
        pdk.setHarga_produk(bundle.getInt("harga"));
        pdk.setNo_produk(bundle.getString("no"));
        pdk.setStok_produk(bundle.getInt("stok"));
        pdk.setStok_minimal(bundle.getInt("mstok"));

        name_etxt.setText(bundle.getString("nama"));
        no_etxt.setText(bundle.getString("no"));
        harga_etxt.setText(String.valueOf(pdk.getHarga_produk()));
        stok_etxt.setText(String.valueOf(pdk.getStok_produk()));
        mstok_etxt.setText(String.valueOf(pdk.getStok_minimal()));

        no_etxt.setEnabled(false);
        name_etxt.setEnabled(false);
        harga_etxt.setEnabled(false);
        stok_etxt.setEnabled(false);
        mstok_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchpdk_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    harga_etxt.setEnabled(true);
                    mstok_etxt.setEnabled(true);
                } else {
                    no_etxt.setEnabled(false);
                    name_etxt.setEnabled(false);
                    harga_etxt.setEnabled(false);
                    stok_etxt.setEnabled(false);
                    mstok_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletepdk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showProduk> call = apiInterface.hapusProduk(pdk.getId_produk(),mnama,mnama,mnama);
                call.enqueue(new Callback<showProduk>() {
                    @Override
                    public void onResponse(Call<showProduk> call, Response<showProduk> response) {
                        Toast.makeText(getContext(), "Produk Dihapus", Toast.LENGTH_SHORT).show();
                        ((PengPdkActivity)getActivity()).onFinishDialog();
                        dismiss();


                    }

                    @Override
                    public void onFailure(Call<showProduk> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.editpdk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showProduk> call = apiInterface.editProduk(pdk.getId_produk(),
                        Integer.parseInt(harga_etxt.getText().toString()),
                        Integer.parseInt(mstok_etxt.getText().toString()),mnama,mnama);


                call.enqueue(new Callback<showProduk>() {
                    @Override
                    public void onResponse(Call<showProduk> call, Response<showProduk> response) {
                        Toast.makeText(getContext(), "Produk Diperbaharui", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showProduk> call, Throwable t) {

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
//        Toast.makeText(getContext(), "test onpause", Toast.LENGTH_SHORT).show();

        ((PengPdkActivity)getContext()).onFinishDialog();
    }
}
