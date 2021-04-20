package com.example.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.model.User;

import java.util.ArrayList;

public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardsViewHolder> {
    Context context;
    ArrayList<User> users;

    public LeaderboardsAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderboardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_leaderboards,null);
        return new LeaderboardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardsViewHolder holder, int position) {
        User user = users.get(position);

        holder.name.setText(user.getName());
        holder.coins.setText(String.valueOf(user.getCoins()) );
        holder.index.setText(String.format("#%d", position+1));


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView coins;
        TextView index;
        public LeaderboardsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            coins = (TextView) itemView.findViewById(R.id.coins);
            index = (TextView) itemView.findViewById(R.id.index);
        }
    }
}
