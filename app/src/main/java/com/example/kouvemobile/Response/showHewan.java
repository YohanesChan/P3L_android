package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.Hewan;
import com.example.kouvemobile.Model.Jenis;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showHewan {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Hewan> result;

    public showHewan() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Hewan> getResult() {
        return result;
    }

    public void setResult(List<Hewan> result) {
        this.result = result;
    }
}
