
package com.example.usermanagement.response;

import com.example.usermanagement.model.User;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginResponse {

    @SerializedName("error")
    private String mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("user")
    private User mUser;

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

}
