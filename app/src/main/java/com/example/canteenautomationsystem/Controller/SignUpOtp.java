package com.example.canteenautomationsystem.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Registration_Otp.Registration;
import com.example.canteenautomationsystem.Response.Registration_Response.RegisterationResponse;
import com.example.canteenautomationsystem.databinding.ActivitySignUpOtpBinding;
import com.example.canteenautomationsystem.model.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpOtp extends AppCompatActivity
{
    ActivitySignUpOtpBinding binding;
    private ProgressDialog progressDialog;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otp);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up_otp);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        //Toast.makeText(getApplicationContext(),getIntent().getStringExtra("otp"),Toast.LENGTH_SHORT).show();
        binding.resetRegistrationOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Retrofit retrofit = new Retrofit
                        .Builder()
                        .baseUrl(LoginInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginInterface api = retrofit.create(LoginInterface.class);
                Call<Registration> call=api.registerationotp(getIntent().getStringExtra("user_id"),getIntent().getStringExtra("university_roll"),getIntent().getStringExtra("name"),getIntent().getStringExtra("email"),getIntent().getStringExtra("mobile"),getIntent().getStringExtra("password"),getIntent().getStringExtra("year_id"),getIntent().getStringExtra("device_token"));
                call.enqueue(new Callback<Registration>() {
                    @Override
                    public void onResponse(Call<Registration> call, Response<Registration> response)
                    {
                        if (response.body()!=null)
                        {
                            if (response.body().getStatus())
                            {
                                progressDialog.dismiss();
                                Intent intent=new Intent(SignUpOtp.this,SignUpOtp.class);
                                intent.putExtra("user_id",getIntent().getStringExtra("user_id"));
                                intent.putExtra("universityrollno",getIntent().getStringExtra("universityrollno"));
                                intent.putExtra("name",getIntent().getStringExtra("name"));
                                intent.putExtra("email",getIntent().getStringExtra("email"));
                                intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                                intent.putExtra("password",getIntent().getStringExtra("password"));
                                intent.putExtra("year_id",getIntent().getStringExtra("year_id"));
                                intent.putExtra("device_token",getIntent().getStringExtra("device_token"));
                                intent.putExtra("check","2");
                                intent.putExtra("otp",response.body().getData().getOtp());
                                startActivity(intent);
                                hideSoftKeyBoard();
                                Snackbar.make(getWindow().getDecorView(),"Waiting for OTP",Snackbar.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Registration> call, Throwable t)
                    {
                        hideSoftKeyBoard();
                        Snackbar.make(getWindow().getDecorView(),"Network Connection Failed",Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.registrationOTPbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String otp1 = getIntent().getStringExtra("otp");
                binding.resetRegistrationOtp.getText();
                if (otp1.equals(binding.registrationOtp.getText().toString().trim()))
                {
                    Retrofit retrofit=new Retrofit
                            .Builder()
                            .baseUrl(LoginInterface.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    LoginInterface api=retrofit.create(LoginInterface.class);
                    Call<RegisterationResponse> call=api.registerationresponse(getIntent().getStringExtra("user_id"),getIntent().getStringExtra("university_roll"),getIntent().getStringExtra("name"),getIntent().getStringExtra("email"),getIntent().getStringExtra("mobile"),getIntent().getStringExtra("password"),getIntent().getStringExtra("year_id"),getIntent().getStringExtra("device_token"),getIntent().getStringExtra("otp"));
                    call.enqueue(new Callback<RegisterationResponse>() {
                        @Override
                        public void onResponse(Call<RegisterationResponse> call, Response<RegisterationResponse> response)
                        {
                            if (response.body()!=null)
                            {
                                if (response.body().getStatus())
                                {
                                    hideSoftKeyBoard();
                                    Snackbar.make(getWindow().getDecorView(),response.body().getMessage(),Snackbar.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpOtp.this,SignUp.class));
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<RegisterationResponse> call, Throwable t)
                        {
                            hideSoftKeyBoard();
                            Snackbar.make(getWindow().getDecorView(),"Network Connection Failed",Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    progressDialog.dismiss();
                    hideSoftKeyBoard();
                    Snackbar.make(getWindow().getDecorView(),"Invalid OTP",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
