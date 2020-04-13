package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.PengJnsActivity;
import com.example.kouvemobile.PengUkActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataUkuranAdapter;
import com.example.kouvemobile.Response.showUkuran;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addUkuranFragment  extends DialogFragment {
    EditText name_etxt;
    DataUkuranAdapter mAdapter;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addukuran, container, false);

        name_etxt = v.findViewById(R.id.nama_uk_etxt);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        v.findViewById(R.id.adduk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showUkuran> call = apiInterface.regisUkuran(name_etxt.getText().toString(), mnama, mnama, mid);
                call.enqueue(new Callback<showUkuran>() {
                    @Override
                    public void onResponse(Call<showUkuran> call, Response<showUkuran> response) {
                        Toast.makeText(getContext(), "Ukuran Ditambah", Toast.LENGTH_SHORT).show();
                        ((PengUkActivity)getActivity()).onFinishDialog();
                        dismiss();
//                        Log.e("data mashookk", response.body().getStatus());
                    }

                    @Override
                    public void onFailure(Call<showUkuran> call, Throwable t) {

                        dismiss();
                    }
                });
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
//        Toast.makeText(getContext(), "test onpause", Toast.LENGTH_SHORT).show();

        ((PengUkActivity)getContext()).onFinishDialog();
    }
}
