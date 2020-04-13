package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.ProdukShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class tampilProduk {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<ProdukShow> result;

    public tampilProduk() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProdukShow> getResult() {
        return result;
    }

    public void setResult(List<ProdukShow> result) {
        this.result = result;
    }
}
