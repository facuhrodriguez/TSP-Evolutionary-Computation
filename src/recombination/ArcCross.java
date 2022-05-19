package recombination;

import java.util.ArrayList;
import java.util.HashMap;

public class ArcCross extends Recombination {

	private HashMap<Integer, ArrayList<Integer>> adjacencies = new HashMap<Integer, ArrayList<Integer>>();
	private ArrayList<Integer> newBreed = new ArrayList<Integer>();

	public ArcCross() {

	}

	@Override
	public ArrayList<Integer> recombinate(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			this.createAdjacencies(parent1, parent2);
			double randomValue = Math.random();
			int firstArc;
			if (randomValue > 0.5)
				firstArc = parent2.get(0);
			else
				firstArc = parent1.get(0);
			newBreed.add(firstArc);
			boolean lastElement = false;
			int previousArc = firstArc;
			while (!lastElement) {
				int newCity = this.selectNextArc(previousArc);
				newBreed.add(newCity);
				previousArc = newCity;
				lastElement = newBreed.size() == parent1.size();
			}

			return newBreed;
		} catch (Exception e) {
			throw e;
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
				adj.add(parent.get(parent.size() - 2));
				adj.add(parent.get(0));
			} else {
				adj.add(parent.get(index + 1));
				adj.add(parent.get(index - 1));
			}
		}
		return adj;
	}

	private int selectNextArc(int previousArc) {
		try {
			ArrayList<Integer> adj = this.adjacencies.get(previousArc);
			if (adj != null) {
				if (adj.size() == 1)
					return adj.get(0);
				int minLength = Integer.MAX_VALUE;
				int indexMinLength = 0;
				for (int i = 0; i < adj.size(); i++) {
					int aux = adj.get(i);
					if (!this.newBreed.contains(aux)) {
						// If there are duplicated arcs return that value, else get the shortest length
						// of adjacencies
						int index = adj.lastIndexOf(aux);
						if (index != i && !this.newBreed.contains(aux))
							return aux;
						int lengthAdj = getLengthAdjacency(aux);
						if ((lengthAdj < minLength) && !this.newBreed.contains(aux)) {
							minLength = lengthAdj;
							indexMinLength = i;
						}
					}
				}
				return adj.get(indexMinLength);
			} else {
				System.out.println("SAF");
			}
			return -1;
		} catch (Exception e) {
			throw e;
		}
	}

	private int getLengthAdjacency(int city) {
		if (adjacencies.containsKey(city)) {
			ArrayList<Integer> adj = this.adjacencies.get(city);
			int counter = 0;
			for (int i = 0; i < adj.size(); i++) {
				int a = adj.get(i);
				int indexA = adj.lastIndexOf(a);
				if (indexA == i && !this.newBreed.contains(a))
					counter++;
			}

			return counter;
		}
		return 0;
	}
}
