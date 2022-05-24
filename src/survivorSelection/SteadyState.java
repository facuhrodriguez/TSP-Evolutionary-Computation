package survivorSelection;

import java.util.ArrayList;
import java.util.Collections;

import Helpers.ComparatorIndividuals;

public class SteadyState extends SurvivorSelection {
	private int umbralValue;
	public SteadyState(ComparatorIndividuals c, int pop) {
		super("Steady State");
		this.comparator = c;
		this.setTotalPopulation(pop);
		this.umbralValue = pop / 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ArrayList<Integer>> generateSurvivors(ArrayList<ArrayList<Integer>> currentPopulation,
			ArrayList<ArrayList<Integer>> offspring) {
		try {
			ArrayList<ArrayList<Integer>> newPop = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<Integer>> currentPop = (ArrayList<ArrayList<Integer>>) currentPopulation.clone();
			ArrayList<ArrayList<Integer>> auxOffspring = (ArrayList<ArrayList<Integer>>) offspring.clone();
			Collections.sort(currentPop, this.comparator);
			Collections.sort(auxOffspring, this.comparator);
			newPop.addAll(addNBestValues(auxOffspring));
			newPop.addAll(addNWorstValues(currentPop));
			return newPop;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private ArrayList<ArrayList<Integer>> addNWorstValues(ArrayList<ArrayList<Integer>> currentPop) {
		try {
			ArrayList<ArrayList<Integer>> newPop = new ArrayList<ArrayList<Integer>>();
			for (int i=this.umbralValue; i < this.getTotalPopulation(); i++) {
				newPop.add(currentPop.get(i));
			}
			return newPop;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private ArrayList<ArrayList<Integer>> addNBestValues(ArrayList<ArrayList<Integer>> offspring) {
		try {
			ArrayList<ArrayList<Integer>> newPop = new ArrayList<ArrayList<Integer>>();
			for (int i=0; i < this.umbralValue; i++) {
				int indexToAdd = offspring.size() - 1 - i;
				newPop.add(offspring.get(indexToAdd));
			}
			return newPop;
		} catch (Exception e) {
			throw e;
		}
		
	}

}
