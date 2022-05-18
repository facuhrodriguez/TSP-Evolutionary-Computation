package parentsSelection;

import java.util.ArrayList;
import java.util.Random;

import Helpers.TSPInstance;


public class TournamentSelection extends ParentsSelection {
	private TSPInstance tsp;
	public TournamentSelection(ArrayList<ArrayList<Integer>> t, TSPInstance tsp) {
		super(t);
		this.tsp = tsp;
	}

	@Override
	public int generateParent() {
		try {
			int firstCompetitorIndex = getCompetitor();
			int secondCompetitorIndex = getCompetitor();
			while (firstCompetitorIndex == secondCompetitorIndex) {
				secondCompetitorIndex = getCompetitor();
			}
			ArrayList<Integer> firstCompetitor = this.getPopulation().get(firstCompetitorIndex);
			double fitnessFirstCompetitor = this.tsp.fitnessFunction(firstCompetitor);
			ArrayList<Integer> secondCompetitor = this.getPopulation().get(secondCompetitorIndex);
			double fitnessSecondCompetitor = this.tsp.fitnessFunction(secondCompetitor);
			
			if (fitnessFirstCompetitor > fitnessSecondCompetitor) return firstCompetitorIndex;
			return secondCompetitorIndex;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private int getCompetitor() {
		Random r = new Random();
		int low = 0;
		int high = this.getPopulation().size() - 1;
		int result = r.nextInt(high-low) + low;
		return result;
	}

}
