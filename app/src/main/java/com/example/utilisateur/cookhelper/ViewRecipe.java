package com.example.utilisateur.cookhelper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import static android.R.drawable.ic_menu_add;

public class ViewRecipe extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ArrayList<String> instructionData = new ArrayList<String>();
    private ArrayList<String> ingredientData = new ArrayList<String>();
    private ArrayList<String> categoryData = new ArrayList<String>();
    private ArrayList<String> typeData = new ArrayList<String>();


    private RatingBar mBar;
    private ListView lView1, lView2;
    View menuListViewSelected;
    private ArrayAdapter<String> arrayAdapterCheckBoxInstruction, arrayAdapterNoCheckInstruction, arrayAdapterIngredient, arrayAdapterIngredientEdit;
    private static int menuItemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        lView1 = (ListView) findViewById(R.id.ingredientListView);
        lView2 = (ListView) findViewById(R.id.instructionListViewCheck);

        instructionData.add("Instruction 1");
        instructionData.add("Instruction 2");
        instructionData.add("Instruction 3");
        instructionData.add("Instruction 4");

        ingredientData.add("Ingredient 1");
        ingredientData.add("Ingredient 2");
        ingredientData.add("Ingredient 3");
        ingredientData.add("Ingredient 4");

        categoryData.add("Category 1");
        categoryData.add("Category 2");
        categoryData.add("Category 3");
        categoryData.add("Category 4");

        typeData.add("Type 1");
        typeData.add("Type 2");
        typeData.add("Type 3");
        typeData.add("Type 4");

        arrayAdapterIngredient = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientData);
        arrayAdapterCheckBoxInstruction = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, instructionData);
        arrayAdapterNoCheckInstruction = new ArrayAdapter<String>(this, R.layout.edit_recipe_list_view, instructionData);
        arrayAdapterIngredientEdit = new ArrayAdapter<String>(this, R.layout.edit_recipe_list_view, ingredientData);

        lView1.setAdapter(arrayAdapterIngredient);
        lView2.setAdapter(arrayAdapterCheckBoxInstruction);
        lView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        setListViewHeightBasedOnItems(lView1);
        setListViewHeightBasedOnItems(lView2);

        mBar = (RatingBar) findViewById(R.id.ratingScore);

        //Click on element in tournament list
        lView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position).toString();
    }});

        lView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position).toString();
            }});

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_example, menu);
        return true;
    }

    // To respond to menu selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                if (item.getTitle().toString().equals("Edit")) {
                    setTitle("Editing recipe...");
                    item.setTitle("done");
                    registerForContextMenu(lView1);
                    registerForContextMenu(lView2);
                    lView1.setAdapter(arrayAdapterIngredientEdit);
                    lView2.setAdapter(arrayAdapterNoCheckInstruction);
                    final TextView recipeName = (TextView) findViewById(R.id.recipeName);
                    final TextView input_category = (TextView) findViewById(R.id.input_category);
                    final TextView input_type = (TextView) findViewById(R.id.input_type);
                    recipeName.setTextColor(Color.RED);
                    input_category.setTextColor(Color.RED);
                    input_type.setTextColor(Color.RED);


                    recipeName.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            editSomeText(recipeName);
                        }
                    });
                    input_category.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            editSomeText(input_category);
                        }
                    });
                    input_type.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            editSomeText(input_type);
                        }
                    });

                    LinearLayout l_layout = (LinearLayout) findViewById(R.id.linear_layout_ingredient);
                    ImageView addAnIngredient = new ImageView(this);
                    addAnIngredient.setImageResource(R.drawable.add_button);
                    addAnIngredient.setAdjustViewBounds(true);
                    addAnIngredient.setMaxHeight(70);
                    addAnIngredient.setMaxWidth(70);
                    addAnIngredient.setId(R.id.add_ingredient_button);
                    l_layout.addView(addAnIngredient);
                    addAnIngredient.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "You would add an ingredient",Toast.LENGTH_SHORT).show();
                        }
                    });


                    LinearLayout l_layout2 = (LinearLayout) findViewById(R.id.linear_layout_instruction);
                    ImageView addAnInstruction = new ImageView(this);
                    addAnInstruction.setImageResource(R.drawable.add_button);
                    addAnInstruction.setAdjustViewBounds(true);
                    addAnInstruction.setMaxHeight(70);
                    addAnInstruction.setMaxWidth(70);
                    addAnInstruction.setId(R.id.add_instruction_button);
                    l_layout2.addView(addAnInstruction);
                    addAnInstruction.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            addAnInstruction(v) ;
                        }
                    });



                } else {
                    item.setTitle("Edit");
                    setTitle("View Recipe");
                    unregisterForContextMenu(lView1);
                    unregisterForContextMenu(lView2);

                    lView2.setAdapter(arrayAdapterCheckBoxInstruction);
                    lView1.setAdapter(arrayAdapterIngredient);

                    final TextView recipeName = (TextView) findViewById(R.id.recipeName);
                    final TextView input_category = (TextView) findViewById(R.id.input_category);
                    final TextView input_type = (TextView) findViewById(R.id.input_type);
                    recipeName.setOnClickListener(null);
                    input_category.setOnClickListener(null);
                    input_type.setOnClickListener(null);
                    recipeName.setTextColor(Color.BLACK);
                    input_category.setTextColor(Color.BLACK);
                    input_type.setTextColor(Color.BLACK);

                    ImageView deleteButton = (ImageView) findViewById(R.id.add_ingredient_button);
                    deleteButton.setVisibility(View.GONE);
                    deleteButton= (ImageView) findViewById(R.id.add_instruction_button);
                    deleteButton.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Done editing",Toast.LENGTH_LONG).show();


                }
                return true;
            case R.id.menu_trash:
                Toast.makeText(getApplicationContext(), "You are trying to delete the recipe",Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "An error occured",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickRatingBar(View v) {
        mBar.getRating() ;
    }



    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = 200 * numberOfItems;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId()==R.id.instructionListViewCheck || v.getId()==R.id.ingredientListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menuItemSelected = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            menuListViewSelected = (ListView) v;
            System.out.println(menuListViewSelected.getId());
            System.out.println(R.id.instructionListViewCheck);
            System.out.println(R.id.ingredientListView);

            menu.setHeaderTitle("Pick an option");
            String[] menuItems = new String[] {"Edit", "Delete"};
            for (int i = 0; i<menuItems.length; i++) {
                    menu.add(Menu.NONE, i, i, menuItems[i]);
            }

    }}
    public boolean onContextItemSelected(MenuItem item){
           if (item.getTitle() == "Edit") {


               editSomeText(menuListViewSelected);

            }
            if(item.getTitle()=="Delete") {
                if (menuListViewSelected.getId() == R.id.instructionListViewCheck) {
                    instructionData.remove(menuItemSelected);
                    arrayAdapterNoCheckInstruction.notifyDataSetChanged();
                    arrayAdapterCheckBoxInstruction.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(lView2);
                    Toast.makeText(getApplicationContext(), "You are trying to delete", Toast.LENGTH_LONG).show();
                } else if (menuListViewSelected.getId() == R.id.ingredientListView) {
                    ingredientData.remove(menuItemSelected);
                    arrayAdapterIngredient.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(lView1);
                }
            }
        return true;
    }

    private void editSomeText(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View view = v;
        ArrayList<String> array = null;

        if (v.getId() == R.id.instructionListViewCheck) {
            array = instructionData;
            alert.setTitle("Edit Instruction");
        }
        else if (v.getId() == R.id.ingredientListView)  {
            array = ingredientData;
            alert.setTitle("Edit Ingredient");
        }
        else /*if(v.getId() == R.id.recipeName)*/ {
            alert.setTitle("Edit Recipe Information");
            array = new ArrayList<String>();
            array.add(((TextView)view).getText().toString());
            menuItemSelected = 0;
        }
       /* else if(v.getId() == R.id.input_category) {
            final Spinner categorySpinner = new Spinner(this);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryData);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(spinnerAdapter);
        }
        else if(v.getId() == R.id.input_type) {
            final Spinner typeSpinner = new Spinner(this);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeData);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(spinnerAdapter);
        }*/


        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(array.get(menuItemSelected));
        input.setGravity(17);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (view.getId() == R.id.instructionListViewCheck) {
                    if(!input.getText().toString().equals("")){
                        instructionData.set(menuItemSelected,input.getText().toString());
                    arrayAdapterNoCheckInstruction.notifyDataSetChanged();
                    arrayAdapterCheckBoxInstruction.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please, use 'Delete' to delete instruction", Toast.LENGTH_LONG).show();}}
                else if (view.getId() == R.id.ingredientListView){

                    ingredientData.set(menuItemSelected,input.getText().toString());
                    arrayAdapterIngredient.notifyDataSetChanged();
                    }
                else{
                    if(!input.getText().toString().equals(""))
                    ((TextView)view).setText(input.getText().toString());
                    else
                        Toast.makeText(getApplicationContext(), "Recipe name must have at least one character", Toast.LENGTH_LONG).show();

                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }


    private void addAnInstruction(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View view = v;
        ArrayList<String> array ;
        array = instructionData;
        alert.setTitle("Add new instruction");

        final EditText input = new EditText(this);
        input.setHint("Enter instruction");
        input.setGravity(17);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(!input.getText().toString().equals("")) {
                    instructionData.add(input.getText().toString());
                    arrayAdapterNoCheckInstruction.notifyDataSetChanged();
                    arrayAdapterCheckBoxInstruction.notifyDataSetChanged();
                }
            }});

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
