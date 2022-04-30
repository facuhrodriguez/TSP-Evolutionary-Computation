package Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

public class TSPInstance {

	private HashMap<Integer, HashMap<Integer, Integer>> paths;
	private ArrayList<Integer> cities = new ArrayList<Integer>();
	String name;
	Integer dimension;
	
	public TSPInstance() {
		this.paths = new HashMap<Integer, HashMap<Integer, Integer>>();
	}
	
	
	public void setCities(ArrayList<Integer> cities) {
			this.cities = cities;
	}
	
	public void setPaths(HashMap<Integer, HashMap<Integer, Integer>> paths) {
		this.paths = paths;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}
	
	@Override
	public String toString() {
		return this.getName() + (this.getDimension());
	}
	
	
	/**
	 * Print all possible paths
	 */
	public void printPaths() {
		try {
			Set<Entry<Integer, HashMap<Integer, Integer>>> cities = this.paths.entrySet();
			for (Entry<Integer, HashMap<Integer, Integer>> c : cities) {
				System.out.println("\n ----- City " + c.getKey() + " paths ------ \n ");
				HashMap<Integer, Integer> paths = c.getValue();
				Set<Entry<Integer, Integer>> pathCities = paths.entrySet();
				for (Entry<Integer, Integer> p : pathCities) {
					System.out.println("City " + p.getKey() + " cost: " + p.getValue());
				}
				
			}
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Print all possible cities
	 */
	public void printCities() {
		System.out.println("---- Cities ------");
		for (int i=0; i <this.cities.size(); i++) {
			if (i < this.cities.size() - 1)
				System.out.print(this.cities.get(i) + "-");
			else 
				System.out.println(this.cities.get(i));
		}
	}
}
