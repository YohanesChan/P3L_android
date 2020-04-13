package com.example.kouvemobile.Response;


import com.example.kouvemobile.Model.Ukuran;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showUkuran {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Ukuran> result;

    public showUkuran() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Ukuran> getResult() {
        return result;
    }

    public void setResult(List<Ukuran> result) {
        this.result = result;
    }
}
