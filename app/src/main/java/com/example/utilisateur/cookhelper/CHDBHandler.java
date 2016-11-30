package com.example.utilisateur.cookhelper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ced on 2016-11-30.
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;


/**
 * Will be required to add, query and delete data on
 * behalf of the activity component.
 */

// TODO: implement the add query and remove db table entries
// we'll have addRecipe()

public class CHDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cookhelperDB.db";
    public  static final String TABLE_RECIPES = "recipes";
    public  static final String TABLE_INGREDIENTS = "ingredients";
    // Mix egg in bowl... x N recipes, or mix egg in bowl x 1
    public  static final String TABLE_INSTRUCTIONS = "recipes";

    public static final String COLUMN_ID = "_id"; // should be in recipe class
    public static final String COLUMN_RECIPENAME = "title";
    public static final String COLUMN_RECIPECOUNTRY = "type";
    public static final String COLUMN_RECIPEDISHTYPE = "category";
    public static final String COLUMN_RECIPECOOKTIME = "time";

    public CHDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // implemented to create initial recipe table when db init'
    // create table
    // create columns 1340
    // pass to execSQL() of SQLiteDatabase from param
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_RECIPE_TABLE = "CREATE TABLE" +
                TABLE_RECIPES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RECIPENAME + " TEXT," +
                COLUMN_RECIPECOUNTRY + " TEXT," +
                COLUMN_RECIPEDISHTYPE + " TEXT," +
                COLUMN_RECIPECOOKTIME + " INTEGER" + ")"; // should have ; in ")" => ");" ???

        db.execSQL(CREATE_RECIPE_TABLE);
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
