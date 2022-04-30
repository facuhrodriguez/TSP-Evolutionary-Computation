package Application;

import Helpers.TSPInstance;
import Helpers.TSPLIBHelper;

public class App {
	static TSPLIBHelper  fh;
	public static void main(String[] args) {
		try {
			String fileUrl = args[0];
			fh = new TSPLIBHelper();
			fh.readTSPInstanceFile(fileUrl);
			TSPInstance tspData = fh.getTSPInstance();
			tspData.printCities();
			tspData.printPaths();
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}

}
