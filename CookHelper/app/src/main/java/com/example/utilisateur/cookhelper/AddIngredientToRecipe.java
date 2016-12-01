package com.example.utilisateur.cookhelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddIngredientToRecipe extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayList<String> listIngredient = new ArrayList<String>();
    private List<String> ingredientList = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayAdapter<String> adapter1;

    private EditText input_ingredient_qty;
    private Spinner ingredientChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient_to_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);
        addItemsOnSpinner();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listIngredient);
        setListAdapter(adapter1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listIngredient.size()>0){
                    Intent intent = new Intent(getApplication(), AddInstructionsToRecipe.class);
                    startActivityForResult(intent, 0);
                }
                else {
                    Snackbar.make(view, "Please add at least one ingredient", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void addItemsOnSpinner() {
        ingredientChosen = (Spinner) findViewById(R.id.ingredientChosen);

        ingredientList.add("-select ingredient-");
        for (int i = 1; i < 100; i++) {
            ingredientList.add("Ingredient sample " +Integer.toString(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredientList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientChosen.setAdapter(dataAdapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addIngredientInList(View v) {

        ingredientChosen = (Spinner) findViewById(R.id.ingredientChosen);
        input_ingredient_qty = (EditText) findViewById(R.id.input_ingredient_recipe);

        if (!String.valueOf(ingredientChosen.getSelectedItem()).equals("-select ingredient-") && !input_ingredient_qty.getText().toString().equals("")) {
            listIngredient.add(input_ingredient_qty.getText().toString() + " " + String.valueOf(ingredientChosen.getSelectedItem()));
            adapter1.notifyDataSetChanged();
            ingredientChosen.setSelection(0);
            input_ingredient_qty.setText("");
        }

    }

}
