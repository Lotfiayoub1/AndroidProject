package com.example.androidproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignupActivity extends AppCompatActivity {
    MediaPlayer mySong;
    Button submitBtn;
    Button alreadyHave;
    EditText emailBox;
    EditText nameBox;
    EditText referBox;
    EditText passwordBox;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mySong = MediaPlayer.create(SignupActivity.this,R.raw.sound);
        submitBtn = (Button) findViewById(R.id.loginBtn);
        alreadyHave = (Button) findViewById(R.id.alreadyHave);
        emailBox = (EditText) findViewById(R.id.emailBox);
        nameBox = (EditText) findViewById(R.id.nameBox);
        referBox = (EditText) findViewById(R.id.referBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        alreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("We're creating new account...");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.start();
                mySong.setLooping(true);

                String email, pass, name, referCode;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();
                referCode = referBox.getText().toString();

                final User user = new User(name, email, pass,referCode);

                if (!email.equals("") && !pass.equals("") && !name.equals("")){
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String uid = task.getResult().getUser().getUid();
                                database.collection("user")
                                        .document(uid)
                                        .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressDialog.dismiss();
                                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                            editor.putString("email", email);
                                            editor.apply();
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignupActivity.this,"The fields are empty",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}