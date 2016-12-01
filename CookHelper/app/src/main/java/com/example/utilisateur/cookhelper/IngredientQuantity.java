
public class IngredientQuantity {
	private Ingredient ingredient;
	

	private float unit;
	private String unitType;
	private Recipe recipe;
	
	public IngredientQuantity(Ingredient ingredient, float unit, String unitType, Recipe recipe){
		this.ingredient = ingredient;
		this.unit = unit;
		this.unitType = unitType;
		this.recipe = recipe;
	}
	public IngredientQuantity(Ingredient ingredient, float unit, String unitType){
		this.ingredient = ingredient;
		this.unit = unit;
		this.unitType = unitType;
		this.recipe = null;
	}
	public Ingredient getIngredient() {return ingredient;}

	public void setIngredient(Ingredient ingredient) {this.ingredient = ingredient;}

	public float getUnit() {return unit;}

	public void setUnit(float unit) {this.unit = unit;}

	public String getUnitType() {return unitType;}

	public void setUnitType(String unitType) {this.unitType = unitType;}

	public Recipe getRecipe() {return recipe;}
}
	

