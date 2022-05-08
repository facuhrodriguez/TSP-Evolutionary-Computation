package TSPSolution;

import java.util.ArrayList;

import Helpers.TSPInstance;

public class LocalSearch {

	TSPInstance tspInstance;
	private final static int DEPTH_CONDITION = 20;
	private final static int LIMIT_NEIGHBORS = 50;
	private int currentInitialIndex = 0;
	private int currentFinalIndex = 1;
	private final static double LIMIT_NEIGHBORHOOD_PERCENT = 0.6;

	public LocalSearch(TSPInstance t) {
		this.tspInstance = t;

	}

	/**
	 * Run the local search algorithm using the @param solution
	 * 
	 * @param solution
	 */
	public void run(ArrayList<Integer> solution) {
		try {
			ArrayList<Integer> bestSolution = solution;
			int fitnessBest = this.tspInstance.fitnessFunction(bestSolution);
			int iterations = 0;
			while (iterations <= LocalSearch.DEPTH_CONDITION) {
				int counter = 0;
				while (counter <= maxDepthNeighborhood()) {
					ArrayList<Integer> newSolution = this.generateNeighbor(solution);
					int newFitness = this.tspInstance.fitnessFunction(newSolution);
					if (newFitness > fitnessBest) {
						fitnessBest = newFitness;
						bestSolution = newSolution;
					}
					counter++;
				}
				iterations++;
			}
		} catch (Exception e) {
			System.out.println("Error run local search algorithm " + e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Generate all neighborhood possible using cross-arc movement for the @param
	 * solution
	 * 
	 * @param solution
	 */
	protected ArrayList<ArrayList<Integer>> neighborhoodGenerator(ArrayList<Integer> solution) {
		try {
			ArrayList<ArrayList<Integer>> neighboors = new ArrayList<ArrayList<Integer>>();
			int i = 0;
			int j = 1;
			while (i < solution.size()) {
				while (j < solution.size()) {
					ArrayList<Integer> auxSolution = (ArrayList<Integer>) solution.clone();
					auxSolution.set(i, solution.get(j));
					auxSolution.set(j, solution.get(i));
					j++;
					if (!neighborExists(auxSolution, neighboors)) {
						ArrayList<Integer> copySolution = (ArrayList<Integer>) auxSolution.clone();
						neighboors.add(copySolution);
					}
				}
				i++;
				j = i + 1;
			}
			return neighboors;
		} catch (Exception e) {
			System.out.println("Error generating neighborhood " + e);
			throw e;
		}
	}

	/**
	 * Generate a new neighbor given a solution
	 * 
	 * @param solution
	 * @return a new neighbor
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> generateNeighbor(ArrayList<Integer> solution) {
		try {
			ArrayList<Integer> newSolution = (ArrayList<Integer>) solution.clone();
			if (currentInitialIndex < solution.size()) {
				if (currentFinalIndex < solution.size()) {
					swapCities(solution, newSolution);
					currentFinalIndex++;
				} else {
					currentInitialIndex++;
					currentFinalIndex = currentInitialIndex + 1;
					swapCities(solution, newSolution);
				}
			}
			return newSolution;
		} catch (Exception e) {
			System.out.println("Error generating a new neighbor " + e);
			throw e;
		}
	}

	/**
	 * Swap cities from @param solution into @param newSolution
	 * 
	 * @param solution
	 * @param newSolution
	 */
	private void swapCities(ArrayList<Integer> solution, ArrayList<Integer> newSolution) {
		newSolution.set(currentInitialIndex, solution.get(currentFinalIndex));
		newSolution.set(currentFinalIndex, solution.get(currentInitialIndex));
		currentFinalIndex++;
	}

	/**
	 * Check if the neighbor to add exists in the current neighborhood
	 * 
	 * @param neighboorToAdd
	 * @return true if the neighbor already exist
	 */
	private boolean neighborExists(ArrayList<Integer> neighborToAdd, ArrayList<ArrayList<Integer>> neighboors) {
		try {
			if (neighboors.size() > 0) {
				for (ArrayList<Integer> neighboor : neighboors) {
					for (int i = 0; i < neighboor.size(); i++) {
						if (neighboor.get(i) != neighborToAdd.get(i))
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
	 * Print all neighborhood found for a solution
	 * 
	 * @param sol
	 */
	public void printNeighborhood(ArrayList<ArrayList<Integer>> sol) {
		for (int i = 0; i < sol.size(); i++) {
			System.out.println("Neighbor " + i + ":");
			for (int j = 0; j < sol.get(i).size(); j++) {
				System.out.print(sol.get(i).get(j) + " | ");
			}
			System.out.println("");
		}
	}

	/**
	 * Determine the pivot rule for neighborhood (If there are many neighbors, only
	 * explore the 60% of them)
	 * 
	 * @return Max depth of neighborhood explore
	 */
	private int maxDepthNeighborhood() {
		Integer dimension = this.tspInstance.getCities().size();
		int maxDepth = (dimension * 2) - 1;
		if (maxDepth > LocalSearch.LIMIT_NEIGHBORS) {
			int newMaxDepth = (int) (maxDepth - Math.ceil((maxDepth * LocalSearch.LIMIT_NEIGHBORHOOD_PERCENT)));
			return newMaxDepth;
		}
		return maxDepth;
	}
}
