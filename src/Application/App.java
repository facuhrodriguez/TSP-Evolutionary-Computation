package Application;

import Helpers.Logger;
import Helpers.TSPInstance;
import Helpers.TSPLIBHelper;
import TSPSolution.TSPSolution;
import localSearch.LocalSearch;

public class App {
	static TSPLIBHelper fh;

	public static void main(String[] args) {
		try {
			String fileUrl = args[0];
			fh = new TSPLIBHelper();
			fh.readTSPInstanceFile(fileUrl);

			TSPInstance tspData = fh.getTSPInstance();
			LocalSearch l = new LocalSearch(tspData);
			Logger logger = new Logger();
			TSPSolution tspSolution = new TSPSolution(tspData, l, logger);
			tspSolution.runAlgorithm();
			logger.closeFile();

//			tspData.printCities();
//			tspData.printPaths();
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}
