import java.util.ArrayList;

public class Territory {

    private String name;
    private int troops;
    private Player holder;

    public Territory(String name){
        this.name = name;
        troops = 0;
    }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public void setHolder(Player holder) {
        this.holder = holder;
    }

    public String getName() {
        return name;
    }

    public int getTroops() {
        return troops;
    }

    public Player getHolder() {
        return holder;
    }

    public void increaseTroops(int increase){
        troops += increase;
    }

    public void decreaseTroops(int decrease){
        troops -= decrease;
    }

    public String shortDescription(){return getName()+"("+getTroops()+" troops)";}
}
