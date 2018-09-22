package com.tregix.cryptocurrencytracker.Model.singup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Confiz123 on 4/7/2018.
 */

public class Errors {

    @SerializedName("existing_user_login")
    @Expose
    private List<String> existingUserLogin = null;

    public List<String> getExistingUserLogin() {
        return existingUserLogin;
    }

    public void setExistingUserLogin(List<String> existingUserLogin) {
        this.existingUserLogin = existingUserLogin;
    }

}
