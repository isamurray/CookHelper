import java.util.LinkedList;
public class Ingredient {
	private String name;
	private LinkedList<IngredientQuantity> occurances;
	
	
	public Ingredient(String name,LinkedList<IngredientQuantity> occurances){
		this.name = name;

		this.occurances = occurances;
				
	}
	public Ingredient(String name){
		this.name = name;
		occurances = null;
		}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public LinkedList<IngredientQuantity> getOccurances() {return occurances;}
	public void setOccurances(LinkedList<IngredientQuantity> occurances) {this.occurances = occurances;}
	public void addOccurance(IngredientQuantity iq){occurances.add(iq);}
}
