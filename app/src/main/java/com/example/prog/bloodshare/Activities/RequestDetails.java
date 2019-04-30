package com.example.prog.bloodshare.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.ChatRoom;
import com.example.prog.bloodshare.models.ChatRoomsResponse;
import com.example.prog.bloodshare.models.Request;
import com.example.prog.bloodshare.utils.Session;
import com.example.prog.bloodshare.webservices.WebService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDetails extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.RequestUserProfileName)
    TextView userName;
    @Bind(R.id.UserRequestBloodTypeDetails)
    TextView bloodType;
    @Bind(R.id.UserRequestNumberOfDonatorsDetails)
    TextView numOfUsers;
    @Bind(R.id.UserRequestReasonDetails)
    TextView Reason;
    @Bind(R.id.UserRequestChatDetails)
    ImageView mChatImage;
    @Bind(R.id.UserRequestDonateDetails)
    ImageView donate;
            //=(ImageView)findViewById(R.id.UserRequestDonateDetails);
    private int mUserRequestID;
    private int mRequestID;
    private int mCurrentUser;
    private int mNumberOfdonator;
    private ChatRoom mChatRoom ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNumberOfdonator = getIntent().getExtras().getInt("num_donator");/***************/
        userName.setText(getIntent().getExtras().getString("UserName"));
        bloodType.setText(getIntent().getExtras().getString("BloodType"));
        numOfUsers.setText(mNumberOfdonator+" / "+getIntent().getExtras().getString("NUMOFUSERS")); //donators
        Reason.setText(getIntent().getExtras().getString("Reason") + "\n" + getIntent().getExtras().getString("Details"));
        mUserRequestID = getIntent().getExtras().getInt("ID");
        mRequestID = getIntent().getExtras().getInt("request_id");/***************/
        mCurrentUser = Session.getInstance().getUser().id ;
        mChatRoom = new ChatRoom();
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RequestDetails.this, mRequestID+"", Toast.LENGTH_SHORT).show();
                 String url ="http://ehab.esy.es/test/addOneDonator.php?id="+mRequestID;
                // its your url path ok
                final RequestQueue requestQueue=Volley.newRequestQueue(RequestDetails.this);
                StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET,url ,new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(RequestDetails.this,response, Toast.LENGTH_SHORT).show();
                                requestQueue.stop();
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RequestDetails.this, "2222", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                                requestQueue.stop();
                            }
                        }
                ) {
                    // here is params will add to your url using post method
                   /* @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id","28");
                        //params.put("2ndParamName","valueoF2ndParam");
                        return params;
                    }*/
                };
                requestQueue.add(postRequest);

            }
        });
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.UserRequestChatDetails)
    public void onClick(View view) {
        if(mCurrentUser != mUserRequestID) {
            chechChatRoom();
        }else{
            Intent intent = new Intent(this , MyChatRooms.class);
            startActivity(intent);
        }
    }
    public void chechChatRoom(){
        final ChatRoom chatRoom = new ChatRoom();
        chatRoom.user_one = mCurrentUser;
        chatRoom.user_two = mUserRequestID;
        WebService.getInstance().getApi().chatRooms(chatRoom).enqueue(new Callback<ChatRoomsResponse>() {
            @Override
            public void onResponse(Call<ChatRoomsResponse> call, Response<ChatRoomsResponse> response) {
                if (response.body().status == 1) {
                    Toast.makeText(RequestDetails.this, response.body().message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RequestDetails.this, ChatActivity.class);
                    intent.putExtra("room_name", response.body().chatRoom.room_name);
                    intent.putExtra("room_id", response.body().chatRoom.id);
                    intent.putExtra("room_name_two", response.body().chatRoom.room_name_two);
                    intent.putExtra("CurrentUser" , response.body().chatRoom.user_one);
                    intent.putExtra("RequestUser" , response.body().chatRoom.user_two);
                    startActivity(intent);
                } else {
                    Toast.makeText(RequestDetails.this, response.body().message+"2222", Toast.LENGTH_SHORT).show();
                    AddChatRoom();
                }
            }

            @Override
            public void onFailure(Call<ChatRoomsResponse> call, Throwable t) {
                Toast.makeText(RequestDetails.this , t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void AddChatRoom(){
        final ChatRoom chatRoom = new ChatRoom();
        chatRoom.room_name = userName.getText().toString();
        chatRoom.user_one = mCurrentUser;
        chatRoom.user_two = mUserRequestID;
        chatRoom.room_name_two = Session.getInstance().getUser().username;
        WebService.getInstance().getApi().addChatRoom(chatRoom).enqueue(new Callback<ChatRoomsResponse>() {
            @Override
            public void onResponse(Call<ChatRoomsResponse> call, Response<ChatRoomsResponse> response) {
                if (response.body().status == 2) {
                    Toast.makeText(RequestDetails.this, response.body().message, Toast.LENGTH_SHORT).show();
                } else if (response.body().status == 1) {
                    Toast.makeText(RequestDetails.this, response.body().message, Toast.LENGTH_SHORT).show();
                    chechChatRoom();
                } else {
                    Toast.makeText(RequestDetails.this, response.body().message+"33333", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatRoomsResponse> call, Throwable t) {
                Toast.makeText(RequestDetails.this , t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }


}
