package com.example.usermanagement.response;

import com.example.usermanagement.model.User;
import com.google.gson.annotations.SerializedName;

public class UpdateUserResponse {

    @SerializedName("error")
    private String mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("user")
    private User mUser;

    public String getmError() {
        return mError;
    }

    public void setmError(String mError) {
        this.mError = mError;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
