package com.example.utilisateur.cookhelper;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ced on 2016-11-30.
 */

public class Recipe implements Serializable {

    private String title, type, category; //type  = entree sauce // category = vegetarien italien
    private int cookingTime;
    //private CookingTime cookingTime;
    private float stars  = 0;
    int servings;
    private ArrayList<String> ingredientsList;
    private ArrayList<String> instructions;

    public Recipe(String title, String type, String category,float stars){
        this.title = title.toLowerCase();
        this.type = type.toLowerCase();
        this.category = category.toLowerCase();
        // this.ingredientsList = null;

        this.stars = stars;
        ArrayList<String> struct = new ArrayList<String>();
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("Hope");
        struct.add("Go to kitchen");
        this.instructions = struct;
        this.ingredientsList = ingredients;

    }
    public Recipe(){} //DUMMY METHOD NEEDS TO BE DELETED

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title.toLowerCase();
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type.toLowerCase();
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category.toLowerCase();
    }
    public void setCookingTime(int time){
        this.cookingTime = time;
    }
    public int getCookingTime(){
        return cookingTime;
    }

    public float getStars() {
        return stars;
    }
    public void setStars(float stars) {
        this.stars = stars;
    }

        public int getServings() {
        return servings;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }
    
    public void addInstruction(String instruction){
        instructions.add(instruction);
    }
    public ArrayList<String> getInstructions(){
        return instructions;
    }
    public void setInstructions(ArrayList<String> instructions){
        this.instructions = instructions;
    }
    
    public void addIngredient(String ingredient){
        ingredientsList.add(ingredient.toLowerCase());
    }
    public ArrayList<String> getIngredients(){
        return ingredientsList;
    }
    public void setIngredients(ArrayList<String> ingredients){
        ingredientsList = ingredients;
    }

}





