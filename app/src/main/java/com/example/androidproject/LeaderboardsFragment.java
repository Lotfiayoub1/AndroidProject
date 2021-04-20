package com.example.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.adapter.CategoryAdapter;
import com.example.androidproject.adapter.LeaderboardsAdapter;
import com.example.androidproject.model.CategoryModel;
import com.example.androidproject.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardsFragment extends Fragment {

    RecyclerView recyclerView;
    LeaderboardsAdapter leaderboardsAdapter;
    FirebaseFirestore database;


    public LeaderboardsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboards, container, false);
        database = FirebaseFirestore.getInstance();
        ArrayList<User> users = new ArrayList<>();
        leaderboardsAdapter = new LeaderboardsAdapter(getContext(),  users);

        database.collection("user")
                .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                }

                leaderboardsAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(leaderboardsAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }
}