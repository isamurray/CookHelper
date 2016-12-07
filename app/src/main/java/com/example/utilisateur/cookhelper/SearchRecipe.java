package com.example.utilisateur.cookhelper;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchRecipe extends AppCompatActivity {
    private Spinner typeChosen, categoryChosen;
    private EditText recipeName, ingredientBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
        readItemSelection();

        final CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        String[] dbCategoryList = handler.getAllRecipeCategories();
        String[] dbTypeList = handler.getAllRecipeTypes();

        ArrayAdapter<String> dataAdapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbCategoryList);
        dataAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryChosen.setAdapter(dataAdapterCategory);

        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbTypeList);
        dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeChosen.setAdapter(dataAdapterType);

    }


    private void readItemSelection() {//<------- we should kee our field methods by the same name
        typeChosen = (Spinner) findViewById(R.id.typeChosen);
        categoryChosen = (Spinner) findViewById(R.id.categoryChosen);
        ingredientBool = (EditText) findViewById(R.id.ingredientBool);
        recipeName = (EditText) findViewById(R.id.recipeName);
    }


    /**
     * Searches for a recipe using only the input name
     */
    public void onClickSearchByName(View v) {

        readItemSelection();//<----- we should keep our 'field methods' by the same name

        // input box is empty
        if (recipeName.getText().toString().equals("")) {
            Snackbar.make(v, "Please enter a recipe name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            // will need case where search returns nothing
            // if search returns something, THEN we send those parameters with
            // the intent... figure out how to do this.

            //>>>> DB
            CHDBHandler handler = new CHDBHandler(this, null, null, 1);
            //updateFields(); //<--- function was in other class in order to make sure field values were taken
            String query = recipeName.getText().toString();


            Recipe recipe = handler.findRecipe(query);
            //send this recipe to the intent below
            System.out.println(recipe);
            //<<<< DB

            if (recipe != null) {
                Intent intent = new Intent(getApplication(), ViewRecipe.class);
                intent.putExtra("recipeName", query);
                startActivityForResult(intent, 0);
            } else {
                Snackbar.make(v, query + " does not exist in recipe database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
    public void onClickReset(View v) {
        readItemSelection();
        recipeName.setText("");
        ingredientBool.setText("");
        typeChosen.setSelection(0);
        categoryChosen.setSelection(0);

    }


    /**
     * Searches for a recipe using all provided parameters
     */
    public void onClickSearch(View v) {


        if(ingredientBool.getText().toString().equals("") &&  String.valueOf(categoryChosen.getSelectedItem()).equals("-select-")
                && String.valueOf(typeChosen.getSelectedItem()).equals("-select-"))  {
            Snackbar.make(v, "Please select at least one criteria", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
            String boolExpression = ingredientBool.getText().toString();
            boolExpression = boolExpression.toLowerCase();
            String []arrayBool = boolExpression.split("\\s+");
           // Recipe[] recipeFound = advancedFindRecipe(String.valueOf(categoryChosen.getSelectedItem()),ingredientBool.getText().toString(), arrayBool);


           CHDBHandler handler = new CHDBHandler(this, null, null, 1);  //FOR TESTING
           // Recipe[] recipeFound = handler.getAllRecipes();             //FOR TESTING

            String cat = String.valueOf(categoryChosen.getSelectedItem());
            String type = String.valueOf(typeChosen.getSelectedItem());

            Recipe[] recipeFound = handler.advancedFindRecipe(cat, type);

           if(recipeFound.length == 1)
                {
                Intent intent = new Intent(getApplication(), ViewRecipe.class);
                intent.putExtra("recipeName", recipeFound[0].getTitle());
                startActivityForResult(intent, 0);
                }
            else{
                Intent intent = new Intent(getApplication(), ResultsFromSearch.class);
                intent.putExtra("recipeList", recipeFound);
                startActivityForResult(intent, 0);
            }

/*
            CHDBHandler handler = new CHDBHandler(this, null, null, 1);
            String cat = String.valueOf(categoryChosen.getSelectedItem());
            String type = String.valueOf(typeChosen.getSelectedItem());
            String[] ingredients = new String[5];
            Recipe[] recipes = handler.advancedFindRecipe(cat, type);
            System.out.println(recipes);
            // Intent intent = new Intent(getApplication(), ViewRecipe.class);                 //FAIRE QQCH AVEC LES CHOIX VIDE POS 0
            // startActivityForResult(intent, 0);
        */
        }
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