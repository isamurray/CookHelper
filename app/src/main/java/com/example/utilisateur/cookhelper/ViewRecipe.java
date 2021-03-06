package com.example.utilisateur.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.ContextMenu;
import android.view.Gravity;
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

import java.util.ArrayList;
import java.util.Arrays;

public class ViewRecipe extends AppCompatActivity {



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private ArrayList<String> instructionData= new ArrayList<String>();
    private ArrayList<String> typeData, categoryData, unitData;
    private ArrayList<String> ingredientData = new ArrayList<String>();
    private String [] dbCategories, dbTypes;
    private ArrayList<String> ingredientList = new ArrayList<String>();
    private ImageView addInstructionButton, addIngredientButton;
    private Spinner typeSpinner, categorySpinner, ingredientSpinner, unitSpinner ;
    private LinearLayout linearSpinnerType, linearSpinnerCategory, linearSpinnerIngredient;
    private TextView recipeName, input_category, input_type;
    private String oldname;
    private Boolean editing = false;

    private RatingBar mBar;
    private ListView lView1, lView2;
    View menuListViewSelected;
    private ArrayAdapter<String> arrayAdapterCheckBoxInstruction, arrayAdapterNoCheckInstruction, arrayAdapterIngredient, arrayAdapterIngredientEdit,
            arrayAdapterTypeSpinner, arrayAdapterCategorySpinner, arrayAdapterIngredientSpinner, arrayAdapterUnitSpinner;
    private static int menuItemSelected;
    private CHDBHandler handler;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String query = getIntent().getExtras().getString("recipeName");
        //>>>> DB
        handler = new CHDBHandler(this, null, null, 1);

        //get DBvalues to populate spinners
        recipe = handler.findRecipe(query);
        dbCategories = handler.getAllRecipeCategories();
        dbTypes = handler.getAllRecipeTypes();
        ingredientData = handler.getIngredients();
        instructionData = handler.getInstructions(query);
        ingredientList = recipe.getIngredients();
        String [] unitString = {"per unit","Cup","Tsp" ,"Tbs" ,"Oz" , "kg" ,"mL"};
        System.out.println("creation");
        System.out.println(ingredientList.size());

        categoryData = new ArrayList<String>(Arrays.asList(dbCategories));
        typeData = new ArrayList<String>(Arrays.asList(dbTypes));
        unitData = new ArrayList<String>(Arrays.asList(unitString));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        //Set to Recipe values
        recipeName = (TextView) findViewById(R.id.recipeName);
        input_category = (TextView) findViewById(R.id.input_category);
        input_type = (TextView) findViewById(R.id.input_type);

        recipeName.setText(recipe.getTitle());
        input_category.setText(recipe.getCategory());
        input_type.setText(recipe.getType());


        lView1 = (ListView) findViewById(R.id.ingredientListView);
        lView2 = (ListView) findViewById(R.id.instructionListViewCheck);


        //Create all necessary ArrayAdapter

