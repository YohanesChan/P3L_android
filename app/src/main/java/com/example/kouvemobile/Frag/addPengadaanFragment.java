package com.example.kouvemobile.Frag;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.kouvemobile.Model.Pengadaan;
import com.example.kouvemobile.Model.Supplier;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.PengadaanActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataPegawaiAdapter;
import com.example.kouvemobile.Response.showPegawai;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showSupplier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addPengadaanFragment extends DialogFragment {
    Spinner mSpinner;
    Integer selected,defTot;
    String supplier,defStat;
    List<Supplier> supplierList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    DataPegawaiAdapter mAdapter;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addpengadaan, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<showSupplier> call = apiInterface.tampilSupplier();
        call.enqueue(new Callback<showSupplier>() {
            @Override
            public void onResponse(Call<showSupplier> call, Response<showSupplier> response) {
                supplierList = response.body().getResult();
                for (Supplier temp : supplierList) {
                    Log.e("temp", temp.getNama_supplier());
                    list.add(temp.getNama_supplier());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showSupplier> call, Throwable t) {

            }
        });
        mSpinner = (Spinner) v.findViewById(R.id.sup_pgd_spn);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = supplierList.get(position).getId_supplier();
                supplier = supplierList.get(position).getNama_supplier();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        v.findViewById(R.id.addpgd_btn).setOnClickListener(new View.OnClickListener() {
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

                    Call<showPengadaan> call = apiInterface.regisPengadaan(selected, defStat, defTot,mid, mnama);
                    call.enqueue(new Callback<showPengadaan>() {
                        @Override
                        public void onResponse(Call<showPengadaan> call, Response<showPengadaan> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Pengadaan Ditambah", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(getContext(), "Pengadaan gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                             ((PengadaanActivity) getActivity()).onFinishDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showPengadaan> call, Throwable t) {
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
                new tampilPgdFragment()).commit();
    }
}
