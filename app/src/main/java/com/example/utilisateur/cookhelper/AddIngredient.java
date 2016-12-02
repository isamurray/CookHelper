package com.example.utilisateur.cookhelper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddIngredient extends AppCompatActivity  {

    private EditText ingredientname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
    }

    public void onClickAddIngredient(View view) {
            ingredientname = (EditText) findViewById(R.id.newIngredient);
        if(/*!ingredientname.getText().toString().equals("")*/false) {                   //commented expression
            Toast.makeText(getApplicationContext(), "Ingredient added!",
                    Toast.LENGTH_LONG).show();
            ingredientname.setText("");
        }
        else
        {
            Snackbar.make(view, "Please enter an ingredient", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }
}
