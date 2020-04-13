package com.example.kouvemobile.Model;

import com.google.gson.annotations.SerializedName;

public class Pegawai {

    @SerializedName("id_pegawai")
    private Integer id_pegawai;

    @SerializedName("no_pegawai")
    private String no_pegawai;

    @SerializedName("nama_pegawai")
    private String nama_pegawai;

    @SerializedName("role_pegawai")
    private String role_pegawai;

    @SerializedName("alamat_pegawai")
    private String alamat_pegawai;

    @SerializedName("birthday_pegawai")
    private String birthday_pegawai;

    @SerializedName("telp_pegawai")
    private String telp_pegawai;

    @SerializedName("username_pegawai")
    private String username_pegawai;

    @SerializedName("password_pegawai")
    private String password_pegawai;

    public Integer getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(Integer id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getNo_pegawai() {
        return no_pegawai;
    }

    public void setNo_pegawai(String no_pegawai) {
        this.no_pegawai = no_pegawai;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getRole_pegawai() {
        return role_pegawai;
    }

    public void setRole_pegawai(String role_pegawai) {
        this.role_pegawai = role_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public String getBirthday_pegawai() {
        return birthday_pegawai;
    }

    public void setBirthday_pegawai(String birthday_pegawai) {
        this.birthday_pegawai = birthday_pegawai;
    }

    public String getTelp_pegawai() {
        return telp_pegawai;
    }

    public void setTelp_pegawai(String telp_pegawai) {
        this.telp_pegawai = telp_pegawai;
    }

    public String getUsername_pegawai() {
        return username_pegawai;
    }

    public void setUsername_pegawai(String username_pegawai) {
        this.username_pegawai = username_pegawai;
    }

    public String getPassword_pegawai() {
        return password_pegawai;
    }

    public void setPassword_pegawai(String password_pegawai) {
        this.password_pegawai = password_pegawai;
    }
}
