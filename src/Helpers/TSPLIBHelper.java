package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TSPLIBHelper {

	private HashMap<Integer, HashMap<Integer, Integer>> paths;
	private ArrayList<Integer> cities = new ArrayList<Integer>();
	private TSPInstance instanceData;
	private static int rowNumber = 0;

	public TSPLIBHelper() {
		this.paths = new HashMap<Integer, HashMap<Integer, Integer>>();
	}

	/**
	 * Read and process file given an fileUrl
	 * 
	 * @param {String} fileUrl
	 */
	public void readTSPInstanceFile(String fileUrl) {
		try {
			File file = new File(fileUrl);
			Scanner reader = new Scanner(file);
			this.instanceData = new TSPInstance();
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				String[] row = data.split(":");
				this.processRow(row);
			}
			this.instanceData.setCities(cities);
			this.instanceData.setPaths(paths);
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Process each row file
	 * 
	 * @param {String[]} row
	 */
	private void processRow(String[] row) {
		String name = "";
		Integer dimension = 1;
		if (row.length > 1) {
			String field = row[0];
			switch (field) {
			case "NAME": {
				name = row[1];
				this.instanceData.setName(name);
				break;
			}
			case "DIMENSION": {
				dimension = Integer.parseInt(row[1].trim());
				this.instanceData.setDimension(dimension);
				this.createCities(dimension);
				break;
			}
			default:
				break;
			}
		} else {
			String auxRow = row[0].replaceAll("\\s", "-");
			if (!auxRow.equals("EDGE_WEIGHT_SECTION") && !auxRow.equals("EOF")) {
				String splitRow[] = auxRow.split("-");
				this.createPaths(splitRow);
				TSPLIBHelper.rowNumber++;
			}
			
		}
	}

	/**
	 * Create cities given the dimension of file
	 * 
	 * @param {int} dimension
	 */
	private void createCities(int dimension) {
		for (int i = 0; i < dimension; i++) {
			this.cities.add(i);
		}

	}

	/**
	 * Create paths give a row file
	 * 
	 * @param {String[]} citiesRow
	 */
	private void createPaths(String[] pathRow) {
		int citySource = TSPLIBHelper.rowNumber;
		int cityDest = 0;
		for (int i = 0; i < pathRow.length; i++) {
			String path = pathRow[i];
			if (!path.equals("")) {
				Integer costPath = Integer.parseInt(path.trim());
				if (this.paths.containsKey(citySource)) {
					HashMap<Integer, Integer> pathCity = this.paths.get(citySource);
					pathCity.put(cityDest, costPath);
				} else {
					if (!this.paths.containsKey(citySource)) {
						HashMap<Integer, Integer> newPath = new HashMap<Integer, Integer>();
						newPath.put(cityDest, costPath);
						this.paths.put(citySource, newPath);
					}
				}
				cityDest++;
			}
		}
	}


	public TSPInstance getTSPInstance() {
		if (this.instanceData != null) {
			return this.instanceData;
		}
		return null;
	}

}
