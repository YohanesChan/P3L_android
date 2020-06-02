package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.TransaksiL;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showTransaksiL {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<TransaksiL> result;

    public showTransaksiL() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransaksiL> getResult() {
        return result;
    }

    public void setResult(List<TransaksiL> result) {
        this.result = result;
    }
}
