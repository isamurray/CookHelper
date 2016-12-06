package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddRecipe extends AppCompatActivity {

    private Spinner typeChoice, categoryChosen;
    private EditText recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipe);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       readItemSelection();

                                       if (String.valueOf(typeChoice.getSelectedItem()).equals("-select-") || String.valueOf(categoryChosen.getSelectedItem()).equals("-select-") ||
                                               recipeName.getText().toString().equals("")){
                                           Snackbar.make(view, "Please enter all information", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();                                       }
                                       else {
                                           Intent intent = new Intent(getApplication(), AddIngredientToRecipe.class);
                                           startActivityForResult(intent, 0);

                                       }
                                   }
                               }
        );

    }

    private void readItemSelection() {
        typeChoice = (Spinner) findViewById(R.id.typeChosen);
        recipeName = (EditText) findViewById(R.id.recipeName);
        categoryChosen = (Spinner) findViewById(R.id.categoryChosen);
    }


}
