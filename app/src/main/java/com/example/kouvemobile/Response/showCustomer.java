package com.example.kouvemobile.Response;

import com.example.kouvemobile.Model.Customer;
import com.example.kouvemobile.Model.Jenis;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class showCustomer {
    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private List<Customer> result;

    public showCustomer() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Customer> getResult() {
        return result;
    }

    public void setResult(List<Customer> result) {
        this.result = result;
    }
}
