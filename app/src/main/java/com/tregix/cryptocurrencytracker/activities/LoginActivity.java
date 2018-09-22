package com.tregix.cryptocurrencytracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tregix.cryptocurrencytracker.Model.cct.LoginData;
import com.tregix.cryptocurrencytracker.Model.user.User;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;

import retrofit2.Response;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.loginAuth;

public class LoginActivity extends BaseActivity {
    LoginData loginData;
    EditText email;
    EditText password;
    Button btnSignin;
    TextView txtSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setListeners();
    }

    private void setListeners() {
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAcitivty(NavigationDrawerActivity.class);

            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText() == null || email.getText().toString().isEmpty()){
                    email.setError(getString(R.string.required_field));
                    return;
                }

                if(password.getText() == null || password.getText().toString().isEmpty()){
                    password.setError(getString(R.string.required_field));
                    return;
                }

                loginData = new LoginData(email.getText().toString(), password.getText().toString());
                fetchData();
            }
        });
    }

    private void initViews() {
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        btnSignin = (Button) findViewById(R.id.login_signin);
        txtSignup = (TextView) findViewById(R.id.txt_signup);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void fetchData() {
        showProgressDialog("Logging In...");
        RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).login(loginData).enqueue(loginAuth(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTaskRoot()) {
            openAcitivty(NavigationDrawerActivity.class);
        }

    }

    @Override
    public void onLoginAuthentication(Response<User> response) {
        hideProgressDialog();
        if (response != null && response.isSuccessful() && response.body()!= null && response.body().getStatus()) {
            SharedPreferenceUtil.getInstance(LoginActivity.this).storeBooleanValue(SharedPreferenceUtil.ISUSERLOGGEDIN,true);
            SharedPreferenceUtil.getInstance(this).storeUserObj(response.body());
            Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this,"username or passowrd is incorrect!",Toast.LENGTH_LONG).show();
        }
    }
}
