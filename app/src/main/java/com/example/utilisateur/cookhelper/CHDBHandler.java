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
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cookhelperDB.db";
    public  static final String TABLE_RECIPES = "recipes";
    public  static final String TABLE_INGREDIENTS = "ingredients";
    // Mix egg in bowl... x N recipes, or mix egg in bowl x 1
    public  static final String TABLE_INSTRUCTIONS = "instructions";

    public static final String COLUMN_ID = "_id"; // should be in recipe class
    public static final String COLUMN_RECIPENAME = "title";
    public static final String COLUMN_RECIPECOUNTRY = "type";
    public static final String COLUMN_RECIPEDISHTYPE = "category";
    public static final String COLUMN_RECIPECOOKTIME = "time";

    public static final String COLUMN_INGREDIENTNAME = "title";


    // INSTRUCTION TABLE CONSTANTS
    // All instructions belong to a recipe... or more?...
    public static final String COL_INSTRUCTION_PARENT_RECIPE = "parent";

    // Index of instruction in set of instructions
    // Starts at 0
    public static final String COL_INSTRUCTION_INDEX = "index";
    public static final String COL_INSTRUCTION_TEXT = "text";

    public CHDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // implemented to create initial recipe table when db init'
    // create table
    // create columns 1340
    // pass to execSQL() of SQLiteDatabase from param
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_RECIPE_TABLE = "CREATE TABLE " +
                TABLE_RECIPES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RECIPENAME + " TEXT," +
                COLUMN_RECIPECOUNTRY + " TEXT," +
                COLUMN_RECIPEDISHTYPE + " TEXT," +
                COLUMN_RECIPECOOKTIME + " INTEGER" + ")"; // should have ; in ")" => ");" ???

        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " +
                TABLE_INGREDIENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_INGREDIENTNAME + " TEXT" + ")";

        String CREATE_INSTRUCTION_TABLE = "CREATE TABLE " +
                TABLE_INSTRUCTIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COL_INSTRUCTION_PARENT_RECIPE + " INTEGER," +
                COL_INSTRUCTION_INDEX + " INTEGER," +
                COL_INSTRUCTION_TEXT + " TEXT" + ")";

        db.execSQL(CREATE_INSTRUCTION_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    // called on handler invocation when dbversion is higher than previous
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    /**
     * Add
     */
    public void addRecipe(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPENAME,recipe.getTitle());
        values.put(COLUMN_RECIPECOUNTRY,recipe.getType());
        values.put(COLUMN_RECIPEDISHTYPE,recipe.getCategory());
        values.put(COLUMN_RECIPECOOKTIME,recipe.getCookingTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECIPES,null,values);
        db.close();
    }

    public void addIngredient(Ingredient ingredient){
        ContentValues values = new ContentValues();
        values.put(COLUMN_INGREDIENTNAME,ingredient.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INGREDIENTS,null,values);
        db.close();
        System.out.println("Ingredient "+ingredient.getName()+" was added to db");
    }

    /**
     * !!!!
     * @param recipeName The recipe's name
     * !!! NOT CURRENTLY SAFE - VULN. TO SQL-INJECTIONS !!!
     * !!!! TODO: Sanitize
     */
    public Recipe findRecipe(String recipeName){
        String query = "Select * FROM " + TABLE_RECIPES +
                " WHERE " + COLUMN_RECIPENAME + " = \"" +
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
                " WHERE " + COLUMN_RECIPENAME + " = \"" +
                recipeName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Recipe recipe = new Recipe();

        if(cursor.moveToFirst()){
            recipe.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_RECIPES, COLUMN_ID + " =?", new String[]{String.valueOf(recipe.getID())});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}
