import java.util.ArrayList;

public class Country {

    private String name;
    private int armies;
    private Player holder;

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

    public String getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }

    public Player getHolder() {
        return holder;
    }

    public void increaseArmies(int increase){
        armies += increase;
    }

    public void decreaseArmies(int decrease){
        armies -= decrease;
    }

    public String shortDescription(){return getName()+"("+getArmies()+" troops)";}
}
