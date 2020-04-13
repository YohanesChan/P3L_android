package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.kouvemobile.Model.Layanan;
import com.example.kouvemobile.PengLynActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showLayanan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editLayananFragment extends DialogFragment {
    EditText name_etxt, harga_etxt, no_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Layanan lyn;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editlayanan, container, false);
        name_etxt = v.findViewById(R.id.namaed_lyn_etxt);
        no_etxt = v.findViewById(R.id.noed_lyn_etxt);
        harga_etxt = v.findViewById(R.id.hargaed_lyn_etxt);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        Bundle bundle = getArguments();
        lyn = new Layanan();
        lyn.setId_layanan(bundle.getInt("id"));
        lyn.setNo_layanan(bundle.getString("no"));
        lyn.setNama_layanan(bundle.getString("nama"));
        lyn.setHarga_layanan(bundle.getInt("harga"));

        name_etxt.setText(bundle.getString("nama"));
        no_etxt.setText(bundle.getString("no"));
        harga_etxt.setText(String.valueOf(lyn.getHarga_layanan()));

        no_etxt.setEnabled(false);
        name_etxt.setEnabled(false);
        harga_etxt.setEnabled(false);

        mswitch = v.findViewById(R.id.switchlyn_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    harga_etxt.setEnabled(true);
                } else {
                    name_etxt.setEnabled(false);
                    no_etxt.setEnabled(false);
                    harga_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletelyn_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showLayanan> call = apiInterface.hapusLayanan(lyn.getId_layanan(), mnama,mnama,mnama);
                call.enqueue(new Callback<showLayanan>() {
                    @Override
                    public void onResponse(Call<showLayanan> call, Response<showLayanan> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Layanan Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Layanan gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        ((PengLynActivity)getActivity()).onFinishDialog();
                        dismiss();

                    }

                    @Override
                    public void onFailure(Call<showLayanan> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.editlyn_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
                Call<showLayanan> call = apiInterface.editLayanan(lyn.getId_layanan(),
                        Integer.parseInt(harga_etxt.getText().toString()), mnama,mnama);


                call.enqueue(new Callback<showLayanan>() {
                    @Override
                    public void onResponse(Call<showLayanan> call, Response<showLayanan> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Layanan Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Layanan gagal Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showLayanan> call, Throwable t) {

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

        ((PengLynActivity)getContext()).onFinishDialog();
    }
}
