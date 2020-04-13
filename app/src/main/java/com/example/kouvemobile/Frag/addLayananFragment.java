package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.Ukuran;
import com.example.kouvemobile.PengLynActivity;
import com.example.kouvemobile.PengPdkActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataLayananAdapter;
import com.example.kouvemobile.RecyclerView.DataUkuranAdapter;
import com.example.kouvemobile.Response.showLayanan;
import com.example.kouvemobile.Response.showUkuran;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addLayananFragment extends DialogFragment {
    EditText name_etxt, harga_etxt;
    DataLayananAdapter mAdapter;
    Spinner mSpinner;
    Integer selected;
    String ukuranLy;
    List<Ukuran> ukuranList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private ApiInterface apiInterface;
    private DataUkuranAdapter mDataUkuranAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addlayanan, container, false);

        name_etxt = v.findViewById(R.id.nama_lyn_etxt);
        harga_etxt = v.findViewById(R.id.harga_lyn_etxt);
        mSpinner = (Spinner) v.findViewById(R.id.uk_spn);
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<showUkuran> call = apiInterface.tampilUkuran();
        call.enqueue(new Callback<showUkuran>() {
            @Override
            public void onResponse(Call<showUkuran> call, Response<showUkuran> response) {
                ukuranList = response.body().getResult();
                for (Ukuran temp : ukuranList) {
                    Log.e("temp", temp.getNama_ukuran_hewan());
                    list.add(temp.getNama_ukuran_hewan());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showUkuran> call, Throwable t) {

            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), ukuranList.get(position).getId_ukuran() + "hahahahahaha aning", Toast.LENGTH_SHORT).show();
                selected = ukuranList.get(position).getId_ukuran();
                ukuranLy = ukuranList.get(position).getNama_ukuran_hewan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        v.findViewById(R.id.addlyn_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_etxt.getText().toString().isEmpty() || harga_etxt.getText().toString().isEmpty() ||
                    mSpinner.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showLayanan> call = apiInterface.regisLayanan(name_etxt.getText().toString() + " - " + ukuranLy,
                            Integer.parseInt(harga_etxt.getText().toString()),
                            selected, mnama, mnama, mid);
                    call.enqueue(new Callback<showLayanan>() {
                        @Override
                        public void onResponse(Call<showLayanan> call, Response<showLayanan> response) {
                            Toast.makeText(getContext(), "Layanan Ditambah", Toast.LENGTH_SHORT).show();
                            ((PengLynActivity) getActivity()).onFinishDialog();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showLayanan> call, Throwable t) {
                            Toast.makeText(getContext(), "Layanan gagal Ditambah", Toast.LENGTH_SHORT).show();
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

        ((PengLynActivity)getContext()).onFinishDialog();
    }
}
