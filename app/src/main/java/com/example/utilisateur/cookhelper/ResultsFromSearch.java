package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;

/**
 * Created by Catherine on 30/11/16.
 */

public class ResultsFromSearch extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeview);

/*      Commentaires de comment afficher les recettes dans la liste view
        ListView mListView;
        mListView = (ListView) findViewById(R.id.listview);

        String[] listItems = {"Recette A", "Recette B", "Recette C"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);*/
    }

    public void displayRecipes(String[] listItems){
        ListView mListView;
        mListView = (ListView) findViewById(R.id.listview); //listview est le nom de ma liste

        //comment ça va afficher les diffrentes recettes avec la listeView
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }
}
