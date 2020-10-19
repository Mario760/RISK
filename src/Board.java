import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {

//    private ArrayList<Country> countries;
//    private ArrayList<Continent>continents;

    private HashMap<String,Country> countryHashMap;
    private HashMap<String,Continent> continentHashMap;
    private HashMap<String,ArrayList<Country>> neighbors;

    public Board()throws IOException{
        readCountryFile();
        readContinentFile();
        readNeighborFile();
    }

    public void readCountryFile() throws FileNotFoundException{
        File countryFile = new File("src/Countries.txt");
        countryHashMap = new HashMap<>();
        Scanner scanner = new Scanner(countryFile);
        while(scanner.hasNext()){
            String str = scanner.nextLine();
            countryHashMap.put(str,new Country(str));
        }
    }

    public void readContinentFile() throws FileNotFoundException{
        continentHashMap = new HashMap<>();
        File continentFile = new File("src/Continents.txt");
        Scanner scanner = new Scanner(continentFile);
        while(scanner.hasNext()){
            String[] array = scanner.nextLine().split(",");
            ArrayList<Country> tempCountries = new ArrayList<>();
            for(int i = 2; i< array.length;i++){
                tempCountries.add(new Country(array[i]));
            }
            continentHashMap.put(array[0],new Continent(array[0],Integer.parseInt(array[1]),tempCountries));
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
        return new ArrayList<>(countryHashMap.values());
    }

    public ArrayList<String> getAllCountriesString() {
        return (ArrayList<String>) countryHashMap.keySet();
    }

    public ArrayList<Continent> getAllContinents(){
        return new ArrayList<>(continentHashMap.values());
    }

    public ArrayList<Country> getAllNeighbors(String countryName){
        return neighbors.get(countryName);
    }

}