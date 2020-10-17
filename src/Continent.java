import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<String> memberString(){
        ArrayList<String> memberString = new ArrayList<>();
        for(Country country:members){
            memberString.add(country.getName());
        }
        return memberString;
    }

}
