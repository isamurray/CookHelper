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
	
	
	public void deleteRecipe(Recipe recipe){recipeDatabase.remove(recipe);}
	public boolean deleteRecipeByName(String name){return false;}
	
	public Recipe searchRecipe(){Recipe a = new Recipe();
	return a;}
	public Recipe searchRecipeByName(String name){return new Recipe(); }
}
