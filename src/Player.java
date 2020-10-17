import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String name;
    private int armies;
    private boolean hasContinent;
    private HashMap<String,Country> countries;
    private HashMap<String,Continent> continents;

    public Player(String name){
        this.name = name;
        countries = new HashMap<>();
        continents = new HashMap<>();
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
        return new ArrayList<>(countries.values());
    }

    public ArrayList<String> getCountriesString(){return new ArrayList<>(countries.keySet());}

    public ArrayList<String> getContinentsString(){return new ArrayList<>(continents.keySet());}

    public ArrayList<Continent> getContinents() {
        return new ArrayList<>(continents.values());
    }

    public void addCountry(Country country){
        countries.put(country.getName(),country);
    }

    public void addContinent(Continent continent){
        continents.put(continent.getName(),continent);
    }

    public void removeCountry(Country country){
        countries.remove(country.getName());
    }

    public void removeContinent(Continent continent){
        continents.remove(continent.getName());
    }

    public void increaseArmy(int increase){
        armies += increase;
    }

    public void decreaseArmy(int decrease){
        armies -= decrease;
    }

    public boolean haveContinent(){
        return !continents.isEmpty();
    }

    public boolean checkCountryByString(String str){
        return countries.containsKey(str);
    }

    public Country getCountryByString(String str){
        return countries.get(str);
    }

    public void printPlayerInfo(){
        System.out.println("The player "+this.name+" has "+getArmies()+" troops and has "+countries.size()+" territories: ");
        for (Country country:getCountries()){
            System.out.println(country.shortDescription());
        }
        System.out.println();
        if(!continents.isEmpty()){
            System.out.println("And the continents: ");
            for(Continent continent:getContinents()){
                System.out.print(continent.getName()+", ");
            }
        }
        System.out.println();
    }

    public void gainArmiesFromTerritory(){
        int bonus = this.getCountries().size()/3;
        if(bonus == 0){bonus = 1;}
        System.out.println("Player " + this.getName()+" has "+this.getCountries().size()+" territories, add "+bonus+" troops.");
        this.increaseArmy(bonus);
        addContinentBonus();
    }

    public void addContinentBonus(){
        if(this.haveContinent()){
            System.out.println("Player " + this.getName()+" has continents: ");
            for(Continent continent:this.getContinents()){
                this.increaseArmy(continent.getBonusArmies());
                System.out.println(continent.getName()+", add "+continent.getBonusArmies()+" troops.8");
            }
        }
    }

    public boolean checkAbilityToAttack(){
        for(Country country:countries.values()){
            if(country.getArmies()!=1)return true;
        }
        return false;
    }

}
