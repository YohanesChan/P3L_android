package com.example.kouvemobile.Frag;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.kouvemobile.API.ApiClient;
import com.example.kouvemobile.API.ApiInterface;
import com.example.kouvemobile.Model.Produk;
import com.example.kouvemobile.PengPdkActivity;
import com.example.kouvemobile.R;
import com.example.kouvemobile.RecyclerView.DataProdukAdapter;
import com.example.kouvemobile.RecyclerView.DataProdukAdapter;
import com.example.kouvemobile.Response.showProduk;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class addProdukFragment extends DialogFragment {
    ImageView mimg;
    Button mChoosebtn;
    EditText name_etxt, harga_etxt, stok_etxt, mstok_etxt;
    private Bitmap ImageBitmap;
    private Bitmap bitmap;
    DataProdukAdapter mAdapter;
    private ApiInterface apiInterface;
    private Uri selectedImage;
    ProgressDialog progressDialog;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addproduk, container, false);

        name_etxt = v.findViewById(R.id.nama_pdk_etxt);
        harga_etxt = v.findViewById(R.id.harga_pdk_etxt);
        stok_etxt = v.findViewById(R.id.stok_pdk_etxt);
        mstok_etxt = v.findViewById(R.id.min_pdk_etxt);
        mimg = v.findViewById(R.id.img_view);
        mChoosebtn = v.findViewById(R.id.chooseImg_btn);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);

        final String mnama = sp.getString("name","");
        final Integer mid = sp.getInt("id",0);

        v.findViewById(R.id.addpdk_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name_etxt.getText().toString().isEmpty() || harga_etxt.getText().toString().isEmpty()||
                        stok_etxt.getText().toString().isEmpty() || mstok_etxt.getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Field harus terisi semua", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile(selectedImage, name_etxt.getText().toString(),
                            Integer.parseInt(harga_etxt.getText().toString()),
                            Integer.parseInt(stok_etxt.getText().toString()),
                            Integer.parseInt(mstok_etxt.getText().toString()),
                            mnama, mnama, mid);
//                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//
//                    Call<showProduk> call = apiInterface.regisProduk(name_etxt.getText().toString(),
//                            Integer.parseInt(harga_etxt.getText().toString()),
//                            Integer.parseInt(stok_etxt.getText().toString()),
//                            Integer.parseInt(mstok_etxt.getText().toString()),
//                            mnama, mnama, mid);
//                    call.enqueue(new Callback<showProduk>() {
//                        @Override
//                        public void onResponse(Call<showProduk> call, Response<showProduk> response) {
//                            if (response.isSuccessful())
//                            {
//                                Toast.makeText(getContext(), "Produk Ditambah", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
//                                Toast.makeText(getContext(), "Produk gagal Ditambah", Toast.LENGTH_SHORT).show();
//                            }
//                            ((PengPdkActivity) getActivity()).onFinishDialog();
//                            dismiss();
//                        }
//
//                        @Override
//                        public void onFailure(Call<showProduk> call, Throwable t) {
//                            dismiss();
//                        }
//                    });
                }
            }
        });

        mChoosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    checkPermissions();
                    pickImageFromGallery();
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

        ((PengPdkActivity)getContext()).onFinishDialog();
    }

    private void pickImageFromGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE : {
                if(grantResults.length > 0 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(getContext(),"permission denied",Toast.LENGTH_SHORT);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                ImageBitmap = bitmap;
                Picasso.get().load(selectedImage).resize(400, 400).centerCrop().into(mimg);
            } catch (Exception e) {
                Log.e("onActivityResult", e.getMessage());
            }
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1052);

        }
    }

    private void uploadFile(Uri fileUri, String nama_produk, Integer harga_produk,
                            Integer stok_produk, Integer stok_minimum, String mnama, String nnama, Integer mid) {
        //creating a file
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] file = baos.toByteArray();
//        File file = new File(getRealPathFromURI(fileUri));

        RequestBody nama_prod = RequestBody.create(MediaType.parse("multipart/form-data"), nama_produk);
        RequestBody harga_prod = RequestBody.create(MediaType.parse("multipart/form-data"), harga_produk.toString());
        RequestBody stok_prod = RequestBody.create(MediaType.parse("multipart/form-data"), stok_produk.toString());
        RequestBody stok_min = RequestBody.create(MediaType.parse("multipart/form-data"), stok_minimum.toString());
//        RequestBody gambar_spare = RequestBody.create(MediaType.parse("text"), "null");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody inama = RequestBody.create(MediaType.parse("multipart/form-data"), mnama);
        RequestBody jnama = RequestBody.create(MediaType.parse("multipart/form-data"), mnama);
        RequestBody kmid = RequestBody.create(MediaType.parse("multipart/form-data"), mid.toString());

        //creating our api
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        MultipartBody.Part gambar = MultipartBody.Part.createFormData("gambar", "image.jpg", requestFile);

        //creating a call and calling the upload image method
        Call<showProduk> call = apiInterface.regisProduk(nama_prod, harga_prod, stok_prod,
                stok_min, gambar, inama, kmid);


        //finally performing the call
        call.enqueue(new Callback<showProduk>() {
        @Override
        public void onResponse(Call<showProduk> call, Response<showProduk> response) {
            if (response.isSuccessful())
            {
                Toast.makeText(getContext(), "Produk Ditambah", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "Produk gagal Ditambah", Toast.LENGTH_SHORT).show();
            }
            ((PengPdkActivity) getActivity()).onFinishDialog();
            dismiss();
        }

        @Override
        public void onFailure(Call<showProduk> call, Throwable t) {
            dismiss();
        }
    });
    }
}
