package TSPSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Helpers.ComparatorIndividuals;
import Helpers.Logger;
import Helpers.TSPInstance;
import localSearch.LocalSearch;
import mutation.GeneticMutation;
import mutation.InversionMutation;
import parentsSelection.ParentsSelection;
import parentsSelection.RouletteWheelSelection;
import recombination.ArcCross;
import recombination.Combination;
import recombination.Recombination;
import survivorSelection.Elitism;
import survivorSelection.SurvivorSelection;

public class TSPSolution {
	private TSPInstance tspInstance;
	private LocalSearch localSearchAlgorithm;
	private ArrayList<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>();
	private final int TOTAL_POPULATION = 2000;
	private ArrayList<Integer> firstParent = new ArrayList<Integer>();
	private ComparatorIndividuals comparator;
	private ArrayList<Integer> secondParent = new ArrayList<Integer>();
	private final int MAX_ITERATIONS = 10;
	private final double MUTATION_PROB = 0.2;
	private final double RECOMBINATION_PROB = 0.9;
	private Logger logger;

	public TSPSolution(TSPInstance tsp, LocalSearch l, Logger logger) {
		this.logger = logger;
		this.tspInstance = tsp;
		this.comparator = new ComparatorIndividuals(tspInstance);
//		this.calculateMaxPopulation();
		this.localSearchAlgorithm = l;
	}

