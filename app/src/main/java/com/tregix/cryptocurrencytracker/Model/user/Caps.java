package com.tregix.cryptocurrencytracker.Model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Caps {

    @SerializedName("contributor")
    @Expose
    private Boolean contributor;

    public Boolean getContributor() {
        return contributor;
    }

    public void setContributor(Boolean contributor) {
        this.contributor = contributor;
    }

}
