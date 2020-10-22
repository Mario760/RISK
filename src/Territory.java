/**
 * The type Territory.
 */
public class Territory {

    private String name;
    private int troops;
    private Player holder;

    /**
     * Instantiates a new Territory.
     *
     * @param name the territory name
     */
    public Territory(String name){
        this.name = name;
        troops = 0;
    }

    /**
     * Sets troops in this territory.
     *
     * @param troops the troops in territory
     */
    public void setTroops(int troops) {
        this.troops = troops;
    }

    /**
     * Sets holder.
     *
     * @param holder the holder
     */
    public void setHolder(Player holder) {
        this.holder = holder;
    }

    /**
     * Gets territory name.
     *
     * @return the name of territory
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
     * Gets holder.
     *
     * @return the holder
     */
    public Player getHolder() {
        return holder;
    }

    /**
     * Increase troops.
     *
     * @param increase the troops
     */
    public void increaseTroops(int increase){
        troops += increase;
    }

    /**
     * Decrease troops.
     *
     * @param decrease the troops
     */
    public void decreaseTroops(int decrease){
        troops -= decrease;
    }

    /**
     * Short description string to show this territory has how many troops.
     *
     * @return the string description
     */
    public String shortDescription(){return getName()+"("+getTroops()+" troops)";}
}
