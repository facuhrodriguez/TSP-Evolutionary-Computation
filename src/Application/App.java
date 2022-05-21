package Application;

import java.util.ArrayList;

import Helpers.ComparatorIndividuals;
import Helpers.Logger;
import Helpers.TSPInstance;
import Helpers.TSPLIBHelper;
import TSPSolution.TSPSolution;
import localSearch.LocalSearch;
import mutation.GeneticMutation;
import mutation.InversionMutation;
import parentsSelection.ParentsSelection;
import parentsSelection.RouletteWheelSelection;
import recombination.ArcCross;
import recombination.Recombination;
import survivorSelection.Elitism;
import survivorSelection.SurvivorSelection;

public class App {
	static TSPLIBHelper fh;

	public static void main(String[] args) {
		try {
			String fileUrl = args[0];
			fh = new TSPLIBHelper();
			fh.readTSPInstanceFile(fileUrl);
			System.out.println("Inicio del algoritmo");
			TSPInstance tspData = fh.getTSPInstance();
			LocalSearch l = new LocalSearch(tspData);
			Logger logger = new Logger(6);
			ComparatorIndividuals c = new ComparatorIndividuals(tspData);
			int totalPopulation = 200;
			
			// Initialize population 
			ArrayList<ArrayList<Integer>> population = tspData.generateInitialPopulation(totalPopulation);
			
			
			// Configuraciones de algoritmos
		
			Recombination r = new ArcCross();
			GeneticMutation g = new InversionMutation();
			ParentsSelection p = new RouletteWheelSelection(population, c, tspData);
			SurvivorSelection s = new Elitism(population, c, totalPopulation, p, tspData);
			
			TSPSolution tspSolution = new TSPSolution(tspData, l, logger, r, g, p, s, totalPopulation);
			tspSolution.setInitialPopulation(population);
			tspSolution.runAlgorithm();
			
			
			logger.closeFile();
			System.out.println("Fin del algoritmo");
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}
