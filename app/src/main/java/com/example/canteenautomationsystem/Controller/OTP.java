package com.example.canteenautomationsystem.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Forgot_Password_Otp.ForgotOtp;
import com.example.canteenautomationsystem.databinding.ActivityOtpVerificationBinding;
import com.example.canteenautomationsystem.model.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTP extends AppCompatActivity
{
    ActivityOtpVerificationBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_otp_verification);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        binding.showEmailId.setText("Enter the OTP we just sent you on " + getIntent().getStringExtra("email") + " to verify the account");

        binding.resetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Retrofit retrofit=new Retrofit
                        .Builder()
                        .baseUrl(LoginInterface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginInterface api=retrofit.create(LoginInterface.class);
                Call<ForgotOtp> forgotPasswordOtpResponseCall=api.forgotpasswordotp(getIntent().getStringExtra("email"));
                forgotPasswordOtpResponseCall.enqueue(new Callback<ForgotOtp>() {
                    @Override
                    public void onResponse(Call<ForgotOtp> call, Response<ForgotOtp> response) {
                        if (response.body()!=null)
                        {
                            if (response.body().getStatus())
                            {
                                progressDialog.dismiss();
                                Intent intent = new Intent(OTP.this, OTP.class);
                                intent.putExtra("otp", response.body().getData().getOtp().toString().trim());
                                intent.putExtra("email",getIntent().getStringExtra("email"));
                                intent.putExtra("type", "1");
//                        Toast.makeText(Verification.this, response.body().getData().getOtp().toString(), Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
//                                Toast.makeText(OTP.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                                Snackbar.make(getWindow().getDecorView().getRootView(),"Email does not exists",Snackbar.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
//                            Toast.makeText(OTP.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(getWindow().getDecorView().getRootView(),response.body().getMessage(),Snackbar.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotOtp> call, Throwable t)
                    {
                        Log.e("Error", "onFailure" + t.getMessage());
//                        Toast.makeText(OTP.this, "API CALL FAILED", Toast.LENGTH_SHORT).show();
                        Snackbar.make(getWindow().getDecorView().getRootView(),"Can't connect to this network",Snackbar.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
//                Toast.makeText(OTP.this,getIntent().getStringExtra("otp"),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        binding.btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String otp1 = getIntent().getStringExtra("otp");
                binding.resetOtp.getText();
                if (otp1.equals(binding.otp.getText().toString()))
                {
                    Intent intent = new Intent(OTP.this, ResetPassword.class);
                    intent.putExtra("id",getIntent().getStringExtra("id"));
                    intent.putExtra("type", getIntent().getStringExtra("type"));
                    intent.putExtra("email", getIntent().getStringExtra("email"));
                    intent.putExtra("otp", getIntent().getStringExtra("otp"));
                    progressDialog.dismiss();
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
//                    Toast.makeText(OTP.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid OTP",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
