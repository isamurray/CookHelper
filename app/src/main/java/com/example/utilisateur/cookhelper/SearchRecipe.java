package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class SearchRecipe extends AppCompatActivity {
    private Spinner typeChosen, categoryChosen;
    private EditText recipeName, ingredientBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
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
        if(recipeName.getText().toString().equals("")) {
            Snackbar.make(v, "Please enter a recipe name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
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

            Intent intent = new Intent(getApplication(), ViewRecipe.class);
            startActivityForResult(intent, 0);}
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

        readItemSelection();
        if(ingredientBool.getText().toString().equals("") &&  String.valueOf(categoryChosen.getSelectedItem()).equals("-select-")
                && String.valueOf(typeChosen.getSelectedItem()).equals("-select-"))  {
            Snackbar.make(v, "Please select at least one criteria", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
            Intent intent = new Intent(getApplication(), ViewRecipe.class);                 //FAIRE QQCH AVEC LES CHOIX VIDE POS 0
            startActivityForResult(intent, 0);}
    }


}