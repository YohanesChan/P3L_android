package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.Jenis;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showJenis {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Jenis> result;

    public showJenis() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Jenis> getResult() {
        return result;
    }

    public void setResult(List<Jenis> result) {
        this.result = result;
    }
}
