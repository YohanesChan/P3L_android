package com.example.kouvemobile.Response;


import com.example.kouvemobile.Model.Pengadaan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showPengadaan {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Pengadaan> result;

    public showPengadaan() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Pengadaan> getResult() {
        return result;
    }

    public void setResult(List<Pengadaan> result) {
        this.result = result;
    }
}
