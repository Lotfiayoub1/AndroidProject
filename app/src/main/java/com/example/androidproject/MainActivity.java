package com.example.androidproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mySong;

    me.ibrahimsn.lib.SmoothBottomBar bottomBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySong = MediaPlayer.create(MainActivity.this,R.raw.sound);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content,  new HomeFragment());
        transaction.commit();

        auth = FirebaseAuth.getInstance();
        bottomBar =  findViewById(R.id.bottomBar);
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        transaction.replace(R.id.content,  new HomeFragment());
                        transaction.commit();

                        break;
                    case 1:
                        transaction.replace(R.id.content,  new LeaderboardsFragment());
                        transaction.commit();

                        break;
                    case 2:
                        transaction.replace(R.id.content,  new WalletFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content,  new profileFragment());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.wallet){

            auth.signOut();
            mySong.release();
            mySong.release();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);




            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();



        }
        return super.onOptionsItemSelected(item);


    }



}