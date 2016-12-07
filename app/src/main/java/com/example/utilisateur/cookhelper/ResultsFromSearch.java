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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Catherine on 30/11/16. Updated by isa 01/01/16
 */

public class ResultsFromSearch extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeview);
        setTitle("View All Recipes");
        ListView recipeListView;
        recipeListView = (ListView) findViewById(R.id.listview);
        TextView relevanceField = (TextView) findViewById(R.id.relevantTextField);
        Recipe[]recipe;

        if( getIntent().getExtras()!= null) {
            recipe = (Recipe[]) getIntent().getSerializableExtra("recipeList");
            System.out.println("received extra");
            System.out.println(recipe.length);
            relevanceField.setVisibility(View.VISIBLE);

        }
        else{
        //>>>> DB
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        //updateFields(); //<--- function was in other class in order to make sure field values were taken
        //get DBvalues to populate spinners
        recipe = handler.getAllRecipes();
        System.out.println(recipe.length);
        System.out.println("there is no extra");
        }


        ArrayList<String> listRecipe = new ArrayList<String>();

        for (int i=0; i< recipe.length; i++) {
            listRecipe.add(recipe[i].getTitle());
        }

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
    }


    //menu in title bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainmenu, menu);
        return true;
    }

    // To respond to menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main:
                Intent intent2 = new Intent(getApplication(), MainActivity.class);
                startActivityForResult(intent2, 0);
                return true;
            default:
                Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
}
