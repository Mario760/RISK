import java.util.ArrayList;

public class Country {

    private String name;
    private int armies;
    private Player holder;
    private ArrayList<Country> neighborCountries;

    public Country(String name){
        this.name = name;
        armies = 0;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    public void setHolder(Player holder) {
        this.holder = holder;
    }

    public void addNeighborCountries(ArrayList<Country> neighbors){
        this.neighborCountries =neighbors;
    }

    public String getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }

    public Player getHolder() {
        return holder;
    }

    public ArrayList<Country> getNeighborCountries() {
        return neighborCountries;
    }

    public void increaseArmies(int increase){
        armies += increase;
    }

    public void decreaseArmies(int decrease){
        armies -= decrease;
    }
}
