package com.example.kouvemobile.Frag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.CsActivity;
import com.example.kouvemobile.MainActivity;
import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.Model.Supplier;
import com.example.kouvemobile.OwnerActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.Response.loginPegawai;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {
    EditText username_txt, password_txt;
    Button login_btn;
    String login_cred;
    String login_role;
    private ApiInterface apiInterface;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        SharedPreferences sp = getContext().getSharedPreferences("login", MODE_PRIVATE);
        login_cred = sp.getString("login_cred", null);
        login_role = sp.getString("login_role", null);

        username_txt = v.findViewById(R.id.username_etxt);
        password_txt = v.findViewById(R.id.password_etxt);
        login_btn= v.findViewById(R.id.login_btn);

        Button btn = v.findViewById(R.id.menu1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openside();
            }
        });

        v.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading...");

                if (username_txt.getText().toString().isEmpty() ||
                        password_txt.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();

                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                    Call<loginPegawai> call = apiInterface.Login(username_txt.getText().toString(),
                            password_txt.getText().toString());
                    call.enqueue(new Callback<loginPegawai>() {
                        @Override
                        public void onResponse(Call<loginPegawai> call, Response<loginPegawai> response) {
                            if (response.isSuccessful()) {
                                SharedPreferences sp = getContext().getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor ed = sp.edit();

                                ed.putString("login_cred", username_txt.getText().toString());
                                ed.putInt("id", response.body().getResult().getId_pegawai());
                                ed.putString("name", response.body().getResult().getNama_pegawai());
                                ed.apply();

                                login_cred = sp.getString("login_cred", null);

                                if (response.body().getResult().getRole_pegawai().equals("Owner")) {
                                    Log.e("Login Berhasil", login_cred);
                                    Toast.makeText(getContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    //intent to another activity
                                    Intent i = new Intent(getActivity(), OwnerActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                }else if (response.body().getResult().getRole_pegawai().equals("Customer Service")) {
                                    Log.e("Login Berhasil", login_cred);
                                    Toast.makeText(getContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    //intent to another activity
                                    Intent i = new Intent(getActivity(), CsActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Role tidak ada", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Log.e("onResponse", response.message());
                                Toast.makeText(getContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                                Log.e("Login Gagal", login_cred);
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<loginPegawai> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return v;

    }
}
