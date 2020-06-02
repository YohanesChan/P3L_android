package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Hewan {
    @SerializedName("id_hewan")
    private Integer id_hewan;

    @SerializedName("no_hewan")
    private String no_hewan;

    @SerializedName("nama_hewan")
    private String nama_hewan;

    @SerializedName("birthday_hewan")
    private String birthday_hewan;

    @SerializedName("id_customer_fk")
    private Integer id_customer_fk;

    public Integer getId_customer_fk() {
        return id_customer_fk;
    }

    public void setId_customer_fk(Integer id_customer_fk) {
        this.id_customer_fk = id_customer_fk;
    }

    public Integer getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(Integer id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getBirthday_hewan() {
        return birthday_hewan;
    }

    public void setBirthday_hewan(String birthday_hewan) {
        this.birthday_hewan = birthday_hewan;
    }

    public String getNo_hewan() {
        return no_hewan;
    }

    public void setNo_hewan(String no_hewan) {
        this.no_hewan = no_hewan;
    }
}
