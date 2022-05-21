package recombination;

import java.util.ArrayList;

public abstract class Recombination {
	
	protected String name;

	Recombination(String name) {
		this.name = name;
	}
	
	public abstract ArrayList<Integer> recombinate(ArrayList<Integer> parent1, ArrayList<Integer> parent2);
	
	public String getName() {
		return this.name;
	}
}
