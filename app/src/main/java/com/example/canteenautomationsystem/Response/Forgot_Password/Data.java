
package com.example.canteenautomationsystem.Response.Forgot_Password;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("device_token")
    private String mDeviceToken;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private String mId;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("universityrollno")
    private String mUniversityrollno;
    @SerializedName("user_type")
    private String mUserType;
    @SerializedName("year_id")
    private String mYearId;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
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

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUniversityrollno() {
        return mUniversityrollno;
    }

    public void setUniversityrollno(String universityrollno) {
        mUniversityrollno = universityrollno;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

    public String getYearId() {
        return mYearId;
    }

    public void setYearId(String yearId) {
        mYearId = yearId;
    }

}
