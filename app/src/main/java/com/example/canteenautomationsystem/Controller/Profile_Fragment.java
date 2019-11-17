package com.example.canteenautomationsystem.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.canteenautomationsystem.R;
import com.example.canteenautomationsystem.Response.ProfileResponse.ProfileResponse;
import com.example.canteenautomationsystem.model.LoginInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile_Fragment extends Fragment
{
    private TextView name,uni_roll,email,mobile,year;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.profile_fragment,null);
        name=root.findViewById(R.id.profile_name);
        uni_roll=root.findViewById(R.id.profile_uni_roll);
        email=root.findViewById(R.id.profile_email);
        mobile=root.findViewById(R.id.profile_mobile);
        year=root.findViewById(R.id.profile_year);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(LoginInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        LoginInterface api=retrofit.create(LoginInterface.class);
        Call<ProfileResponse> call=api.profile(getActivity().getIntent().getStringExtra("id"));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.body()!=null)
                {
                    uni_roll.setText(response.body().getData().getUniversityrollno());
                    name.setText(response.body().getData().getName());
                    email.setText(response.body().getData().getEmail());
                    mobile.setText(response.body().getData().getMobile());
                    year.setText(response.body().getData().getYear());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t)
            {
                Snackbar.make(view,"EOOR",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
