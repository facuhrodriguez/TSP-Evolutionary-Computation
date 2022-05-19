package survivorSelection;

import java.util.ArrayList;

import Helpers.ComparatorIndividuals;
import parentsSelection.ParentsSelection;

public class Elitism extends SurvivorSelection {
	private ComparatorIndividuals comparator;
	
	public Elitism(ArrayList<ArrayList<Integer>> population, ComparatorIndividuals comp, int totalPopulation, ParentsSelection p) {
		this.comparator = comp;
		this.population = population;
		this.selectionMechanism = p;
		this.setTotalPopulation(totalPopulation);
	}
	
	@Override
	public ArrayList<ArrayList<Integer>> generateSurvivors(ArrayList<ArrayList<Integer>> currentPopulation) {
		ArrayList<ArrayList<Integer>> initialPopulation = this.getPopulation();
		ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<ArrayList<Integer>>();
		initialPopulation.sort(this.comparator);
		newPopulation.add(initialPopulation.get(initialPopulation.size() - 1));
		for (int i=1; i < this.getTotalPopulation(); i++) {
			selectionMechanism.setPopulation(initialPopulation);
			int getNewGeneIndex = selectionMechanism.generateParent();
			newPopulation.add(initialPopulation.get(getNewGeneIndex));
			initialPopulation.remove(getNewGeneIndex);
		}
		return newPopulation;
	}

	

}
