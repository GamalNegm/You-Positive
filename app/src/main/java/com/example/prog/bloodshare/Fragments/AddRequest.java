package com.example.prog.bloodshare.Fragments;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.prog.bloodshare.Activities.MainActivity;
import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.MainResponse;
import com.example.prog.bloodshare.models.Request;
//import com.example.prog.bloodshare.notify.FCMRegistrationService;
import com.example.prog.bloodshare.webservices.WebService;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRequest extends DialogFragment {
    @Bind(R.id.blood_Type)
    EditText Blood_Type;
    @Bind(R.id.Location)
    EditText Location;
    @Bind(R.id.Num)
    EditText Num;
    @Bind(R.id.Reason)
    EditText Reason;
    @Bind(R.id.details)
    EditText Details;
    @Bind(R.id.add)
    Button Add;
    @Bind(R.id.ib_close)
    ImageButton close;
    private int id;
    private String userName;
    public static final String TAG = "AddRequest";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_request, container, false);
        ButterKnife.bind(this, view);
        id = getArguments().getInt("USERID");
        userName = getArguments().getString("USERNAME");
        return view;
    }

    @OnClick(R.id.add)             /************************************************/
    public void onClick() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding Request...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
        //////////////////////////////////////////////
        //startService(new Intent(this,FCMRegistrationService.class));
        Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
       Log.e("Token is ", FirebaseInstanceId.getInstance().getToken());


    }
    public void onSignupSuccess() {
        Request request = new Request();
        request.bloodtype = Blood_Type.getText().toString();
        request.location = Location.getText().toString();
        request.numofuser = Integer.parseInt(Num.getText().toString());
        request.reason = Reason.getText().toString();
        request.details = Details.getText().toString();
        request.id = id;
        request.user_name = userName;

        WebService.getInstance().getApi().addRequest(request).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.body().status == 2) {
                    Toast.makeText(getActivity(), response.body().message + "Status Number is " + response.body().status, Toast.LENGTH_SHORT).show();
                } else if (response.body().status == 1) {
                    Toast.makeText(getActivity(), response.body().message + "Status Number is " + response.body().status, Toast.LENGTH_SHORT).show();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.AddData();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), response.body().message + "Status Number is " + response.body().status, Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
