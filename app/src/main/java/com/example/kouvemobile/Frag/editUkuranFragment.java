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
import com.example.kouvemobile.Model.Ukuran;
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.PengUkActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showUkuran;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editUkuranFragment extends DialogFragment {
    EditText name_etxt, no_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Ukuran uk;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editukuran, container, false);
        name_etxt = v.findViewById(R.id.namaed_uk_etxt);

        Bundle bundle = getArguments();
        uk = new Ukuran();
        uk.setId_ukuran(bundle.getInt("id"));
        uk.setNama_ukuran_hewan(bundle.getString("nama"));
        uk.setNo_ukuran(bundle.getString("no"));

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        name_etxt.setText(bundle.getString("nama"));

        name_etxt.setEnabled(false);

        //Toast.makeText(context, bundle.getString("test"), Toast.LENGTH_SHORT).show();
        mswitch = v.findViewById(R.id.switchuk_btn);
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

        v.findViewById(R.id.deleteuk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showUkuran> call = apiInterface.hapusUkuran(uk.getId_ukuran(), mnama, mnama, mnama);
                call.enqueue(new Callback<showUkuran>() {
                    @Override
                    public void onResponse(Call<showUkuran> call, Response<showUkuran> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "Ukuran Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Ukuran gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        ((PengUkActivity)getActivity()).onFinishDialog();
                        dismiss();


                    }

                    @Override
                    public void onFailure(Call<showUkuran> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.edituk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
                Call<showUkuran> call = apiInterface.editUkuran(uk.getId_ukuran(),name_etxt.getText().toString(),mnama, mnama);

                call.enqueue(new Callback<showUkuran>() {
                    @Override
                    public void onResponse(Call<showUkuran> call, Response<showUkuran> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Ukuran Diperbaharui", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(getContext(), "Ukuran gagal Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showUkuran> call, Throwable t) {

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

        ((PengUkActivity)getContext()).onFinishDialog();
    }
}
