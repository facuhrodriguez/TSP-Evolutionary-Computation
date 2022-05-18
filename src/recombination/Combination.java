package recombination;

import java.util.ArrayList;
import java.util.Random;

public class Combination extends Recombination {
	ArrayList<Integer> newBreed = new ArrayList<Integer>();

	@Override
	public ArrayList<Integer> recombinate(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		int pointCross = getPointCross(parent1);
		copyFirstPositions(pointCross, parent1, parent2);
		copyLastPositions(pointCross, parent1, parent2);
		return newBreed;
	}

	/**
	 * Get the point to cross
	 * 
	 * @param parent
	 * @return point to cross
	 */
	private int getPointCross(ArrayList<Integer> parent) {
		Random r = new Random();
		int low = 0;
		int high = parent.size() - 2;
		int result = r.nextInt(high - low) + low;
		return result;
	}

	/**
	 * Copy the first N values to new breed
	 * 
	 * @param indexCopy
	 * @param parent1
	 * @param parent2
	 */
	private void copyFirstPositions(int indexCopy, ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			for (int i = 0; i < indexCopy; i++) {
				newBreed.add(parent1.get(i));
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Copy the last elements from parent1 to new breed
	 * 
	 * @param indexCopy
	 * @param parent1
	 * @param parent2
	 */
	private void copyLastPositions(int indexCopy, ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			for (int i = indexCopy; i < parent1.size(); i++) {
				int minIndex = parent1.size() - 1;
				int minCity = parent2.get(minIndex);
				for (int j = indexCopy; j < parent1.size(); j++) {
					int city = parent1.get(j);
					int indexCity = parent2.indexOf(city);
					if (indexCity < minIndex && !newBreed.contains(city)) {
						minIndex = indexCity;
						minCity = city;
					}
				}
				newBreed.add(minCity);
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
