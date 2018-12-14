/**
 * This Class represents a country object
 * 
 * @author Nick
 *
 */
public class Country {
	
	public String name;
	public int population;
	public double area;
	public String continent;

	public Country(String name, int population, double area, String continent){
	this.name = name;
	this.population = population;
	this.area = area;
	this.continent = continent; 
}

/**
 * @return
 * 		population of the country
 */
public int getPopulation() {
	return population;
}

/**
 * @param population
 * 		sets the population of the country
 */
public void setPopulation(int population) {
	this.population = population;
}

/**
 * @return
 * 		the the continent of the country
 */	
public String getContinent() {
	return continent;
}

/**
 * @param continent
 * 		sets the continent of the country
 */
public void setContinent(String continent) {
	this.continent = continent;
}

/**
 * @return
 * 		the name of the country
 */		
public String getName() {
	return name;
}

/**
 * @return
 * 		the area of the country
 */
public double getArea() {
	return area;
}

/**
 * @return
 * 		the population density of the country
 */
public double getPopDensity() {
	return (population/area);
}

/**
 * @param out
 * 		writes to the file 'out'
 */
public void writeToFile(ThingToWriteFile out){
	out.writeLine(this.getName() + ", " + this.getContinent() + ", " + this.population + ", " + this.getPopDensity() + "\n");
}

/**
 * prints the details of the country
 */
public void printCountryDetails(){
	System.out.println(this.name + " is located in " + this.continent + " has a population of " + this.population + " and an area of " + this.area + " and has a population density of " + this.getPopDensity());
}

/* 
 * String representation of the country
 */
public String toString() {
	String result = this.name + " in " + this.getContinent() + "\n"  ;
	return result;
	}
}