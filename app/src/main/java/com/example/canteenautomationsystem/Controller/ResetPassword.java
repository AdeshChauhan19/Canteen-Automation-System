package com.example.canteenautomationsystem.Controller;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Forgot_Password.ForgotResponse;
import com.example.canteenautomationsystem.model.LoginInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPassword extends AppCompatActivity {

    private EditText password,password1;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        password=findViewById(R.id.action_change_password);
        password1=findViewById(R.id.action_change_password1);
        button=findViewById(R.id.change_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(password1.getText().toString()))
                {
                    password_validation(password.getText().toString(),password1.getText().toString());
                }
                else
                {
                    Snackbar.make(getWindow().getDecorView(),"Password does not same",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void password_validation(String password, String password1)
    {
        if (password.equals(password1))
        {
            if (password.length()<8 && password1.length()<8)
            {
                //loadingBar.dismiss();
//                Toast.makeText(ChangePassword.this,"Password must be greater than 8 digit",Toast.LENGTH_SHORT).show();
                Snackbar.make(getWindow().getDecorView().getRootView(),"Password must be greater than 8 digits",Snackbar.LENGTH_LONG).show();
            }
            else
            {
                if (getIntent().getStringExtra("type").equals("1"))
                {
                    forgot_password(password);
                }
                else
                {
                    if (getIntent().getStringExtra("type").equals("2"))
                    {
                        change_password(getIntent().getStringExtra("id"),password);
                    }
                }
            }
        }
        else
        {
//            loadingBar.dismiss();
//            Toast.makeText(ChangePassword.this,"Enter your password again",Toast.LENGTH_SHORT).show();
            Snackbar.make(getWindow().getDecorView().getRootView(),"Enter your password again",Snackbar.LENGTH_LONG).show();
        }
    }

    private void forgot_password(final String pass1)
    {
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(LoginInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface api=retrofit.create(LoginInterface.class);
        Call<ForgotResponse> responseCall=api.forgotpassword(getIntent().getStringExtra("email")
                ,getIntent().getStringExtra("otp"),pass1);
        responseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
//                loadingBar.dismiss();
                if (response.body() != null) {
                    if (response.body().getStatus())
                    {
                        Toast.makeText(ResetPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Response", "onResponse: " + response.body().toString());
                        Intent intent=new Intent(ResetPassword.this,SignInActivity.class);
                        Toast.makeText(ResetPassword.this,"Login with new Password",Toast.LENGTH_SHORT).show();
                        //Bug here invoking null object method
//                        new SignInActivity().changePasswordLogin(response.body().getData().getEmail(), password);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t)
            {
//                loadingBar.dismiss();
//                Toast.makeText(ChangePassword.this,"API CALL FAILED",Toast.LENGTH_SHORT).show();
                Snackbar.make(getWindow().getDecorView().getRootView(),"Can't Connect to Network",Snackbar.LENGTH_LONG).show();
            }

        });
    }

    private void change_password(String user_id, String pwd)
    {
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(LoginInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface API=retrofit.create(LoginInterface.class);
        Call<JsonObject> call=API.changepassword(user_id,pwd);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                loadingBar.dismiss();
                if (response.body() != null) {
                    JsonObject jsonObject = response.body();
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//            Toast.makeText(ChangePassword.this, jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
                    Snackbar.make(getWindow().getDecorView().getRootView(),jsonObject1.optString("message"),Snackbar.LENGTH_LONG).show();
                    Intent intent=new Intent(ResetPassword.this, MainActivity.class);
                    intent.putExtra("id",getIntent().getStringExtra("id"));
//            Toast.makeText(ChangePassword.this,getIntent().getStringExtra("email"),Toast.LENGTH_SHORT).show();
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t)
            {
                Log.e("Error", t.getMessage());
//                loadingBar.dismiss();
            }
        });
    }
}
