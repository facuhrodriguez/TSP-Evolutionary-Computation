package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TSPLIBHelper {

	private HashMap<Integer, HashMap<Integer, Integer>> paths;
	private HashMap<Integer, Integer> cities = new HashMap<Integer, Integer>();
	private final Integer DIAG_VALUE = 9999;
	private TSPInstance instanceData;

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
			ArrayList<Integer> cities = new ArrayList<Integer>(this.cities.values());
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
				break;
			}
			default:
				break;
			}
		} else {
			String auxRow = row[0].replaceAll("\\s", "-");
			if (!auxRow.equals("EDGE_WEIGHT_SECTION") && !auxRow.equals("EOF")) {
				String splitRow[] = auxRow.split("-");
				if (this.cities.size() == 0)
					this.createCities(splitRow);
				else
					this.createPaths(splitRow);

			}
		}
	}

	/**
	 * Create cities given the first row of file
	 * 
	 * @param {String[]} citiesRow
	 */
	private void createCities(String[] citiesRow) {
		Integer indexCityToAdd = 1;
		for (int i = 0; i < citiesRow.length; i++) {
			String city = citiesRow[i];
			if (!city.equals("")) {
				Integer cityToAdd = Integer.parseInt(citiesRow[i].trim());
				if (!cities.containsValue(cityToAdd) && !cityToAdd.equals(this.DIAG_VALUE))
					this.cities.put(indexCityToAdd, cityToAdd);
				indexCityToAdd++;
			}
		}

	}

	/**
	 * Create paths give a row file
	 * 
	 * @param {String[]} citiesRow
	 */
	private void createPaths(String[] pathRow) {
		Integer cityIndex = 1;
		Integer city = Integer.parseInt(pathRow[getFirstCity(pathRow)]);
		for (int i = 0; i < pathRow.length; i++) {
			String path = pathRow[i];
			if (!path.equals("")) {
				Integer cityToAdd = this.cities.get(cityIndex);
				Integer costPath = Integer.parseInt(path.trim());
				if (cityToAdd != null ) {
					if (this.paths.containsKey(city)) {
						HashMap<Integer, Integer> pathCity = this.paths.get(city);
						if (!pathCity.containsKey(cityToAdd))
							pathCity.put(cityToAdd, costPath);
					} else {
						if (!this.paths.containsKey(city)) {
							HashMap<Integer, Integer> newPath = new HashMap<Integer, Integer>();
							newPath.put(cityToAdd, costPath);
							this.paths.put(city, newPath);
						}
					}
				}
				cityIndex++;
			}
		}
	}

	/**
	 * Get the first city different to ""
	 * 
	 * @param {String[]} row
	 * @return {Integer} the first city of row
	 */
	private Integer getFirstCity(String[] row) {
		String value = row[0];
		int i = 0;
		while (value.equals("") && i < row.length) {
			value = row[i].trim();
			i++;
		}
		if (i <= row.length)
			return i - 1;
		return -1;
	}
	
	public TSPInstance getTSPInstance() {
		if (this.instanceData != null) {
			return this.instanceData;
		}
		return null;
	}

}
