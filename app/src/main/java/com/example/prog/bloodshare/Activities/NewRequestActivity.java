package com.example.prog.bloodshare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.MainResponse;
import com.example.prog.bloodshare.models.Request;
import com.example.prog.bloodshare.models.User;
import com.example.prog.bloodshare.webservices.WebService;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRequestActivity extends AppCompatActivity {
    private static final String TAG = "NewRequestActivity";

    @Bind(R.id.new_blood_type) EditText bloodType;
    @Bind(R.id.new_Location) EditText location;
    @Bind(R.id.new_donors_count) EditText donorsCount;
    @Bind(R.id.new_reason) EditText reason;
    @Bind(R.id.new_details) EditText details;
    @Bind(R.id.btn_new_request) Button newRequestButton;

    private int id;
    private String userName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        ButterKnife.bind(this);
        id = getIntent().getExtras().getInt("USERID");
        userName = getIntent().getExtras().getString("USERNAME");

        newRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRequest();
            }
        });

       
    }

    public void addNewRequest() {
        Log.d(TAG, "addNewRequest");

        if (!validate()) {
            onAddNewRequestFailed();
            return;
        }

        newRequestButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(NewRequestActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onAddNewRequestSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onAddNewRequestSuccess() {
        Request request = new Request();
        request.bloodtype = bloodType.getText().toString();
        request.location = location.getText().toString();
        request.numofuser = Integer.parseInt(donorsCount.getText().toString());
        request.reason = reason.getText().toString();
        request.details = details.getText().toString();
        request.id = id;
        request.user_name = userName;
        WebService.getInstance().getApi().addRequest(request).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.body().status == 2) {
                    Toast.makeText(getBaseContext(), response.body().message+"", Toast.LENGTH_SHORT).show();
                } else if (response.body().status == 1) {
                    Toast.makeText(getBaseContext(), response.body().message+"", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NewRequestActivity.this , RequestDetails.class);
                    intent.putExtra("UserName" , userName);
                    intent.putExtra("BloodType" , bloodType.getText().toString());
                    intent.putExtra("NUMOFUSERS" , Integer.parseInt(donorsCount.getText().toString()));
                    intent.putExtra("Reason" , reason.getText().toString());
                    intent.putExtra("Details" , details.getText().toString());
                    intent.putExtra("ID" , id);

                    startActivity(intent);
                    overridePendingTransition(R.anim.fad_in, R.anim.fad_out);

                } else {
                    Toast.makeText(getBaseContext(), response.body().message+"", Toast.LENGTH_SHORT).show();

                }
                newRequestButton.setEnabled(true);
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                newRequestButton.setEnabled(true);
                Log.e(TAG, t.getLocalizedMessage());
                Toast.makeText(getBaseContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onAddNewRequestFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        newRequestButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mBloodType = bloodType.getText().toString();
        String mLocation = location.getText().toString();
        String mDonorsCount = donorsCount.getText().toString();
        String mReason = reason.getText().toString();
        String mDetails = details.getText().toString();


        if (mBloodType.isEmpty()) {
            bloodType.setError("Can't be empty");
            valid = false;
        } else {
            bloodType.setError(null);
        }

        if (mLocation.isEmpty()) {
            location.setError("Can't be empty");
            valid = false;
        } else {
            location.setError(null);
        }


        if (mDonorsCount.isEmpty()) {
            donorsCount.setError("Can't be empty");
            valid = false;
        } else {
            donorsCount.setError(null);
        }

        if (mReason.isEmpty()) {
            reason.setError("Can't be empty");
            valid = false;
        } else {
            reason.setError(null);
        }

        if (mDetails.isEmpty()) {
            details.setError("Can't be empty");
            valid = false;
        } else {
            details.setError(null);
        }

        
        return valid;
    }


}