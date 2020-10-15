import java.util.ArrayList;

public class Player {
    private String name;
    private int armies;
    private ArrayList<Country> countries;
    private ArrayList<Continent> continents;

    public Player(String name){
        this.name = name;
        countries = new ArrayList<>();
        continents = new ArrayList<>();
    }

    public void setArmies(int armies){
        this.armies = armies;
    }

    public String getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }

    public void addCountry(Country country){
        countries.add(country);
    }

    public void addContinent(Continent continent){
        continents.add(continent);
    }

    public void removeCountry(Country country){
        countries.remove(country);
    }

    public void removeContinent(Continent continent){
        continents.remove(continent);
    }

    public void increaseArmy(int increase){
        armies += increase;
    }

    public void decreaseArmy(int decrease){
        armies -= decrease;
    }

    public void printPlayerInfo(){
        System.out.println("The player "+this.name+" has countries: ");
        for (Country country:countries){
            System.out.print(country.getName()+", ");
        }
        if(!continents.isEmpty()){
            System.out.println("And the continents: ");
            for(Continent continent:continents){
                System.out.print(continent.getName()+", ");
            }
        }
        System.out.println("");
    }
}
