package com.example.usermanagement.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.R;
import com.example.usermanagement.model.User;

import java.util.List;

public class FetchUserAdapter extends RecyclerView.Adapter<FetchUserAdapter.UserDetailHolder>{
    private List<User> userList;
    private Activity activity;

    public FetchUserAdapter(List<User> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail_list,parent,false);
        UserDetailHolder userDetailHolder = new UserDetailHolder(view);
        return userDetailHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetailHolder holder, int position) {
        User user = userList.get(position);
        holder.username.setText(user.getmUsername());
        holder.email.setText(user.getmEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserDetailHolder extends RecyclerView.ViewHolder{
        TextView username,email;
        public UserDetailHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_list);
            email = itemView.findViewById(R.id.email_list);
        }
    }
}
