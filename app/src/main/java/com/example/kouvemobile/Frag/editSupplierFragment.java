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
import com.example.kouvemobile.Model.Supplier;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.PengSupActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showSupplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editSupplierFragment extends DialogFragment {
    EditText name_etxt, alamat_etxt, telp_etxt,no_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Supplier sup;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editsupplier, container, false);
        name_etxt = v.findViewById(R.id.namaed_sup_etxt);
        alamat_etxt = v.findViewById(R.id.alamated_sup_etxt);
        telp_etxt = v.findViewById(R.id.telped_sup_etxt);
        no_etxt = v.findViewById(R.id.noed_sup_etxt);


        Bundle bundle = getArguments();
        sup = new Supplier();
        sup.setId_supplier(bundle.getInt("id"));
        sup.setNo_supplier(bundle.getString("no"));
        sup.setNama_supplier(bundle.getString("nama"));
        sup.setTelp_supplier(bundle.getString("telp"));
        sup.setAlamat_supplier(bundle.getString("alamat"));

        no_etxt.setText(bundle.getString("no"));
        name_etxt.setText(bundle.getString("nama"));
        telp_etxt.setText(bundle.getString("telp"));
        alamat_etxt.setText(bundle.getString("alamat"));

        no_etxt.setEnabled(false);
        name_etxt.setEnabled(false);
        telp_etxt.setEnabled(false);
        alamat_etxt.setEnabled(false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        mswitch = v.findViewById(R.id.switchsup_btn);
        mswitch.setChecked(false);
        mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    telp_etxt.setEnabled(true);
                    alamat_etxt.setEnabled(true);
                } else {
                    no_etxt.setEnabled(false);
                    name_etxt.setEnabled(false);
                    telp_etxt.setEnabled(false);
                    alamat_etxt.setEnabled(false);
                }
            }
        });

        v.findViewById(R.id.deletesup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showSupplier> call = apiInterface.hapusSupplier(sup.getId_supplier(), mnama, mnama, mnama);
                call.enqueue(new Callback<showSupplier>() {
                    @Override
                    public void onResponse(Call<showSupplier> call, Response<showSupplier> response) {
                        Toast.makeText(getContext(), "Supplier Dihapus", Toast.LENGTH_SHORT).show();
                        ((PengSupActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showSupplier> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.editsup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
                Call<showSupplier> call = apiInterface.editSupplier(sup.getId_supplier(),name_etxt.getText().toString(),
                        telp_etxt.getText().toString(), alamat_etxt.getText().toString(), mnama,mnama);


                call.enqueue(new Callback<showSupplier>() {
                    @Override
                    public void onResponse(Call<showSupplier> call, Response<showSupplier> response) {
                        Toast.makeText(getContext(), "Supplier Diperbaharui", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showSupplier> call, Throwable t) {

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

        ((PengSupActivity)getContext()).onFinishDialog();
    }
}
