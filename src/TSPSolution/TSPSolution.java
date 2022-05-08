package TSPSolution;

import java.util.ArrayList;
import java.util.Collections;

import Helpers.TSPInstance;

public class TSPSolution {
	private TSPInstance tspInstance;
	private ArrayList<ArrayList<Integer>> initialSolution = new ArrayList<ArrayList<Integer>>();

	public TSPSolution(TSPInstance tsp) {
		this.tspInstance = tsp;
	}

	/**
	 * Create gene for TSP problem randomizing the possible solutions given an array
	 * of cities
	 */
	private void generateGene() {
		ArrayList<Integer> cities = tspInstance.getCities();
		ArrayList<Integer> newGene = cities;
		Collections.shuffle(newGene);
		this.initialSolution.add(newGene);
	}
}
