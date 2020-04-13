package com.example.kouvemobile.Response;


import com.example.kouvemobile.Model.Produk;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showProduk {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Produk> result;

    public showProduk() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produk> getResult() {
        return result;
    }

    public void setResult(List<Produk> result) {
        this.result = result;
    }
}
