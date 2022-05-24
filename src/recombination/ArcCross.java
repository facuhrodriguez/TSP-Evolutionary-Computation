package recombination;

import java.util.ArrayList;
import java.util.HashMap;

public class ArcCross extends Recombination {

	private HashMap<Integer, ArrayList<Integer>> adjacencies = new HashMap<Integer, ArrayList<Integer>>();
	private ArrayList<Integer> newBreed1 = new ArrayList<Integer>();
	private ArrayList<Integer> newBreed2 = new ArrayList<Integer>();
	
	public ArcCross() {
		super("Cruce basado en arcos");
	}

	@Override
	public ArrayList<Integer> getFirstBreed(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			this.newBreed1 = new ArrayList<Integer>();
			this.newBreed2 = new ArrayList<Integer>();
			this.createAdjacencies(parent1, parent2);
			double randomValue = Math.random();
			int firstArc, secondArc;
			if (randomValue > 0.5) {
				firstArc = parent1.get(0);
				secondArc = parent2.get(0);
			} else {
				secondArc = parent1.get(0);
				firstArc = parent2.get(0);
			}
			
			int previousArc1 = firstArc;
			int previousArc2 = secondArc;
			newBreed1.add(firstArc);
			newBreed2.add(secondArc);
			generateBreed(parent1, previousArc1, newBreed1);
			generateBreed(parent2, previousArc2, newBreed2);
		
			return newBreed1;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<Integer> getSecondBreed(ArrayList<Integer> parent1, ArrayList<Integer> parent2) { 
		return this.newBreed2;
	}
	

	private void generateBreed(ArrayList<Integer> parent, int previousArc, ArrayList<Integer> newBreed) {
		boolean lastElement = false;
		while (!lastElement) {
			int newCity;
			newCity = this.selectNextArc(previousArc, newBreed);
			if (newBreed.contains(newCity)) {
				newCity = getRandomCity(parent, newBreed);
			}
			newBreed.add(newCity);
			previousArc = newCity;
			lastElement = newBreed.size() == parent.size();
		}
	}

	private void createAdjacencies(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			for (int i = 0; i < parent1.size(); i++) {
				Integer city = parent1.get(i);
				ArrayList<Integer> adjacencies = getAdjacencies(parent1, i);
				int indexCity = parent2.indexOf(city);
				ArrayList<Integer> adj2 = new ArrayList<Integer>();
				if (indexCity != -1) {
					adj2 = getAdjacencies(parent2, indexCity);
				}
				adjacencies.addAll(adj2);
				this.adjacencies.put(city, adjacencies);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private ArrayList<Integer> getAdjacencies(ArrayList<Integer> parent, int index) {
		ArrayList<Integer> adj = new ArrayList<Integer>();
		if (index == 0) {
			adj.add(parent.get(parent.size() - 1));
			adj.add(parent.get(1));
		} else {
			if (index == parent.size() - 1) {
				adj.add(parent.get(index - 1));
				adj.add(parent.get(0));
			} else {
				adj.add(parent.get(index + 1));
				adj.add(parent.get(index - 1));
			}
		}

		return adj;
	}

	private int selectNextArc(int previousArc, ArrayList<Integer> newBreed) {
		try {
			ArrayList<Integer> adj = this.adjacencies.get(previousArc);
			if (adj != null) {
				if (adj.size() == 1)
					return adj.get(0);
				int minLength = Integer.MAX_VALUE;
				int indexMinLength = 0;
				for (int i = 0; i < adj.size(); i++) {
					int aux = adj.get(i);
					if (!newBreed.contains(aux)) {
						// If there are duplicated arcs return that value, else get the shortest length
						// of adjacencies
						int index = adj.lastIndexOf(aux);
						if (index != i && !newBreed.contains(aux))
							return aux;
						int lengthAdj = getLengthAdjacency(aux, newBreed);
						if ((lengthAdj < minLength) && newBreed.contains(aux)) {
							minLength = lengthAdj;
							indexMinLength = i;
						}
					}
				}
				return adj.get(indexMinLength);
			}
			return -1;
		} catch (Exception e) {
			throw e;
		}
	}

	private int getLengthAdjacency(int city, ArrayList<Integer> newBreed) {
		if (adjacencies.containsKey(city)) {
			ArrayList<Integer> adj = this.adjacencies.get(city);
			int counter = 0;
			for (int i = 0; i < adj.size(); i++) {
				int a = adj.get(i);
				int indexA = adj.lastIndexOf(a);
				if (indexA == i && !newBreed.contains(a))
					counter++;
			}

			return counter;
		}
		return 0;
	}

	private int getRandomCity(ArrayList<Integer> parent1, ArrayList<Integer> newBreed) {
		for (Integer p : parent1) {
			if (newBreed.indexOf(p) == -1)
				return p;
		}
		return 0;
	}
}
