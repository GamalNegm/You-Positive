package com.example.prog.bloodshare.webservices;

import com.example.prog.bloodshare.models.ChatRoom;
import com.example.prog.bloodshare.models.ChatRoomsResponse;
import com.example.prog.bloodshare.models.LoginResponse;
import com.example.prog.bloodshare.models.MainResponse;
import com.example.prog.bloodshare.models.Message;
import com.example.prog.bloodshare.models.Request;
import com.example.prog.bloodshare.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *  hendiware on 2016/12 .
 */

public interface API {

    @POST("login-user.php")
    Call<LoginResponse> loginUser(@Body User user);
    @POST("register-user.php")
    Call<MainResponse> registerUser(@Body User user);

    @POST("request.php")
    Call<MainResponse> addRequest(@Body Request request);
    @POST("getAllRequests.php")
    Call<List<Request>> getRequests();
    @POST("add-chat-room.php")
    Call<ChatRoomsResponse> addChatRoom(@Body ChatRoom chatRoom);
    @POST("add-message.php")
    Call<MainResponse> addMessage(@Body Message message);
    @FormUrlEncoded
    @POST("get-messages.php")
    Call<List<Message>> getMessages(@Field("room_id") int roomId);
    @POST("get-chat-room.php")
    Call<ChatRoomsResponse> chatRooms(@Body ChatRoom chatRoom);
    @FormUrlEncoded
    @POST("delete-chat-room.php")
    Call<MainResponse> deleteChatRoom(@Field("id") int roomID);
    @FormUrlEncoded
    @POST("get-all-chat-rooms.php")
    Call<List<ChatRoom>> getAllChatRooms(@Field("user_id") int userId);
}

