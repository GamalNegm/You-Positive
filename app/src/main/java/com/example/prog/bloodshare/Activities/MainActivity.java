package com.example.prog.bloodshare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prog.bloodshare.Fragments.AddRequest;
import com.example.prog.bloodshare.Fragments.NavegationDrawerFragment;
import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.Request;
import com.example.prog.bloodshare.notify.FCMRegistrationService;
import com.example.prog.bloodshare.utils.ItemClickSupport;
import com.example.prog.bloodshare.utils.Request_Recycler;
import com.example.prog.bloodshare.utils.Session;
import com.example.prog.bloodshare.webservices.WebService;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Request_Recycler mRequestRecyclerAdapter;
    @Bind(R.id.RequestList)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    ImageView fab;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;
    private final String TAG = "MainActivity";
    private Call<List<Request>> getRequestsCall;
    private List<Request> requests;
    private  NavegationDrawerFragment navegationDrawerFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //////////////////////////////////////////////////
        startService(new Intent(this,FCMRegistrationService.class));
        Log.e("Token is ", FirebaseInstanceId.getInstance().getToken());
            /**********************************************************/
        navegationDrawerFragment =(NavegationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        navegationDrawerFragment.setUp(drawerLayout , toolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        AddData();
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRequest addRequest = new AddRequest();
                Bundle bundle = new Bundle();
                bundle.putInt("USERID" , Session.getInstance().getUser().id);
                bundle.putString("USERNAME" , Session.getInstance().getUser().username);
                addRequest.setArguments(bundle);
                addRequest.show(getFragmentManager() , addRequest.TAG);
            }
        });*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , NewRequestActivity.class);
                intent.putExtra("USERID" , Session.getInstance().getUser().id);
                intent.putExtra("USERNAME" , Session.getInstance().getUser().username);
                startActivity(intent);
            }
        });


        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            //////read data from server to list detail when clicking on an  item******////
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(MainActivity.this , RequestDetails.class);
                intent.putExtra("UserName" , requests.get(position).user_name);
                intent.putExtra("BloodType" , requests.get(position).bloodtype);
                intent.putExtra("NUMOFUSERS" , requests.get(position).numofuser+"");
                intent.putExtra("Reason" , requests.get(position).reason);
                intent.putExtra("Details" , requests.get(position).details);
                intent.putExtra("ID" , requests.get(position).id);
                intent.putExtra("request_id" , requests.get(position).request_id);
                intent.putExtra("num_donator" , requests.get(position).num_donator);
                Toast.makeText(MainActivity.this,requests.get(position).id+"", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                overridePendingTransition(R.anim.fad_in, R.anim.fad_out);
            }
        });
    }
    public void AddData() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getRequestsCall = WebService.getInstance().getApi().getRequests();
        getRequestsCall.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                requests = response.body();
                mRequestRecyclerAdapter = new Request_Recycler(MainActivity.this, requests);
                mRecyclerView.setAdapter(mRequestRecyclerAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "Error " + t.getLocalizedMessage());
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getRequestsCall.cancel();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else
            super.onBackPressed();
    }
}
