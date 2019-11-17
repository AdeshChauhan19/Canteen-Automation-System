package com.example.canteenautomationsystem.model;

import com.example.canteenautomationsystem.Response.Cart_Payment.CartPayment;
import com.example.canteenautomationsystem.Response.Category_Display.CategoryDisplay;
import com.example.canteenautomationsystem.Response.Feedback.Feedback;
import com.example.canteenautomationsystem.Response.Forgot_Password.ForgotResponse;
import com.example.canteenautomationsystem.Response.Forgot_Password_Otp.ForgotOtp;
import com.example.canteenautomationsystem.Response.ProfileResponse.ProfileResponse;
import com.example.canteenautomationsystem.Response.Registration_Otp.Registration;
import com.example.canteenautomationsystem.Response.Registration_Response.RegisterationResponse;
import com.example.canteenautomationsystem.Response.Remove_Cart.RemoveCart;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {

    String BASE_URL = "http://172.16.88.102/anugrah/index.php/api/";

    @FormUrlEncoded
    @POST("users/login")
    Call<LogInResponse> getData(
            @Field("universityrollno") String universityrollno,
            @Field("password") String password,
            @Field("device_token") String device_token

    );
    @FormUrlEncoded
    @POST("users/forget_password")
    Call<ForgotOtp> forgotpasswordotp(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("users/forget_password")
    Call<ForgotResponse> forgotpassword(
            @Field("username") String username,
            @Field("otp") String otp,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("users/change_password")
    Call<JsonObject> changepassword(
            @Field("user_id") String user_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("home/myorder")
    Call<JsonObject> myorder(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("home/cart")
    Call<CartPayment> cart(
            @Field("user_id") String user_id,
            @Field("cart_id") String cart_id,
            @Field("food_id") String food_id,
            @Field("quantity") String quantity,
            @Field("transaction") String transaction
    );


    @FormUrlEncoded
    @POST("home/food")
    Call<JsonObject> food(
            @Field("user_id") String user_id,
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST("users/registeration")
    Call<Registration> registerationotp(
            @Field("user_id") String user_id,
            @Field ("universityrollno") String universityrollno,
            @Field ("name") String name,
            @Field ("email") String email,
            @Field ("mobile") String mobile,
            @Field ("password") String password,
            @Field ("year_id") String year_id,
            @Field ("device_token") String device_token
    );

    @FormUrlEncoded
    @POST("users/registeration")
    Call<RegisterationResponse> registerationresponse(
            @Field("user_id") String user_id,
            @Field ("universityrollno") String universityrollno,
            @Field ("name") String name,
            @Field ("email") String email,
            @Field ("mobile") String mobile,
            @Field ("password") String password,
            @Field ("year_id") String year_id,
            @Field ("device_token") String device_token,
            @Field ("otp") String otp
    );

    @FormUrlEncoded
    @POST("home/profile")
    Call<ProfileResponse> profile(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("home/category")
    Call<JsonObject> categorydisplay(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("home/remove")
    Call<RemoveCart> removecart(
            @Field("cart_id") String cart_id

    );

    @FormUrlEncoded
    @POST("home/feedback")
    Call<Feedback> feedback(
            @Field("user_id") String user_id,
            @Field("feedback") String feedback

    );
    @FormUrlEncoded
    @POST("home/todayorder")
    Call<JsonObject> todayorder(
            @Field("user_id") String user_id
    );


}
