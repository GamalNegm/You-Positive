package com.example.prog.bloodshare.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prog.bloodshare.R;
import com.example.prog.bloodshare.models.LoginResponse;
import com.example.prog.bloodshare.models.User;
import com.example.prog.bloodshare.utils.Session;
import com.example.prog.bloodshare.webservices.WebService;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null){
            _emailText.setText(getIntent().getExtras().getString("email"));
            _passwordText.setText(getIntent().getExtras().getString("password"));
        }
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                //finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        if (validate()) {
            final User user = new User();
            user.email = _emailText.getText().toString();
            user.password = _passwordText.getText().toString();
            WebService.getInstance().getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().status == 2) {
                        Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();

                    } else if (response.body().status == 1) {
                        Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        user.username = response.body().user.user_name;
                        user.email = response.body().user.user_email;
                        user.id = response.body().user.user_id;
                        Session.getInstance().loginUser(user);
                        Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMain);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, response.body().message + "123456", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    // print error message in logcat
                    Toast.makeText(LoginActivity.this , t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();

                }
            });


        }
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }
}
