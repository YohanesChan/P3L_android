package com.example.kouvemobile.Frag;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataSupplierAdapter;
import com.example.kouvemobile.Response.showPegawai;
import com.example.kouvemobile.Response.showSupplier;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

public class addSupplierFragment extends DialogFragment {
    EditText name_etxt, alamat_etxt, telp_etxt;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addsupplier, container, false);

        name_etxt = v.findViewById(R.id.nama_sup_etxt);
        alamat_etxt = v.findViewById(R.id.alamat_sup_etxt);
        telp_etxt = v.findViewById(R.id.telp_sup_etxt);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);
        v.findViewById(R.id.addsup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showSupplier> call = apiInterface.regisSupplier(name_etxt.getText().toString(),
                        telp_etxt.getText().toString(), alamat_etxt.getText().toString(), mid, mnama, mnama);
                call.enqueue(new Callback<showSupplier>() {
                    @Override
                    public void onResponse(Call<showSupplier> call, Response<showSupplier> response) {
                        Toast.makeText(getContext(), "Suplier Ditambah", Toast.LENGTH_SHORT).show();
                        ((PengSupActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showSupplier> call, Throwable t) {

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

        ((PengSupActivity)getContext()).onFinishDialog();
    }
}
