package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.showCustomer;
import com.example.kouvemobile.Response.showTransaksiL;
import com.example.kouvemobile.TransaksiLActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addTLynFragment extends DialogFragment {
    Spinner mSpinner;
    Integer selected,defTot;
    String customer,defStat;
    List<Customer> customerList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addtransaksil, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<showCustomer> call = apiInterface.tampilCustomer();
        call.enqueue(new Callback<showCustomer>() {
            @Override
            public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                customerList = response.body().getResult();
                for (Customer temp : customerList) {
                    Log.e("temp", temp.getNama_customer());
                    list.add(temp.getNama_customer());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showCustomer> call, Throwable t) {

            }
        });
        mSpinner = (Spinner) v.findViewById(R.id.cst_trl_spn);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = customerList.get(position).getId_customer();
                customer = customerList.get(position).getNama_customer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        v.findViewById(R.id.addtrl_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinner.getSelectedItem().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    defStat="proses";
                    defTot=0;
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showTransaksiL> call = apiInterface.regisTransaksiL(selected, defStat, defTot,mid, mnama);
                    call.enqueue(new Callback<showTransaksiL>() {
                        @Override
                        public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Transaksi Ditambah", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(getContext(), "Transaksi gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            ((TransaksiLActivity) getActivity()).onFinishDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showTransaksiL> call, Throwable t) {
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

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new tampilTLynFragment()).commit();
    }
}
