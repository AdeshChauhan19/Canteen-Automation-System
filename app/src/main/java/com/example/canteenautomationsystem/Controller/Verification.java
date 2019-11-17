package com.example.canteenautomationsystem.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Forgot_Password_Otp.ForgotOtp;
import com.example.canteenautomationsystem.model.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Verification extends AppCompatActivity
{
    private EditText user;
    private Button verification;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        verification=findViewById(R.id.btn_veri_submit);
        user=findViewById(R.id.verification_email);
        loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Loading");
        loadingBar.setMessage("Please Wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadingBar.show();
                String username=user.getText().toString().trim();
                if (!username.isEmpty())
                {
                    username_validation(username);
                }
                else
                {
                    loadingBar.dismiss();
//                    Toast.makeText(Verification.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Enter Email",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void username_validation(String username)
    {
        if (!username.contains("@") || !username.contains(".") || username.length()<10)
        {
            loadingBar.dismiss();
//            Toast.makeText(Verification.this,"Enter Valid Email",Toast.LENGTH_SHORT).show();
            Snackbar.make(getWindow().getDecorView().getRootView(),"Enter Valid Email",Snackbar.LENGTH_LONG).show();
        }
        else
        {
            veri(username);
        }
    }

    private void veri(final String username)
    {
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(LoginInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface api=retrofit.create(LoginInterface.class);
        Call<ForgotOtp> forgotPasswordOtpResponseCall=api.forgotpasswordotp(username);
        forgotPasswordOtpResponseCall.enqueue(new Callback<ForgotOtp>() {
            @Override
            public void onResponse(Call<ForgotOtp> call, Response<ForgotOtp> response) {
                if (response.body()!=null)
                {
                    if (response.body().getStatus())
                    {
                        loadingBar.dismiss();
                        Intent intent = new Intent(Verification.this, OTP.class);
                        intent.putExtra("otp", response.body().getData().getOtp().toString());
                        intent.putExtra("email", user.getText().toString());
                        intent.putExtra("type", "1");
//                        Toast.makeText(Verification.this, response.body().getData().getOtp().toString(), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        loadingBar.dismiss();
//                            Toast.makeText(Verification.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                        Snackbar.make(getWindow().getDecorView().getRootView(),"Email id does not exists",Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
//                    Toast.makeText(Verification.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Snackbar.make(getWindow().getDecorView().getRootView(),response.body().getMessage(),Snackbar.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ForgotOtp> call, Throwable t)
            {
                Log.e("Error", "onFailure" + t.getMessage());
//                Toast.makeText(Verification.this, "API CALL FAILED", Toast.LENGTH_SHORT).show();
                Snackbar.make(getWindow().getDecorView().getRootView(),"Can't Connect to Network",Snackbar.LENGTH_LONG).show();
                loadingBar.dismiss();
            }
        });
    }
}