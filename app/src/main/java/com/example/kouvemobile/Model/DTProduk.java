package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class DTProduk {
    @SerializedName("id_pproduk")
    private Integer id_pproduk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("jml_produk")
    private Integer jml_produk;

    @SerializedName("harga_produk")
    private Integer harga_produk;

    public Integer getId_pproduk() {
        return id_pproduk;
    }

    public void setId_pproduk(Integer id_pproduk) {
        this.id_pproduk = id_pproduk;
    }

    public String getNama_pproduk() {
        return nama_produk;
    }

    public void setNama_pproduk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Integer getJml_pproduk() {
        return jml_produk;
    }

    public void setJml_pproduk(Integer jml_produk) {
        this.jml_produk = jml_produk;
    }

    public Integer getHarga_pproduk() {
        return harga_produk;
    }

    public void setHarga_pproduk(Integer harga_produk) {
        this.harga_produk = harga_produk;
    }
}
