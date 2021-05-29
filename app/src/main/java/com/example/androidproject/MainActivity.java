package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidproject.adapter.CategoryAdapter;
import com.example.androidproject.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<CategoryModel> categories = new ArrayList<>();
        categories.add(new CategoryModel("","Mathematics","https://cdn.dribbble.com/users/2552641/screenshots/6549959/icon_challenge_originals_edu2_1x.jpg"));
        categories.add(new CategoryModel("","Science","https://pineapplemarketing.com.au/wp-content/uploads/2019/04/Science-icon.png"));
        categories.add(new CategoryModel("","History","https://image.flaticon.com/icons/png/512/1800/1800196.png"));
        categories.add(new CategoryModel("","Sport","https://cdn.iconscout.com/icon/premium/png-256-thumb/sport-2588906-2160920.png"));
        categories.add(new CategoryModel("","Space","https://cdn4.iconfinder.com/data/icons/space-84/64/planet-science-space-education-512.png"));
        categories.add(new CategoryModel("","Art","https://cdn.iconscout.com/icon/premium/png-256-thumb/art-1751606-1491810.png"));

        recyclerView = findViewById(R.id.categoryList);
        categoryAdapter = new CategoryAdapter(this, categories);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.wallet){
            Toast.makeText(this, "Wall is clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}