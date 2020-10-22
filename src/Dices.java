import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The type Dices.
 */
public class Dices {
    private ArrayList<Integer> dices;
    private int dicesAmount;

    /**
     * Instantiates a new Dices.
     *
     * @param dicesAmount the dices amount
     */
    public Dices(int dicesAmount){
        this.dicesAmount = dicesAmount;
        dices = new ArrayList<>(dicesAmount);
    }

    /**
     * Dice rolling. add dice amount number of random numbers into arraylist
     */
    public void diceRolling(){
        for(int i = 0; i < dicesAmount; i++){
            dices.add(new Random().nextInt(6)+1);
        }
    }

    /**
     * Sort dices from largest to smallest
     */
    public void sortDices(){
        Collections.sort(dices);
        Collections.reverse(dices);
    }

    /**
     * Get dices amount.
     *
     * @return the number of dices
     */
    public int getDicesAmount(){
        return dicesAmount;
    }

    /**
     * Get index dice int.
     *
     * @param index the index of dices
     * @return the index of dices rolled number
     */
    public int getIndexDice(int index){
        sortDices();
        return dices.get(index);
    }

}
