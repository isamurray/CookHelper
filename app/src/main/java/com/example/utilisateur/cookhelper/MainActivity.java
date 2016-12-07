package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CHDBHandler db = new CHDBHandler(this,null,null,1);
        db.getWritableDatabase();
        db.close();

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.debug, menu);
        return true;
    }

    public void onClickHelp(View v) {
        Intent intent = new Intent(getApplicationContext(), HelpMenu.class); //Application Context and Activity
        startActivityForResult (intent,0);
    }

    public void onClickAddRecipePage(View v) {
        Intent intent = new Intent(getApplication(), AddRecipe.class);
        startActivityForResult(intent, 0);
    }

    public void onClickSearchPage(View v) {
        Intent intent = new Intent(getApplication(), SearchRecipe.class);
        startActivityForResult(intent, 0);
    }

    public void onClickAddIngredientPage(View v) {
        Intent intent = new Intent(getApplication(), AddIngredient.class);
        startActivityForResult(intent, 0);
    }

    public void onClickViewAllRecipesPage(View v) {
        Intent intent = new Intent(getApplication(), ResultsFromSearch.class);
        startActivityForResult(intent, 0);
    }


    // To respond to menu selections
    // DEBUG
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if(item.getItemId() == R.id.menu_dbg_1){
            System.out.println("\nSerializing and inserting into DB\n");
            serializeObject();           
        }else if(item.getItemId() == R.id.menu_dbg_2){
            System.out.println("\nPopulating DB\n");            
            populateDatabase();
        }else if(item.getItemId() == R.id.menu_dbg_3){
            System.out.println("\nDropping All Tables\n");
            dropAllTables();            
        }else if(item.getItemId() == R.id.menu_dbg_4){
            System.out.println("\nDeserialized\n");
            deserialize();            
        }else{
            System.out.println("\nunknown\n");            
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates new ingredient entry in the database
     */
    public void dropAllTables(){
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        handler.dropAllTables();
        // System.out.println(recipes);
        // updateFields();
        // String ingredientName = ingredientBox.getText().toString();
        // Ingredient ingredient = new Ingredient(ingredientName);

        //handler.addIngredient(ingredient);
    }


    public void deserialize(){
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        handler.getInstructions("Burger");
    }
    public void populateDatabase(){
        CHDBHandler handler = new CHDBHandler(this,null,null,1);
        handler.populateDatabase();
    }    
    
    /**
     * Creates new ingredient entry in the database
     */
    public void newIngredient(){
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        Recipe[] recipes = handler.getAllRecipes();
        System.out.println(recipes);
        // updateFields();
        // String ingredientName = ingredientBox.getText().toString();
        // Ingredient ingredient = new Ingredient(ingredientName);

        //handler.addIngredient(ingredient);
    }
    
    public void serializeObject(){
        CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        ArrayList<String> sampleInstructions = new ArrayList<String>();
        sampleInstructions.add("Step 1");
        sampleInstructions.add("Step 2");
        sampleInstructions.add("Step 3");
        handler.storeInstructions(sampleInstructions);
        
    }


}

