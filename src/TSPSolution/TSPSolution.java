package TSPSolution;

import java.util.ArrayList;
import java.util.Collections;

import Helpers.TSPInstance;

public class TSPSolution {
	private TSPInstance tspInstance;
	private LocalSearch localSearchAlgorithm;
	private ArrayList<ArrayList<Integer>> initialPopulation = new ArrayList<ArrayList<Integer>>();
	private static int TOTAL_POPULATION;

	public TSPSolution(TSPInstance tsp, LocalSearch l) {
		this.tspInstance = tsp;
		this.calculateMaxPopulation();
		this.localSearchAlgorithm = l;
	}

	public void runAlgorithm() {
		try {
			this.initiatePopulation();
			System.out.println(" Population without improvement");
			this.printInitialPopulation();
			this.improvePopulation();
			System.out.println(" Population with improvement");
			this.printInitialPopulation();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Calculate all possibles genes in the population
	 */
	private void calculateMaxPopulation() {
		ArrayList<Integer> cities = this.tspInstance.getCities();
		int fact = 1;
		for (int i = 2; i <= cities.size(); i++) {
			fact = fact * i;
		}
		TSPSolution.TOTAL_POPULATION = fact;
	}
	
	/**
	 * Improve the initial population with local search algorithm
	 */
	@SuppressWarnings("unchecked")
	private void improvePopulation() {
		try {
			System.out.println("\n Improving population......");
			int populationToImprove = (int) Math.ceil(TSPSolution.TOTAL_POPULATION * 0.30);
			for (int i=0; i < populationToImprove; i++) {
				ArrayList<Integer> currentGene = (ArrayList<Integer>) this.initialPopulation.get(i).clone();
				ArrayList<Integer> improveGene = localSearchAlgorithm.runLocalSearchAlgorithm(currentGene);
				this.initialPopulation.set(i, improveGene);
			}
			System.out.println("\n Population improved successfully \n");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Initiate the population with new genes
	 */
	private void initiatePopulation() {
		System.out.println("Generating initial population randomly.........\n");
		int i = 0;
		while (i < TOTAL_POPULATION) {
			ArrayList<Integer> newGene = this.generateGene();
			if (!existGene(newGene)) {
				this.initialPopulation.add(newGene);
				i++;
			}
		}
		System.out.println("Generation of initial population finished \n");
	}

	/**
	 * Create gene for TSP problem randomizing the possible solutions given an array
	 * of cities
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> generateGene() {
		ArrayList<Integer> newGene = new ArrayList<Integer>();
		ArrayList<Integer> cities = tspInstance.getCities();
		newGene = (ArrayList<Integer>) cities.clone();
		Collections.shuffle(newGene);
		return newGene;
	}

	/**
	 * Print all initial population
	 */
	private void printInitialPopulation() {
		for (int i = 0; i < initialPopulation.size(); i++) {
			System.out.println("Gene - " + i);
			ArrayList<Integer> population = initialPopulation.get(i);
			for (Integer city : population) {
				System.out.print("| " + city + "");
			}
			System.out.println("");
		}

	}

	/**
	 * Check if the new gene exist in the population
	 * 
	 * @param solution
	 * @return
	 */
	private boolean existGene(ArrayList<Integer> solution) {
		try {
			if (initialPopulation.size() > 0) {
				for (ArrayList<Integer> gene : initialPopulation) {
					for (int i = 0; i < gene.size(); i++) {
						if (gene.get(i) != solution.get(i))
							return false;
					}
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			throw e;
		}
	}
	
}
