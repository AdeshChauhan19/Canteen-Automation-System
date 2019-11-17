
package com.example.canteenautomationsystem.Response.Forgot_Password_Otp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("otp")
    private Long mOtp;

    public Long getOtp() {
        return mOtp;
    }

    public void setOtp(Long otp) {
        mOtp = otp;
    }

}
