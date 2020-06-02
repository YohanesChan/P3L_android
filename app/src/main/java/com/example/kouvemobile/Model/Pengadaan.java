package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Pengadaan {
    @SerializedName("id_pengadaan")
    private Integer id_pengadaan;

    @SerializedName("kode_pengadaan")
    private String kode_pengadaan;

    @SerializedName("status_PO")
    private String status_PO;

    @SerializedName("tgl_pengadaan")
    private String tgl_pengadaan;

    @SerializedName("total_pengadaan")
    private Integer total_pengadaan;

    public Integer getId_pengadaan() {
        return id_pengadaan;
    }

    public void setId_pengadaan(Integer id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public String getKode_pengadaan() {
        return kode_pengadaan;
    }

    public void setKode_pengadaan(String kode_pengadaan) {
        this.kode_pengadaan = kode_pengadaan;
    }

    public String getStatus_PO() {
        return status_PO;
    }

    public void setStatus_PO(String status_PO) {
        this.status_PO = status_PO;
    }

    public String getTgl_pengadaan() {
        return tgl_pengadaan;
    }

    public void setTgl_pengadaan(String tgl_pengadaan) {
        this.tgl_pengadaan = tgl_pengadaan;
    }

    public Integer getTotal_pengadaan() {
        return total_pengadaan;
    }

    public void setTotal_pengadaan(Integer total_pengadaan) {
        this.total_pengadaan = total_pengadaan;
    }
}
