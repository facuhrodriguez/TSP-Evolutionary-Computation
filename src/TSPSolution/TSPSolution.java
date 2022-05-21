package TSPSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Helpers.ComparatorIndividuals;
import Helpers.Logger;
import Helpers.TSPInstance;
import localSearch.LocalSearch;
import mutation.GeneticMutation;
import parentsSelection.ParentsSelection;
import recombination.Recombination;
import survivorSelection.SurvivorSelection;

public class TSPSolution {
	
	private TSPInstance tspInstance;
	private LocalSearch localSearchAlgorithm;
	private ParentsSelection parentSelection;
	private Recombination recombination;
	private GeneticMutation mutation;
	private SurvivorSelection survivorSelection;
	
	private ArrayList<ArrayList<Integer>> population = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> matingPool = new ArrayList<ArrayList<Integer>>();
	
	// Parámetros
	private int totalPopulation = 100;
	private final double LOCAL_SEARCH_PERC = 0.4;
	private ComparatorIndividuals comparator;
	private final int MAX_ITERATIONS = 6000;
	private final double MUTATION_PROB = 0.1;
	private final double RECOMBINATION_PROB = 0.9;
	
	private Logger logger;


	public TSPSolution(TSPInstance tsp, LocalSearch l, Logger logger, Recombination r, GeneticMutation m, ParentsSelection p, SurvivorSelection s, int totalPop) {
		this.logger = logger;
		this.tspInstance = tsp;
		this.comparator = new ComparatorIndividuals(tspInstance);
		this.localSearchAlgorithm = l;
		this.parentSelection = p;
		this.recombination = r;
		this.mutation = m;
		this.survivorSelection = s;
		this.totalPopulation = totalPop;
	
	}
	
	public void setInitialPopulation(ArrayList<ArrayList<Integer>> pop) {
		this.population = pop;
	}

	public void runAlgorithm() {
		try {
			writeHeader();
			int count = 1;
			long start1 = System.currentTimeMillis();

			System.out.println("Corriendo algoritmo.............");
			logger.writeRow("\nResultados: ");
			logger.writeRow("\t Fitnes promedio inicial (sin mejora): " + this.avgFitness());
			
			// Improve population with local search
			this.improvePopulation();
			
			logger.writeRow("\t Fitness promedio inicial (con mejora de búsqueda local): " + this.avgFitness());
			logger.writeRow("\t Mejor fitness población inicial (con mejora de búsqueda local): " + tspInstance.fitnessFunction(getBestSolution()));
			
			
			while (count <= this.MAX_ITERATIONS) {
				// Select parents in the Mating Pool
				this.generateMatingPool();
				
				// Recombine and generate new breed
				this.generateRecombination();

				// Generate offspring
				this.generateOffspring();
				ArrayList<Integer> bestSolution = this.getBestSolution();
				logger.writeRow("\t Mejor solución de la iteracion " + count + ": " + bestSolution);
				logger.writeRow(
						"\t Mejor fitness para la iteración " + count + ": " + tspInstance.fitnessFunction(bestSolution));
				logger.writeRow("\t Fitness promedio para la iteración " + count + ": " + this.avgFitness());

				count++;
			}
			
			
			long end1 = System.currentTimeMillis();        
			ArrayList<Integer> bestSolution = this.getBestSolution();
			logger.writeRow("\nResultados finales: ");
			logger.writeRow("\t Fitness promedio final: " + this.avgFitness());
			logger.writeRow("\t Mejor solución final: " + bestSolution);
			logger.writeRow("\t Fitness Mejor solución final: " + tspInstance.fitnessFunction(bestSolution));
			logger.writeRow("\t Costo del camino de la mejor solución final: " + 1 / tspInstance.fitnessFunction(bestSolution));
			logger.writeRow("\t Tiempo total de ejecución del algoritmo: "+ (end1-start1) + " ms.");    
		} catch (Exception e) {
			throw e;
		}
	}

	private void writeHeader() {
		logger.writeRow(" -------------------- Algoritmo memético ------------------");
		logger.writeRow("Búsqueda local aplicada al " + (this.LOCAL_SEARCH_PERC * 100) +  " de la población inicial");
		logger.writeRow("Parametros: ");
		logger.writeRow("\t Poblacion total: " + this.totalPopulation);
		logger.writeRow("\t Condición de corte: " + this.MAX_ITERATIONS + " iteraciones.");
		logger.writeRow("\t Probabilidad de cruce: " + this.RECOMBINATION_PROB);
		logger.writeRow("\t Probabilidad de mutación: " + this.MUTATION_PROB);
		logger.writeRow("\t Mecanismo de selección de padres: " + this.parentSelection.getName());
		logger.writeRow("\t Mecanismo de generación de hijos: " + this.recombination.getName());
		logger.writeRow("\t Mecanismo de mutación: " + this.mutation.getName());
		logger.writeRow("\t Mecanismo de elección de supervivientes: " + this.survivorSelection.getName());
	}

	/**
	 * Mutate gene
	 */
	private ArrayList<Integer> mutateGene(ArrayList<Integer> geneToMutate) {
		try {
			double randomValue = Math.random();
			if (randomValue >= (1 - MUTATION_PROB)) {
				ArrayList<Integer> newGene = mutation.mutate(geneToMutate);
				return newGene;
			}
			return geneToMutate;
		} catch (Exception e) {
			throw e;
		}
	}

	private void generateMatingPool() {
		try {
			for (int i=0; i < this.totalPopulation; i++) {
				parentSelection.setPopulation(population);
				parentSelection.setTotalFitness(tspInstance.getTotalFitness(this.population));
				int parentIndex = parentSelection.generateParent();
				ArrayList<Integer> selectedParent = population.get(parentIndex);
				matingPool.add(selectedParent);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private int getRandomGene() {
		Random r = new Random();
		int low = 0;
		int high = this.totalPopulation - 1;
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
			int count = 0;
			while (count < (this.totalPopulation / 2)) {
				int randomPos1 = getRandomGene();
				int randomPos2 = getRandomGene();
				ArrayList<Integer> firstParent = matingPool.get(randomPos1);
				ArrayList<Integer> secondParent = matingPool.get(randomPos2);
				double randomValue = Math.random();
				if (randomValue >= (1 - RECOMBINATION_PROB)) {
					// Generate new offspring
					ArrayList<Integer> newBreed = recombination.recombinate(firstParent, secondParent);
					// Mutate gene
					ArrayList<Integer> mutatedGene = this.mutateGene(newBreed);
					this.population.add(mutatedGene);
				}
				count++;
			}
			

		} catch (Exception e) {
			throw e;
		}
	}


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
	@SuppressWarnings("unchecked")
	private void generateOffspring() {
		ArrayList<ArrayList<Integer>> newOffspring = survivorSelection.generateSurvivors(population);
		this.population = (ArrayList<ArrayList<Integer>>) newOffspring.clone();
	}

	/**
	 * Improve the initial population with local search algorithm
	 */
	@SuppressWarnings("unchecked")
	private void improvePopulation() {
		try {
			int populationToImprove = (int) Math.ceil(this.totalPopulation * this.LOCAL_SEARCH_PERC);
			for (int i = 0; i < populationToImprove; i++) {
				ArrayList<Integer> currentGene = (ArrayList<Integer>) this.population.get(i).clone();
				ArrayList<Integer> improveGene = localSearchAlgorithm.runLocalSearchAlgorithm(currentGene);
				this.population.set(i, improveGene);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	

	/**
	 * Print all initial population
	 */
	public void printInitialPopulation() {
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
	 * Get initial population
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> getInitialPopulation() {
		return population;
	}

}
