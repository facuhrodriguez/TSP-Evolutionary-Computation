package parentsSelection;

import java.util.ArrayList;

public abstract class ParentsSelection {
	private ArrayList<ArrayList<Integer>> population;
	double totalFitness;
	protected String name;
	
	ParentsSelection(String name) {
		this.name = name;
	}

	public ParentsSelection(ArrayList<ArrayList<Integer>> t) { 
		this.population = t;
	}
	
	public abstract int generateParent();
	
	public ArrayList<ArrayList<Integer>> getPopulation() {
		return this.population;
	}
	
	public void setPopulation(ArrayList<ArrayList<Integer>> population) {
		this.population = population;
	}
	
	public void setTotalFitness(double fitness) {
		this.totalFitness = fitness;
	}
	
	public String getName() {
		return this.name;
	}
}
