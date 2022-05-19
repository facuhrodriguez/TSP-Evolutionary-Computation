package Helpers;

import java.io.FileWriter;   
import java.io.IOException;

public class Logger {
	private FileWriter output;
	public Logger() {
		try {
			output = new FileWriter("results.txt");
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
