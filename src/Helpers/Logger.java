package Helpers;

import java.io.File;
import java.io.FileWriter;   
import java.io.IOException;

public class Logger {
	private FileWriter output;
	File file;
	public Logger(String fileName) {
		try {
			String path = System.getProperty("user.dir") + "/Resultados/";
			File folder = new File(path);
			if (!folder.exists()) {
				folder.mkdir();
			}
			file = new File(path + fileName + ".txt");
			output = new FileWriter(path + fileName + ".txt");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public File getFile() {
		return this.file;
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
