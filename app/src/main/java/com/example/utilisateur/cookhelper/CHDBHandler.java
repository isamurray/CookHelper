package com.example.utilisateur.cookhelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by ced on 2016-11-30.
 */

public class CHDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "cookhelperDB.db";
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String TABLE_RECIPETYPES = "recipetypes";
    private  static final String TABLE_INSTRUCTIONS = "instructions";
    private static final String TABLE_RECIPECATEGORIES = "categories";
    private static final String[] TABLES = new String[]{TABLE_RECIPES,
        TABLE_INGREDIENTS,TABLE_RECIPETYPES,TABLE_INSTRUCTIONS,TABLE_RECIPECATEGORIES};

    private static final String COL_ID = "_id";
    private static final String COL_RECIPENAME = "title";
    private static final String COL_RECIPECOUNTRY = "type";
    private static final String COL_RECIPEDISHTYPE = "category";
    private static final String COL_RECIPECOOKTIME = "time";

    private static final String COL_INGREDIENTNAME = "title";
    private static final String COL_RECIPETYPES_TYPE = "type";
    private static final String COL_CAT_CATEGORY = "category";


    // INSTRUCTION TABLE CONSTANTS
    // All instructions belong to a recipe... or more?...
    private static final String COL_INSTRUCTION_PARENT_RECIPE = "parent";

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
                COL_RECIPECOOKTIME + " INTEGER" + ")";
        
        // CREATE TABLE ingredients(_id INTEGER PRIMARY KEY, title TEXT);
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_INGREDIENTS + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_INGREDIENTNAME + " TEXT" + ")";
        
        // CREATE TABLE instructions(_id INTEGER PRIMARY KEY, parent INTEGER, idx INTEGER, instruction TEXT);
        String CREATE_INSTRUCTION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_INSTRUCTIONS + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_INSTRUCTION_PARENT_RECIPE + " INTEGER," +
                COL_INSTRUCTION_INDEX + " INTEGER," +
                COL_INSTRUCTION_TEXT + " TEXT" + ")";
        
        // CREATE TABLE recipetypes(_id INTEGER PRIMARY KEY, type TEXT);
        String CREATE_RECIPETYPE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RECIPETYPES + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_RECIPETYPES_TYPE + " TEXT" + ")";
        
        // CREATE TABLE categories(_id INTEGER PRIMARY KEY, category TEXT);
        String CREATE_RECIPECATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_RECIPECATEGORIES + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_CAT_CATEGORY + " TEXT" + ")";
        
        

        db.execSQL(CREATE_INSTRUCTION_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
        db.execSQL(CREATE_RECIPECATEGORY_TABLE);
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

    /**
     * Add recipe to db
     * TODO: check for duplicates
     */
    public void addRecipe(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(COL_RECIPENAME,recipe.getTitle());
        values.put(COL_RECIPECOUNTRY,recipe.getType());
        values.put(COL_RECIPEDISHTYPE,recipe.getCategory());
        values.put(COL_RECIPECOOKTIME,recipe.getCookingTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPES,null,values);
        db.close();
    }

    /**
     * Add ingredient to DB
     * TODO: check for duplicates
     */
    public void addIngredient(Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put(COL_INGREDIENTNAME,ingredient.getName());

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
    public void addRecipeType(String recipeType){
        ContentValues values = new ContentValues();
        values.put(COL_RECIPETYPES_TYPE,recipeType);
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
        values.put(COL_CAT_CATEGORY,recipeCategory);
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
            recipe.setID(Integer.parseInt(cursor.getString(0)));
            recipe.setTitle(cursor.getString(1));
            recipe.setType(cursor.getString(2));
            recipe.setCategory(cursor.getString(3));
            recipe.setCookingTime(Integer.parseInt(cursor.getString(4)));
            cursor.close();
        } else{
            recipe = null;
        }
        db.close();
        return recipe;
    }
    
    /**
     * Get all recipe categories as array of strings from DB
     */
    public String[] getAllRecipeCategories(){
        String query = "Select * FROM " + TABLE_RECIPECATEGORIES + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int queryCount = cursor.getCount();
        
        String[] categories = new String[queryCount];
        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                categories[i] = cursor.getString(1);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return categories;
    }
    
    /**
     * Get all recipe types as array of strings from DB
     */
    public String[] getAllRecipeTypes(){
        String query = "Select * FROM " + TABLE_RECIPETYPES + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int queryCount = cursor.getCount();
        String[] types = new String[queryCount];

        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                types[i] = cursor.getString(1);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return types;

    }
    
    /**
     * Get array of recipes
     */
    public Recipe[] getAllRecipes(){
        String query = "Select * FROM " + TABLE_RECIPES + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int queryCount = cursor.getCount();

        Recipe[] recipes = new Recipe[queryCount];
        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                int index = cursor.getInt(0);
                String title = cursor.getString(1);
                String type = cursor.getString(2);
                String category = cursor.getString(3);
                int time = cursor.getInt(4);
                recipes[i] = new Recipe(index,title,type,category,time);
                cursor.moveToNext();
            }

        }
        cursor.close();
        return recipes;
    }


    /**
     *
     */
    public boolean deleteRecipe(String recipeName){
        boolean result = false;

        String query = "Select * FROM " + TABLE_RECIPES +
                " WHERE " + COL_RECIPENAME + " = \"" +
                recipeName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Recipe recipe = new Recipe();

        if(cursor.moveToFirst()){
            recipe.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_RECIPES, COL_ID + " =?", new String[]{String.valueOf(recipe.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}
