package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;

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

    public void testSpecificRecipe(View v){
        Intent intent = new Intent(getApplication(), ViewRecipe.class);
        startActivityForResult(intent, 0);
    }

//code quand j'ai on simule, ça met Recette A,B,C sur l'écran de viewRecipe
    //donc ça l'affiche toutes les recettes sur l'écran
        /*ListView mListView;
        mListView = (ListView) findViewById(R.id.listview);

        String[] listItems = {"Recette A", "Recette B", "Recette C"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);*/

}

