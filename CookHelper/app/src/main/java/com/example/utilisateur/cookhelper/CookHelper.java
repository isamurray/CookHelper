import java.util.LinkedList;
public class CookHelper {
	private CookHelper cookHelper;
	private LinkedList<Ingredient> ingredientDatabase;
	
	private LinkedList<Recipe> recipeDatabase;
	private CookHelper(){
		
	}
	public CookHelper getInstance(){
		if(cookHelper == null){
			this.cookHelper = new CookHelper();
		}
		return cookHelper;
	}
	public LinkedList<Ingredient> getIngredientDatabase() {
		return ingredientDatabase;
	}
	public void setIngredientDatabase(LinkedList<Ingredient> ingredientDatabase) {
		this.ingredientDatabase = ingredientDatabase;
	}
	public LinkedList<Recipe> getRecipeDatabase() {
		return recipeDatabase;
	}
	public void setRecipeDatabase(LinkedList<Recipe> recipeDatabase) {
		this.recipeDatabase = recipeDatabase;
	}
	
	public void addIngredient(Ingredient ingredient){ingredientDatabase.add(ingredient);}
	public void addRecipe(Recipe recipe){recipeDatabase.add(recipe);}
	public void addRecipe(String title,String type,String category, LinkedList<String> instructions,LinkedList<Ingredient> ingredients,
						  LinkedList<float> quantity,LinkedList<String> quantityType,int preptime,int cooktime)throws IllegalArgumentException(){
		Iterator iter = quantity.iterator();
		Iterator iter2 = ingredients.iterator();
		Iterator iter3 = quantityType.iterator();
		LinkedList<IngredientsQuantity> quantityLinkedList = new LinkedList<IngredientsOccurance>();
		if (ingredients.size() != quantity.size() || ingredients.size() != quantityType.size() || quantity.size() != quantityType.size()
				) {
			throw new IllegalArgumentException("The size of the list of ingredients and quantity are not the same")
		}
		while (iter.hasNext()) {
			float f = iter.next();
			Ingredient i = iter2.next();
			String s = iter3.next();
			IngredientQuantity iOcc = new IngredientQuantity(i, f, s);
			quantityLinkedList.add(iOcc);
			i.addOccurance(iOcc);
		}
		iter = instructions.iterator();
		LinkedList<Instruction> instructionLinkedList = new LinkedList<Instruction>();
		while (iter.hasNext()) {
			instrucitons.add(new Instruction(iter.next()));
		}

		Recipe r = new Recipe(title,type,category,quantityLinkedList,instructionLinkedList,new CookingTime(preptime,cooktime));
		recipeDatabase.add(r);
	}

	
	public void deleteRecipe(Recipe recipe){recipeDatabase.remove(recipe);}
	public boolean deleteRecipeByName(String name){
		Iterator iter = recipeDatabase.iterator();
		while(iter.hasNext()){
			Recipe r = iter.next();
			if(r.getTitle() == name){
				recipe.database.remove(r);
				return true;
			}

		}
		return false;
	}
	
	public Recipe searchRecipe(String type,String category, String instructions){Recipe a = new Recipe();
	return a;}
	public Recipe searchRecipe(String name){
		Iterator iter = recipeDatabase.iterator();
		while(iter.hasNext()){
			Recipe r = iter.next();
			if(r.getTitle() == name){
				return r;
			}

		}
		return null;
	}
}
