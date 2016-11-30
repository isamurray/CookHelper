package com.example.utilisateur.cookhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddIngredient extends AppCompatActivity implements View.OnClickListener {

    private TextView ingredientname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        ingredientname = (TextView) findViewById(R.id.newIngredient);
    }




    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.addIngredientButton){
             ingredientname.setText("Awignahan");
        }
    }
}
