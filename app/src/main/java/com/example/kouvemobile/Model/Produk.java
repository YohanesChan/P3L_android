package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Produk {
    @SerializedName("id_produk")
    private Integer id_produk;

    @SerializedName("gambar")
    String gambar;

    @SerializedName("no_produk")
    private String no_produk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("harga_produk")
    private Integer harga_produk;

    @SerializedName("stok_produk")
    private Integer stok_produk;

    @SerializedName("stok_minimal")
    private Integer stok_minimal;

    public Integer getId_produk() {
        return id_produk;
    }

    public void setId_produk(Integer id_produk) {
        this.id_produk = id_produk;
    }

    public String getNo_produk() {
        return no_produk;
    }

    public void setNo_produk(String no_produk) {
        this.no_produk = no_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Integer getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(Integer harga_produk) {
        this.harga_produk = harga_produk;
    }

    public Integer getStok_produk() {
        return stok_produk;
    }

    public void setStok_produk(Integer stok_produk) {
        this.stok_produk = stok_produk;
    }

    public Integer getStok_minimal() {
        return stok_minimal;
    }

    public void setStok_minimal(Integer stok_minimal) {
        this.stok_minimal = stok_minimal;
    }
}
