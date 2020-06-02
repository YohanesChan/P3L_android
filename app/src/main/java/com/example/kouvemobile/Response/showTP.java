package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.DTProduk;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showTP {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<DTProduk> result;

    public showTP() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DTProduk> getResult() {
        return result;
    }

    public void setResult(List<DTProduk> result) {
        this.result = result;
    }
}
