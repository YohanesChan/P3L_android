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
import com.example.kouvemobile.PengCustActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataCustomerAdapter;
import com.example.kouvemobile.RecyclerView.DataCustomerAdapter;
import com.example.kouvemobile.Response.showCustomer;
import com.example.kouvemobile.Response.showCustomer;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addCustomerFragment extends DialogFragment {
    TextView mBday;
    EditText name_etxt, alamat_etxt, telp_etxt;
    Spinner mSpinner;
    DataCustomerAdapter mAdapter;
    private ApiInterface apiInterface;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addcustomer, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        name_etxt = v.findViewById(R.id.nama_cst_etxt);
        alamat_etxt = v.findViewById(R.id.alamat_cst_etxt);
        mBday= (TextView) v.findViewById(R.id.bday_cst_txt);
        v.findViewById(R.id.bday_cst_btn).setOnClickListener(new View.OnClickListener() {
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

        telp_etxt = v.findViewById(R.id.telp_cst_etxt);

        v.findViewById(R.id.addcst_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_etxt.getText().toString().isEmpty() || alamat_etxt.getText().toString().isEmpty() || mBday.getText().toString().isEmpty() ||
                        telp_etxt.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showCustomer> call = apiInterface.regisCustomer(name_etxt.getText().toString(),
                            alamat_etxt.getText().toString(), mBday.getText().toString(),
                            telp_etxt.getText().toString(), mnama, mid);
                    call.enqueue(new Callback<showCustomer>() {
                        @Override
                        public void onResponse(Call<showCustomer> call, Response<showCustomer> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Customer Ditambah", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(getContext(), "Customer gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            ((PengCustActivity) getActivity()).onFinishDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showCustomer> call, Throwable t) {
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

        ((PengCustActivity)getContext()).onFinishDialog();
    }
}
