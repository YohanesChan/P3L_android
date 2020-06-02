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
import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.PengHewActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showHewan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editHewanFragment extends DialogFragment {
    EditText name_etxt,no_etxt, idcst_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Hewan hew;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edithew, container, false);
        name_etxt = v.findViewById(R.id.namaed_hew_etxt);
        no_etxt = v.findViewById(R.id.noed_hew_etxt);
        idcst_etxt = v.findViewById(R.id.idcst_hew_etxt);

        Bundle bundle = getArguments();
        hew = new Hewan();
        hew.setId_hewan(bundle.getInt("id"));
        hew.setNama_hewan(bundle.getString("nama"));
        hew.setNo_hewan(bundle.getString("no"));
        hew.setId_customer_fk(bundle.getInt("id_cust"));

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        name_etxt.setText(bundle.getString("nama"));
        no_etxt.setText(bundle.getString("no"));
        idcst_etxt.setText(String.valueOf(hew.getId_customer_fk()));

        name_etxt.setEnabled(false);
        no_etxt.setEnabled(false);
        idcst_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchhew_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    name_etxt.setEnabled(true);
                } else {
                    name_etxt.setEnabled(false);
                    no_etxt.setEnabled(false);
                    idcst_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletehew_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showHewan> call = apiInterface.hapusHewan(hew.getId_hewan(), mnama,mnama,mnama);
                call.enqueue(new Callback<showHewan>() {
                    @Override
                    public void onResponse(Call<showHewan> call, Response<showHewan> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Hewan Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Hewan gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }

                        ((PengHewActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showHewan> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.edithew_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
                Call<showHewan> call = apiInterface.editHewan(hew.getId_hewan(),name_etxt.getText().toString(),mnama,mnama);


                call.enqueue(new Callback<showHewan>() {
                    @Override
                    public void onResponse(Call<showHewan> call, Response<showHewan> response) {
                        if (response.isSuccessful())
                        {

                            Toast.makeText(getContext(), "Hewan Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Hewan gagal Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showHewan> call, Throwable t) {

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

        ((PengHewActivity)getContext()).onFinishDialog();
    }
}

