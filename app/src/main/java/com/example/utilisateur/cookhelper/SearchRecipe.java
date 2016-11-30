package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
    }

    public void testSpecificRecipe(View v){
        Intent intent = new Intent(getApplication(), ViewRecipe.class);
        startActivityForResult(intent, 0);
    }
}
