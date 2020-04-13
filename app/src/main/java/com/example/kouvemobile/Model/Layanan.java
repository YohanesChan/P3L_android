package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Layanan {
    @SerializedName("id_layanan")
    private Integer id_layanan;

    @SerializedName("no_layanan")
    private String no_layanan;



    @SerializedName("nama_layanan")
    private String nama_layanan;

    @SerializedName("harga_layanan")
    private Integer harga_layanan;

    public Integer getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(Integer id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public Integer getHarga_layanan() {
        return harga_layanan;
    }

    public void setHarga_layanan(Integer harga_layanan) {
        this.harga_layanan = harga_layanan;
    }

    public String getNo_layanan() {
        return no_layanan;
    }

    public void setNo_layanan(String no_layanan) {
        this.no_layanan = no_layanan;
    }
}
