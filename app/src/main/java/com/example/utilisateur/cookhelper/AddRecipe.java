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

    private Spinner typeChoice, categoryChosen, numIngredient, numInstruction;
    private EditText recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addItemsOnSpinners();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipe);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       readItemSelection();
                                       System.out.println("Type : " + String.valueOf(typeChoice.getSelectedItem()));
                                       System.out.println("Category : " + String.valueOf(categoryChosen.getSelectedItem()));
                                       System.out.println("Name : " + recipeName.getText().toString());
                                       System.out.println("Nombre ingredient : " + String.valueOf(numIngredient.getSelectedItem()));
                                       System.out.println("Nombre instruction : " + String.valueOf(numInstruction.getSelectedItem()));
                                       if (String.valueOf(typeChoice.getSelectedItem()).equals("-select-") || String.valueOf(categoryChosen.getSelectedItem()).equals("-select-") ||
                                               recipeName.getText().toString().equals("") ||String.valueOf(numIngredient.getSelectedItem()).equals("-select-")||
                                               String.valueOf(numInstruction.getSelectedItem()).equals("-select-")){
                                           Snackbar.make(view, "Please enter all information", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();                                       }
                                       else {
                                           Intent intent = new Intent(getApplication(), AddRecipeInformation.class);
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

    private void addItemsOnSpinners() {

        numIngredient = (Spinner) findViewById(R.id.numIngredient);
        numInstruction = (Spinner) findViewById(R.id.numInstruction);
        List<String> list = new ArrayList<String>();
        list.add("-select-");
        for(int i=1 ; i<100 ; i++) {
            list.add(Integer.toString(i));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numIngredient.setAdapter(dataAdapter);
        numInstruction.setAdapter(dataAdapter);

    }




}
