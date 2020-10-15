import java.util.ArrayList;

public class Continent {
    private String name;
    private int bonusArmies;
    private ArrayList<Country> members;

    public Continent(String name, int bonusArmies, ArrayList<Country> members){
        this.name = name;
        this.bonusArmies = bonusArmies;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }

    public ArrayList<Country> getMembers() {
        return members;
    }
}
