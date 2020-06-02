package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class TransaksiL {
    @SerializedName("id_tlayanan")
    private Integer id_tlayanan;

    @SerializedName("kode_tlayanan")
    private String kode_tlayanan;

    @SerializedName("status_tlayanan")
    private String status_tlayanan;

    @SerializedName("tgl_tlayanan")
    private String tgl_tlayanan;

    @SerializedName("total_tlayanan")
    private Integer total_tlayanan;

    public Integer getId_tlayanan() {
        return id_tlayanan;
    }

    public void setId_tlayanan(Integer id_tlayanan) {
        this.id_tlayanan = id_tlayanan;
    }

    public String getKode_tlayanan() {
        return kode_tlayanan;
    }

    public void setKode_tlayanan(String kode_tlayanan) {
        this.kode_tlayanan = kode_tlayanan;
    }

    public String getStatus_tlayanan() {
        return status_tlayanan;
    }

    public void setStatus_tlayanan(String status_tlayanan) {
        this.status_tlayanan = status_tlayanan;
    }

    public String getTgl_tlayanan() {
        return tgl_tlayanan;
    }

    public void setTgl_tlayanan(String tgl_tlayanan) {
        this.tgl_tlayanan = tgl_tlayanan;
    }

    public Integer getTotal_tlayanan() {
        return total_tlayanan;
    }

    public void setTotal_tlayanan(Integer total_tlayanan) {
        this.total_tlayanan = total_tlayanan;
    }
}
