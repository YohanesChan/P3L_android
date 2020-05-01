package com.example.kouvemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OwnerActivity extends AppCompatActivity implements View.OnClickListener {
    TextView nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Button btnKelolaPgw = findViewById(R.id.kelolapgw_btn);
        btnKelolaPgw.setOnClickListener(this);

        Button btnKelolaPdk = findViewById(R.id.kelolapdk_btn);
        btnKelolaPdk.setOnClickListener(this);

        Button btnKelolaLyn = findViewById(R.id.kelolalyn_btn);
        btnKelolaLyn.setOnClickListener(this);

        Button btnKelolaHwn = findViewById(R.id.kelolahwn_btn);
        btnKelolaHwn.setOnClickListener(this);

        Button btnKelolaSup = findViewById(R.id.kelolasup_btn);
        btnKelolaSup.setOnClickListener(this);

        Button btnPengadaan = findViewById(R.id.pengadaan_btn);
        btnPengadaan.setOnClickListener(this);

        Button btnNotifikasi = findViewById(R.id.notif_btn);
        btnNotifikasi.setOnClickListener(this);

        Button btnLogout = findViewById(R.id.logout_btn);
        btnLogout.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        String mnama = sp.getString("name","");
        nama = findViewById(R.id.nama);
        nama.setText(mnama);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kelolapgw_btn:
                 Intent kelolapgw = new Intent(OwnerActivity.this, PengPgwActivity.class);
                 startActivity(kelolapgw);
                 break;

            case R.id.kelolapdk_btn:
                Intent kelolapdk = new Intent(OwnerActivity.this, PengPdkActivity.class);
                startActivity(kelolapdk);
                break;

            case R.id.kelolalyn_btn:
                Intent keloladlyn = new Intent(OwnerActivity.this, PengLynActivity.class);
                startActivity(keloladlyn);
                break;

            case R.id.kelolahwn_btn:
                Intent kelolahwn = new Intent(OwnerActivity.this, PengHwnActivity.class);
                startActivity(kelolahwn);
                break;

            case R.id.kelolasup_btn:
                Intent kelolasup = new Intent(OwnerActivity.this, PengSupActivity.class);
                startActivity(kelolasup);
                break;

            case R.id.pengadaan_btn:
                Intent pengadaan = new Intent(OwnerActivity.this, PengadaanActivity.class);
                startActivity(pengadaan);
                break;

            case R.id.notif_btn:
                Intent notifikasi = new Intent(OwnerActivity.this, PdkHabisActivity.class);
                startActivity(notifikasi);
                break;

            case R.id.logout_btn:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("login_cred", "null");
                ed.apply();

                finish();
                startActivity(new Intent(OwnerActivity.this, MainActivity.class));
                break;
        }
    }
}
