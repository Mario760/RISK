import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {

    private ArrayList<Country> countries;
    private ArrayList<Continent>continents;
    private HashMap<String,ArrayList<Country>> neighbors;

    public Board()throws IOException{
        readCountryFile();
        readContinentFile();
        readNeighborFile();
    }

    public void readCountryFile() throws FileNotFoundException{
        File countryFile = new File("src/Countries.txt");
        countries = new ArrayList<>();
        Scanner scanner = new Scanner(countryFile);
        while(scanner.hasNext()){
            countries.add(new Country(scanner.nextLine()));
        }
    }

    public void readContinentFile() throws FileNotFoundException{
        continents = new ArrayList<>();
        File continentFile = new File("src/Continents.txt");
        Scanner scanner = new Scanner(continentFile);
        while(scanner.hasNext()){
            String[] array = scanner.nextLine().split(",");
            ArrayList<Country> tempCountries = new ArrayList<>();
            for(int i = 2; i< array.length;i++){
                tempCountries.add(new Country(array[i]));
            }
            continents.add(new Continent((String) array[0],Integer.parseInt(array[1]),tempCountries));
        }
    }

    public void readNeighborFile() throws FileNotFoundException{
        neighbors = new HashMap<>();
        File neighborFile = new File("src/Neighbors.txt");
        Scanner scanner = new Scanner(neighborFile);
        while(scanner.hasNext()){
            String[] array = scanner.nextLine().split(",");
            ArrayList<Country> neighborCountries =new ArrayList<>();
            for(int i=1; i< array.length;i++){
                neighborCountries.add(new Country(array[i]));
            }
            neighbors.put(array[0],neighborCountries);
        }
    }

    public ArrayList<Country> getAllCountries() {
        return countries;
    }

    public ArrayList<Continent> getAllContinents(){
        return continents;
    }

    public ArrayList<Country> getAllNeighbors(String countryName){
        return neighbors.get(countryName);
    }

    public boolean areNeighbors(String countryName,Country country){
        return getAllNeighbors(countryName).contains(country);
    }
}
