package com.example.kouvemobile.API;

import com.example.kouvemobile.Model.Pegawai;
import com.example.kouvemobile.Response.loginPegawai;
import com.example.kouvemobile.Response.showCustomer;
import com.example.kouvemobile.Response.showDP;
import com.example.kouvemobile.Response.showHewan;
import com.example.kouvemobile.Response.showJenis;
import com.example.kouvemobile.Response.showLayanan;
import com.example.kouvemobile.Response.showPegawai;
import com.example.kouvemobile.Response.showPengadaan;
import com.example.kouvemobile.Response.showProduk;
import com.example.kouvemobile.Response.showSupplier;
import com.example.kouvemobile.Response.showTL;
import com.example.kouvemobile.Response.showTP;
import com.example.kouvemobile.Response.showTransaksiL;
import com.example.kouvemobile.Response.showTransaksiP;
import com.example.kouvemobile.Response.showUkuran;
import com.example.kouvemobile.Response.tampilProduk;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
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
            @Field("created_by") String created_by
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
            @Field("created_by") String created_by
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

    @GET("produk/notif")
    Call<showProduk> tampilProdukHabis();

//    @FormUrlEncoded
//    @POST("produk/create")
//    Call<showProduk> regisProduk (
//            @Field("nama_produk") String nama_produk,
//            @Field("harga_produk") Integer harga_produk,
//            //@Field("gambar") String gambar,
//            @Field("stok_produk") Integer stok_produk,
//            @Field("stok_minimal") Integer stok_minimal,
//            @Field("created_by") String created_by,
//            @Field("updated_by") String updated_by,
//            @Field("id_pegawai_fk") Integer id_pegawai_fk
//    );

    @Multipart
    @POST("produk/create")
    Call<showProduk> regisProduk(
            @Part("nama_produk") RequestBody nama_produk,
            @Part("harga_produk") RequestBody harga_produk,
            @Part("stok_produk") RequestBody stok_produk,
            @Part("stok_minimal") RequestBody stok_minimal,
            @Part MultipartBody.Part gambar,
            @Part("created_by") RequestBody created_by,
            @Part("id_pegawai_fk") RequestBody id_pegawai_fk
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
            @Field("nama_layanan") String nama_layanan,
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

    @GET("produk/sPriceA")
    Call<tampilProduk> produkShowPA();

    @GET("produk/sPriceD")
    Call<tampilProduk> produkShowPD();

    @GET("produk/sStockA")
    Call<tampilProduk> produkShowSA();

    @GET("produk/sStockD")
    Call<tampilProduk> produkShowSD();

    @FormUrlEncoded
    @POST("customer/create")
    Call<showCustomer> regisCustomer (
            @Field("nama_customer") String nama_customer,
            @Field("alamat_customer") String alamat_customer,
            @Field("birthday_customer") String birthday_customer,
            @Field("telp_customer") String telp_customer,
            @Field("created_by") String created_by,
            @Field("id_pegawai_fk") Integer id_pegawai_fk
    );

    @GET("hewan")
    Call<showHewan> tampilHewan();

    @FormUrlEncoded
    @POST("hewan/delete/{id_hewan}")
    Call<showHewan> hapusHewan(
            @Path("id_hewan") int id_customer,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("hewan/update/{id_hewan}")
    Call<showHewan> editHewan(
            @Path("id_hewan") int id_hewan,
            @Field("nama_hewan") String nama_hewan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @FormUrlEncoded
    @POST("hewan/create")
    Call<showHewan> regisHewan (
            @Field("nama_hewan") String nama_hewan,
            @Field("birthday_hewan") String birthday_hewan,
            @Field("id_customer_fk") Integer id_customer_fk,
            @Field("id_pegawai_fk") Integer id_pegawai_fk,
            @Field("id_jenis_fk") Integer id_jenis_fk,
            @Field("created_by") String created_by
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

    @FormUrlEncoded
    @POST("pengadaan/create")
    Call<showPengadaan> regisPengadaan (
            @Field("id_supplier_fk") Integer id_supplier_fk,
            @Field("status_PO") String status_PO,
            @Field("total_pengadaan") Integer total_pengadaan,
            @Field("id_pegawai_fk") Integer id_pegawai_fk,
            @Field("created_by") String created_by
    );

    @GET("pengadaan")
    Call<showPengadaan> tampilPengadaan();

    @FormUrlEncoded
    @POST("pengadaan/delete/{id_pengadaan}")
    Call<showPengadaan> hapusPengadaan(
            @Path("id_pengadaan") int id_pengadaan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("pengadaan/udt/{id_pengadaan}")
    Call<showPengadaan> editPengadaan(
            @Path("id_pengadaan") int id_pengadaan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @FormUrlEncoded
    @POST("pengadaan/ttl/{id_pengadaan_fk}")
    Call<showPengadaan> totalPengadaan(
            @Path("id_pengadaan_fk") int id_pengadaan_fk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("pengadaan/total/{id_pengadaan_fk}")
    Call<showPengadaan> ttlPengadaan(
            @Path("id_pengadaan_fk") int id_pengadaan_fk
    );

    @GET("detilP/{id_pengadaan_fk}")
    Call<showDP> tampilDPengadaan(
        @Path("id_pengadaan_fk") int id_pengadaan_fk
    );

    @FormUrlEncoded
    @POST("detilP/create")
    Call<showDP> regisDPengadaan (
            @Field("nama_produk") String nama_produk,
            @Field("jml_produk") Integer jml_produk,
            @Field("harga_produk") Integer harga_produk,
            @Field("id_pengadaan_fk") Integer id_pengadaan_fk,
            @Field("id_produk_fk") Integer id_produk_fk,
            @Field("created_by") String created_by
    );


    @POST("detilP/delete/{id_detil_pengadaan}")
    Call<showDP> hapusDPengadaan(
            @Path("id_detil_pengadaan") int id_detil_pengadaan
    );

    @FormUrlEncoded
    @POST("detilP/update/{id_detil_pengadaan}")
    Call<showDP> editDPengadaan(
            @Path("id_detil_pengadaan") int id_detil_pengadaan,
            @Field("jml_produk") Integer jml_produk,
            @Field("harga_produk") Integer harga_produk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @FormUrlEncoded
    @POST("tproduk/create")
    Call<showTransaksiP> regisTransaksiP (
            @Field("id_customer_fk") Integer id_customer_fk,
            @Field("status_tproduk") String status_tproduk,
            @Field("total_tproduk") Integer total_tproduk,
            @Field("id_pegawai_fk") Integer id_pegawai_fk,
            @Field("created_by") String created_by
    );

    @GET("tproduk")
    Call<showTransaksiP> tampilTransaksiP();

    @FormUrlEncoded
    @POST("tproduk/delete/{id_tproduk}")
    Call<showTransaksiP> hapusTransaksiP(
            @Path("id_tproduk") int id_tproduk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("tproduk/update/{id_tproduk}")
    Call<showTransaksiP> editTransaksiP(
            @Path("id_tproduk") int id_tproduk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @FormUrlEncoded
    @POST("tproduk/ttl/{id_tproduk_fk}")
    Call<showTransaksiP> totalTransaksiP(
            @Path("id_tproduk_fk") int id_tproduk_fk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("detilPP/{id_tproduk_fk}")
    Call<showTP> tampilDTransaksiP(
            @Path("id_tproduk_fk") int id_tproduk_fk
    );

    @FormUrlEncoded
    @POST("detilPP/create")
    Call<showTP> regisDTransaksiP (
            @Field("nama_produk") String nama_produk,
            @Field("jml_produk") Integer jml_produk,
            @Field("harga_produk") Integer harga_produk,
            @Field("id_tproduk_fk") Integer id_tproduk_fk,
            @Field("id_produk_fk") Integer id_produk_fk,
            @Field("created_by") String created_by
    );


    @POST("detilPP/delete/{id_pproduk}")
    Call<showTP> hapusDTransaksiP(
            @Path("id_pproduk") int id_pproduk
    );

    @FormUrlEncoded
    @POST("detilPP/update/{id_pproduk}")
    Call<showTP> editDTransaksiP(
            @Path("id_pproduk") int id_pproduk,
            @Field("jml_produk") Integer jml_produk,
            @Field("harga_produk") Integer harga_produk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    //////////////////////////
    @FormUrlEncoded
    @POST("tlayanan/create")
    Call<showTransaksiL> regisTransaksiL (
            @Field("id_customer_fk") Integer id_customer_fk,
            @Field("status_tlayanan") String status_tlayanan,
            @Field("total_tlayanan") Integer total_tlayanan,
            @Field("id_pegawai_fk") Integer id_pegawai_fk,
            @Field("created_by") String created_by
    );

    @GET("tlayanan")
    Call<showTransaksiL> tampilTransaksiL();

    @GET("tlayanan/finish")
    Call<showTransaksiL> tampilTransaksiL_finih();

    @GET("tlayanan/proccess")
    Call<showTransaksiL> tampilTransaksiL_proccess();

    @FormUrlEncoded
    @POST("tlayanan/delete/{id_tlayanan}")
    Call<showTransaksiL> hapusTransaksiL(
            @Path("id_tlayanan") int id_tlayanan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by,
            @Field("deleted_by") String deleted_by
    );

    @FormUrlEncoded
    @POST("tlayanan/update/{id_tlayanan}")
    Call<showTransaksiL> editTransaksiL(
            @Path("id_tlayanan") int id_tlayanan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @FormUrlEncoded
    @POST("tlayanan/ttl/{id_tlayanan_fk}")
    Call<showTransaksiL> totalTransaksiL(
            @Path("id_tlayanan_fk") int id_tlayanan_fk,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

    @GET("detilPL/{id_playanan_fk}")
    Call<showTL> tampilDTransaksiL(
            @Path("id_playanan_fk") int id_playanan_fk
    );

    @FormUrlEncoded
    @POST("detilPL/create")
    Call<showTL> regisDTransaksiL (
            @Field("nama_layanan") String nama_layanan,
            @Field("jml_layanan") Integer jml_layanan,
            @Field("harga_layanan") Integer harga_layanan,
            @Field("id_hewan_fk") Integer id_hewan_fk,
            @Field("id_tlayanan_fk") Integer id_tlayanan_fk,
            @Field("id_layanan_fk") Integer id_layanan_fk,
            @Field("created_by") String created_by
    );


    @POST("detilPL/delete/{id_playanan}")
    Call<showTL> hapusDTransaksiL(
            @Path("id_playanan") int id_playanan
    );

    @FormUrlEncoded
    @POST("detilPL/update/{id_playanan}")
    Call<showTL> editDTransaksiL(
            @Path("id_playanan") int id_playanan,
            @Field("jml_layanan") Integer jml_layanan,
            @Field("harga_layanan") Integer harga_layanan,
            @Field("created_by") String created_by,
            @Field("updated_by") String updated_by
    );

}
