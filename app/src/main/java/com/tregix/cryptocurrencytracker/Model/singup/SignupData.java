
package com.tregix.cryptocurrencytracker.Model.singup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupData {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean type) {
        this.status = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
