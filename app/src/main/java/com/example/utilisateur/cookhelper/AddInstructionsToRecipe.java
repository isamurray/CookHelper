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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddInstructionsToRecipe extends ListActivity {


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private List<String> instructionList = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    private ArrayAdapter<String> adapter1;

    private EditText input_instruction_recipe;
    private int i=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructions_to_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, instructionList);
        setListAdapter(adapter1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(instructionList.size()>0){
                    Intent intent = new Intent(getApplication(), ViewRecipe.class);
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
            instructionList.add(Integer.toString(i)+ " - " + input_instruction_recipe.getText().toString());
            adapter1.notifyDataSetChanged();
            input_instruction_recipe.setText("");
            i++;
        }
    }



    //DOESNT WORK YET
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==android.R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int itemSelected = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

            menu.setHeaderTitle(instructionList.get(itemSelected));

            String[] menuItems = new String[] {"Edit", "Delete"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item){
            if (item.getTitle() == "Edit") {
                Toast.makeText(getApplicationContext(), "EDIT",
                        Toast.LENGTH_LONG).show();
            }
        return true;

    }

}