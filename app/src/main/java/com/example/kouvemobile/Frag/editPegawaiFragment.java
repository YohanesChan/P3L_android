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
import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showPegawai;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editPegawaiFragment extends DialogFragment {
    EditText name_etxt, alamat_etxt, telp_etxt, no_etxt, bday_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Pegawai pgw;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editpegawai, container, false);
        no_etxt = v.findViewById(R.id.noed_etxt);
        name_etxt = v.findViewById(R.id.namaed_etxt);
        alamat_etxt = v.findViewById(R.id.alamated_etxt);
        bday_etxt = v.findViewById(R.id.bdayed_etxt);
        telp_etxt = v.findViewById(R.id.telped_etxt);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");


        Bundle bundle = getArguments();
        pgw = new Pegawai();
        pgw.setId_pegawai(bundle.getInt("id"));
        pgw.setNo_pegawai(bundle.getString("no"));
        pgw.setNama_pegawai(bundle.getString("nama"));
        pgw.setTelp_pegawai(bundle.getString("telp"));
        pgw.setTelp_pegawai(bundle.getString("bday"));
        pgw.setAlamat_pegawai(bundle.getString("alamat"));

        no_etxt.setText(bundle.getString("no"));
        name_etxt.setText(bundle.getString("nama"));
        telp_etxt.setText(bundle.getString("telp"));
        bday_etxt.setText(bundle.getString("bday"));
        alamat_etxt.setText(bundle.getString("alamat"));


        no_etxt.setEnabled(false);
        name_etxt.setEnabled(false);
        telp_etxt.setEnabled(false);
        bday_etxt.setEnabled(false);
        alamat_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchpgw_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    name_etxt.setEnabled(true);
                    telp_etxt.setEnabled(true);
                    alamat_etxt.setEnabled(true);
                } else {
                    no_etxt.setEnabled(false);
                    name_etxt.setEnabled(false);
                    telp_etxt.setEnabled(false);
                    bday_etxt.setEnabled(false);
                    alamat_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletepgw_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showPegawai> call = apiInterface.hapusPegawai(pgw.getId_pegawai(),mnama,mnama,mnama);
                call.enqueue(new Callback<showPegawai>() {
                    @Override
                    public void onResponse(Call<showPegawai> call, Response<showPegawai> response) {
                        Toast.makeText(getContext(), "Pegawai Dihapus", Toast.LENGTH_SHORT).show();
                        ((PengPgwActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showPegawai> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.editpgw_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<showPegawai> call = apiInterface.editPegawai(pgw.getId_pegawai(),name_etxt.getText().toString(),
                        telp_etxt.getText().toString(), alamat_etxt.getText().toString(),mnama,mnama);
                call.enqueue(new Callback<showPegawai>() {
                    @Override
                    public void onResponse(Call<showPegawai> call, Response<showPegawai> response) {
                        Toast.makeText(getContext(), "Pegawai Diperbaharui", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showPegawai> call, Throwable t) {

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

        ((PengPgwActivity)getContext()).onFinishDialog();
    }
}

