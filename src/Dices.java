import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dices {
    private ArrayList<Integer> dices;
    private int dicesAmount;

    public Dices(int dicesAmount){
        this.dicesAmount = dicesAmount;
        dices = new ArrayList<>(dicesAmount);
    }

    public void diceRolling(){
        for(int i = 0; i < dicesAmount; i++){
            dices.add(new Random().nextInt(6)+1);
        }
    }

    public void sortDices(){
        Collections.sort(dices);
        Collections.reverse(dices);
    }

    public int getDicesAmount(){
        return dicesAmount;
    }

    public int getIndexDice(int index){
        sortDices();
        return dices.get(index);
    }

}
