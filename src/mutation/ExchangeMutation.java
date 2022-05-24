package mutation;

import java.util.ArrayList;

public class ExchangeMutation extends GeneticMutation {

	public ExchangeMutation() {
		super("Mutación por intercambio");
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Integer> mutate(ArrayList<Integer> gene) {
		try {
			ArrayList<Integer> newGene = (ArrayList<Integer>) gene.clone();
			int firstPosition = getRandomPosition(gene);
			int secondPosition = getRandomPosition(gene);
			while (firstPosition == secondPosition) {
				secondPosition = getRandomPosition(gene);
			}
			newGene.set(secondPosition, gene.get(firstPosition));
			newGene.set(firstPosition, gene.get(secondPosition));
			return newGene;
		} catch (Exception e) {
			throw e;
		}
	}
	
	

}
