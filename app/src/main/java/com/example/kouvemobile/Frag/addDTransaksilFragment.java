package com.example.kouvemobile.Frag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.DTLayanan;
import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.Model.Layanan;
import com.example.kouvemobile.Model.TransaksiL;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataTlAdapter;
import com.example.kouvemobile.Response.showHewan;
import com.example.kouvemobile.Response.showLayanan;
import com.example.kouvemobile.Response.showTL;
import com.example.kouvemobile.Response.showTransaksiL;
import com.example.kouvemobile.TransaksiLActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class addDTransaksilFragment extends DialogFragment {
    EditText jml_etxt;
    TextView hrg_txt;
    Spinner mSpinner,mSpinner1;
    private DataTlAdapter mDataTlAdapter;
    private List<DTLayanan> list2 = new ArrayList<>();
    private ApiInterface apiInterface;
    List<Layanan> layananList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    List<Hewan> hewanList = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    Integer selected,selected1,hrg_pdk;
    String namaLayanan,namaHewan;
    private TransaksiL trl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_adddetiltrl, container, false);

        jml_etxt = v.findViewById(R.id.jml_dtrl_etxt);
        hrg_txt = v.findViewById(R.id.hrg_dtrl_txt);
        mSpinner = (Spinner) v.findViewById(R.id.lyn_dtrl_spn);
        mSpinner1 = (Spinner) v.findViewById(R.id.hew_dtrl_spn);

        Bundle bundle = getArguments();
        trl = new TransaksiL();
        trl.setId_tlayanan(bundle.getInt("id"));
        trl.setKode_tlayanan(bundle.getString("kode"));
        trl.setStatus_tlayanan(bundle.getString("status"));
        trl.setTotal_tlayanan(bundle.getInt("total"));

//        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<showHewan> call = apiInterface.tampilHewan();
//        call.enqueue(new Callback<showHewan>() {
//            @Override
//            public void onResponse(Call<showHewan> call, Response<showHewan> response) {
//                hewanList = response.body().getResult();
//                for (Hewan temp : hewanList) {
//                    Log.e("temp", temp.getNama_hewan());
//                    list3.add(temp.getNama_hewan());
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list3);
//
//                //how the spinner will look when it drop downs on click
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                //setting adapter to spinner
//                mSpinner.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<showHewan> call, Throwable t) {
//
//            }
//        });
//        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selected1 = hewanList.get(position).getId_hewan();
//                hewan = hewanList.get(position).getNama_hewan();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });//////////////////////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<showHewan> call1 = apiInterface.tampilHewan();
        call1.enqueue(new Callback<showHewan>() {
            @Override
            public void onResponse(Call<showHewan> call, Response<showHewan> response) {
                hewanList = response.body().getResult();
                for (Hewan temp : hewanList) {
                    Log.e("temp", temp.getNama_hewan());
                    list1.add(temp.getNama_hewan());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list1);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showHewan> call, Throwable t) {

            }
        });

        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected1 = hewanList.get(position).getId_hewan();
                namaHewan = hewanList.get(position).getNama_hewan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<showLayanan> call = apiInterface.tampilLayanan();
        call.enqueue(new Callback<showLayanan>() {
            @Override
            public void onResponse(Call<showLayanan> call, Response<showLayanan> response) {
                layananList = response.body().getResult();
                for (Layanan temp : layananList) {
                    Log.e("temp", temp.getNama_layanan());
                    list.add(temp.getNama_layanan());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                //how the spinner will look when it drop downs on click
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //setting adapter to spinner
                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<showLayanan> call, Throwable t) {

            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = layananList.get(position).getId_layanan();
                namaLayanan = layananList.get(position).getNama_layanan();
                hrg_pdk = layananList.get(position).getHarga_layanan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jml_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() > 0 )
                {
                    Integer jml = Integer.parseInt(jml_etxt.getText().toString());
                    Integer temp;
                    temp = jml*hrg_pdk;
                    String inputText = temp.toString();
                    hrg_txt.setText(inputText);
                }
                else {
                    Integer temp;
                    temp = 0*hrg_pdk;
                    String inputText = temp.toString();
                    hrg_txt.setText(inputText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("idp",0);

        v.findViewById(R.id.adddtrl_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSpinner.getSelectedItem().toString().isEmpty() || jml_etxt.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<showTL> call = apiInterface.regisDTransaksiL(namaLayanan,
                            Integer.parseInt(jml_etxt.getText().toString()),
                            Integer.parseInt(hrg_txt.getText().toString()),
                            selected1,
                            mid,
                            selected,
                            mnama);

                    call.enqueue(new Callback<showTL>() {
                        @Override
                        public void onResponse(Call<showTL> call, Response<showTL> response) {
                            if (response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Layanan Ditambah", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getContext(), "Layanan gagal Ditambah", Toast.LENGTH_SHORT).show();
                            }
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<showTL> call, Throwable t) {
                            dismiss();
                        }
                    });
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<showTransaksiL> call1 = apiInterface.totalTransaksiL(mid,mnama,mnama);
                    call1.enqueue(new Callback<showTransaksiL>() {
                        @Override
                        public void onResponse(Call<showTransaksiL> call, Response<showTransaksiL> response) {

                        }

                        @Override
                        public void onFailure(Call<showTransaksiL> call, Throwable t) {

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
        detilTLynFragment detiltlyn = new detilTLynFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", trl.getId_tlayanan());
        bundle.putString("kode", trl.getKode_tlayanan());
        bundle.putString("status", trl.getStatus_tlayanan());
        bundle.putInt("total", trl.getTotal_tlayanan());
        detiltlyn.setArguments(bundle);


        ((TransaksiLActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detiltlyn).commit();
    }
}
