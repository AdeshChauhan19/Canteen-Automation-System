
package com.example.canteenautomationsystem.Response.Registration_Response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("created")
    private Long mCreated;
    @SerializedName("device_token")
    private String mDeviceToken;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("otp")
    private String mOtp;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("universityrollno")
    private String mUniversityrollno;
    @SerializedName("user_type")
    private Long mUserType;
    @SerializedName("year_id")
    private String mYearId;

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        mDeviceToken = deviceToken;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOtp() {
        return mOtp;
    }

    public void setOtp(String otp) {
        mOtp = otp;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public String getUniversityrollno() {
        return mUniversityrollno;
    }

    public void setUniversityrollno(String universityrollno) {
        mUniversityrollno = universityrollno;
    }

    public Long getUserType() {
        return mUserType;
    }

    public void setUserType(Long userType) {
        mUserType = userType;
    }

    public String getYearId() {
        return mYearId;
    }

    public void setYearId(String yearId) {
        mYearId = yearId;
    }

}
