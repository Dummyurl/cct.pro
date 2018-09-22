package com.tregix.cryptocurrencytracker.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.Model.cct.SignupDataParams;
import com.tregix.cryptocurrencytracker.Model.singup.SignupData;

import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;

import retrofit2.Response;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.registerDataCallback;

public class SignUpActivity extends BaseActivity {
    private SignupDataParams registerData;
    private EditText email,phnNumber,password;
    private Button btnSignup;
    private TextView txtSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        setListeners();


    }

    private void setListeners() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText() == null || email.getText().toString().isEmpty()){
                    email.setError(getString(R.string.required_field));
                    return;
                }

                if(phnNumber.getText() == null || phnNumber.getText().toString().isEmpty()){
                    phnNumber.setError(getString(R.string.required_field));
                    return;
                }

                if(password.getText() == null || password.getText().toString().isEmpty()){
                    password.setError(getString(R.string.required_field));
                    return;
                }

                if(password.getText().toString().length() < 3){
                    password.setError(getString(R.string.long_password));
                }

                registerData=new SignupDataParams(email.getText().toString(),
                        phnNumber.getText().toString(),password.getText().toString());
                registerData();
            }
        });
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openAcitivty(LoginActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTaskRoot()) {
            openAcitivty(NavigationDrawerActivity.class);
        }

    }
    private void initViews() {
        email=(EditText)findViewById(R.id.signup_email);
        phnNumber=(EditText)findViewById(R.id.signup_phone);
        password=(EditText)findViewById(R.id.signup_password);
        btnSignup=(Button)findViewById(R.id.signup);
        txtSignin=(TextView)findViewById(R.id.txt_signin);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void registerData() {
        showProgressDialog("Signing Up...");
        RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).registration(registerData).
                enqueue(registerDataCallback(this));
    }

    @Override
    public void onRegistration(Response<SignupData> registerDataResponse) {
        hideProgressDialog();
        if (registerDataResponse!=null && registerDataResponse.isSuccessful()) {
            if (registerDataResponse.body() != null && registerDataResponse.body().getStatus()) {
                showDialogSignedUp("Signup successfull. Please login");
                //Toast.makeText(this, , Toast.LENGTH_LONG).show();

             /*   Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();*/
            }
            else{
                Utils.showError(this,"User already exists!");
            }
        }else{
            Utils.showError(this);
         }
    }

}
