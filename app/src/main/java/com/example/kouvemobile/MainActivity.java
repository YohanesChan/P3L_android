package com.example.kouvemobile;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.kouvemobile.Frag.HomeFragment;
import com.example.kouvemobile.Frag.ProdukFragment;
import com.example.kouvemobile.Frag.LoginFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int selected_id = menuItem.getItemId();

                switch (selected_id) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        break;
                    case R.id.nav_login:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new LoginFragment()).commit();
                        break;
                    case R.id.nav_produk:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ProdukFragment()).commit();
                        break;
                }

                drawer.closeDrawers();

                return true;
            }
        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();//////////////////////////////////////////ini apa

    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Anda akan keluar aplikasi")
                    .setMessage("Yakin akan melanjutkan?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

    public void openside(){
        drawer.openDrawer(GravityCompat.START);
    }
}
