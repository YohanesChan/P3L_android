package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class TransaksiP {
    @SerializedName("id_tproduk")
    private Integer id_tproduk;

    @SerializedName("kode_tproduk")
    private String kode_tproduk;

    @SerializedName("status_tproduk")
    private String status_tproduk;

    @SerializedName("tgl_tproduk")
    private String tgl_tproduk;

    @SerializedName("total_tproduk")
    private Integer total_tproduk;

    public Integer getId_tproduk() {
        return id_tproduk;
    }

    public void setId_tproduk(Integer id_tproduk) {
        this.id_tproduk = id_tproduk;
    }

    public String getKode_tproduk() {
        return kode_tproduk;
    }

    public void setKode_tproduk(String kode_tproduk) {
        this.kode_tproduk = kode_tproduk;
    }

    public String getStatus_tproduk() {
        return status_tproduk;
    }

    public void setStatus_tproduk(String status_tproduk) {
        this.status_tproduk = status_tproduk;
    }

    public String getTgl_tproduk() {
        return tgl_tproduk;
    }

    public void setTgl_tproduk(String tgl_tproduk) {
        this.tgl_tproduk = tgl_tproduk;
    }

    public Integer getTotal_tproduk() {
        return total_tproduk;
    }

    public void setTotal_tproduk(Integer total_tproduk) {
        this.total_tproduk = total_tproduk;
    }
}
