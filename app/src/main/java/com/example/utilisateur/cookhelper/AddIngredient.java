package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

public class AddIngredient extends AppCompatActivity {
    private EditText ingredientBox;


    /**
     * 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientBox = (EditText) findViewById(R.id.newIngredient);
        setContentView(R.layout.activity_add_ingredient);
    }


    /**
     * Adds ingredient text to field
     */
    public void onClickAddIngredient(View view) {

            ingredientBox = (EditText) findViewById(R.id.newIngredient);
        if(!ingredientBox.getText().toString().equals("")) {
            
            //>>>> DB
            CHDBHandler handler = new CHDBHandler(this, null, null, 1);
            updateFields();
            String ingredientName = ingredientBox.getText().toString();
            Ingredient ingredient = new Ingredient(ingredientName);

            handler.addIngredient(ingredient);
            //<<<< DB
            
            Toast.makeText(getApplicationContext(), "Ingredient added!",
                    Toast.LENGTH_LONG).show();
            ingredientBox.setText("");
        }
        else
        {
            Snackbar.make(view, "Please enter an ingredient", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
    
    /**
     * Updates the all fields for DB sync
     */
    public void updateFields(){
        ingredientBox = (EditText) findViewById(R.id.newIngredient);
    }
    
    /**
     * Creates new ingredient entry in the database
     * TODO: Can be removed, functionality added to onClickAddIngredient
     */
    public void newIngredient(View view){
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        updateFields();
        String ingredientName = ingredientBox.getText().toString();
        Ingredient ingredient = new Ingredient(ingredientName);

        handler.addIngredient(ingredient);
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
