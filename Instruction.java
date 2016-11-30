
public class Instruction {
	private String instruction;
	private float time;
	private Recipe recipeLink;

	public Instruction(String instruction,float time, String timeUnit,Recipe recipeLink){
		this.instruction = instruction;
		this.time = time;
		this.recipeLink = recipeLink;
		
	}
	
	

	public String getInstruction() {return instruction;}
	public void setInstruction(String instruction) {this.instruction = instruction;}

	public float getTime() {return time;}
	public void setTime(float time) {this.time = time;}


	public Recipe getRecipe() {return recipeLink;}
}
