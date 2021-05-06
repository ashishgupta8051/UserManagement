package com.example.usermanagement.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.activity.Login;
import com.example.usermanagement.apiclient.APIClient;
import com.example.usermanagement.response.DeleteAccountResponse;
import com.example.usermanagement.response.UpdatePasswordResponse;
import com.example.usermanagement.response.UpdateUserResponse;
import com.example.usermanagement.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {
    private EditText usernameEt,emailEt,currentEt,newEt;
    private Button updateUserBtn,updatePass;
    private SharedPrefManager sharedPrefManager;
    private int id;

    public Profile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        getActivity().setTitle("Profile");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefManager = new SharedPrefManager(getActivity());

        id = sharedPrefManager.getUser().getmId();
        String email = sharedPrefManager.getUser().getmEmail();

        usernameEt = view.findViewById(R.id.update_username);
        emailEt = view.findViewById(R.id.update_email);
        updateUserBtn = view.findViewById(R.id.updateProfile);

        currentEt = view.findViewById(R.id.currentPassword);
        newEt = view.findViewById(R.id.newPassword);
        updatePass = view.findViewById(R.id.updatePassword);

        usernameEt.setText(sharedPrefManager.getUser().getmUsername());
        emailEt.setText(sharedPrefManager.getUser().getmEmail());

        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                if (username.isEmpty()){
                    usernameEt.requestFocus();
                    usernameEt.setError("Enter username");
                }else if (email.isEmpty()){
                    emailEt.requestFocus();
                    emailEt.setError("Enter email id");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEt.requestFocus();
                    emailEt.setError("Please enter valid email id");
                }else {
                    Call<UpdateUserResponse> call = APIClient
                            .getApiClient()
                            .getDataInterface()
                            .updateUser(id,username,email);

                    call.enqueue(new Callback<UpdateUserResponse>() {
                        @Override
                        public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                            UpdateUserResponse updateUserResponse = response.body();
                            if (response.isSuccessful()){
                                if (updateUserResponse.getmError().equals("200")){
                                    sharedPrefManager.saveUser(updateUserResponse.getmUser());
                                    Toast.makeText(getActivity(), updateUserResponse.getmMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), updateUserResponse.getmMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPass = currentEt.getText().toString().trim();
                String newPass = newEt.getText().toString().trim();

                if (currentPass.isEmpty()){
                    currentEt.requestFocus();
                    currentEt.setError("Enter current password");
                }else if (newPass.isEmpty()){
                    newEt.requestFocus();
                    newEt.setError("Enter new password");
                }else {
                    Call<UpdatePasswordResponse> call = APIClient
                            .getApiClient()
                            .getDataInterface()
                            .updateUserPassword(email,currentPass,newPass);

                    call.enqueue(new Callback<UpdatePasswordResponse>() {
                        @Override
                        public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                            UpdatePasswordResponse updatePasswordResponse = response.body();
                            if (response.isSuccessful()){
                                if (updatePasswordResponse.getError().equals("200")){
                                    Toast.makeText(getActivity(), updatePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), updatePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.logout,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAccount() {
        Call<DeleteAccountResponse> call = APIClient.getApiClient().getDataInterface()
                .deleteAccount(id);

        call.enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {
                DeleteAccountResponse deleteAccountResponse = response.body();
                if (response.isSuccessful()){
                    if (deleteAccountResponse.getError().equals("200")){
                        Toast.makeText(getActivity(), deleteAccountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        sharedPrefManager.logout();
                        Intent intent = new Intent(getActivity(),Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(), deleteAccountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        sharedPrefManager.logout();
        Intent intent = new Intent(getActivity(),Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}