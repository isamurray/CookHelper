package com.example.utilisateur.cookhelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.io.ObjectOutput;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ced on 2016-11-30.
 */

public class CHDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 39;
    private static final String DATABASE_NAME = "cookhelperDB.db";
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String TABLE_RECIPETYPES = "recipetypes";
    private  static final String TABLE_INSTRUCTIONS = "instructions";
    private  static final String TABLE_INSTRUCTIONS_S = "instructions_s";
    private static final String TABLE_RECIPECATEGORIES = "categories";
    private static final String[] TABLES = new String[]{TABLE_RECIPES,
        TABLE_INGREDIENTS,TABLE_RECIPETYPES,TABLE_INSTRUCTIONS,TABLE_RECIPECATEGORIES};

    private static final String COL_ID = "_id";
    private static final String COL_RECIPENAME = "title";
    private static final String COL_RECIPECOUNTRY = "type";
    private static final String COL_RECIPEDISHTYPE = "category";
    private static final String COL_RECIPERATING = "stars";
    private static final String COL_RECIPE_INGREDIENTS = "ingredients";

    private static final String COL_INGREDIENTNAME = "title";
    private static final String COL_RECIPETYPES_TYPE = "type";
    private static final String COL_CAT_CATEGORY = "category";


    // INSTRUCTION TABLE CONSTANTS
    // All instructions belong to a recipe... or more?...
    private static final String COL_INSTRUCTION_PARENT_RECIPE = "parent";
    
    private static final String COL_PARENT_RECIPE = "parent";

    // Index of instruction in set of instructions
    // Starts at 0
    private static final String COL_INSTRUCTION_INDEX = "idx";
    private static final String COL_INSTRUCTION_TEXT = "instruction";

    public CHDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates the initial database and all associated tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_RECIPE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RECIPES + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_RECIPENAME + " TEXT," +
                COL_RECIPECOUNTRY + " TEXT," +
                COL_RECIPEDISHTYPE + " TEXT," +
                COL_INSTRUCTION_TEXT + " BLOB," +
                COL_RECIPERATING + " FLOAT," + 
                COL_RECIPE_INGREDIENTS + " BLOB" + ")";
        
        // CREATE TABLE ingredients(_id INTEGER PRIMARY KEY, title TEXT);
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_INGREDIENTS + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_PARENT_RECIPE + " INTEGER," +
                COL_INGREDIENTNAME + " TEXT" + ")";
        
        // CREATE TABLE instructions(_id INTEGER PRIMARY KEY, parent INTEGER, idx INTEGER, instruction TEXT);
        String CREATE_INSTRUCTION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_INSTRUCTIONS + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_PARENT_RECIPE + " INTEGER," +
                COL_INSTRUCTION_INDEX + " INTEGER," +
                COL_INSTRUCTION_TEXT + " TEXT" + ")";
        
        // CREATE TABLE recipetypes(_id INTEGER PRIMARY KEY, type TEXT);
        String CREATE_RECIPETYPE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RECIPETYPES + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_PARENT_RECIPE + " INTEGER," +
                COL_RECIPETYPES_TYPE + " TEXT" + ")";
        
        // CREATE TABLE categories(_id INTEGER PRIMARY KEY, category TEXT);
        String CREATE_RECIPECATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RECIPECATEGORIES + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_PARENT_RECIPE + " INTEGER," +
                COL_CAT_CATEGORY + " TEXT" + ")";
        
        
        db.execSQL(CREATE_INSTRUCTION_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_RECIPECATEGORY_TABLE);
        db.execSQL(CREATE_RECIPETYPE_TABLE);
    }

    /**
     * Called when DBVersion mismatch occurs.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // dropAllTables();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }
    
    public void dropAllTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i = 0; i < TABLES.length; i++){
            db.execSQL("DROP TABLE IF EXISTS " + TABLES[i]);
        }
        
        db.close();
        System.out.println("All tables dropped");
    }
    
    public void populateDatabase(){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> sampleInstructions = new ArrayList<String>();
        sampleInstructions.add("Stop".toLowerCase());
        sampleInstructions.add("Drop".toLowerCase());
        sampleInstructions.add("Roll".toLowerCase());
        ArrayList<String> sampleIngredients = new ArrayList<String>();
        sampleIngredients.add("Apple (1 Cup)".toLowerCase());
        sampleIngredients.add("Orange (1 Tbs)".toLowerCase());
        sampleIngredients.add("Banana (1)".toLowerCase());
        sampleIngredients.add("Milk (3 kg)".toLowerCase());
        // POPULATE 3 SAMPLE RECIPES
        values.put(COL_RECIPENAME,"Burger".toLowerCase());
        values.put(COL_RECIPECOUNTRY,"Canadian".toLowerCase());
        values.put(COL_RECIPEDISHTYPE,"Lunch".toLowerCase());
        values.put(COL_INSTRUCTION_TEXT,serializeObject(sampleInstructions));
        values.put(COL_RECIPERATING,1);
        values.put(COL_RECIPE_INGREDIENTS,serializeObject(sampleIngredients));
        db.insert(TABLE_RECIPES,null,values);

        ContentValues values2 = new ContentValues();
        values2.put(COL_RECIPENAME,"Cereal".toLowerCase());
        values2.put(COL_RECIPECOUNTRY,"Indian".toLowerCase());
        values2.put(COL_RECIPEDISHTYPE,"Breakfast".toLowerCase());
        values2.put(COL_INSTRUCTION_TEXT,serializeObject(sampleInstructions));
        values2.put(COL_RECIPERATING,3);
        values2.put(COL_RECIPE_INGREDIENTS,serializeObject(sampleIngredients));
        db.insert(TABLE_RECIPES,null,values2);

        ContentValues values3 = new ContentValues();
        values3.put(COL_RECIPENAME,"GreaseRoll".toLowerCase());
        values3.put(COL_RECIPECOUNTRY,"American".toLowerCase());
        values3.put(COL_RECIPEDISHTYPE,"Dinner".toLowerCase());
        values3.put(COL_INSTRUCTION_TEXT,serializeObject(sampleInstructions));
        values3.put(COL_RECIPERATING,4);
        values3.put(COL_RECIPE_INGREDIENTS,serializeObject(sampleIngredients));
        db.insert(TABLE_RECIPES,null,values3);
        
        // POPULATE 3 SAMPLE INGREDIENTS
        ContentValues values18 = new ContentValues();
        values18.put(COL_PARENT_RECIPE,3);
        values18.put(COL_INGREDIENTNAME,"-select ingredient-");
        db.insert(TABLE_INGREDIENTS,null,values18);

        ContentValues values4 = new ContentValues();
        values4.put(COL_PARENT_RECIPE,1);
        values4.put(COL_INGREDIENTNAME,"Potato".toLowerCase());
        db.insert(TABLE_INGREDIENTS,null,values4);

        ContentValues values5 = new ContentValues();
        values5.put(COL_PARENT_RECIPE,2);
        values5.put(COL_INGREDIENTNAME,"Banana".toLowerCase());
        db.insert(TABLE_INGREDIENTS,null,values5);
        
        ContentValues values6 = new ContentValues();
        values6.put(COL_PARENT_RECIPE,3);
        values6.put(COL_INGREDIENTNAME,"Milk".toLowerCase());
        db.insert(TABLE_INGREDIENTS,null,values6);
        
        // POPULATE SAMPLE INSTRUCTIONS
        ContentValues values7 = new ContentValues();
        values7.put(COL_PARENT_RECIPE,3);
        values7.put(COL_INSTRUCTION_INDEX,1);
        values7.put(COL_INSTRUCTION_TEXT,"This is the first step for recipe 3");
        db.insert(TABLE_INSTRUCTIONS,null,values7);
        values7.put(COL_INSTRUCTION_INDEX,2);
        values7.put(COL_INSTRUCTION_TEXT,"This is the second step for recipe 3");
        db.insert(TABLE_INSTRUCTIONS,null,values7);
        values7.put(COL_INSTRUCTION_INDEX,3);
        values7.put(COL_INSTRUCTION_TEXT,"This is the third step for recipe 3");
        db.insert(TABLE_INSTRUCTIONS,null,values7);

        ContentValues values8 = new ContentValues();
        values8.put(COL_PARENT_RECIPE,2);
        values8.put(COL_INSTRUCTION_INDEX,1);
        values8.put(COL_INSTRUCTION_TEXT,"This is the first step for recipe 2");
        db.insert(TABLE_INSTRUCTIONS,null,values8);
        values8.put(COL_INSTRUCTION_INDEX,2);
        values8.put(COL_INSTRUCTION_TEXT,"This is the second step for recipe 2");
        db.insert(TABLE_INSTRUCTIONS,null,values8);
        values8.put(COL_INSTRUCTION_INDEX,3);
        values8.put(COL_INSTRUCTION_TEXT,"This is the third step for recipe 2");
        db.insert(TABLE_INSTRUCTIONS,null,values8);

        ContentValues values9 = new ContentValues();
        values9.put(COL_PARENT_RECIPE,1);
        values9.put(COL_INSTRUCTION_INDEX,1);
        values9.put(COL_INSTRUCTION_TEXT,"This is the first step for recipe 1");
        db.insert(TABLE_INSTRUCTIONS,null,values9);
        values9.put(COL_INSTRUCTION_INDEX,2);
        values9.put(COL_INSTRUCTION_TEXT,"This is the second step for recipe 1");
        db.insert(TABLE_INSTRUCTIONS,null,values9);
        values9.put(COL_INSTRUCTION_INDEX,3);
        values9.put(COL_INSTRUCTION_TEXT,"This is the third step for recipe 1");
        db.insert(TABLE_INSTRUCTIONS,null,values9);

        // POPULATE RECIPES TYPES
        
                // COL_PARENT_RECIPE + " INTEGER," +
                // COL_RECIPETYPES_TYPE + " TEXT" + ")";
        ContentValues values17 = new ContentValues();
        values17.put(COL_PARENT_RECIPE,1);
        values17.put(COL_RECIPETYPES_TYPE,"-select-");
        db.insert(TABLE_RECIPETYPES,null,values17);

        ContentValues values10 = new ContentValues();
        values10.put(COL_PARENT_RECIPE,1);
        values10.put(COL_RECIPETYPES_TYPE,"Canadian".toLowerCase());
        db.insert(TABLE_RECIPETYPES,null,values10);

        ContentValues values11 = new ContentValues();
        values11.put(COL_PARENT_RECIPE,2);
        values11.put(COL_RECIPETYPES_TYPE,"American".toLowerCase());
        db.insert(TABLE_RECIPETYPES,null,values11);

        ContentValues values12 = new ContentValues();
        values12.put(COL_PARENT_RECIPE,3);
        values12.put(COL_RECIPETYPES_TYPE,"Indian".toLowerCase());
        db.insert(TABLE_RECIPETYPES,null,values12);
        // POPULATE RECIPE CATEGORIES

        ContentValues values16 = new ContentValues();
        values16.put(COL_CAT_CATEGORY,"-select-");
        values16.put(COL_PARENT_RECIPE,1);
        db.insert(TABLE_RECIPECATEGORIES,null,values16);

        ContentValues values13 = new ContentValues();
        values13.put(COL_CAT_CATEGORY,"Breakfast".toLowerCase());
        values13.put(COL_PARENT_RECIPE,3);
        db.insert(TABLE_RECIPECATEGORIES,null,values13);

        ContentValues values14 = new ContentValues();
        values14.put(COL_CAT_CATEGORY,"Lunch".toLowerCase());
        values14.put(COL_PARENT_RECIPE,2);
        db.insert(TABLE_RECIPECATEGORIES,null,values14);

        ContentValues values15 = new ContentValues();
        values15.put(COL_CAT_CATEGORY,"Dinner".toLowerCase());
        values15.put(COL_PARENT_RECIPE,1);
        db.insert(TABLE_RECIPECATEGORIES,null,values15);



        // values8.put(COL_PARENT_RECIPE,3);
        // values8.put(COL_INGREDIENTNAME,"Rock");
        // ContentValues values9 = new ContentValues();
        // values9.put(COL_PARENT_RECIPE,3);
        // values9.put(COL_INGREDIENTNAME,"Rock");

        db.close();                        

        
        
    }

    /**
     * Adds a recipe object to the database.
     * Some fields are deserilized/serialized on read/write
     * TODO: check for duplicates
     */
    public void addRecipe(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(COL_RECIPENAME,recipe.getTitle().toLowerCase());
        values.put(COL_RECIPECOUNTRY,recipe.getType().toLowerCase());
        values.put(COL_RECIPEDISHTYPE,recipe.getCategory().toLowerCase());
        values.put(COL_INSTRUCTION_TEXT,serializeObject(recipe.getInstructions()));
        values.put(COL_RECIPERATING,recipe.getStars());
        values.put(COL_RECIPE_INGREDIENTS,serializeObject(recipe.getIngredients()));

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPES,null,values);
        db.close();
    }

    /**
     * Adds an ingredient to the general ingredients list.
     * This is used to add ingredients which are not a part of a recipe.
     * TODO: check for duplicates
     */
    public void addIngredient(Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put(COL_PARENT_RECIPE,1);
        values.put(COL_INGREDIENTNAME,ingredient.getName().toLowerCase());//need to track...
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INGREDIENTS,null,values);
        db.close();
        System.out.println("Ingredient "+ingredient.getName()+" was added to db");
    }
    
    /**
     * Add recipe type to DB
     * TODO: check for duplicates
     * TODO: SECURITY VULN SQL INJECTION
     */
    public void addRecipeType(String recipeType){// recipe
        ContentValues values = new ContentValues();
        values.put(COL_PARENT_RECIPE,1);
        values.put(COL_RECIPETYPES_TYPE,recipeType.toLowerCase());// need to track what
        // recipe belongs to what category
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPETYPES,null,values);
        db.close();
        System.out.println("Recipetype "+recipeType+" was added to db");
    }
    
    /**
     * Add recipe category to DB
     * TODO: check for duplicates
     * TODO: SECURITY VULN SQL INJECTION
     */
    public void addRecipeCategory(String recipeCategory){
        ContentValues values = new ContentValues();
        values.put(COL_CAT_CATEGORY,recipeCategory.toLowerCase());// need to track what
        // recipe belongs to what category
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPECATEGORIES,null,values);
        db.close();
        System.out.println("Recipecategory "+recipeCategory+" was added to db");
    }


    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * !!! NOT CURRENTLY SAFE - VULN. TO SQL-INJECTIONS !!!
     * !!! TODO: Sanitize !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Recipe findRecipe(String recipeName){
        String query = "Select * FROM " + TABLE_RECIPES +
                " WHERE " + COL_RECIPENAME + " = \"" +
                recipeName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Recipe recipe = new Recipe();

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            // recipe.setID(Integer.parseInt(cursor.getString(0)));
            recipe.setTitle(cursor.getString(1).toLowerCase());
            recipe.setType(cursor.getString(2).toLowerCase());
            recipe.setCategory(cursor.getString(3).toLowerCase());
            recipe.setInstructions(deserializeObject(cursor.getBlob(4)));
            recipe.setStars(Float.parseFloat(cursor.getString(5)));
            recipe.setIngredients(deserializeObject(cursor.getBlob(6)));
            cursor.close();
        } else{
            recipe = null;
        }
        db.close();
        return recipe;
    }
    
    /**
     * Bypass being true skips the BOOLEAN search phase
     */

    public Recipe[] advancedFindRecipe(String category, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        
        String query = "Select * FROM " + TABLE_RECIPES +
            " WHERE " + COL_RECIPECOUNTRY + " = \"" + type + "\"" +
            " AND " + COL_RECIPEDISHTYPE + " = \"" + category + "\"";
        Cursor cursor = db.rawQuery(query, null);
        int queryCount = cursor.getCount();
        System.out.println("QueryCount for advancedFind is "+ queryCount);
        Recipe[] returnRecipes = new Recipe[queryCount];
        
        // LOOK FOR RECIPES WHICH ARE OF COUNTRY AND TYPE
        if(cursor.moveToFirst()){
            for(int i = 0; i < queryCount; i++){
                int index = cursor.getInt(0);
                String searchTitle = cursor.getString(1).toLowerCase();
                String searchType = cursor.getString(2).toLowerCase();
                String searchCategory = cursor.getString(3).toLowerCase();
                ArrayList<String> searchStructs = deserializeObject(cursor.getBlob(4));
                float searchStars = cursor.getFloat(5);
                ArrayList<String> searchIngredients = deserializeObject(cursor.getBlob(6));
                returnRecipes[i] = new Recipe(searchTitle,searchType,searchCategory,searchStars);
                returnRecipes[i].setInstructions(searchStructs);
                returnRecipes[i].setIngredients(searchIngredients);
                cursor.moveToNext();
            }
        }
        // FILTER RESULTS FOR RECIPES FOUND IN PREVIOUS STEP BY BOOLEAN
        
        System.out.println("Records found "+returnRecipes.length);
        cursor.close();
        return returnRecipes;
            
    }

    /**
     * Get all recipe categories as array of strings from DB
     */
    public String[] getAllRecipeCategories(){
        String query = "Select * FROM " + TABLE_RECIPECATEGORIES ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int queryCount = cursor.getCount();
        
        String[] categories = new String[queryCount];
        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                categories[i] = cursor.getString(2).toLowerCase();
                cursor.moveToNext();
            }
        }
        cursor.close();
        // db.close();
        return categories;
    }
    
    //public ArrayList<String> getIngredients(){
    //    ArrayList<String> list = new ArrayList<String>();
    //}
    //public ArrayList<String> getInstructions(){
    //    ArrayList<String> list = new ArrayList<String>();
    //}
    //
    
    
    // TODO: get list of alphabetically sort ingredients
    // TODO: get list of sequential instructions
    
    public ArrayList<String> getIngredients(){
        ArrayList<String> list = new ArrayList<String>();
        String query = "Select * FROM " + TABLE_INGREDIENTS + 
            " ORDER BY " + COL_INGREDIENTNAME + " ASC";
        System.out.println(query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            for(int i = 0; i < cursor.getCount(); i++){
                list.add(cursor.getString(2).toLowerCase());
                cursor.moveToNext();
            }
        }
        cursor.close();
        // db.close();
        return list;
    }
    
    public void storeInstructions(ArrayList<String> instructions){

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] serializedRecipe = serializeObject(instructions);
        // Blob storeMe = new Blob(serializedRecipe);

        values.put(COL_PARENT_RECIPE,1);
        values.put(COL_INSTRUCTION_TEXT,serializedRecipe);
        db.insert(TABLE_INSTRUCTIONS_S,null,values);
        db.close();                        
    }
    
    //String recipeName
    public ArrayList<String> getInstructions(String recipeName){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        int index = getRecipeIndex(recipeName);
        System.out.println(index);
        String query = "Select * FROM " + TABLE_RECIPES +
                " WHERE " + COL_ID + " = " + index;
        System.out.println(query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<String> obj = deserializeObject(cursor.getBlob(4));
        System.out.println(obj);
        cursor.close();
        // db.close();
        return obj;
        
    }
    
    private int getRecipeIndex(String recipeName){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_RECIPES +
            " WHERE " + COL_RECIPENAME + " = \"" + recipeName+ "\"";
        System.out.println(query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int index = cursor.getInt(0);
        System.out.println(index);
        cursor.close();
        // db.close();
        return index;
    }
    
    private static byte[] serializeObject(Object o){
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        
        try{
            ObjectOutput out = new ObjectOutputStream(byteStream);
            out.writeObject(o);
            out.close();
            byte[] buffer = byteStream.toByteArray();
            return buffer;
        } catch(IOException e){
            return null;
        }
        
    }
        
    private static ArrayList<String> deserializeObject(byte[] b){
        try{
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
            ArrayList<String> object = (ArrayList<String>) in.readObject();
            in.close();
            return object;
        }catch(ClassNotFoundException ea){
            System.out.println("Class not found");
            return null;
        }catch(IOException eb){
            System.out.println("IOE");
            return null;
        }
    }
    
    
    
    /**
     * Get all recipe types as array of strings from DB
     */
    public String[] getAllRecipeTypes(){
        String query = "Select * FROM " + TABLE_RECIPETYPES ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int queryCount = cursor.getCount();
        String[] types = new String[queryCount];

        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                types[i] = cursor.getString(2).toLowerCase();
                cursor.moveToNext();
            }
        }
        cursor.close();
        // db.close();
        return types;

    }
    
    
    /**
     * Get array of recipes
     */
    public Recipe[] getAllRecipes(){
        String query = "Select * FROM " + TABLE_RECIPES ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int queryCount = cursor.getCount();

        Recipe[] recipes = new Recipe[queryCount];
        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                int index = cursor.getInt(0);
                String title = cursor.getString(1).toLowerCase();
                String type = cursor.getString(2).toLowerCase();
                String category = cursor.getString(3).toLowerCase();
                ArrayList<String> structs = deserializeObject(cursor.getBlob(4));
                float stars = cursor.getFloat(5);
                ArrayList<String> ingredients = deserializeObject(cursor.getBlob(6));
                recipes[i] = new Recipe(title,type,category,stars);
                recipes[i].setInstructions(structs);
                recipes[i].setIngredients(ingredients);
                cursor.moveToNext();
            }

        }
        cursor.close();
        // db.close();
        return recipes;
    }
    /**
     * Updates the recipe which currently has the title of oldName.
     * 
     */
    public void updateRecipe(Recipe recipe, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues record = new ContentValues();
        String newTitle = recipe.getTitle().toLowerCase();
        String newType = recipe.getType().toLowerCase();
        String newCat = recipe.getCategory().toLowerCase();
        byte[] structs = serializeObject(recipe.getInstructions());
        float newStars = recipe.getStars();
        byte[] ingredients = serializeObject(recipe.getIngredients());
        
        record.put(COL_RECIPENAME, newTitle);
        record.put(COL_RECIPECOUNTRY, newType);
        record.put(COL_RECIPEDISHTYPE, newCat);
        record.put(COL_RECIPERATING, newStars);
        record.put(COL_INSTRUCTION_TEXT, structs);
        record.put(COL_RECIPE_INGREDIENTS, ingredients);
        String whereClause = COL_ID + " = ?";
        int index = getRecipeIndex(oldName);
        String[] str = new String[]{Integer.toString(index)};
        int result = db.update(TABLE_RECIPES,record,whereClause,str);

        System.out.println("Updated "+result+" records.");
        db.close();
    }

    /**
     * TODO: broken
     */
    public void deleteRecipe(String recipeName){
        // boolean result = false;

        // String query = "Select * FROM " + TABLE_RECIPES +
                // " WHERE " + COL_RECIPENAME + " = \"" +
                // recipeName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery(query,null);
        // Recipe recipe = new Recipe();
        
        int index = getRecipeIndex(recipeName);
        String whereClause = COL_ID + " = ?";
        String[] str = new String[]{Integer.toString(index)};
        db.delete(TABLE_RECIPES,whereClause,str);
        
        // if(cursor.moveToFirst()){
            // 
            // String indexQuery = "Select * FROM " + TABLE_RECIPES +
                    // " WHERE " + COL_RECIPENAME + " =\"" +
                    // recipeName + "\"";
            // Cursor indexCursor = db.rawQuery(indexQuery,null);
            // 
            // int delIdx = indexCursor.getInt(0);
////            recipe.setID(Integer.parseInt(cursor.getString(0)));
            // db.delete(TABLE_RECIPES, COL_ID + " =?", new String[]{String.valueOf(delIdx)});
            // cursor.close();
            // result = true;
        // }
        db.close();
        // return result;
    }



    //Gabriel-modification


    /**

     *@param instructions
     *@return returnedlist
     *This method finds the list of recipes ordered by their level of pertinence according to the
     *instructions (the ingredients that the user wants or doesn't want in his recipe) the category
     *and type that the user put in the application. The level of pertinence works as follow: Each
     *ingredient that the user wants in the recipe that is in the recipe gives the recipe 1 extra point
     * and each boolean operations (or,and) give 1 point per operation, the category and type also go for 1
     *point each, but if the users says " NOT ingredient " then the score goes to -1 and isn't considered
     *pertinent anymore, in other terms it is no longer considered.
     */
    public LinkedList<Recipe> searchRecipe(Recipe[] recipeList, String instructions) {

        LinkedList<Recipe> recipeDatabase = new LinkedList<Recipe>();
        for(int j =0 ; j<recipeList.length; j++) {
            recipeDatabase.add(recipeList[j]);
        }

        int[] score = new int[recipeDatabase.size()];

        for(int j =0 ; j<recipeList.length; j++) {
            score[j]=0;
        }

        int previousSpace = 0;

        LinkedList<String> test = new LinkedList<String>();

        if (instructions != null) {

            for (int j = 0; j < instructions.length(); j++) {
                if (instructions.charAt(j) == ' ' ) {
                    System.out.println("decoupage des mots " + j + " "+ instructions.substring(previousSpace, j));
                    test.push(instructions.substring(previousSpace, j));
                    previousSpace = j + 1;
                }
                else if(j == instructions.length()-1){
                    System.out.println("decoupage des mots " + j + " "+ instructions.substring(previousSpace));
                    test.push(instructions.substring(previousSpace));
                }
            }

            String first = "";
            String second = "";
            String third = "";

            while (!test.isEmpty()) {

                if (first.equals("")) {
                    first = test.pop();
                }

                if (!test.isEmpty()) {
                    second = test.pop();
                    System.out.println("second : " + second);
                }

                if (!second.equals("")) {
                    System.out.println("second dans le if: " + second);
                    System.out.println("first dans le if: " + first);

                    if (second.equals("or")) {

                        if (!test.isEmpty()) {
                            third = test.pop();
                            OR(recipeDatabase,first, third, score);
                            first = "";
                            second = "";
                            third = "";

                        }}
                        else if (second.equals("not")) {
                            NOT(recipeDatabase,first, score);
                            first = "";
                            second = "";
                            third = "";

                        }
                        else if (second.equals("and")) {
                            if (!test.isEmpty()) {
                                third = test.pop();
                                AND(recipeDatabase,first, third, score);
                                first = "";
                                second = "";
                                third = "";

                            } else {
                                ONE(recipeDatabase,first, score);
                                first = second;
                                second = "";

                            }

                        }
                        else {
                            ONE(recipeDatabase,first, score);
                            first = second;
                        }


                } else {
                    ONE(recipeDatabase,first, score);
                    first = second;
                }
                System.out.println("0 de la recette score " + score[0]);
                System.out.println( "1 de la recette score " + score[1]);
                System.out.println("2 de la recette score " + score[2]);
                if(second.equals("not"))
                    break;

            }
        }

        LinkedList<Recipe> returnedlist = new LinkedList<Recipe>(); // initie la liste de retour
        int i = 0;
        int recipeindex = 0; // pour guarder en place la location que l'on ajoute dans la liste
        LinkedList<Integer> scorelist = new LinkedList<Integer>(); // pour garder la position dans score que chaque recette que j'ajoute dans returnedlist est.
        Iterator<Recipe> iterRecipe = recipeDatabase.iterator();
        Boolean added = false; // pour s'assurer que si l'element que j'observe est le plus petit qu'on le met a la fin

        while (iterRecipe.hasNext() && i < score.length) {
            Recipe rec = iterRecipe.next();
            Iterator<Recipe> rliter = returnedlist.iterator(); // pour comparer  a la recette rec que l'on desire ajouter a la liste
            Iterator<Integer> sliter = scorelist.iterator(); // pour pouvoir comparer les scores

            System.out.println("Le score de la recette : " + score[i]);

            if (score[i] != -1) { // on ne met pas les -1 car c'est le seul  temps qu'il y a un ingredient NOT.

                while (rliter.hasNext() && sliter.hasNext()) {
                    Recipe comp = rliter.next();
                    Integer sindex = sliter.next();

                    if (score[sindex] < score[i]) { // si le score de la recette qu'on observe est plus grand alors on peut l'ajouter a la bonne place.
                        scorelist.add(recipeindex, score[i]);
                        returnedlist.add(recipeindex, rec);
                        added = true;
                        break;
                    } else if (score[sindex] == score[i]) {// si le score est  egal on compare avec les etoiles

                       /* if (comp.getStars() < returnedlist.get(i).getStars()) {
                            returnedlist.add(recipeindex, rec);
                            scorelist.add(recipeindex, score[i]);
                            added = true;
                            break;
                        }*/
                        recipeindex++;

                    }


                }
                if (!added) { //si non ajouter on l'ajoute a la fin de la liste
                    returnedlist.add(recipeindex,rec);
                    scorelist.add(recipeindex,score[i]);

                }
              
            }
            recipeindex = 0;
            i++;
            added = false;

        }

        System.out.println(returnedlist.size());
        return returnedlist;

    }
    private void ONE(LinkedList<Recipe> recipeDatabase,String first, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            System.out.println(r);
            Iterator<String> iterI = splitL(r.getIngredients()).iterator();

            while (iterI.hasNext()) {

                if (score[index] == -1) { //verifie que la recette n'a pas deja un NOT
                    break;
                }
                String s = iterI.next();
                System.out.println("this is s : " + s);
                System.out.println("this is first :  " + first);


                if (s.equals(first)) { //attribue un point si la recette a l'ingredient demander.
                    score[index]++;
                    System.out.println("score was updated for recipe " + index);
                    break;
                }
            }
            index++;
        }

    }

    private ArrayList<String> splitL(ArrayList<String> list){
        ArrayList<String> newList = new ArrayList<String>();
        for(int i = 0; i<list.size() ; i++ ){
            newList.add((list.get(i)).substring(0,(list.get(i)).indexOf(' ')));
        }
        return newList;
    }

    private void NOT(LinkedList<Recipe> recipeDatabase, String first, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            Iterator<String> iterI = splitL(r.getIngredients()).iterator();

            while (iterI.hasNext()) {

                if (score[index] == -1) {
                    break;
                }
                String s = iterI.next();
                System.out.println("first :  " + first);
                System.out.println("s :  " + s);

                if (s.equals(first) ) {
                    score[index] = -1;
                    System.out.println("we found a not element in " + index);
                    break;
                }
            }
            index++;
        }
    }

    private void OR(LinkedList<Recipe> recipeDatabase,String first, String third, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            Iterator<String> iterI = splitL(r.getIngredients()).iterator();

            while (iterI.hasNext()) {
                if (score[index] == -1) {
                    break;
                }
                String s = iterI.next();

                if (s.equals(first) || s.equals(third)) {
                    score[index]++;
                    break;
                }

            }
            index++;
        }

    }

    private void AND(LinkedList<Recipe> recipeDatabase,String first, String third, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;
        Boolean and1 = false;
        Boolean and2 = false;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            Iterator<String> iterI = splitL(r.getIngredients()).iterator();

            while (iterI.hasNext()) {
                if (score[index] == -1) {
                    break;
                }
                String s = iterI.next();

                if (s.equals(first)) {
                    and1 = true;
                }
                if (s.equals(third)) {
                    and2 = true;
                }
                if (and1 && and2) {
                    score[index]++;
                    break;
                }

            }
            index++;
            and1 = false;
            and2 = false;
        }

    }

}



    


