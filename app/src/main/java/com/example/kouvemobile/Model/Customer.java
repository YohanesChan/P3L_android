package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("id_customer")
    private Integer id_customer;

    @SerializedName("no_customer")
    private String no_customer;

    @SerializedName("nama_customer")
    private String nama_customer;

    @SerializedName("alamat_customer")
    private String alamat_customer;

    @SerializedName("birthday_customer")
    private String birthday_customer;

    @SerializedName("telp_customer")
    private String telp_customer;

    public Integer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Integer id_customer) {
        this.id_customer = id_customer;
    }

    public String getNo_customer() {
        return no_customer;
    }

    public void setNo_customer(String no_customer) {
        this.no_customer = no_customer;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getBirthday_customer() {
        return birthday_customer;
    }

    public void setBirthday_customer(String birthday_customer) {
        this.birthday_customer = birthday_customer;
    }

    public String getTelp_customer() {
        return telp_customer;
    }

    public void setTelp_customer(String telp_customer) {
        this.telp_customer = telp_customer;
    }
}
