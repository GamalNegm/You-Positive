package com.example.prog.bloodshare.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hendiware on 2016/12 .
 */
public class ChatRoom {

    @SerializedName("id")
    public int id;
    @SerializedName("room_name")
    public String room_name;
    @SerializedName("room_name_two")
    public String room_name_two;
    @SerializedName("user_one")
    public int user_one;
    @SerializedName("user_two")
    public int user_two;
}

