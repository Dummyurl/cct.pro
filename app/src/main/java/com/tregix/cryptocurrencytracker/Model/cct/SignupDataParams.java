package com.tregix.cryptocurrencytracker.Model.cct;

public class SignupDataParams {
    private String name;
    private String email;
    private String password;
    private int role_id = 3;
    private int newsletter = 0;

    public SignupDataParams(String email, String phnNumber, String password) {
        this.name = phnNumber;
        this.email = email;
        this.password = password;
    }
}
