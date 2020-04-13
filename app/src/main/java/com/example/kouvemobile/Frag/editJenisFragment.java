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
import com.example.kouvemobile.Model.Jenis;
import com.example.kouvemobile.PengJnsActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showJenis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editJenisFragment extends DialogFragment {
    EditText name_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Jenis jns;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editjenis, container, false);
        name_etxt = v.findViewById(R.id.namaed_jns_etxt);

        Bundle bundle = getArguments();
        jns = new Jenis();
        jns.setId_jenis(bundle.getInt("id"));
        jns.setNama_jenis_hewan(bundle.getString("nama"));

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        name_etxt.setText(bundle.getString("nama"));

        name_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchjns_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    name_etxt.setEnabled(true);
                } else {
                    name_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletejns_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showJenis> call = apiInterface.hapusJenis(jns.getId_jenis(), mnama,mnama,mnama);
                call.enqueue(new Callback<showJenis>() {
                    @Override
                    public void onResponse(Call<showJenis> call, Response<showJenis> response) {
                        Toast.makeText(getContext(), "Jenis Dihapus", Toast.LENGTH_SHORT).show();
                        ((PengJnsActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showJenis> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.editjns_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
                Call<showJenis> call = apiInterface.editJenis(jns.getId_jenis(),name_etxt.getText().toString(),mnama,mnama);


                call.enqueue(new Callback<showJenis>() {
                    @Override
                    public void onResponse(Call<showJenis> call, Response<showJenis> response) {
                        Toast.makeText(getContext(), "Jenis Diperbaharui", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showJenis> call, Throwable t) {

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

        ((PengJnsActivity)getContext()).onFinishDialog();
    }
}
