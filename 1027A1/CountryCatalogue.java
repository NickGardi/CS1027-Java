import java.util.*;

/**
 * This class describes a Catalogue containing Country objects. It contains
 * methods to add, search, alter, and display Country data
 * 
 * @author Nicholas Gardi - 250868721 - 6/29/2017
 */

public class CountryCatalogue {
	public final int DEFAULT_SIZE = 5;
	public final int NOT_FOUND = -1;
	public Country[] catalogue;
	public int numCountries;
	public Set<String> continents;
	public Map<String, String> cDict;

	/**
	 * Constructor: Reads in the data files and creates catalogue and adds 
	 * country objects to it. Creates a  set of continents and a hashmap with 
	 * country-continent keys.
	 * 
	 * @param filenameCountry 
	 * 		file that contains country name, population and area
	 * @param filenameContinent
	 * 		file that contains country name and continent
	 */
	public CountryCatalogue(String filenameCountry, String filenameContinent) {
		catalogue = new Country [DEFAULT_SIZE];
		numCountries = 0;
		continents = new HashSet<String>();
		cDict = new HashMap<String, String>();
		
		ThingToReadFile continentFile = new ThingToReadFile(filenameContinent);
		continentFile.readLine();
		String country;
		String continent;
		while(!(continentFile.endOfFile())){
			String line = continentFile.readLine();
			String[] array = line.split(",");
			country = array[0];
			continent = array[1];
			cDict.put(country, continent);
			continents.add(continent);
		}
		continentFile.close();
		
		ThingToReadFile countryFile = new ThingToReadFile(filenameCountry); 
		countryFile.readLine();
		String name;
		int pop;
		double area;
		while (!(countryFile.endOfFile())){
			String line = countryFile.readLine();
			String[] array = line.split(",");			
			name = array[0];
			pop = Integer.parseInt(array[1]);
			area = Double.parseDouble(array[2]);
			Country countryObj = new Country(name, pop, area, cDict.get(name));
			addCatalogue(countryObj);
		}
		countryFile.close();
		
	}

	/**
	 * This method adds a country object to the catalogue, and calls the 
	 * expandCapacity() if needed
	 * 
	 * @param country
	 * 		the country onject to be added to the catalogue
	 */
	public void addCatalogue(Country country){
		catalogue[numCountries] = country;
		numCountries++;
		if (catalogue.length == numCountries){
			expandCapacity();
		}
	}
	
	/**
	 * This method doubles the size of the catalogue when it gets full
	 */
	public void expandCapacity(){
		Country[] larger = new Country [numCountries*2];
		
		for (int i=0; i<numCountries; i++){
			larger[i] = catalogue[i];
		}
		catalogue = larger;
	}
	
	/**
	 * This method calls the addCatalogue() method to add the country
	 * object to the catalog. Also updates the cDict map and continent set
	 * 
	 * @param country
	 * 		the country object to be added to the catalogue, cDict 
	 * 		map and continent set
	 */
	public void addCountry(Country country){
		addCatalogue(country);
		continents.add(country.getContinent());
		cDict.put(country.getName(), country.getContinent());
		numCountries++;
	}
	
	/**
	 * This method tkaes and index and returns the country object 
	 * at that index in the catalogue
	 * 
	 * @param index
	 * 		index of country to be returned
	 * @return
	 * 		country object at index
	 */
	public Country getCountry(int index){
		if (index >= numCountries || index == NOT_FOUND){
			return null;
		}else{
			return catalogue[index];
		}
	}
	
	/**
	 * This method calls the toString() method for each country
	 * in the catalogue
	 */
	public void printCountryCatalogue(){
		for(int i=0; i < numCountries-1; i++){
			System.out.println(catalogue[i].toString());
		}
	}
	
	/**
	 * This method will print out all the countries from a specified continent
	 * 
	 * @param continent
	 * 		the countries that will be printed will be from this continent
	 */
	public void filterCountriesByContinent(String continent){
		System.out.println("Countries in " + continent + ":");
		for(int i=0; i < numCountries-1; i++){
			if (catalogue[i].getContinent().equals(continent)){
				System.out.println(catalogue[i].getName());
			}
		}
	}
		
	/**
	 * This method takes a String of a name of a country we want to find. 
	 * This method will return an int representing the index of the found 
	 * country in catalogue.
	 * 
	 * @param countryName
	 * 		the country that will be searched for
	 * @return
	 * 		return the index of the country or -1 if not found
	 */
	public int searchCatalogue(String countryName){
		for(int i=0; i < numCountries-1; i++){
			if((catalogue[i].getName()).equals(countryName) && catalogue[i].getName() != null){
				return i;
			}
		}
		System.out.println("Country was not found");
		return NOT_FOUND;
	}
	
