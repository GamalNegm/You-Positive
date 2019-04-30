package com.example.prog.bloodshare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.ChatRoom;
import com.example.prog.bloodshare.utils.Session;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hendiware on 2016/12 .
 */

public class ChatRoomsAdapter extends
        RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomHolder> {

    // define list and context
    private List<ChatRoom> chatRooms;
    private Context context;

    // constructor
    public ChatRoomsAdapter(List<ChatRoom> chatRooms, Context context) {
        this.chatRooms = chatRooms;
        this.context = context;
    }

    @Override
    public ChatRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create view holder
        return new ChatRoomHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_room, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatRoomHolder holder, int position) {
        // get room
        final ChatRoom room = chatRooms.get(position);
        // set room data for row
        Toast.makeText(context, ""+room.room_name, Toast.LENGTH_SHORT).show();
        if (room.user_one == Session.getInstance().getUser().id)
        holder.tvTitle.setText(room.room_name);
        else
            holder.tvTitle.setText(room.room_name_two);
        // start chat activity
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ChatActivity.class);
//                intent.putExtra("room_id", room.id);
//                intent.putExtra("room_name", room.room_name);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    class ChatRoomHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        public ChatRoomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
