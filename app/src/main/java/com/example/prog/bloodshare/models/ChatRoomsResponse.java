package com.example.prog.bloodshare.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prog on 23/12/2016.
 */
public class ChatRoomsResponse {
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("user")
    public ChatRoom chatRoom;

    public static class ChatRoom {

        @SerializedName("id")
        public int id;
        @SerializedName("room_name")
        public String room_name;
        @SerializedName("room_name_two")
        public String room_name_two;
        @SerializedName("user_one")
        public String user_one;
        @SerializedName("user_two")
        public String user_two;
    }
}
