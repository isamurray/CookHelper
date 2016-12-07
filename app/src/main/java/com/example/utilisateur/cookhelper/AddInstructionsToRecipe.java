package com.example.utilisateur.cookhelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddInstructionsToRecipe extends AppCompatActivity {


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayList<String> instructionList = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayAdapter<String> adapter1;

    private EditText input_instruction_recipe;
    private int i=1;
    private Recipe newRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructions_to_recipe);
        newRecipe = (Recipe) getIntent().getSerializableExtra("recipe");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Instructions To Recipe");

        final  CHDBHandler handler = new CHDBHandler(this, null, null, 1);
        ListView lview = (ListView) findViewById(android.R.id.list);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, instructionList);
        lview.setAdapter(adapter1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(instructionList.size()>0){
                    newRecipe.setInstructions(instructionList);
                    newRecipe.setStars(0);
                    handler.addRecipe(newRecipe);
                    Intent intent = new Intent(getApplication(), ViewRecipe.class);
                    intent.putExtra("recipeName", newRecipe.getTitle());
                    startActivityForResult(intent, 0);
                }
                else{
                    Snackbar.make(view, "Please add at least one instruction", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addInstructionInList(View v) {

        input_instruction_recipe = (EditText) findViewById(R.id.input_instruction_recipe);

        if (!input_instruction_recipe.getText().toString().equals("")) {
            instructionList.add(input_instruction_recipe.getText().toString());
            adapter1.notifyDataSetChanged();
            input_instruction_recipe.setText("");
            i++;
        }
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
                newRecipe = null;
                Intent intent2 = new Intent(getApplication(), MainActivity.class);
                startActivityForResult(intent2, 0);
                return true;
            default:
                Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

}
