import java.util.ArrayList;
import java.util.Collections;

public class Dices {
    private ArrayList<Integer> dices;
    private int dicesAmount;

    public Dices(int dicesAmount){
        this.dicesAmount = dicesAmount;
        dices = new ArrayList<>(dicesAmount);
    }

    public void diceRolling(){
        for(int i = 0; i < dicesAmount; i++){
            dices.add((int)Math.random()*6+1);
        }
    }

    public void sortDices(){
        Collections.sort(dices);
        Collections.reverse(dices);
    }

    public int getLargestDice(){
        return dices.get(0);
    }

    public int getMiddleDice(){
        return dices.get(1);
    }

    public int getSmallestDice(){
        return dices.get(dices.size()-1);
    }
}
