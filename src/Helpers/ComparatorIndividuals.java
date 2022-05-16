package Helpers;

import java.util.ArrayList;
import java.util.Comparator;

public class ComparatorIndividuals implements Comparator<ArrayList<Integer>> {
	private TSPInstance tspInstance;
	
	public ComparatorIndividuals(TSPInstance t) {
		this.tspInstance = t;
	}

	@Override
	public int compare(ArrayList<Integer> ind1, ArrayList<Integer> ind2) {
		double fitnessInd1 = this.tspInstance.fitnessFunction(ind1);
		double fitnessInd2 = this.tspInstance.fitnessFunction(ind2);
		if (fitnessInd1 > fitnessInd2) return -1;
		if (fitnessInd1 < fitnessInd2) return 1;
		return 0;
	}

}
