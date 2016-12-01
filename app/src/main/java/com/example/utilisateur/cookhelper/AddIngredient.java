package com.example.utilisateur.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class AddIngredient extends AppCompatActivity {
    EditText ingredientBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientBox = (EditText) findViewById(R.id.newIngredient);
        setContentView(R.layout.activity_add_ingredient);
    }

    public void updateFields(){
        ingredientBox = (EditText) findViewById(R.id.newIngredient);
    }

    public void newIngredient(View view){
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        updateFields();
        String ingredientName = ingredientBox.getText().toString();
        Ingredient ingredient = new Ingredient(ingredientName);

        handler.addIngredient(ingredient);
    }
}
