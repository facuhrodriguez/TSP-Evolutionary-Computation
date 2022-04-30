package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TSPLIBHelper {

	ArrayList<Integer> cities = new ArrayList<Integer>();

	private TSPInstance instanceData;

	public TSPLIBHelper() {
	}

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
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

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
				for (String city : splitRow) {
					if (!city.equals("")) {
						Integer cityToAdd = Integer.parseInt(city.trim());
						if (!cities.contains(cityToAdd)) {
							this.cities.add(cityToAdd);
							System.out.println("city: " + cityToAdd);
						}
							
					}

				}

			}
		}
	}

}
