package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.Layanan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showLayanan {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Layanan> result;

    public showLayanan() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Layanan> getResult() {
        return result;
    }

    public void setResult(List<Layanan> result) {
        this.result = result;
    }
}