	/**
	 * This method takes a String of a name of a country and removes it from the catalogue.
	 * 
	 * @param country
	 * 		the country to be removed
	 */
	public void removeCountry(String country){
		int indexToRemove = searchCatalogue(country);
		if (indexToRemove == NOT_FOUND){
			System.out.println("country was not found");
		}else{
			for(int i = indexToRemove; i < numCountries; i++){
				catalogue[indexToRemove] = catalogue[(indexToRemove + 1)];	
			}
		}
		numCountries--;
		System.out.println("Country successfully removed");
	}
	
	/**
	 * This method sets the population of a country
	 * 
	 * @param country
	 * 		population of this country will be changed
	 * @param pop
	 * 		country's new population
	 */
	public void setPopulationOfACountry(String country, int pop){
		int index = searchCatalogue(country);
		if (index == NOT_FOUND){
			System.out.println("The population could not be changed");
		}
		else {
			catalogue[index].setPopulation(pop);
			System.out.println("Population was successfully changed");
		}
	}
	
	/**
	 * This method will write the catalogue’s county’s to file. 
	 * 
	 * @param filename
	 * 		name of the file to be saved
	 */
	public void saveCountryCatalogue(String filename){
		ThingToWriteFile out = new ThingToWriteFile(filename);
		for(int i = 0; i < numCountries-1; i++){
			catalogue[i].writeToFile(out);
		}
		out.close();
	}
	
	/**
	 * This method finds the country with the largest population
	 * @return
	 * 		index of largest population country
	 */
	public int findCountryWithLargestPop() {
		int currentHighestPop = catalogue[0].getPopulation();
		int currentHighestPopIndex = 0;
		for(int i = 0; i < numCountries-1; i++){
			if (catalogue[i].getPopulation() > currentHighestPop){
				currentHighestPopIndex = i;
				currentHighestPop = catalogue[i].getPopulation();
			}
		}
		return currentHighestPopIndex;
	}
			
	
	/**
	 * This method finds the country with the smallest area
	 * @return
	 * 		the index of the country with the smallest area
	 */
	public int findCountryWithSmallestArea(){
		double currentSmallestArea = catalogue[0].getArea();
		int currentSmallestAreaIndex = 0;
		for(int i = 0; i < numCountries-1; i++){
			if(catalogue[i].getArea() < currentSmallestArea){
				currentSmallestAreaIndex = i;
				currentSmallestArea = catalogue[i].getArea();
			}
		}
		return currentSmallestAreaIndex;
	}
	
	/**
	 * This method shows which countries have a population density within a certain range
	 * @param low
	 * 		lowest pop density for range
	 * @param high
	 * 		highest pop density for range
	 */		
	public void printCountriesFilterDensity(int low, int high) {
		int x = low;
		int y = high;
		String xx = Integer.toString(x);
		String yy = Integer.toString(y);
		System.out.println("Countries with a population density between " + xx + " and " + yy + ":");
			
		for(int i = 0; i < numCountries-1; i++){
			if(catalogue[i].getPopDensity() >= low && catalogue[i].getPopDensity() <= high){
				System.out.println(catalogue[i].toString() + " has a population density of " + catalogue[i].getPopDensity());
			}
		}
	}
	
	/**
	 * This method finds the continent that has the highest total population
	 */
	public void findMostPopulousContinent() {

		HashMap<String, Integer> continentCounter = new HashMap<>();
		int runningPopTotal;
		int currentPopulation;
		String currentContinent;
		for (int k = 0; k < this.numCountries-1; k++) {
	
				currentPopulation = this.catalogue[k].getPopulation();
				currentContinent = this.catalogue[k].getContinent();
	
				if (!(continentCounter.containsKey(currentContinent))) {
	
					continentCounter.put(currentContinent, currentPopulation);
	
				} else {
					runningPopTotal = ((continentCounter.get(currentContinent) + (currentPopulation)));
					continentCounter.put(currentContinent, runningPopTotal);
	
				}

		}

		int highestPopulation = 0;
		String mostPopulatedCont = "";

		for (HashMap.Entry<String, Integer> entry : continentCounter.entrySet())
				if ((entry.getValue()) > highestPopulation) {
					highestPopulation = entry.getValue();
					mostPopulatedCont = entry.getKey();
				}

		System.out.println("Continent with the largest population: " + mostPopulatedCont + ", with population "
				+ highestPopulation);

	}

}
