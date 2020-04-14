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
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.PengCustActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showCustomer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class editCustomerFragment extends DialogFragment {
    EditText name_etxt, alamat_etxt, telp_etxt, no_etxt, bday_etxt;
    Switch mswitch=null;

    private ApiInterface apiInterface;
    private Customer cst;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editcustomer, container, false);
        no_etxt = v.findViewById(R.id.noed_cst_etxt);
        name_etxt = v.findViewById(R.id.namaed_cst_etxt);
        alamat_etxt = v.findViewById(R.id.alamated_cst_etxt);
        bday_etxt = v.findViewById(R.id.bdayed_cst_etxt);
        telp_etxt = v.findViewById(R.id.telped_cst_etxt);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        Bundle bundle = getArguments();
        cst = new Customer();
        cst.setId_customer(bundle.getInt("id"));
        cst.setNo_customer(bundle.getString("no"));
        cst.setNama_customer(bundle.getString("nama"));
        cst.setTelp_customer(bundle.getString("telp"));
        cst.setTelp_customer(bundle.getString("bday"));
        cst.setAlamat_customer(bundle.getString("alamat"));

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
        mswitch = v.findViewById(R.id.switchcst_btn);
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

        v.findViewById(R.id.deletecst_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showCustomer> call = apiInterface.hapusCustomer(cst.getId_customer(),mnama,mnama,mnama);
                call.enqueue(new Callback<showCustomer>() {
                    @Override
                    public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "customer Dihapus", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "customer gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        ((PengCustActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showCustomer> call, Throwable t) {

                    }
                });

            }
        });
        v.findViewById(R.id.editcst_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<showCustomer> call = apiInterface.editCustomer(cst.getId_customer(),name_etxt.getText().toString(),
                        telp_etxt.getText().toString(), alamat_etxt.getText().toString(),mnama,mnama);
                call.enqueue(new Callback<showCustomer>() {
                    @Override
                    public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(getContext(), "customer Diperbaharui", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(getContext(), "customer gagal Diperbaharui", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showCustomer> call, Throwable t) {

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

        ((PengCustActivity)getContext()).onFinishDialog();
    }
}
