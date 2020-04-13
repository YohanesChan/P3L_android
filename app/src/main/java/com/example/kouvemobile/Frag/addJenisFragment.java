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
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataJenisAdapter;
import com.example.kouvemobile.Response.showJenis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addJenisFragment extends DialogFragment {
    EditText name_etxt;
    DataJenisAdapter mAdapter;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addjenis, container, false);

        name_etxt = v.findViewById(R.id.nama_jns_etxt);
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);
        v.findViewById(R.id.addjns_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_etxt.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showJenis> call = apiInterface.regisJenis(name_etxt.getText().toString(), mnama, mnama, mid);
                    call.enqueue(new Callback<showJenis>() {
                        @Override
                        public void onResponse(Call<showJenis> call, Response<showJenis> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(getContext(), "Jenis Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getContext(), "Jenis gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            ((PengJnsActivity) getActivity()).onFinishDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showJenis> call, Throwable t) {
                            dismiss();
                        }
                    });
                }
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

        ((PengJnsActivity)getContext()).onFinishDialog();
    }
}
