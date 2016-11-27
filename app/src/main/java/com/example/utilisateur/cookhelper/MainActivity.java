package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void testAdd(View v) {
        Intent intent = new Intent(getApplication(), AddRecipe.class);
        startActivityForResult(intent, 0);
    }

    public void testSearch(View v) {
        Intent intent = new Intent(getApplication(), SearchRecipe.class);
        startActivityForResult(intent, 0);
    }

    public void testAddIngredient(View v) {
        Intent intent = new Intent(getApplication(), AddIngredient.class);
        startActivityForResult(intent, 0);
    }

    public void testViewRecipe(View v) {
        Intent intent = new Intent(getApplication(), ViewRecipe.class);
        startActivityForResult(intent, 0);
    }



}

