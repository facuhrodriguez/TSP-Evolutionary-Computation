package survivorSelection;

import java.util.ArrayList;

import parentsSelection.ParentsSelection;

public abstract class SurvivorSelection {
	private int totalPopulation;
	protected ParentsSelection selectionMechanism;
	protected String name;
	protected ArrayList<ArrayList<Integer>> population;
	
	SurvivorSelection(String name) {
		this.name = name;
	}
	
	public abstract ArrayList<ArrayList<Integer>> generateSurvivors(ArrayList<ArrayList<Integer>> currentPopulation);
	
	protected ArrayList<ArrayList<Integer>> getPopulation() {
		return this.population;
	}
	
	protected int getTotalPopulation() {
		return this.totalPopulation;
	}
	
	protected void setTotalPopulation(int p) {
		this.totalPopulation = p;
	}

	public String getName() {
		return name;
	}

}
