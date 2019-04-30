package com.example.prog.bloodshare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.adapters.ChatRoomsAdapter;
import com.example.prog.bloodshare.models.ChatRoom;
import com.example.prog.bloodshare.utils.ItemClickSupport;
import com.example.prog.bloodshare.utils.Session;
import com.example.prog.bloodshare.webservices.WebService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChatRooms extends AppCompatActivity {
    private List<ChatRoom> ChatRooms;
    private ChatRoomsAdapter adapter ;
    @Bind(R.id.recycler_chat_rooms)
    RecyclerView recyclerChat ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_rooms);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(MyChatRooms.this, ""+ Session.getInstance().getUser().id, Toast.LENGTH_SHORT).show();
        getChatRooms(Session.getInstance().getUser().id);
    }
    private void getChatRooms(int userId) {
        final ProgressDialog progressDialog = new ProgressDialog(MyChatRooms.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        WebService.getInstance().getApi().getAllChatRooms(userId).enqueue(new Callback<List<ChatRoom>>() {
            @Override
            public void onResponse(Call<List<ChatRoom>> call, Response<List<ChatRoom>> response) {
                ChatRooms = response.body();
//                Toast.makeText(MyChatRooms.this, ""+ChatRooms.get(0).id, Toast.LENGTH_SHORT).show();
                adapter = new ChatRoomsAdapter(ChatRooms, MyChatRooms.this);
                recyclerChat.setAdapter(adapter);
                progressDialog.dismiss();
                ItemClickSupport.addTo(recyclerChat).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(MyChatRooms.this , ChatActivity.class);
                        intent.putExtra("room_name" , ChatRooms.get(position).room_name);
                        intent.putExtra("room_id" , ChatRooms.get(position).id);
                        intent.putExtra("room_name_two", ChatRooms.get(position).room_name_two);
                        intent.putExtra("CurrentUser" , ChatRooms.get(position).user_one);
                        intent.putExtra("RequestUser" , ChatRooms.get(position).user_two);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.fad_in,R.anim.fad_out);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ChatRoom>> call, Throwable t) {
                Toast.makeText(MyChatRooms.this, "Error:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
