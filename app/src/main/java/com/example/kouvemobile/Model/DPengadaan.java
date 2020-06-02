package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class DPengadaan {
    @SerializedName("id_detil_pengadaan")
    private Integer id_detil_pengadaan;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("jml_produk")
    private Integer jml_produk;

    @SerializedName("harga_produk")
    private Integer harga_produk;

    public Integer getId_detil_pengadaan() {
        return id_detil_pengadaan;
    }

    public void setId_detil_pengadaan(Integer id_detil_pengadaan) {
        this.id_detil_pengadaan = id_detil_pengadaan;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Integer getJml_produk() {
        return jml_produk;
    }

    public void setJml_produk(Integer jml_produk) {
        this.jml_produk = jml_produk;
    }

    public Integer getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(Integer harga_produk) {
        this.harga_produk = harga_produk;
    }
}
