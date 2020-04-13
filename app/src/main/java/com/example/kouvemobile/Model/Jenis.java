package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Jenis {
    @SerializedName("id_jenis")
    private Integer id_jenis;

    @SerializedName("nama_jenis_hewan")
    private String nama_jenis_hewan;

    public Integer getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(Integer id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
    }
}
