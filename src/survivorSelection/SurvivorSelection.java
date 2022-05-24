package survivorSelection;

import java.util.ArrayList;

import Helpers.ComparatorIndividuals;
import parentsSelection.ParentsSelection;

public abstract class SurvivorSelection {
	private int totalPopulation;
	protected ParentsSelection selectionMechanism;
	protected String name;
	protected ArrayList<ArrayList<Integer>> population;
	protected ComparatorIndividuals comparator;
	
	SurvivorSelection(String name) {
		this.name = name;
	}
	
	public abstract ArrayList<ArrayList<Integer>> generateSurvivors(ArrayList<ArrayList<Integer>> currentPopulation, ArrayList<ArrayList<Integer>> offspring);
	
	protected ArrayList<ArrayList<Integer>> getPopulation() {
		return this.population;
	}
	
	protected int getTotalPopulation() {
		return this.totalPopulation;
	}
	
	public void setTotalPopulation(int p) {
		this.totalPopulation = p;
	}

	public String getName() {
		return name;
	}

	public ComparatorIndividuals getComp() {
		return comparator;
	}

	public void setComp(ComparatorIndividuals comp) {
		this.comparator = comp;
	}

}
