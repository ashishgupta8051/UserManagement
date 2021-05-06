package com.example.usermanagement.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagement.R;
import com.example.usermanagement.utils.SharedPrefManager;


public class DashBoard extends Fragment {
    private TextView userNameTxt,emailTxt;
    private SharedPrefManager sharedPrefManager;

    public DashBoard() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dash_board, container, false);

        getActivity().setTitle("Dashboard");

        sharedPrefManager = new SharedPrefManager(getActivity());

        userNameTxt = view.findViewById(R.id.username);
        emailTxt = view.findViewById(R.id.email);

        String userName = "Hey! "+sharedPrefManager.getUser().getmUsername();
        String userEmail = sharedPrefManager.getUser().getmEmail();

        userNameTxt.setText(userName);
        emailTxt.setText(userEmail);
        
        return view;
    }
}