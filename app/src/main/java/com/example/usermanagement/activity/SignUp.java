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
import com.example.usermanagement.response.RegisterResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private TextView sign_in;
    private EditText name,email,password,mobile,address;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("SignUp");

        setupUI();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (userName.isEmpty()){
            name.requestFocus();
            name.setError("Please enter name");
        }else if (userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please enter email");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Enter valid email");
        }else if (userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please enter password");
        }else {
            registerUser(userName,userEmail,userPassword);
        }
    }

    private void registerUser(String userName, String userEmail, String userPassword) {
        Call<RegisterResponse> call = APIClient
                .getApiClient()
                .getDataInterface()
                .register(userName,userEmail,userPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()){
                    Intent intent = new Intent(SignUp.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(SignUp.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(SignUp.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupUI(){
        sign_in = (TextView)findViewById(R.id.user_sign_in);

        name = (EditText)findViewById(R.id.user_name);
        email = (EditText)findViewById(R.id.user_email);
        password = (EditText)findViewById(R.id.user_password);

        register = (Button)findViewById(R.id.user_registration_bt);

        //profileImage = (ImageView) findViewById(R.id.r_imageView);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}