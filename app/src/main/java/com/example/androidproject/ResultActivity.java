package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    int POINTS = 10;
    TextView score;
    TextView earnedCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        score = findViewById(R.id.score);
        earnedCoins = findViewById(R.id.earnedCoins);

        int correctAnswers  = getIntent().getIntExtra("correct",0);
        int totalQuestions = getIntent().getIntExtra("total", 0);
        int points = correctAnswers * POINTS;
        score.setText(String.format("%d/%d",correctAnswers,totalQuestions));
        earnedCoins.setText(String.valueOf(points));

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("user")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

    }
}