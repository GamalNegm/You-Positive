package com.example.prog.bloodshare.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prog on 18/12/2016.
 */
public class LoginResponse {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("user")
    public User user;

    public static class User {

        @SerializedName("user_name")
        public String user_name;
        @SerializedName("user_email")
        public String user_email;
        @SerializedName("id")
        public int user_id;
    }
}
