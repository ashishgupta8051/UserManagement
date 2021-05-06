package com.example.usermanagement.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.adapter.FetchUserAdapter;
import com.example.usermanagement.apiclient.APIClient;
import com.example.usermanagement.model.User;
import com.example.usermanagement.response.FetchUserResponse;
import com.example.usermanagement.utils.DataInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Users extends Fragment {
    private RecyclerView recyclerView;
    private List<User> userArrayList;
    private FetchUserAdapter fetchUserAdapter;


    public Users() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_users, container, false);

        getActivity().setTitle("Users");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.userRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<FetchUserResponse> call = APIClient.getApiClient().getDataInterface().fetchUser();

        call.enqueue(new Callback<FetchUserResponse>() {
            @Override
            public void onResponse(Call<FetchUserResponse> call, Response<FetchUserResponse> response) {
                if (response.isSuccessful()){
                    userArrayList = response.body().getUserList();
                    recyclerView.setAdapter(new FetchUserAdapter(userArrayList,getActivity()));
                }else {
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchUserResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}