package com.example.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.androidproject.model.CategoryModel;
import com.example.androidproject.model.User;
import com.example.androidproject.model.WithdrawRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class WalletFragment extends Fragment {

    FirebaseFirestore database;
    TextView currentCoins;
    User user;
    Button sendRequest;
    EditText emailBox;

    public WalletFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        currentCoins = view.findViewById(R.id.currentCoins);
        database = FirebaseFirestore.getInstance();
        database.collection("user")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                currentCoins.setText(user.getCoins() + "");
            }
        });

        emailBox = view.findViewById(R.id.emailBox1);
        sendRequest = view.findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getCoins() > 50000){
                    String uid = FirebaseAuth.getInstance().getUid();
                    String email = emailBox.getText().toString();
                    WithdrawRequest withdrawRequest = new WithdrawRequest(uid,email,user.getName());
                    database.collection("withdraws")
                            .document(uid)
                            .set(withdrawRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Request Sent successfully.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "You need more coins to get withdraw ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        

        return view;
    }
}