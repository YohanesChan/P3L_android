package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Ukuran {
    @SerializedName("id_ukuran")
    private Integer id_ukuran;

    @SerializedName("no_ukuran")
    private String no_ukuran;

    @SerializedName("nama_ukuran_hewan")
    private String nama_ukuran_hewan;

    @SerializedName("created_by")
    private String created_by;

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Integer getId_pegawai_fk() {
        return id_pegawai_fk;
    }

    public void setId_pegawai_fk(Integer id_pegawai_fk) {
        this.id_pegawai_fk = id_pegawai_fk;
    }

    @SerializedName("id_pegawai_fk")
    private Integer id_pegawai_fk;

    public Integer getId_ukuran() {
        return id_ukuran;
    }

    public void setId_ukuran(Integer id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getNo_ukuran() {
        return no_ukuran;
    }

    public void setNo_ukuran(String no_ukuran) {
        this.no_ukuran = no_ukuran;
    }

    public String getNama_ukuran_hewan() {
        return nama_ukuran_hewan;
    }

    public void setNama_ukuran_hewan(String nama_ukuran_hewan) {
        this.nama_ukuran_hewan = nama_ukuran_hewan;
    }
}