	public void runAlgorithm() {
		try {
			int count = 0;
			// Initiate random population
			this.initiatePopulation();
			logger.writeRow("Initial average fitness (Without improve): " + this.avgFitness());

			// Improve population
			this.improvePopulation();
			logger.writeRow("Initial average fitness (with improve): " + this.avgFitness());

			while (count < this.MAX_ITERATIONS) {
				// Select parents to cross
				this.generateParents();

				// Recombine and generate new breed
				this.generateRecombination();

				// Mutate gene
				this.mutateGene();

				// Generate offspring
				this.generateOffspring();
				ArrayList<Integer> bestSolution = this.getBestSolution();
				logger.writeRow("Best solution for iteration: " + count + ": " + bestSolution);
				logger.writeRow(
						"Best fitness for iteration: " + count + ": " + tspInstance.fitnessFunction(bestSolution));
				logger.writeRow("Average fitness in iteration " + count + ": " + this.avgFitness());

				count++;
			}
			ArrayList<Integer> bestSolution = this.getBestSolution();
			logger.writeRow("Final average fitness: " + this.avgFitness());
			logger.writeRow("Best final solution: " + count + ": " + 1 / tspInstance.fitnessFunction(bestSolution));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Mutate gene
	 */
	private void mutateGene() {
		try {
			double randomValue = Math.random();
			if (randomValue <= (1 - MUTATION_PROB)) {
				logger.writeRow("Mutating gene...");
				ArrayList<Integer> randomGene = this.population.get(getRandomGene());
				GeneticMutation mutation = new InversionMutation();
				ArrayList<Integer> newGene = mutation.mutate(randomGene);
				population.set(getRandomGene(), newGene);
//				System.out.println("");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Generate new parents
	 */
	@SuppressWarnings("unchecked")
	private void generateParents() {
		try {
//			System.out.println("Selecting parents to recombine...\n");
			ArrayList<ArrayList<Integer>> aux = (ArrayList<ArrayList<Integer>>) this.population.clone();
			ParentsSelection parentSelection = new RouletteWheelSelection(population, comparator, tspInstance);
			parentSelection.setTotalFitness(getTotalFitness());
			int firstParentIndex = parentSelection.generateParent();
			this.firstParent = aux.get(firstParentIndex);
			aux.remove(firstParentIndex);
			parentSelection.setPopulation(aux);
			parentSelection.setTotalFitness(getTotalFitness());
			int secondParentIndex = parentSelection.generateParent();
			this.secondParent = aux.get(secondParentIndex);
			aux.remove(secondParentIndex);
//			System.out.println("");
		} catch (Exception e) {
			System.out.println("Error generating parents " + e.getMessage());
			throw e;
		}

	}

	private int getRandomGene() {
		Random r = new Random();
		int low = 0;
		int high = this.population.size() - 1;
		int result = r.nextInt(high - low) + low;
		return result;
	}

	private ArrayList<Integer> getBestSolution() {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<Integer>> populationClone = (ArrayList<ArrayList<Integer>>) this.population.clone();
			Collections.sort(populationClone, comparator);
			return populationClone.get(populationClone.size() - 1);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Generate new breed
	 */
	private void generateRecombination() {
		try {
			double randomValue = Math.random();
			if (randomValue >= (1 - RECOMBINATION_PROB)) {
//				System.out.println("Recombinating parents...\n");
				Recombination r = new Combination();
				ArrayList<Integer> newBreed = r.recombinate(firstParent, secondParent);
//				System.out.println("Finished recombination. New breed: " + newBreed);
//				System.out.println("");
				this.population.add(newBreed);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Calculate all possibles genes in the population
	 */
//	private void calculateMaxPopulation() {
//		ArrayList<Integer> cities = this.tspInstance.getCities();
//		int fact = 1;
//		for (int i = 2; i <= cities.size(); i++) {
//			fact = fact * i;
//		}
//		TSPSolution.TOTAL_POPULATION = fact;
//	}

	/**
	 * Calculate the average fitness for the current population
	 * 
	 * @return average fitness
	 */
	private double avgFitness() {
		double count = 0.0;
		for (ArrayList<Integer> gene : population) {
			count += tspInstance.fitnessFunction(gene);
		}
		return count / population.size();
	}

	/**
	 * Generate new offspring
	 */
	private void generateOffspring() {
		ParentsSelection parentSelection = new RouletteWheelSelection(population, comparator, tspInstance);
		SurvivorSelection offspringSelection = new Elitism(population, comparator, TOTAL_POPULATION, parentSelection);
		ArrayList<ArrayList<Integer>> offspring = offspringSelection.generateSurvivors(population);
		this.population = offspring;
	}

	/**
	 * Improve the initial population with local search algorithm
	 */
	@SuppressWarnings("unchecked")
	private void improvePopulation() {
		try {
			logger.writeRow("\nImproving population......");
			int populationToImprove = (int) Math.ceil(TOTAL_POPULATION * 0.30);
			for (int i = 0; i < populationToImprove; i++) {
				ArrayList<Integer> currentGene = (ArrayList<Integer>) this.population.get(i).clone();
				ArrayList<Integer> improveGene = localSearchAlgorithm.runLocalSearchAlgorithm(currentGene);
				this.population.set(i, improveGene);
			}
			logger.writeRow("\nPopulation improved successfully \n");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Initiate the population with new genes
	 */
	private void initiatePopulation() {
		logger.writeRow("Generating initial population randomly.........\n");
		int i = 0;
		while (i < TOTAL_POPULATION) {
			ArrayList<Integer> newGene = this.generateGene();
			if (!existGene(newGene)) {
				this.population.add(newGene);
				i++;
			}
		}
		logger.writeRow("Generation of initial population finished \n");
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
	@SuppressWarnings("unused")
	private void printInitialPopulation() {
		for (int i = 0; i < population.size(); i++) {
			System.out.println("Gene - " + i);
			ArrayList<Integer> p = population.get(i);
			for (Integer city : p) {
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
			if (population.size() > 0) {
				for (ArrayList<Integer> gene : population) {
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

	/**
	 * Get initial population
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getInitialPopulation() {
		return population;
	}

	/**
	 * Get the sum of all fitness for all the population
	 * 
	 * @return
	 */
	public double getTotalFitness() {
		try {
			double count = 0;
			for (ArrayList<Integer> gene : population) {
				count += this.tspInstance.fitnessFunction(gene);
			}
			return count;
		} catch (Exception e) {
			System.out.println("Error calculating total fitness " + e.getMessage());
			throw e;
		}
	}
}
