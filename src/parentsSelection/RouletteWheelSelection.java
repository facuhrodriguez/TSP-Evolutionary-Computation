package parentsSelection;

import java.util.ArrayList;

import Helpers.ComparatorIndividuals;
import Helpers.TSPInstance;

public class RouletteWheelSelection extends ParentsSelection {
	private ComparatorIndividuals comparator;
	private TSPInstance tsp;
	public RouletteWheelSelection(ArrayList<ArrayList<Integer>> t, ComparatorIndividuals comp, TSPInstance tsp) {
		super("Rueda de la ruleta");
		this.comparator = comp;
		this.tsp = tsp;
	}

	@Override
	@SuppressWarnings("unchecked")
	/**
	 * Generate parent to cross, using Roulette Wheel Selection
	 */
	public int generateParent() {
		try {
			ArrayList<ArrayList<Integer>> initialPopulation = (ArrayList<ArrayList<Integer>>) this.getPopulation()
					.clone();
			initialPopulation.sort(this.comparator);
			ArrayList<Double> positions = generatePositions(initialPopulation);
			double randomNmber1 = Math.random();
			int parent1 = getParent(randomNmber1, positions);
			return parent1;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Generate a distributed probability using the fitness function
	 * 
	 * @param initialPopulation
	 * @return the positions of each gene
	 */
	private ArrayList<Double> generatePositions(ArrayList<ArrayList<Integer>> initialPopulation) {
		ArrayList<Double> positions = new ArrayList<Double>();
		double count = 0.0;
		for (ArrayList<Integer> gene : initialPopulation) {
			double fitnessGene = this.tsp.fitnessFunction(gene);	
			double positionValue = fitnessGene / this.totalFitness;
			count += positionValue;
			positions.add(count);
		}
		
		return positions;
	}

	/**
	 * Given a random number, select the place in the roulette to select the gene
	 * 
	 * @param randomNmb
	 * @param positions
	 * @return the parent to be cross
	 */
	private int getParent(double randomNmb, ArrayList<Double> positions) {
		try {
			double count = 0.0;
			for (int i = 0; i < positions.size(); i++) {
				count += positions.get(i);
				if (count >= randomNmb)
					return i;
			}
			return positions.size() - 1;
		} catch (Exception e) {
			throw e;
		}
	}

}
