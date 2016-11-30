import java.util.LinkedList;
public class Recipe {
	
	private String title, type, category; //type  = entree sauce // category = vegetarien italien
	private CookingTime cookingTime;
	private int stars  = 0, servings;
	private LinkedList<IngredientQuantity> ingredients;
	private LinkedList<Instruction> instructions;
	
/**
 * This is a simple constructor of the Recipe class.
 * @param title
 * @param type
 * @param category
 * @param time
 * @param servings
 * @param ingredients
 * @param instructionSet
 * @param comments
 * @param containNuts
 */
	
	public Recipe(String title, String type, String category,float time,String timeUnit,
			LinkedList<IngredientQuantity> ingredients,LinkedList<Instruction> instructions, CookingTime cookingTime, int servings){
		this.title = title;
		this.type = type;
		this.category = category;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.cookingTime = cookingTime;
		this.servings = servings;
		
	}
	public Recipe(){} //DUMMY METHOD NEEDS TO BE DELETED
	


	public void addInstruction(Instruction instruction){instructions.add(instruction);} // to reverify
	public void addInstructionSetWithIndex(int index,Instruction set){instructions.add(index,set);} // to reverify
	
	public void removeInstruction(Instruction instruction){instructions.remove(instruction);}
	public void removeInstructionByIndex(int index){instructions.remove(index);}
	
	
	public void addIngredientAmount(IngredientQuantity ingredient){ingredients.add(ingredient);}
	public void addIngredientAmountWithIndex(int index,IngredientQuantity ingredient){ingredients.add(index,ingredient);}
	
	public void removeIngredientAmount(IngredientQuantity ingredient){ingredients.remove(ingredient);}
	public void removeIngredientAmountByIndex(int index){ingredients.remove(index);}
	
	
	
	
	
	
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

	public CookingTime getCookingTime() {
		return cookingTime;
	}
	public void setCookingTime(CookingTime cookingTime) {
		this.cookingTime = cookingTime;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public LinkedList<IngredientQuantity> getIngredients() {
		return ingredients;
	}
	public void setIngredients(LinkedList<IngredientQuantity> ingredients) {
		this.ingredients = ingredients;
	}
	public LinkedList<Instruction> getInstructions() {
		return instructions;
	}
	public void setInstructions(LinkedList<Instruction> instructions) {
		this.instructions = instructions;
	}
	public int getServings() {
		return servings;
	}
	public void setServings(int servings) {
		this.servings = servings;
	}
}




