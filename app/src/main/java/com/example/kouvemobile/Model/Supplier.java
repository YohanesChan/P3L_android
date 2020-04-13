package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Supplier {
    @SerializedName("id_supplier")
    private Integer id_supplier;

    @SerializedName("no_supplier")
    private String no_supplier;

    @SerializedName("nama_supplier")
    private String nama_supplier;

    @SerializedName("alamat_supplier")
    private String alamat_supplier;

    @SerializedName("telp_supplier")
    private String telp_supplier;

    public Integer getId_supplier() {
        return id_supplier;
    }
    public String getNo_supplier() {
        return no_supplier;
    }

    public void setNo_supplier(String no_supplier) {
        this.no_supplier = no_supplier;
    }

    public void setId_supplier(Integer id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getTelp_supplier() {
        return telp_supplier;
    }

    public void setTelp_supplier(String telp_supplier) {
        this.telp_supplier = telp_supplier;
    }
}
