package com.example.utilisateur.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.example.utilisateur.cookhelper.R;


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
        // getMenuInflater().inflate(R.menu.menu_menu_example, menu);
        getMenuInflater().inflate(R.menu.debug, menu);
        return true;
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
        Intent intent = new Intent(getApplication(), ViewRecipe.class);
        startActivityForResult(intent, 0);
    }

    public void onClickViewHelp(View view) {
        Intent intent = new Intent(getApplicationContext(), HelpMenu.class); //Application Context and Activity
        startActivityForResult (intent,0);
    }

//code quand j'ai on simule, ça met Recette A,B,C sur l'écran de viewRecipe
    //donc ça l'affiche toutes les recettes sur l'écran
        /*ListView mListView;
        mListView = (ListView) findViewById(R.id.listview);

        String[] listItems = {"Recette A", "Recette B", "Recette C"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);*/

    public void GOTOADDINFO(View v) {
        Intent intent = new Intent(getApplication(), AddInstructionsToRecipe.class);
        startActivityForResult(intent, 0);
    }
    
    // To respond to menu selections
    // DEBUG
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if(item.getItemId() == R.id.menu_dbg_1){
            System.out.println("\ndbg1\n");            
        }else if(item.getItemId() == R.id.menu_dbg_2){
            System.out.println("\ndbg2\n");            
        }else if(item.getItemId() == R.id.menu_dbg_3){
            System.out.println("\ndbg3\n");
            dropAllTables();            
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


}

