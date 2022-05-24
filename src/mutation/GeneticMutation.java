package mutation;

import java.util.ArrayList;
import java.util.Random;

public abstract class GeneticMutation {
	
	protected String name;
	
	GeneticMutation(String name) {
		this.name = name;
	}
	
	public abstract ArrayList<Integer> mutate(ArrayList<Integer> gene);
	
	protected int getRandomPosition(ArrayList<Integer> gene) {
		Random r = new Random();
		int low = 0;
		int high = gene.size() - 1;
		int result = r.nextInt(high - low) + low;
		return result;
	}

	public String getName() {
		return name;
	}
}
