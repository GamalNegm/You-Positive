package com.example.prog.bloodshare.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.Request;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Request_Recycler extends RecyclerView.Adapter<Request_Recycler.ViewHolder> {

    Context mContext;
    List<Request> Requests;

    public Request_Recycler(Context mContext, List<Request> Requests) {
        this.mContext = mContext;
        this.Requests = Requests;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //final Request request = Requests.get(0);
        holder.mCountOfCompleted.setText(Requests.get(position).num_donator+""+" / "+Requests.get(position).numofuser);
        holder.mBloodType.setText(Requests.get(position).bloodtype);
        holder.mDate.setText(Requests.get(position).getTime());
        holder.mLocation.setText(Requests.get(position).location);
        holder.mName.setText(Requests.get(position).user_name);
    }

    @Override
    public int getItemCount() {
       return Requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.Name)
        TextView mName;
        @Bind(R.id.Location)
        TextView mLocation;
        @Bind(R.id.Date)
        TextView mDate;
        @Bind(R.id.BloodType)
        TextView mBloodType;
        @Bind(R.id.CountOfCompleted)
        TextView mCountOfCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
