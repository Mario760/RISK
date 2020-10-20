import java.util.ArrayList;
import java.util.HashMap;

public class Continent {
    private String name;
    private int bonusTroops;
    private ArrayList<Territory> members;

    public Continent(String name, int bonusTroops, ArrayList<Territory> members){
        this.name = name;
        this.bonusTroops = bonusTroops;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public int getBonusTroops() {
        return bonusTroops;
    }

    public ArrayList<Territory> getMembers() {
        return members;
    }

    public ArrayList<String> memberString(){
        ArrayList<String> memberString = new ArrayList<>();
        for(Territory territory:members){
            memberString.add(territory.getName());
        }
        return memberString;
    }

}
