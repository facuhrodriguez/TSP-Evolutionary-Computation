package mutation;

import java.util.ArrayList;

public class InversionMutation extends GeneticMutation {
	
	public InversionMutation() {
		super("Mutación por Inversión");
		// TODO Auto-generated constructor stub
	}
	public static String name = "Mutación por inversión";
	@Override
	public ArrayList<Integer> mutate(ArrayList<Integer> gene) {
		try {
			int firstPosition = getRandomPosition(gene);
			int secondPosition = getRandomPosition(gene);
			while (firstPosition == secondPosition) {
				secondPosition = getRandomPosition(gene);
			}
			if (firstPosition > secondPosition) {
				int aux = firstPosition;
				firstPosition = secondPosition;
				secondPosition = aux;
			}
			ArrayList<Integer> newGene = invertSelection(gene, firstPosition, secondPosition);
			return newGene;
		} catch (Exception e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> invertSelection(ArrayList<Integer> gene, int firstPosition, int secondPosition) {
		ArrayList<Integer> newGene = (ArrayList<Integer>) gene.clone();
		while (firstPosition < secondPosition) {
			newGene.set(firstPosition, gene.get(secondPosition));
			newGene.set(secondPosition, gene.get(firstPosition));
			firstPosition++;
			secondPosition--;
		}
		return newGene;
	}
}
