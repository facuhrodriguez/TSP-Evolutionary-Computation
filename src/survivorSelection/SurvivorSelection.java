package survivorSelection;

import java.util.ArrayList;

import parentsSelection.ParentsSelection;

public abstract class SurvivorSelection {
	private int totalPopulation;
	protected ParentsSelection selectionMechanism;
	ArrayList<ArrayList<Integer>> population;
	
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
	
}
