package com.example.kouvemobile.Response;


import com.example.kouvemobile.Model.Pegawai;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showPegawai {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Pegawai> result;

    public showPegawai() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Pegawai> getResult() {
        return result;
    }

    public void setResult(List<Pegawai> result) {
        this.result = result;
    }
}