        arrayAdapterIngredient = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientList);
        arrayAdapterCheckBoxInstruction = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, instructionData);
        arrayAdapterNoCheckInstruction = new ArrayAdapter<String>(this, R.layout.edit_recipe_list_view, instructionData);
        arrayAdapterIngredientEdit = new ArrayAdapter<String>(this, R.layout.edit_recipe_list_view, ingredientList);


        //Create and populate the 4 spinners (ingredient, category, unit and type)

        typeSpinner = new Spinner(this);
        arrayAdapterTypeSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeData);
        arrayAdapterTypeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(arrayAdapterTypeSpinner);
        linearSpinnerType = new LinearLayout(this);
        linearSpinnerType.setOrientation(LinearLayout.HORIZONTAL);
        linearSpinnerType.setGravity(Gravity.CENTER);
        linearSpinnerType.setPadding(0,25,0,0);
        linearSpinnerType.addView(typeSpinner);

        categorySpinner = new Spinner(this);
        arrayAdapterCategorySpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryData);
        arrayAdapterCategorySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapterCategorySpinner);
        linearSpinnerCategory = new LinearLayout(this);
        linearSpinnerCategory.setOrientation(LinearLayout.HORIZONTAL);
        linearSpinnerCategory.setGravity(Gravity.CENTER);
        linearSpinnerCategory.setPadding(0,25,0,0);
        linearSpinnerCategory.addView(categorySpinner);

        ingredientSpinner = new Spinner(this);
        arrayAdapterIngredientSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredientData);
        arrayAdapterIngredientSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientSpinner.setAdapter(arrayAdapterIngredientSpinner);

        unitSpinner = new Spinner(this);
        arrayAdapterUnitSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitData);
        arrayAdapterUnitSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(arrayAdapterUnitSpinner);

        //linear view for the add/edit ingredient
        linearSpinnerIngredient = new LinearLayout(this);
        linearSpinnerIngredient.setOrientation(LinearLayout.HORIZONTAL);
        linearSpinnerIngredient.setGravity(Gravity.CENTER);
        linearSpinnerIngredient.setPadding(0,25,0,0);
        linearSpinnerIngredient.addView(ingredientSpinner);
        linearSpinnerIngredient.addView(unitSpinner, 0);


        lView1.setAdapter(arrayAdapterIngredient);
        lView2.setAdapter(arrayAdapterCheckBoxInstruction);
        lView2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //check box

        setListViewHeightBasedOnItems(lView1);
        setListViewHeightBasedOnItems(lView2);

        //set rating
        mBar = (RatingBar) findViewById(R.id.ratingScore);
        mBar.setRating(recipe.getStars());

        //Click on element in both list
        lView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position).toString();
            }
        });

        lView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position).toString();
            }
        });


        //Create the add ingredient and add instruction buttons for edit mode
        addIngredientButton = createAddIngredientButton();
        addInstructionButton = createAddInstructionButton();

        LinearLayout l_layout = (LinearLayout) findViewById(R.id.linear_layout_ingredient);
        l_layout.addView(addIngredientButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnIngredient(v);
            }
        });

        LinearLayout l_layout2 = (LinearLayout) findViewById(R.id.linear_layout_instruction);

        l_layout2.addView(addInstructionButton);
        addInstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnInstruction(v);
            }
        });
        //Hide the button until EDIT mode activated
        addIngredientButton.setVisibility(View.GONE);
        addInstructionButton.setVisibility(View.GONE);

        oldname = recipeName.getText().toString();

    }

    //menu in title bar
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
                if (editing == false) {
                    setTitle("Editing recipe...");
                    item.setTitle("done");
                    editing = true;

                    //long click activated
                    registerForContextMenu(lView1);
                    registerForContextMenu(lView2);

                    //EDIT mode adapter
                    lView1.setAdapter(arrayAdapterIngredientEdit);
                    lView2.setAdapter(arrayAdapterNoCheckInstruction);

                    //Indentify what can be modified
                    recipeName.setTextColor(Color.RED);
                    input_category.setTextColor(Color.RED);
                    input_type.setTextColor(Color.RED);

                    //Set listener on recipe information
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

                    //Add buttons are now visible to user
                    addIngredientButton.setVisibility(View.VISIBLE);
                    addInstructionButton.setVisibility(View.VISIBLE);

                    //save the recipe name for database purpose

                } else if(editing ==true){
                    //To quit EDIT mode
                    item.setTitle("Edit");
                    setTitle("View Recipe");

                    //List no more clickable
                    unregisterForContextMenu(lView1);
                    unregisterForContextMenu(lView2);

                    //Adapter in mode view only
                    lView2.setAdapter(arrayAdapterCheckBoxInstruction);
                    lView1.setAdapter(arrayAdapterIngredient);

                    //Recipe information no more clickable
                    recipeName.setOnClickListener(null);
                    input_category.setOnClickListener(null);
                    input_type.setOnClickListener(null);

                    //Back to view only parameters
                    recipeName.setTextColor(Color.BLACK);
                    input_category.setTextColor(Color.BLACK);
                    input_type.setTextColor(Color.BLACK);

                    //Add buttons are now gone
                    addIngredientButton.setVisibility(View.GONE);
                    addInstructionButton.setVisibility(View.GONE);

                    //update database
                    updateRecipeValues();
                    handler.updateRecipe(recipe, oldname);
                    oldname = recipeName.getText().toString();
                    editing = false;
                    Toast.makeText(getApplicationContext(), "Done editing", Toast.LENGTH_SHORT).show();


                }
                return true;
            case R.id.menu_trash:
                handler.deleteRecipe(oldname);
                Toast.makeText(getApplicationContext(), oldname + " was deleted from database", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.menu_main:
                if(editing == false){   //if we are done editing
                    recipe.setStars(mBar.getRating());
                    handler.updateRecipe(recipe, oldname);                                                          //should be change to update rating only in a better world

                    Intent intent2 = new Intent(getApplication(), MainActivity.class);
                startActivityForResult(intent2, 0);}
                else{
                    Toast.makeText(getApplicationContext(), "Please finish editing before leaving", Toast.LENGTH_SHORT).show();

                }
                return true;
            default:
                Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickRatingBar(View v) {
        recipe.setStars(mBar.getRating());
    }


    //Adapts the listview height according to number of items
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

    //Creates the popup menu to EDIT or DELETE an element in a listview
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId() == R.id.instructionListViewCheck || v.getId() == R.id.ingredientListView) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menuItemSelected = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            menuListViewSelected = (ListView) v;

            menu.setHeaderTitle("Pick an option");
            String[] menuItems = new String[]{"Edit", "Delete"};

            //Add options strings to the menu
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }

        }
    }



    //Respond to the user selection in the EDIT/DELETE menu
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Edit") {

            editSomeText(menuListViewSelected);

        }
        if (item.getTitle() == "Delete") {
            if (menuListViewSelected.getId() == R.id.instructionListViewCheck) {
                if(instructionData.size()==1){
                    Toast.makeText(getApplicationContext(), "Impossible to delete: Recipe must have at least one instruction", Toast.LENGTH_LONG).show();
                }
                else {
                    instructionData.remove(menuItemSelected);
                    arrayAdapterNoCheckInstruction.notifyDataSetChanged();
                    arrayAdapterCheckBoxInstruction.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(lView1);
                    setListViewHeightBasedOnItems(lView2);
                }
            } else if (menuListViewSelected.getId() == R.id.ingredientListView) {
                if(ingredientList.size()==1){
                    Toast.makeText(getApplicationContext(), "Impossible to delete: Recipe must have at least one ingredient", Toast.LENGTH_LONG).show();
                }
                else{
                    ingredientList.remove(menuItemSelected);
                    arrayAdapterIngredient.notifyDataSetChanged();
                    arrayAdapterIngredientEdit.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(lView1);
                    setListViewHeightBasedOnItems(lView2);
                }
            }
        }
        return true;
    }

    //To edit some fields
    private void editSomeText(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View view = v;
        ArrayList<String> array = null;


        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setGravity(17);

        if (v.getId() == R.id.instructionListViewCheck) {               //instruction
            array = instructionData;
            input.setText(array.get(menuItemSelected));
            alert.setTitle("Edit Instruction");
            alert.setView(input);

        } else if (v.getId() == R.id.ingredientListView) {              //ingredient
            array = ingredientList;
            input.setGravity(17);
            linearSpinnerIngredient.addView(input,0);

            //make sure input is numerical
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            input.setKeyListener(DigitsKeyListener.getInstance(false,true));

            //set values to actual values
            String qtyIngredient = array.get(menuItemSelected);
            String ingredient = qtyIngredient.substring(0,qtyIngredient.indexOf(' ')); // INGREDIENT
            String qtyInfo = qtyIngredient.substring(qtyIngredient.indexOf('(')+1, qtyIngredient.indexOf(')')); // QTY+UNIT
            String[] info = qtyInfo.split(" ");     //QTY
            String qty = info[0];           //UNIT
            String unit = "per unit";
            if(info.length==2)
                unit = info[1];

            input.setText(qty);
            ingredientSpinner.setSelection(ingredientData.indexOf(ingredient));
            unitSpinner.setSelection(unitData.indexOf(unit));


            //reset the child's parent
            if(linearSpinnerIngredient.getParent()!=null)
                ((ViewGroup)linearSpinnerIngredient.getParent()).removeView(linearSpinnerIngredient);

            alert.setTitle("Edit Ingredient");
            alert.setView(linearSpinnerIngredient);

        } else if(v.getId() == R.id.recipeName) {       //recipe name
            alert.setTitle("Edit Recipe Name");
            input.setText( ((TextView) v).getText().toString());
            alert.setView(input);
        }

        else if(v.getId() == R.id.input_category) {       //recipe category
            alert.setTitle("Edit Category");
            //reset the child's parent
            if(linearSpinnerCategory.getParent()!=null)
                ((ViewGroup)linearSpinnerCategory.getParent()).removeView(linearSpinnerCategory);
            //set to the actual selected value
            categorySpinner.setSelection(categoryData.indexOf(input_category.getText().toString()));
            //set the alert to the spinner view
            alert.setView(linearSpinnerCategory);

        }

        else if(v.getId() == R.id.input_type) {             //recipe type
            alert.setTitle("Edit Type");
            //reset the child's parent
            if(linearSpinnerType.getParent()!=null)
                ((ViewGroup)linearSpinnerType.getParent()).removeView(linearSpinnerType);
            //set to the actual selected value
            typeSpinner.setSelection(typeData.indexOf(input_type.getText().toString()));
            //set the alert to the spinner view
            alert.setView(linearSpinnerType);
        }

        //update the changed value
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (view.getId() == R.id.instructionListViewCheck) {
                    if (!input.getText().toString().equals("")) {

                        instructionData.set(menuItemSelected, input.getText().toString());
                        arrayAdapterNoCheckInstruction.notifyDataSetChanged();
                        arrayAdapterCheckBoxInstruction.notifyDataSetChanged();
                        menuItemSelected = -1;

                    } else {
                        Toast.makeText(getApplicationContext(), "Please, use 'Delete' to delete instruction", Toast.LENGTH_SHORT).show();
                    }

                } else if (view.getId() == R.id.ingredientListView) {
                    if (String.valueOf(ingredientSpinner.getSelectedItem()).equals("-select ingredient-")) {
                        editSomeText(view);
                        linearSpinnerIngredient.removeView(input);
                        Toast.makeText(getApplicationContext(), "Please select an ingredient", Toast.LENGTH_SHORT).show();
                    }
                    else if(input.getText().toString().equals("")){
                        editSomeText(view);
                        linearSpinnerIngredient.removeView(input);
                        Toast.makeText(getApplicationContext(), "Ingredient quantity can't be null", Toast.LENGTH_SHORT).show();}

                    else{
                      //if per unit do not print
                        if( String.valueOf(unitSpinner.getSelectedItem()).equals("per unit"))
                            ingredientList.set(menuItemSelected,String.valueOf(ingredientSpinner.getSelectedItem()) +" (" + input.getText().toString() + ")" );
                        else
                            ingredientList.set(menuItemSelected, String.valueOf(ingredientSpinner.getSelectedItem()) +" (" + input.getText().toString() + " " + String.valueOf(unitSpinner.getSelectedItem()) + ") " );

                    arrayAdapterIngredient.notifyDataSetChanged();
                    arrayAdapterIngredientEdit.notifyDataSetChanged();
                    menuItemSelected = -1;
                    linearSpinnerIngredient.removeView(input);}


                } else if (view.getId() == R.id.input_type){
                    if (String.valueOf(typeSpinner.getSelectedItem()).equals("-select-")) {
                        editSomeText(view);
                        Toast.makeText(getApplicationContext(), "Please select a type", Toast.LENGTH_SHORT).show();
                    }else{
                    input_type.setText(String.valueOf(typeSpinner.getSelectedItem()));}
                }

                else if ( view.getId() == R.id.input_category){
                    if (String.valueOf(categorySpinner.getSelectedItem()).equals("-select-")) {
                        editSomeText(view);
                        Toast.makeText(getApplicationContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                    }else{
                    input_category.setText(String.valueOf(categorySpinner.getSelectedItem()));}
                }

                else{
                    if (!input.getText().toString().equals(""))
                        ((TextView) view).setText(input.getText().toString());
                    else
                        Toast.makeText(getApplicationContext(), "Recipe name must have at least one character", Toast.LENGTH_SHORT).show();

                }
                setListViewHeightBasedOnItems(lView1);
                setListViewHeightBasedOnItems(lView2);
            }


        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                linearSpinnerIngredient.removeView(input);

            }
        });

        alert.show();
    }


    private void addAnInstruction(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View view = v;
        ArrayList<String> array;
        array = instructionData;
        alert.setTitle("Add new instruction");

        final EditText input = new EditText(this);
        input.setHint("Enter instruction");
        input.setGravity(17);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    instructionData.add(input.getText().toString());
                    arrayAdapterNoCheckInstruction.notifyDataSetChanged();
                    arrayAdapterCheckBoxInstruction.notifyDataSetChanged();
                    setListViewHeightBasedOnItems(lView1);
                    setListViewHeightBasedOnItems(lView2);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Instruction must have at least one character", Toast.LENGTH_SHORT).show();
                    addAnInstruction(view);
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


    private ImageView createAddIngredientButton() {

        ImageView addAnIngredient = new ImageView(this);
        addAnIngredient.setImageResource(R.drawable.add_button);
        addAnIngredient.setAdjustViewBounds(true);
        addAnIngredient.setMaxHeight(70);
        addAnIngredient.setMaxWidth(70);
        addAnIngredient.setId(R.id.add_ingredient_button);
        return  addAnIngredient;
    }

    private ImageView createAddInstructionButton() {
        ImageView addAnInstruction = new ImageView(this);
        addAnInstruction.setImageResource(R.drawable.add_button);
        addAnInstruction.setAdjustViewBounds(true);
        addAnInstruction.setMaxHeight(70);
        addAnInstruction.setMaxWidth(70);
        addAnInstruction.setId(R.id.add_instruction_button);
        return addAnInstruction;
    }


    private void addAnIngredient(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final View view = v;
        ArrayList<String> array = ingredientList;
        alert.setTitle("Add New Ingredient");
        final EditText input = new EditText(this);
        input.setHint("Quantity");
        input.setText("");
        input.setGravity(17);

        //make sure input is numerical
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setKeyListener(DigitsKeyListener.getInstance(false,true));

        ingredientSpinner.setSelection(0);
        unitSpinner.setSelection(0);

        linearSpinnerIngredient.addView(input,0);
        //reset the child's parent
        if(linearSpinnerIngredient.getParent()!=null)
            ((ViewGroup)linearSpinnerIngredient.getParent()).removeView(linearSpinnerIngredient);


        alert.setView(linearSpinnerIngredient);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (String.valueOf(ingredientSpinner.getSelectedItem()).equals("-select ingredient-")) {
                    addAnIngredient(view);
                    linearSpinnerIngredient.removeView(input);
                    Toast.makeText(getApplicationContext(), "Please select an ingredient", Toast.LENGTH_SHORT).show();
                }
                else if(input.getText().toString().equals("")){
                    addAnIngredient(view);
                    linearSpinnerIngredient.removeView(input);
                    Toast.makeText(getApplicationContext(), "Ingredient quantity can't be null", Toast.LENGTH_SHORT).show();

                }
                else{

                    if( String.valueOf(unitSpinner.getSelectedItem()).equals("per unit"))
                        ingredientList.add(String.valueOf(ingredientSpinner.getSelectedItem())+ " (" + input.getText().toString() + ")");
                    else
                        ingredientList.add(String.valueOf(ingredientSpinner.getSelectedItem()) +" (" + input.getText().toString() + " " + String.valueOf(unitSpinner.getSelectedItem()) + ") " );

                    arrayAdapterIngredient.notifyDataSetChanged();
                    arrayAdapterIngredientEdit.notifyDataSetChanged();
                    menuItemSelected = -1;
                    linearSpinnerIngredient.removeView(input);
                    setListViewHeightBasedOnItems(lView1);
                    setListViewHeightBasedOnItems(lView2);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                linearSpinnerIngredient.removeView(input);
            }
        });

        alert.show();
    }

    private void updateRecipeValues(){
        recipe.setTitle(recipeName.getText().toString());
        recipe.setType(input_type.getText().toString());
        recipe.setCategory(input_category.getText().toString());
        recipe.setInstructions(instructionData);
        recipe.setStars(mBar.getRating());
        recipe.setIngredients(ingredientList);

    }

    }
