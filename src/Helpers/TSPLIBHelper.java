package Helpers;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TSPLIBHelper {
	private TSPInstance instanceData;
	public TSPLIBHelper() {
	}

	public void readTSPInstanceFile(String fileUrl) {
		try {
			File file = new File(fileUrl);
			Scanner reader = new Scanner(file);
			
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				String[] row =  data.split(":");
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
		ArrayList<Integer> cities = new ArrayList<Integer>();
		if (row.length > 1) {
			String field = row[0];
			switch (field) {
			case "NAME": {
				name = row[1];
			}
			case "DIMENSION": {
				dimension = Integer.parseInt(row[1]);
			}
			default:
				break;
			}
		} else {
			
		}
		this.instanceData = new TSPInstance(name, cities, dimension);
	}
	
	
}
