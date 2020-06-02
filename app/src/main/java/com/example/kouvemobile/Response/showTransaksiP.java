package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.TransaksiP;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showTransaksiP {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<TransaksiP> result;

    public showTransaksiP() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransaksiP> getResult() {
        return result;
    }

    public void setResult(List<TransaksiP> result) {
        this.result = result;
    }
}
