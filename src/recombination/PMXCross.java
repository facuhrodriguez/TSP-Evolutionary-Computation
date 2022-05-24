package recombination;

import java.util.ArrayList;
import java.util.Random;

public class PMXCross extends Recombination {
	int firstCrossPoint, secondCrossPoint;
	public PMXCross(int size) {
		super("Cruce PMX");
		int firstCrossPoint = getPointCross(size);
		int secondCrossPoint = getPointCross(size);
		while (firstCrossPoint == secondCrossPoint) {
			secondCrossPoint = getPointCross(size);
		}
	}

	@Override
	public ArrayList<Integer> getFirstBreed(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			ArrayList<Integer> newBreed = new ArrayList<Integer>();
			initializeBreed(newBreed, parent1.size());
			copyValues(parent1, newBreed, firstCrossPoint, secondCrossPoint);
			copyRestValues(newBreed, parent1, parent2);
			return newBreed;
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public ArrayList<Integer> getSecondBreed(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			ArrayList<Integer> newBreed = new ArrayList<Integer>();
			initializeBreed(newBreed, parent1.size());
			copyValues(parent2, newBreed, firstCrossPoint, secondCrossPoint);
			copyRestValues(newBreed, parent2, parent1);
			return newBreed;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Get the point to cross
	 * 
	 * @param parent
	 * @return point to cross
	 */
	private int getPointCross(int size) {
		Random r = new Random();
		int low = 0;
		int high = size;
		int result = r.nextInt(high - low) + low;
		return result;
	}
	
	private void copyValues(ArrayList<Integer> parent, ArrayList<Integer> newBreed, int index1, int index2) {
		try {
			for (int i=index1; i <= index2; i++) {
				newBreed.set(i, parent.get(i));
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void initializeBreed(ArrayList<Integer> breed, int size) {
		for (int i=0; i < size; i++) {
			breed.add(-1);
		}
	}
	
	private void copyRestValues(ArrayList<Integer> breed, ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		try {
			for (int i=firstCrossPoint; i <= secondCrossPoint; i++) {
				int value = parent1.get(i);
				if (!breed.contains(value)) {
					int indexParent1 = parent1.indexOf(value);
					if (breed.get(indexParent1) == -1) {
						breed.set(indexParent1, value);
					} else {
						value = breed.get(indexParent1);
						indexParent1 = parent1.indexOf(value);
						breed.set(indexParent1, value);
					}
				}
			}
			for (int i=0; i < firstCrossPoint; i++) {
				breed.set(i, parent1.get(i));
			}
			for (int i=secondCrossPoint; i < parent1.size(); i++) {
				breed.set(i, parent1.get(i));
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
