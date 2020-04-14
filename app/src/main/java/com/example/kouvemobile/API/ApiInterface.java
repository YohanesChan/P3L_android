package com.example.kouvemobile.API;

import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.Response.loginPegawai;
import com.example.kouvemobile.Response.showCustomer;
import com.example.kouvemobile.Response.showJenis;
import com.example.kouvemobile.Response.showLayanan;
import com.example.kouvemobile.Response.showPegawai;
import com.example.kouvemobile.Response.showProduk;
import com.example.kouvemobile.Response.showSupplier;
import com.example.kouvemobile.Response.showUkuran;
import com.example.kouvemobile.Response.tampilProduk;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("pegawai/create")
    Call<showPegawai> regisPegawai (
            @Field("nama_pegawai") String nama_pegawai,
            @Field("role_pegawai") String role_pegawai,
            @Field("alamat_pegawai") String alamat_pegawai,
            @Field("birthday_pegawai") String birthday_pegawai,
            @Field("telp_pegawai") String telp_pegawai,
            @Field("username_pegawai") String username_pegawai,
            @Field("password_pegawai") String password_pegawai,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("pegawai")
    Call<showPegawai> tampilPegawai();

    @FormUrlEncoded
    @POST("pegawai/delete/{id_pegawai}")
    Call<showPegawai> hapusPegawai(
            @Path("id_pegawai") int id_pegawai,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("pegawai/update/{id_pegawai}")
    Call<showPegawai> editPegawai(
            @Path("id_pegawai") int id_pegawai,
            @Field("nama_pegawai") String nama_pegawai,
            @Field("telp_pegawai") String telp_pegawai,
            @Field("alamat_pegawai") String alamat_pegawai,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );


    @GET("supplier")
    Call<showSupplier> tampilSupplier();

    @FormUrlEncoded
    @POST("supplier/create")
    Call<showSupplier> regisSupplier (
            @Field("nama_supplier") String nama_supplier,
            @Field("telp_supplier") String telp_supplier,
            @Field("alamat_supplier") String alamat_supplier,
            @Field("id_pegawai_fk") Integer id_pegawai_fk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @FormUrlEncoded
    @POST("supplier/delete/{id_supplier}")
    Call<showSupplier> hapusSupplier(
            @Path("id_supplier") int id_supplier,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("supplier/update/{id_supplier}")
    Call<showSupplier> editSupplier(
            @Path("id_supplier") int id_supplier,
            @Field("nama_supplier") String nama_supplier,
            @Field("telp_supplier") String telp_supplier,
            @Field("alamat_supplier") String alamat_supplier,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("produk")
    Call<showProduk> tampilProduk();

    @FormUrlEncoded
    @POST("produk/create")
    Call<showProduk> regisProduk (
            @Field("nama_produk") String nama_produk,
            @Field("harga_produk") Integer harga_produk,
            @Field("stok_produk") Integer stok_produk,
            @Field("stok_minimal") Integer stok_minimal,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("id_pegawai_fk") Integer id_pegawai_fk
    );

    @FormUrlEncoded
    @POST("produk/delete/{id_produk}")
    Call<showProduk> hapusProduk(
            @Path("id_produk") int id_produk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
            );

    @FormUrlEncoded
    @POST("produk/update/{id_produk}")
    Call<showProduk> editProduk(
            @Path("id_produk") int id_produk,
            @Field("harga_produk") Integer harga_produk,
            @Field("stok_minimal") Integer stok_minimal,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );


    @GET("layanan")
    Call<showLayanan> tampilLayanan();

    @FormUrlEncoded
    @POST("layanan/create")
    Call<showLayanan> regisLayanan (
            @Field("nama_layanan") String nama_layanan,
            @Field("harga_layanan") Integer harga_layanan,
            @Field("id_ukuran_fk") Integer id_ukuran_fk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("id_pegawai_fk") Integer id_pegawai_fk
    );
    @FormUrlEncoded
    @POST("layanan/delete/{id_layanan}")
    Call<showLayanan> hapusLayanan(
            @Path("id_layanan") int id_layanan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
            );

    @FormUrlEncoded
    @POST("layanan/update/{id_layanan}")
    Call<showLayanan> editLayanan(
            @Path("id_layanan") int id_layanan,
            @Field("harga_layanan") Integer harga_layanan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("jenishwn")
    Call<showJenis> tampilJenis();

    @FormUrlEncoded
    @POST("jenishwn/create")
    Call<showJenis> regisJenis (
            @Field("nama_jenis_hewan") String nama_jenis_hewan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("id_pegawai_fk") Integer id_pegawai_fk
    );
    @FormUrlEncoded
    @POST("jenishwn/delete/{id_jenis}")
    Call<showJenis> hapusJenis(
            @Path("id_jenis") int id_jenis,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("jenishwn/update/{id_jenis}")
    Call<showJenis> editJenis(
            @Path("id_jenis") int id_jenis,
            @Field("nama_jenis_hewan") String nama_jenis_hewan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("ukuranhwn")
    Call<showUkuran> tampilUkuran();

    @FormUrlEncoded
    @POST("ukuranhwn/create")
    Call<showUkuran> regisUkuran (
            @Field("nama_ukuran_hewan") String nama_ukuran_hewan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("id_pegawai_fk") Integer id_pegawai_fk
    );
    @FormUrlEncoded
    @POST("ukuranhwn/delete/{id_ukuran}")
    Call<showUkuran> hapusUkuran(
            @Path("id_ukuran") int id_ukuran,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("ukuranhwn/update/{id_ukuran}")
    Call<showUkuran> editUkuran(
            @Path("id_ukuran") int id_ukuran,
            @Field("nama_ukuran_hewan") String nama_ukuran_hewan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by

    );

    @FormUrlEncoded
    @POST("pegawai/login")
    Call<loginPegawai> Login(
            @Field("username_pegawai") String username_pegawai,
            @Field("password_pegawai") String password_pegawai);

    @GET("produk")
    Call<tampilProduk> produkShow();

    @FormUrlEncoded
    @POST("customer/create")
    Call<showCustomer> regisCustomer (
            @Field("nama_customer") String nama_customer,
            @Field("alamat_customer") String alamat_customer,
            @Field("birthday_customer") String birthday_customer,
            @Field("telp_customer") String telp_customer,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("id_pegawai_fk") Integer id_pegawai_fk
    );

    @GET("customer")
    Call<showCustomer> tampilCustomer();

    @FormUrlEncoded
    @POST("customer/delete/{id_customer}")
    Call<showCustomer> hapusCustomer(
            @Path("id_customer") int id_customer,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("customer/update/{id_customer}")
    Call<showCustomer> editCustomer(
            @Path("id_customer") int id_Customer,
            @Field("nama_customer") String nama_customer,
            @Field("telp_customer") String telp_customer,
            @Field("alamat_customer") String alamat_customer,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

}
