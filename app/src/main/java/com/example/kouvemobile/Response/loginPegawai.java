package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.Pegawai;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class loginPegawai {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private Pegawai result;

    public loginPegawai() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pegawai getResult() {
        return result;
    }

    public void setResult(Pegawai result) {
        this.result = result;
    }
}
