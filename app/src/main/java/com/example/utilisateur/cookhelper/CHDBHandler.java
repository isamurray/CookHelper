package com.example.utilisateur.cookhelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by ced on 2016-11-30.
 */

public class CHDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
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
                COL_RECIPECOOKTIME + " INTEGER" + ")";
        
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
        values.put(COL_PARENT_RECIPE,1);
        values.put(COL_INGREDIENTNAME,ingredient.getName());//need to track...
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
        values.put(COL_RECIPETYPES_TYPE,recipeType);// need to track what
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
        values.put(COL_CAT_CATEGORY,recipeCategory);// need to track what
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

    /**
     * Get array of recipes that is sorted alphabetically (I hope...)
     */
    public LinkedList<Recipe> getAllRecipesLList(){
        String query = "Select * FROM " + TABLE_RECIPES + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int queryCount = cursor.getCount();

        LinkedList<Recipe> recipes = new LinkedList<Recipe>();
        if(cursor.moveToFirst()){
            for(int i=0; i < queryCount; i++){
                int index = cursor.getInt(0);
                String title = cursor.getString(1);
                String type = cursor.getString(2);
                String category = cursor.getString(3);
                int time = cursor.getInt(4);
                recipes.add(new Recipe(index,title,type,category,time));
                cursor.moveToNext();
            }

        }

        cursor.close();


        LinkedList<Recipe> returnedrecipes = new LinkedList<Recipe>();
        Iterator<Recipe> iter = recipes.iterator();
        while(iter.hasNext()){
            int classeur = 0;
            Iterator<Recipe> iter1 = returnedrecipes.iterator();
            Recipe rec = iter.next();
            boolean notadded = true;
            while(iter1.hasNext()){

                Recipe tocompareto= iter1.next();
                int reclength = rec.getTitle().length();
                int tocomparetolength =  tocompareto.getTitle().length();
                int recint = 0;
                int tocomparetoint = 0;

                while(recint<reclength && tocomparetoint < tocomparetolength){
                    if(rec.getTitle().charAt(recint) < tocompareto.getTitle().charAt(tocomparetoint) ){
                        returnedrecipes.add(classeur,rec);
                        notadded = false;
                        break;
                    }else if(rec.getTitle().charAt(recint) > tocompareto.getTitle().charAt(tocomparetoint) ){
                        break;
                    }else{
                        recint++;
                        tocomparetoint++;
                    }
                }

                classeur++;

            }
            if(notadded){
                returnedrecipes.add(classeur,rec);

            }
        }
        return returnedrecipes;
    }

    /**
     *@param type
     *@param category
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
    public LinkedList<Recipe> searchRecipe(String type, String category, String instructions) {
        LinkedList<Recipe> recipeDatabase = getAllRecipesLList();
        int[] score = new int[recipeDatabase.size()];
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int i = 0;
        while (iter.hasNext()) {
            score[i] = 0;
            Recipe r = iter.next();
            if(type!=null){
                if (r.getType() == type) {
                    score[i]++;
                }
            }
            if(category!=null){
                if (r.getCategory() == category) {
                    score[i]++;
                }
            }
            i++;
        }
        int previousSpace = 0;
        LinkedList<String> test = new LinkedList<String>();
        if (instructions != null) {

            for (int j = 0; j < instructions.length(); j++) {
                if (instructions.charAt(j) == ' ' || j == instructions.length() - 1) {
                    test.push(instructions.substring(previousSpace, j - 1));
                    previousSpace = j + 1;
                }
            }
            String first = "";
            String second = "";
            String third = "";

            while (!test.isEmpty()) {

                if (first != "") {
                    first = test.pop();
                }

                if (!test.isEmpty()) {
                    second = test.pop();
                }

                if (second != "") {

                    if (second.equals("OR")) {

                        if (!test.isEmpty()) {
                            third = test.pop();
                            OR(recipeDatabase,first, third, score);
                            first = "";
                            second = "";
                            third = "";

                        } else if (second.equals("NOT")) {
                            NOT(recipeDatabase,first, score);
                            first = "";
                            second = "";
                            third = "";

                        } else if (second.equals("AND")) {
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

                        } else {
                            ONE(recipeDatabase,first, score);
                            first = second;
                        }
                    }

                } else {
                    ONE(recipeDatabase,first, score);
                    first = second;
                }
            }
        }

        LinkedList<Recipe> returnedlist = new LinkedList<Recipe>(); // initie la liste de retour
        i = 0;
        int recipeindex = 0; // pour guarder en place la location que l'on ajoute dans la liste
        LinkedList<Integer> scorelist = new LinkedList<Integer>(); // pour garder la position dans score que chaque recette que j'ajoute dans returnedlist est.
        Iterator<Recipe> iterRecipe = recipeDatabase.iterator();
        Boolean added = false; // pour s'assurer que si l'element que j'observe est le plus petit qu'on le met a la fin

        while (iterRecipe.hasNext() && i < score.length) {
            Recipe rec = iter.next();
            Iterator<Recipe> rliter = returnedlist.iterator(); // pour comparer  a la recette rec que l'on desire ajouter a la liste
            Iterator<Integer> sliter = scorelist.iterator(); // pour pouvoir comparer les scores


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

                        if (comp.getStars() < returnedlist.get(i).getStars()) {
                            returnedlist.add(recipeindex, rec);
                            scorelist.add(recipeindex, score[i]);
                            added = true;
                            break;
                        }
                        recipeindex++;

                    }


                }
                if (!added) { //si non ajouter on l'ajoute a la fin de la liste
                    returnedlist.add(recipeindex,rec);
                    scorelist.add(recipeindex,score[i]);

                }
                recipeindex = 0;
                i++;
                added = false;
            }

        }
        return returnedlist;

    }
    private void ONE(LinkedList<Recipe> recipeDatabase,String first, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            //Iterator<IngredientQuantity> iterI = r.getIngredients().iterator();

            //while (iterI.hasNext()) {

            if (score[index] == -1) { //verifie que la recette n'a pas deja un NOT
                break;
            }
            //  String s = iterI.next().getIngredient().getName();

            //if (s == first) { //attribue un point si la recette a l'ingredient demander.
            score[index]++;
            break;
        }
    }
    // index++;
    // }

    //   }

    private void NOT(LinkedList<Recipe> recipeDatabase,String first, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            //Iterator<IngredientQuantity> iterI = r.getIngredients().iterator();

            //while (iterI.hasNext()) {

            if (score[index] == -1) {
                break;
            }
            //  String s = iterI.next().getIngredient().getName();

            //if (s == first) {
            score[index] = -1;
            break;
        }
    }
    //     index++;
    //}
    // }

    private void OR(LinkedList<Recipe> recipeDatabase,String first, String third, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            // Iterator<IngredientQuantity> iterI = r.getIngredients().iterator();

            // while (iterI.hasNext()) {
            if (score[index] == -1) {
                break;
            }
            //  String s = iterI.next().getIngredient().getName();

            //  if (s == first || s == third) {
            score[index]++;
            break;
        }

        //  }
        //  index++;
        //   }

    }

    private void AND(LinkedList<Recipe> recipeDatabase,String first, String third, int[] score) {
        Iterator<Recipe> iter = recipeDatabase.iterator();
        int index = 0;
        Boolean and1 = false;
        Boolean and2 = false;

        while (iter.hasNext()) {
            Recipe r = iter.next();
            //Iterator<IngredientQuantity> iterI = r.getIngredients().iterator();

            //  while (iterI.hasNext()) {
            if (score[index] == -1) {
                break;
            }
            //  String s = iterI.next().getIngredient().getName();

            //  if (s == first) {
            and1 = true;
            //}
            //  if (s == third) {
            and2 = true;
            //   }
            //  if (and1 && and2) {
            //   score[index]++;
            //  break;
            // }

        }
        index++;
        and1 = false;
        and2 = false;
    }

}