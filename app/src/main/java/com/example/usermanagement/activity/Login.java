package com.example.usermanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.apiclient.APIClient;
import com.example.usermanagement.fragment.DashBoard;
import com.example.usermanagement.response.LoginResponse;
import com.example.usermanagement.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText emailEditText,passwordEditText;
    private Button loginButton;
    private TextView signUpTextView,passwordReset;
    private String email,password;
    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI();
        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
                finish();
            }
        });
    }

    private void setupUI() {
        emailEditText = findViewById(R.id.user_login_email);
        passwordEditText = findViewById(R.id.user_login_password);

        loginButton = findViewById(R.id.User_login_bt);

        signUpTextView = findViewById(R.id.user_sign_up);
        passwordReset = findViewById(R.id.user_forgot_password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPrefManager.isLoggedIn()){
            Intent intent = new Intent(Login.this, BottomNavigation.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void checkValidation() {
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        if (email.isEmpty()){
            emailEditText.requestFocus();
            emailEditText.setError("Please enter email");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.requestFocus();
            emailEditText.setError("Please enter valid email");
        }else if (password.isEmpty()){
            passwordEditText.requestFocus();
            passwordEditText.setError("Please enter valid password");
        }else {
            login(email,password);
        }
    }

    private void login(String email, String password) {
        Call<LoginResponse> call = APIClient.getApiClient()
                .getDataInterface()
                .login(email,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful()){
                    if (loginResponse.getError().equals("200")){
                        sharedPrefManager.saveUser(loginResponse.getUser());
                        Intent intent = new Intent(Login.this, BottomNavigation.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}