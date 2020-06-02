package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class DTLayanan {
    @SerializedName("id_playanan")
    private Integer id_playanan;

    @SerializedName("nama_layanan")
    private String nama_layanan;

    @SerializedName("jml_layanan")
    private Integer jml_layanan;

    @SerializedName("harga_layanan")
    private Integer harga_layanan;

    public Integer getId_playanan() {
        return id_playanan;
    }

    public void setId_playanan(Integer id_playanan) {
        this.id_playanan = id_playanan;
    }

    public String getNama_playanan() {
        return nama_layanan;
    }

    public void setNama_playanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public Integer getJml_playanan() {
        return jml_layanan;
    }

    public void setJml_playanan(Integer jml_layanan) {
        this.jml_layanan = jml_layanan;
    }

    public Integer getHarga_playanan() {
        return harga_layanan;
    }

    public void setHarga_playanan(Integer harga_layanan) {
        this.harga_layanan = harga_layanan;
    }
}
