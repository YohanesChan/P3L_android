package com.example.kouvemobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PengHwnActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peng_hwn);

        Button btnKelolaJns = findViewById(R.id.pengjns_btn);
        btnKelolaJns.setOnClickListener(this);

        Button btnKelolaUk = findViewById(R.id.penguk_btn);
        btnKelolaUk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pengjns_btn:
                Intent kelolajns = new Intent(PengHwnActivity.this, PengJnsActivity.class);
                startActivity(kelolajns);
                break;

            case R.id.penguk_btn:
                Intent kelolauk = new Intent(PengHwnActivity.this, PengUkActivity.class);
                startActivity(kelolauk);
                break;
        }
    }
}
