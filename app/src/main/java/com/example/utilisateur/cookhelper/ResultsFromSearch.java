package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Catherine on 30/11/16.
 */

public class ResultsFromSearch extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeview);
        setTitle("View All Recipes");
        ListView recipeListView;
        recipeListView = (ListView) findViewById(R.id.listview);

        //>>>> DB
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        //updateFields(); //<--- function was in other class in order to make sure field values were taken
        //get DBvalues to populate spinners
        Recipe recipe[] = handler.getAllRecipes();
        System.out.println(recipe);
        System.out.println(recipe.length);


        ArrayList<String> listRecipe = new ArrayList<String>();

        for (int i=0; i< recipe.length; i++) {
            listRecipe.add(recipe[i].getTitle());
        }

<<<<<<< HEAD
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
=======
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listRecipe);
        recipeListView.setAdapter(adapter);

        //Click on element in list
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String recipeName = (String) parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplication(), ViewRecipe.class);
                intent.putExtra("recipeName", recipeName);
                startActivityForResult(intent, 0);
            }
        });
>>>>>>> master
    }
}
