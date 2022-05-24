package Helpers;

import java.io.File;
import java.io.FileWriter;   
import java.io.IOException;

public class Logger {
	private FileWriter output;
	public Logger(String file) {
		try {
			File folder = new File("/Resultados");
			if (!folder.exists()) {
				folder.mkdir();
			}
			
			String path = System.getProperty("user.dir") + "/Resultados/ " + file + ".txt";
			output = new FileWriter(path);
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void writeRow(String data) {
		try {
			output.write(data + "\n");
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public void closeFile() {
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
