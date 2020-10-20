import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String name;
    private int troops;
    private HashMap<String,Territory> territories;
    private HashMap<String,Continent> continents;

    public Player(String name){
        this.name = name;
        territories = new HashMap<>();
        continents = new HashMap<>();
    }

    public void setTroops(int troops){
        this.troops = troops;
    }

    public String getName() {
        return name;
    }

    public int getTroops() {
        return troops;
    }

    public ArrayList<Territory> getTerritories() {
        return new ArrayList<>(territories.values());
    }

    public ArrayList<Continent> getContinents() {
        return new ArrayList<>(continents.values());
    }

    public void addTerritory(Territory territorytry){
        territories.put(territorytry.getName(),territorytry);
    }

    public void addContinent(Continent continent){
        continents.put(continent.getName(),continent);
    }

    public void removeTerritory(Territory territory){
        territories.remove(territory.getName());
    }

    public void removeContinent(Continent continent){
        continents.remove(continent.getName());
    }

    public void increaseTroop(int increase){
        troops += increase;
    }

    public void decreaseTroops(int decrease){
        troops -= decrease;
    }

    public boolean haveContinent(){
        return !continents.isEmpty();
    }

    public boolean checkTerritoryByString(String str){
        return territories.containsKey(str);
    }

    public Territory getTerritoryByString(String str){
        return territories.get(str);
    }

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

    public void gainTroopsFromTerritory(){
        int bonus = this.getTerritories().size()/3;
        if(bonus < 3){bonus = 3;}
        System.out.println("Player " + this.getName()+" has "+this.getTerritories().size()+" territories, add "+bonus+" troops.");
        this.increaseTroop(bonus);
        addContinentBonus();
    }

    public void addContinentBonus(){
        if(this.haveContinent()){
            System.out.println("Player " + this.getName()+" has continents: ");
            for(Continent continent:this.getContinents()){
                this.increaseTroop(continent.getBonusTroops());
                System.out.println(continent.getName()+", add "+continent.getBonusTroops()+" troops.8");
            }
        }
    }

    public boolean checkAbilityToAttack(){
        for(Territory territory:territories.values()){
            if(territory.getTroops()!=1)return true;
        }
        return false;
    }

}
