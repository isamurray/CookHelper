package com.example.utilisateur.cookhelper;
import java.util.ArrayList;

import java.util.ArrayList;

/**
 * Created by ced on 2016-11-30.
 */

public class Recipe {

    private String title, type, category; //type  = entree sauce // category = vegetarien italien
    private int cookingTime;
    //private CookingTime cookingTime;
    private int stars  = 0, servings;
    private ArrayList<String> ingredientsList;
    private ArrayList<String> instructions;
    
    //private LinkedList<IngredientQuantity> ingredients;
    //private LinkedList<Instruction> instructions;
    //private int _id;

    /**
     * This is a simple constructor of the Recipe class.
     * @param title
     * @param type
     * @param category
     * @param time
     */

    
     // public Recipe(String title, String type, String category,float time,String timeUnit,
     // LinkedList<IngredientQuantity> ingredients,LinkedList<Instruction> instructions, CookingTime cookingTime, int servings
     // ,int _id){
     // this.title = title;
     // this.type = type;
     // this.category = category;
     // this.ingredients = ingredients;
     // this.instructions = instructions;
     // this.cookingTime = cookingTime;
     // this.servings = servings;
     // this._id = _id;
// 
     // }

    public Recipe(String title, String type, String category,int time){
        this.title = title;
        this.type = type;
        this.category = category;
        this.ingredientsList = null;

        this.cookingTime = time;
        ArrayList<String> struct = new ArrayList<String>();
        struct.add("Go to kitchen");
        this.instructions = struct;

    }
    public Recipe(){} //DUMMY METHOD NEEDS TO BE DELETED

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setCookingTime(int time){
        this.cookingTime = time;
    }
    public int getCookingTime(){
        return cookingTime;
    }

    //public CookingTime getCookingTime() {
    //    return cookingTime;
    //}
    //public void setCookingTime(CookingTime cookingTime) {
    //    this.cookingTime = cookingTime;
    //}
    public int getStars() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }

    //public ArrayList<String> getIngredients() {
    //    return ingredients;
    //}
    //public void setIngredients(LinkedList<IngredientQuantity> ingredients) {
    //    this.ingredients = ingredients;
    //}
    //public LinkedList<Instruction> getInstructions() {
    //    return instructions;
    //}
    //public void setInstructions(LinkedList<Instruction> instructions) {
    //    this.instructions = instructions;
    //}
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
    //public int getID() {
    //    return _id;
    //}
    //public void setID(int _id) {
    //    this._id = _id;
    //}
}





