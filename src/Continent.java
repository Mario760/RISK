import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Continent.
 */
public class Continent {
    private String name;
    private int bonusTroops;
    private ArrayList<Territory> members;

    /**
     * Instantiates a new Continent.
     *
     * @param name        the continent name
     * @param bonusTroops the bonus troops
     * @param members     the member territories of continents
     */
    public Continent(String name, int bonusTroops, ArrayList<Territory> members){
        this.name = name;
        this.bonusTroops = bonusTroops;
        this.members = members;
    }

    /**
     * Gets continent name.
     *
     * @return the name of continent
     */
    public String getName() {
        return name;
    }

    /**
     * Gets bonus troops.
     *
     * @return the bonus troops
     */
    public int getBonusTroops() {
        return bonusTroops;
    }

    /**
     * Member territory of continent in string array list.
     *
     * @return the array list
     */
    public ArrayList<String> memberString(){
        ArrayList<String> memberString = new ArrayList<>();
        for(Territory territory:members){
            memberString.add(territory.getName());
        }
        return memberString;
    }

}
