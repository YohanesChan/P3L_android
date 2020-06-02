package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.DTLayanan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showTL {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<DTLayanan> result;

    public showTL() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DTLayanan> getResult() {
        return result;
    }

    public void setResult(List<DTLayanan> result) {
        this.result = result;
    }
}
