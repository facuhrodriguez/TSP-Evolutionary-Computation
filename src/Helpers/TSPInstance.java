package Helpers;

import java.util.ArrayList;

public class TSPInstance {
	ArrayList<Integer> cities = new ArrayList<Integer>();
	String name;
	Integer dimension;
	
	TSPInstance(String name, ArrayList<Integer> cities, Integer dimension) {
		this.cities = cities;
		this.name = name;
		this.dimension = dimension;
	}

	public ArrayList<Integer> getCities() {
		return cities;
	}

	public void setCities(ArrayList<Integer> cities) {
		this.cities = cities;
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
}
