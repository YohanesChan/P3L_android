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
import androidx.fragment.app.Fragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.PengPgwActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataPegawaiAdapter;
import com.example.kouvemobile.Response.showPegawai;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addPegawaiFragment extends DialogFragment {
    TextView mBday;
    EditText name_etxt, alamat_etxt, telp_etxt, username_etxt, password_etxt;
    Spinner mSpinner;
    DataPegawaiAdapter mAdapter;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addpegawai, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");

        name_etxt = v.findViewById(R.id.nama_pgw_etxt);
        alamat_etxt = v.findViewById(R.id.alamat_pgw_etxt);
        mBday= (TextView) v.findViewById(R.id.bday_pgw_txt);
        v.findViewById(R.id.bday_pgw_btn).setOnClickListener(new View.OnClickListener() {
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

        telp_etxt = v.findViewById(R.id.telp_pgw_etxt);
        username_etxt = v.findViewById(R.id.username_pgw_etxt);
        password_etxt = v.findViewById(R.id.password_pgw_etxt);

        mSpinner = (Spinner) v.findViewById(R.id.role_spn);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.role,
                        android.R.layout.simple_spinner_item);

        //how the spinner will look when it drop downs on click
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adapter to spinner
        mSpinner.setAdapter(adapter);

        v.findViewById(R.id.addpgw_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                Call<showPegawai> call = apiInterface.regisPegawai(name_etxt.getText().toString(),
                        mSpinner.getSelectedItem().toString(), alamat_etxt.getText().toString(), mBday.getText().toString(),
                        telp_etxt.getText().toString(), username_etxt.getText().toString(), password_etxt.getText().toString(),mnama,mnama);
                call.enqueue(new Callback<showPegawai>() {
                    @Override
                    public void onResponse(Call<showPegawai> call, Response<showPegawai> response) {
                        Toast.makeText(getContext(), "Suplier Ditambah", Toast.LENGTH_SHORT).show();
                        ((PengPgwActivity)getActivity()).onFinishDialog();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<showPegawai> call, Throwable t) {

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

        ((PengPgwActivity)getContext()).onFinishDialog();
    }
}