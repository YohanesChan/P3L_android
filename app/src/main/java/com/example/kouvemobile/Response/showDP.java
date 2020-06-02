package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.DPengadaan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showDP {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<DPengadaan> result;

    public showDP() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DPengadaan> getResult() {
        return result;
    }

    public void setResult(List<DPengadaan> result) {
        this.result = result;
    }
}
