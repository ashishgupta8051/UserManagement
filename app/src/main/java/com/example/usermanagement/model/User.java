
package com.example.usermanagement.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class User {

    @SerializedName("id")
    private int mId;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("email")
    private String mEmail;

    public User(int mId, String mUsername, String mEmail) {
        this.mId = mId;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
