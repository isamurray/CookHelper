package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchRecipe extends AppCompatActivity {
    private Spinner typeChosen, categoryChosen;
    private EditText recipeName, ingredientBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
    }


    private void readItemSelection() {
        typeChosen = (Spinner) findViewById(R.id.typeChosen);
        categoryChosen = (Spinner) findViewById(R.id.categoryChosen);
        ingredientBool = (EditText) findViewById(R.id.ingredientBool);
        recipeName = (EditText) findViewById(R.id.recipeName);
    }


    public void onClickSearchByName(View v) {

        readItemSelection();
        if(recipeName.getText().toString().equals("")) {
            Snackbar.make(v, "Please enter a recipe name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{
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