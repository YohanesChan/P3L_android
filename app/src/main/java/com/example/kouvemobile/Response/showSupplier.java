package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.Supplier;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showSupplier {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Supplier> result;

    public showSupplier() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Supplier> getResult() {
        return result;
    }

    public void setResult(List<Supplier> result) {
        this.result = result;
    }
}
