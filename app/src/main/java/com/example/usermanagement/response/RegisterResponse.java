
package com.example.usermanagement.response;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class RegisterResponse {

    @SerializedName("error")
    private String mError;
    @SerializedName("message")
    private String mMessage;

    public RegisterResponse(String mError, String mMessage) {
        this.mError = mError;
        this.mMessage = mMessage;
    }

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

}
