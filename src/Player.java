import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Player.
 */
public class Player {
    private String name;
    private int troops;
    private HashMap<String,Territory> territories;
    private HashMap<String,Continent> continents;

    /**
     * Instantiates a new Player.
     *
     * @param name the player's name
     */
    public Player(String name){
        this.name = name;
        territories = new HashMap<>();
        continents = new HashMap<>();
    }

    /**
     * Set troops.
     *
     * @param troops the troops
     */
    public void setTroops(int troops){
        this.troops = troops;
    }

    /**
     * Gets name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets troops.
     *
     * @return the troops
     */
    public int getTroops() {
        return troops;
    }

    /**
     * Gets all territories of this player.
     *
     * @return the territories of this player in an arraylist
     */
    public ArrayList<Territory> getTerritories() {
        return new ArrayList<>(territories.values());
    }

    /**
     * Gets all continents of this player.
     *
     * @return the continents of this player in array list
     */
    public ArrayList<Continent> getContinents() {
        return new ArrayList<>(continents.values());
    }

    /**
     * Add territory which belongs to this player.
     *
     * @param territorytry the territorytry
     */
    public void addTerritory(Territory territorytry){
        territories.put(territorytry.getName(),territorytry);
    }

    /**
     * Add continent which belongs to this player.
     *
     * @param continent the continent
     */
    public void addContinent(Continent continent){
        continents.put(continent.getName(),continent);
    }

    /**
     * Remove territory.
     *
     * @param territory the territory
     */
    public void removeTerritory(Territory territory){
        territories.remove(territory.getName());
    }

    /**
     * Remove continent.
     *
     * @param continent the continent
     */
    public void removeContinent(Continent continent){
        continents.remove(continent.getName());
    }

    /**
     * Increase troop.
     *
     * @param increase the increase of troops
     */
    public void increaseTroop(int increase){
        troops += increase;
    }

    /**
     * Decrease troops.
     *
     * @param decrease the decrease of troops
     */
    public void decreaseTroops(int decrease){
        troops -= decrease;
    }

    /**
     * Check this player has continent(s) or not.
     *
     * @return the boolean, true if have continent.
     */
    public boolean haveContinent(){
        return !continents.isEmpty();
    }

    /**
     * Check territory by string boolean. Check player has this country or not.
     *
     * @param countryName, the string of country to be checked
     * @return whether player has the territory by name, true if contains
     */
    public boolean checkTerritoryByString(String countryName){
        return territories.containsKey(countryName);
    }

    /**
     * Get territory by string territory.
     *
     * @param str, the territory's name
     * @return the territory found by its name;
     */
    public Territory getTerritoryByString(String str){
        return territories.get(str);
    }

    /**
     * Print player info include name, troops on hand, number of territories and continents.
     */
    public void printPlayerInfo(){
        System.out.println("The player "+this.name+" has "+getTroops()+" troops and has "+territories.size()+" territories: ");
        for (Territory territory:getTerritories()){
            System.out.println(territory.shortDescription());
        }
        System.out.println();
        if(!continents.isEmpty()){
            System.out.println("And the continents: ");
            for(Continent continent:getContinents()){
                System.out.print(continent.getName()+", ");
            }
        }
    }

    /**
     * Gain troops from territories.
     * At least gain three troops, the number of gained troops is number of territories / 3. Always round down
     */
    public void gainTroopsFromTerritory(){
        int bonus = this.getTerritories().size()/3;
        if(bonus < 3){bonus = 3;}
        System.out.println("Player " + this.getName()+" has "+this.getTerritories().size()+" territories, add "+bonus+" troops.");
        this.increaseTroop(bonus);
        addContinentBonus();
    }

    /**
     * Add continent bonus. Each continent will have different bonus troops, depending on different maps and rules.
     */
    public void addContinentBonus(){
        if(this.haveContinent()){
            System.out.println("Player " + this.getName()+" has continents: ");
            for(Continent continent:this.getContinents()){
                this.increaseTroop(continent.getBonusTroops());
                System.out.println(continent.getName()+", add "+continent.getBonusTroops()+" troops.8");
            }
        }
    }

    /**
     * Check ability to attack boolean.
     *
     * @return the boolean whether player has ability to attack. True if any territory has more than 1 troop.
     */
    public boolean checkAbilityToAttack(){
        for(Territory territory:territories.values()){
            if(territory.getTroops()!=1)return true;
        }
        return false;
    }

}
