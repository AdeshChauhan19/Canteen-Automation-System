package com.example.canteenautomationsystem.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.Registration_Response.RegisterationResponse;
import com.example.canteenautomationsystem.model.LoginInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    private String year_id, member_id;
    private ProgressDialog loadingBar;
    Spinner spinner;
    EditText name, university_roll, email, mobile, password, conf_password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        mobile = findViewById(R.id.signup_mobile);
        university_roll = findViewById(R.id.signup_roll);
        password = findViewById(R.id.signup_pass);
        conf_password = findViewById(R.id.signup_conf_pass);
        register = findViewById(R.id.registration_btn);
        //////////////   Year  ////////////////
        spinner = findViewById(R.id.signup_year);
        final List<Year> yearList = new ArrayList<>();
        Year year5 = new Year("Select", "");
        yearList.add(year5);
        Year year = new Year("First Year", "1");
        yearList.add(year);
        Year year1 = new Year("Second Year", "2");
        yearList.add(year1);
        Year year2 = new Year("Third Year", "3");
        yearList.add(year2);
        Year year3 = new Year("Forth Year", "4");
        yearList.add(year3);
        ArrayAdapter<Year> adapter1 = new ArrayAdapter<Year>(this, R.layout.spinner_text, yearList);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year_id = yearList.get(i).getId();
//                Toast.makeText(Registration.this,""+year_id,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Please Wait...");
        loadingBar.setCancelable(false);
        loadingBar.setTitle("Loading");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.show();
                validation();
            }
        });
    }


    private void validation() {
        if (name.getText().toString().trim().isEmpty()) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter Name", Snackbar.LENGTH_SHORT).show();
        } else if (university_roll.getText().toString().trim().isEmpty()) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter University Roll No", Snackbar.LENGTH_SHORT).show();
        } else if (!email.getText().toString().contains("@gla.ac.in") || !email.getText().toString().contains(".")) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter Official GLA Email", Snackbar.LENGTH_SHORT).show();
        } else if (mobile.getText().toString().trim().isEmpty()) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter Mobile Number", Snackbar.LENGTH_SHORT).show();
        } else if (year_id.isEmpty()) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Select Your Year", Snackbar.LENGTH_SHORT).show();
        } else if (password.getText().toString().trim().isEmpty()) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter Password", Snackbar.LENGTH_SHORT).show();
        } else if (conf_password.getText().toString().trim().isEmpty()) {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter Confirm Password", Snackbar.LENGTH_SHORT).show();
        } else if (password.getText().toString().trim().equals(conf_password.getText().toString().trim())) {
            if (password.getText().toString().trim().length() > 8) {
                sumit_details();
            } else {
                loadingBar.dismiss();
                hideSoftKeyBoard();
                Snackbar.make(getWindow().getDecorView(), "Password must be greater than 8 Digit", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            loadingBar.dismiss();
            hideSoftKeyBoard();
            Snackbar.make(getWindow().getDecorView(), "Enter Same Password", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void sumit_details()
    {
//        HashMap<String, String> parameter = new HashMap<>();
//        parameter.put("user_id", "0");
//        parameter.put("name", name.getText().toString());
//        parameter.put("email", email.getText().toString());
//        parameter.put("mobile", mobile.getText().toString());
//        parameter.put("password", password.getText().toString());
//        parameter.put("year_id", year_id.toString().trim());
//        parameter.put("member_id", member_id.toString().trim());
//        parameter.put("course_id", course_id.toString().trim());
//        parameter.put("university_roll", university_roll.getText().toString());
//        parameter.put("branch_id", branch_id.toString().trim());
//        parameter.put("device_token", "jjjjjj");
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(LoginInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginInterface api = retrofit.create(LoginInterface.class);
        Call<RegisterationResponse> responseCall = api.registerationresponse("1",university_roll.getText().toString(),name.getText().toString(),email.getText().toString().toLowerCase(),mobile.getText().toString(),password.getText().toString(),year_id.toString(),member_id.toString(),"jjj");
        responseCall.enqueue(new Callback<RegisterationResponse>() {
            @Override
            public void onResponse(Call<RegisterationResponse> call, Response<RegisterationResponse> response) {
                loadingBar.dismiss();
                if (response.body() != null) {
                    if (response.body().getStatus())
                    {
                        hideSoftKeyBoard();
                        Snackbar.make(getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this,SignUpOtp.class);
                        intent.putExtra("user_id", "1");
                        intent.putExtra("name", name.getText().toString().trim());
                        intent.putExtra("email", email.getText().toString().toLowerCase().trim());
                        intent.putExtra("password", password.getText().toString().trim());
                        intent.putExtra("university_roll", university_roll.getText().toString().trim());
                        intent.putExtra("year_id", year_id.toString().trim());
                        intent.putExtra("mobile", mobile.getText().toString().trim());
                        intent.putExtra("device_token", "jjjjj");
                        intent.putExtra("otp",response.body().getData().getOtp().toString().trim());
//                        Toast.makeText(Registration.this,response.body().getData().getOtp().toString(),Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        loadingBar.dismiss();
                        hideSoftKeyBoard();
                        Snackbar.make(getWindow().getDecorView(),response.body().getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterationResponse> call, Throwable t) {
                Log.e("Error", "onFailure" + t.getMessage());
                hideSoftKeyBoard();
                Snackbar.make(getWindow().getDecorView(), "Network Connection Failed", Snackbar.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}