package survivorSelection;

import java.util.ArrayList;
import java.util.Collections;

import Helpers.ComparatorIndividuals;
import Helpers.TSPInstance;
import parentsSelection.ParentsSelection;

public class Elitism extends SurvivorSelection {
	private ComparatorIndividuals comparator;
	private double totalFitness;
	private TSPInstance tsp;
	public static String name = "Elitismo";

	public Elitism(ArrayList<ArrayList<Integer>> population, ComparatorIndividuals comp, int totalPopulation,
			ParentsSelection p, TSPInstance tsp) {
		super("Elitismo");
		this.comparator = comp;
		this.population = population;
		this.selectionMechanism = p;
		this.tsp = tsp;
		this.totalFitness = tsp.getTotalFitness(population);
		this.setTotalPopulation(totalPopulation);
	}

	@Override
	public ArrayList<ArrayList<Integer>> generateSurvivors(ArrayList<ArrayList<Integer>> currentPopulation) {
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<Integer>> initialPopulation = (ArrayList<ArrayList<Integer>>) currentPopulation.clone();
		ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<ArrayList<Integer>>();
		Collections.sort(initialPopulation, this.comparator);
		newPopulation.add(initialPopulation.get(initialPopulation.size() - 1));
		initialPopulation.remove(initialPopulation.get(initialPopulation.size() - 1));
		for (int i = 0; i < this.getTotalPopulation() - 1; i++) { 
			selectionMechanism.setPopulation(initialPopulation);
			totalFitness = tsp.getTotalFitness(initialPopulation);
			selectionMechanism.setTotalFitness(totalFitness);
			int getNewGeneIndex = selectionMechanism.generateParent();
			if (getNewGeneIndex != -1) {
				newPopulation.add(initialPopulation.get(getNewGeneIndex));
				initialPopulation.remove(getNewGeneIndex);
			}
		}
		Collections.sort(newPopulation, this.comparator);
		return newPopulation;
	}

}
