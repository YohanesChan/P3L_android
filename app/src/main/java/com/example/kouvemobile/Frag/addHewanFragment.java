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
import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.Model.Jenis;
import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.Model.Ukuran;
import com.example.kouvemobile.PengHewActivity;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataPegawaiAdapter;
import com.example.kouvemobile.Response.showCustomer;
import com.example.kouvemobile.Response.showHewan;
import com.example.kouvemobile.Response.showJenis;
import com.example.kouvemobile.Response.showUkuran;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addHewanFragment extends DialogFragment {
    TextView mBday;
    EditText name_etxt;
    Spinner mSpinner1, mSpinner2;
    List<Customer> customerList = new ArrayList<>();
    List<Jenis> jenisList = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    Integer selected1,selected2;
    String namaCustomer,namaJenis;
    DataPegawaiAdapter mAdapter;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addhew, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        name_etxt = v.findViewById(R.id.nama_hew_etxt);
        mBday= (TextView) v.findViewById(R.id.bday_hew_txt);
        v.findViewById(R.id.bday_hew_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay){
                        mBday.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        mSpinner1 = (Spinner) v.findViewById(R.id.cst_spn);
        mSpinner2 = (Spinner) v.findViewById(R.id.jen_spn);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<showCustomer> call1 = apiInterface.tampilCustomer();
        call1.enqueue(new Callback<showCustomer>() {
            @Override
            public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                customerList = response.body().getResult();
                for (Customer temp : customerList) {
                    Log.e("temp", temp.getNama_customer());
                    list1.add(temp.getNama_customer());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list1);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showCustomer> call, Throwable t) {

            }
        });

        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected1 = customerList.get(position).getId_customer();
                namaCustomer = customerList.get(position).getNama_customer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Call<showJenis> call = apiInterface.tampilJenis();
        call.enqueue(new Callback<showJenis>() {
            @Override
            public void onResponse(Call<showJenis> call, Response<showJenis> response) {
                jenisList = response.body().getResult();
                for (Jenis temp : jenisList) {
                    Log.e("temp", temp.getNama_jenis_hewan());
                    list2.add(temp.getNama_jenis_hewan());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list2);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner2.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showJenis> call, Throwable t) {

            }
        });

        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected2 = jenisList.get(position).getId_jenis();
                namaJenis = jenisList.get(position).getNama_jenis_hewan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        v.findViewById(R.id.addhew_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_etxt.getText().toString().isEmpty() || mSpinner1.getSelectedItem().toString().isEmpty() ||
                        mSpinner2.getSelectedItem().toString().isEmpty() || mBday.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showHewan> call = apiInterface.regisHewan(name_etxt.getText().toString(), mBday.getText().toString(),
                           selected1,mid,selected2, mnama);
                    call.enqueue(new Callback<showHewan>() {
                        @Override
                        public void onResponse(Call<showHewan> call, Response<showHewan> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Hewan Ditambah", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(getContext(), "Hewan gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            ((PengHewActivity) getActivity()).onFinishDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showHewan> call, Throwable t) {
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

        ((PengHewActivity)getContext()).onFinishDialog();
    }
}

