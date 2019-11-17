package com.example.canteenautomationsystem.Controller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.model.LogInResponse;
import com.example.canteenautomationsystem.model.LoginInterface;
import com.example.canteenautomationsystem.model.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity
{

    private TextView forgot;
    private ProgressDialog progressDialog;
    private EditText universityrollno, password;
    private Button signIn;
    private TextView signUp;
    private SharedPrefManager sharedPrefManager;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        forgot = findViewById(R.id.forgot_password);
        universityrollno = findViewById(R.id.uni_roll);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.btn_button);
        signUp= findViewById(R.id.signup);
        checkBox=findViewById(R.id.remember);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        sharedPrefManager = new SharedPrefManager(this);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(SignInActivity.this, Verification.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SignInActivity.this,SignUp.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String logIn = universityrollno.getText().toString().trim();
                String pass = password.getText().toString().trim();
                validation(logIn, pass);


            }
        });

    }
    private void validation(String univ_roll, String pass) {
        if (univ_roll.length() <9 || pass.length() < 4) {
            Snackbar.make(getWindow().getDecorView().getRootView(),"Enter valid univ_roll/password",Snackbar.LENGTH_LONG).show();
            progressDialog.dismiss();
        } else {
            login(univ_roll, pass);
        }
    }
    private void login(String universityrollno, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<LogInResponse> response = (Call<LogInResponse>) api.getData(universityrollno, password,"device_token");
        response.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response)
            {
                if (response.body() != null) {
                    LogInResponse data = response.body();
                    if (data.getStatus())
                    {
                        //sharedPrefManager.saveData("LoginResponse", response.body().getData());
                        if (checkBox.isChecked())
                        {
                            sharedPrefManager.saveData("LoginResponse",response.body().getData());
                        }
                        sharedPrefManager.putString(getBaseContext(),"id",response.body().getData().getId());
                        progressDialog.dismiss();
                        if (data.getData().getUserType().equals("2"))
                        {

                            Intent intent = new Intent(SignInActivity.this, Dashboard.class);
                            intent.putExtra("data",data.getData().toString().trim());
                            intent.putExtra("id",data.getData().getId());
                            intent.putExtra("name",data.getData().getName());
                            intent.putExtra("email",data.getData().getEmail());
                            intent.putExtra("universityrollno",data.getData().getUniversityrollno());
                            if (checkBox.isChecked()){
                                sharedPrefManager.putString(SignInActivity.this,"id",response.body().getData().getId().toString().trim());
                                sharedPrefManager.putString(SignInActivity.this,"name",response.body().getData().getId().toString().trim());
                                sharedPrefManager.putString(SignInActivity.this,"email",response.body().getData().getId().toString().trim());
                                sharedPrefManager.putString(SignInActivity.this,"password",response.body().getData().getId().toString().trim());
                                sharedPrefManager.putString(SignInActivity.this,"universityrollno",response.body().getData().getUniversityrollno().toString().trim());
                            }
                            progressDialog.dismiss();
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Snackbar.make(getWindow().getDecorView().getRootView(),"User Does not Exists",Snackbar.LENGTH_LONG).show();

                        }
                        finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Snackbar.make(getWindow().getDecorView().getRootView(),"User does not Exists",Snackbar.LENGTH_LONG).show();
                    }
                } else {
//                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Snackbar.make(getWindow().getDecorView().getRootView(),response.body().getMessage(),Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }


            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t)
            {
                Log.e("Error", "onFailure" + t.getMessage());
//                Toast.makeText(Login.this, "API CALL FAILED", Toast.LENGTH_SHORT).show();
                Snackbar.make(getWindow().getDecorView().getRootView(),"Can't Connect to Network",Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}

