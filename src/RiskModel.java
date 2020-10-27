import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * The main Game.
 */
public class RiskModel {
    private final Scanner scanner;

    private final ArrayList<Player> players;
    private final ArrayList<Territory> allCountries;
    private final ArrayList<Continent> allContinents;

    private final LinkedHashSet<Territory> neighborCountries;

    private int numberPlayers;
    private int initialTroops;

    private final Board board;

    public static DefaultListModel<String> allCountriesJList;
    public static DefaultListModel<String> allContinentsJList;

    /**
     * Instantiates a new Game.
     *
     * @throws IOException the io exception
     */
    public RiskModel() throws IOException {
        players = new ArrayList<>();
        board = new Board();
        allCountries =board.getAllCountries();
        allContinents = board.getAllContinents();
        scanner = new Scanner(System.in);
        neighborCountries = new LinkedHashSet<>();

        allCountriesJList = new DefaultListModel<>();
        allContinentsJList = new DefaultListModel<>();

        for(Territory territory:allCountries)allCountriesJList.addElement(territory.getName());
        for(Continent continent:allContinents)allContinentsJList.addElement(continent.getName());

//        initialGame();
//        System.out.println("Alright! Let's start battling!");
//        processGaming();
    }

    /**
     * Initialize the game include add number of players, add troops to players depending on player numbers,
     * assign randomly territories with randomly troops to each player.
     */
    public void initialGame(int numberPlayers) throws IOException {
//        do{
//            boolean Error;
//            do {
//                try {
//                    System.out.println("Please enter the number of players (2-6 players): ");
//                    numberPlayers = scanner.nextInt();
//                    scanner.nextLine();
//                    Error = false;
//                } catch (Exception e) {
//                    Error = true;
//                    System.out.println("Please enter an legal positive integer to present the number of players!");
//                    scanner.next();
//                }
//            } while (Error);
//        }while(!(numberPlayers>1 && numberPlayers<7));

        numberPlayers = RiskView.getNumberPlayerDialog();
        addPlayers(numberPlayers);
        System.out.println("Awesome, we have "+ numberPlayers +" players, each player will have "+ setTroopsInitially()+" troops.");
        assignCountriesRandomly();
        for(Player player:players){
            checkContinent(player);
            assignTroops(player);
            player.printPlayerInfo();
            player.addContinentBonus();
        }
    }

    /**
     * Processing the game includes making each player to do draft, attack and fortify stage.
     * And check whether we have the winner. If we have the winner, end game.
     */
    public void processGaming(){
        while (players.size()!=1){
            for(Player player:players){
                System.out.println("\nIt's "+player.getName()+"'s turn:");
                draft(player);
                attack(player);
                checkWinner();
                fortify(player);
                checkContinent(player);
                player.printPlayerInfo();
            }
            for(Player player:players){
                checkContinent(player);
                player.gainTroopsFromTerritory();
                player.printPlayerInfo();
                player.addContinentBonus();
            }
        }
    }

    /**
     * Add players.
     *
     * @param numberPlayers get the number players from user and add them one by one
     */
    public void addPlayers(int numberPlayers){
        players.clear();
        for(int i = 0; i < numberPlayers; i++){
            System.out.println("Please enter Player "+ (i+1) + " name:");
            players.add(new Player(scanner.nextLine()));
        }
    }

    /**
     * Set troops initially to each player recording the number of players in this game.
     *
     * @return the initial troops assigned to each player.
     */
    public int setTroopsInitially(){
        switch (numberPlayers) {
            case 2 -> {
                for (Player player : players) {
                    player.setTroops(50);
                }
                initialTroops = 50;
            }
            case 3 -> {
                for (Player player : players) {
                    player.setTroops(35);
                }
                initialTroops = 35;
            }
            case 4 -> {
                for (Player player : players) {
                    player.setTroops(30);
                }
                initialTroops = 30;
            }
            case 5 -> {
                for (Player player : players) {
                    player.setTroops(25);
                }
                initialTroops = 25;
            }
            case 6 -> {
                for (Player player : players) {
                    player.setTroops(20);
                }
                initialTroops = 20;
            }
        }
        return initialTroops;
    }

    /**
     * Assign countries randomly.
     */
    public void assignCountriesRandomly(){
        int averageStates = allCountries.size()/numberPlayers;
        int extraStates = allCountries.size()%numberPlayers;
        ArrayList<Territory> tempAllCountries = allCountries;
        Random random = new Random();
        for(Player player:players) {
            for (int i = 0; i < averageStates; i++) {
                Territory tempCountry = tempAllCountries.get(random.nextInt(tempAllCountries.size()));
                player.addTerritory(tempCountry);
                tempCountry.setHolder(player);
                tempAllCountries.remove(tempCountry);
            }
        }
        for(int i = 0;i<extraStates;i++){
            Territory tempCountry = tempAllCountries.get(random.nextInt(tempAllCountries.size()));
            players.get(i).addTerritory(tempCountry);
            tempAllCountries.remove(tempCountry);
        }
    }

    /**
     * Check whether the player has continent.
     *
     * @param player the player to be checked continent
     */
    public void checkContinent(Player player){
            for (Continent continent : allContinents) {
                if (player.getTerritories().containsAll(continent.memberString())) {
                    player.addContinent(continent);
                }
            }
            for (Continent continent : player.getContinents()) {
                if (!player.getTerritories().containsAll(continent.memberString())) {
                    player.removeContinent(continent);
                }
            }
    }

    /**
     * Assign random troops initially to all territories of this player.
     *
     * @param player the player that assign troops to all territories  randomly
     */
    public void assignTroops(Player player){
            int averageTroopsEachState = initialTroops / player.getTerritories().size();
            for(Territory territory:player.getTerritories()){
                int signedTroops =(int)(Math.random()*averageTroopsEachState+1);
                territory.setTroops(signedTroops);
                player.decreaseTroops(signedTroops);
            }
            Random random = new Random();
            int leftTroops = player.getTroops();
            for(int i = 0; i < leftTroops;i++){
                player.getTerritories().get(random.nextInt(player.getTerritories().size())).increaseTroops(1);
                player.decreaseTroops(1);
            }
    }


    /**
     * Draft stage.
     * Assign troops in player to his/her territories until the player has no troops in hand.
     *
     * @param player the player process draft stage
     */
    public void draft(Player player){
        String territory;
        int troops = 0;
        player.gainTroopsFromTerritory();
        System.out.println("It's DRAFT stage, you have "+ player.getTroops()+" troops to add to any of your territory.");
        player.printPlayerInfo();
        while(player.getTroops()!=0) {
            do {
                System.out.println("Please enter the territory name that you want to add troops to.");
                territory = scanner.nextLine();
                if(!player.checkTerritoryByString(territory)) System.out.println("Please enter your own territory's name...");
            }while(!player.checkTerritoryByString(territory));
            do {
                try {
                    System.out.println("Please enter the number of troops you want to send.");
                    troops = scanner.nextInt();
                    if(troops<1)System.out.println("Please enter valid input!");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.out.println("Please enter valid input!");
                    scanner.next();
                }
            }while(troops<1);
            if(troops <= player.getTroops()) {
                player.getTerritoryByString(territory).increaseTroops(troops);
                player.decreaseTroops(troops);
            }else{
                System.out.println("Sorry,,, you don't have so much territories...");
            }
        }
    }

    /**
     * Attack turn.
     * Print out the message that player can attack and process attack stage.
     *
     * @param player the player in attack stage
     */
    public void attack(Player player){
        System.out.println("\nIt's ATTACK stage, you have these territory can attack:");
        printAttackableInfo(player);
        attackStage(player);
    }

    /**
     * Attack stage.
     * Attack from one of player's territory to enemy's territory (Can skip this stage any time)
     *
     * @param player the player in attack stage
     */
    public void attackStage(Player player){
        String attackCountryString;
        String defenceCountryString;
        Territory attackCountry;
        Territory defenceCountry;
        Outterloop:
        do{
            while(true) {
                System.out.println("Please enter your territory that you want to launch an offense. (Enter \"skip\" to skip this stag)");
                attackCountryString = scanner.nextLine();
                if (attackCountryString.equals("skip")) break Outterloop;
                else if (!player.checkTerritoryByString(attackCountryString)) {
                    System.out.println("Please enter your own territory's name...");
                    continue;
                } else if (player.getTerritoryByString(attackCountryString).getTroops() == 1)
                    System.out.println("You have to leave at least one troop to guard this city!");
                attackCountry = player.getTerritoryByString(attackCountryString);
                for (Territory neighbor : board.getAllNeighbors(attackCountry.getName())) {
                    for (Player player1 : players) {
                        if (player1.checkTerritoryByString(neighbor.getName()) && (!player1.getName().equals(player.getName()))) {
                            System.out.println(player1.getTerritoryByString(neighbor.getName()).shortDescription());
                        }
                    }
                }
                System.out.println("Which territory do you want to attack?");
                defenceCountryString = scanner.nextLine();
                if (!defenceCountryString.equals(attackCountryString)) break;
            }

        for(Player player1:players){
            if(player1.checkTerritoryByString(defenceCountryString)){
                defenceCountry=player1.getTerritoryByString(defenceCountryString);
                battle(attackCountry,defenceCountry);
                break;
            }
        }
        printAttackableInfo(player);
        }while(player.checkAbilityToAttack());
    }

    /**
     * Print ability to attack info.
     *
     * @param player the player to check info of his/her territories
     */
    public void printAttackableInfo(Player player){
        for(Territory country:player.getTerritories()){
            if(country.getTroops() == 1)continue;
            System.out.print(country.shortDescription()+"--");
            for(Territory neighbor:board.getAllNeighbors(country.getName())){
                for(Player player1:players){
                    if(player1.checkTerritoryByString(neighbor.getName())&&(!player1.getName().equals(player.getName()))){
                        System.out.print(player1.getTerritoryByString(neighbor.getName()).shortDescription()+" ");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Battle.
     * User can use one die, two dices, three dices or blitz to attack.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public void battle(Territory attackCountry,Territory defenceCountry){
        if(attackCountry.getHolder()==defenceCountry.getHolder()){
            System.out.println("You cannot attack yourself!!");
            return;
        }
        int defenceTroops;
        int attackTroops;
        if(defenceCountry.getTroops()>=2)defenceTroops=2;
        else defenceTroops=1;
        loop:
        while(true) {
            System.out.println("How many troops(which type) do you want to attack? (one/two/three/blitz/finish)");
            String type = scanner.nextLine();
            switch (type) {
                case "one":
                    attackTroops = 1;
                    break loop;
                case "two":
                    attackTroops = 2;
                    break loop;
                case "three":
                    attackTroops = 3;
                    break loop;
                case "blitz":
                    blitz(attackCountry, defenceCountry);
                    return;
                case"finish":
                    return;
                default:
                    System.out.println("Please re-select your option without typos!");
                    break;
            }
        }
        if(attackTroops>attackCountry.getTroops()){
            System.out.println("Sorry, you only have "+attackCountry.getTroops()+" troops in this country");
            attackTroops = attackCountry.getTroops();
        }

        Dices attackDice = new Dices(attackTroops);
        Dices defenceDice = new Dices(defenceTroops);
        compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        if(defenceCountry.getTroops()==0){deployTroops(attackCountry,defenceCountry);}
    }

    /**
     * Blitz.
     * Directly get the results of battle.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public void blitz(Territory attackCountry,Territory defenceCountry){
        while((attackCountry.getTroops()>1)&&(defenceCountry.getTroops()>0)) {
            int attack = attackCountry.getTroops();
            int defence = defenceCountry.getTroops();
            if(attack>3)attack=3;
            if(defence>2)defence=2;
            Dices attackDice = new Dices(attack);
            Dices defenceDice = new Dices(defence);
            compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        }
        if(defenceCountry.getTroops()==0){deployTroops(attackCountry,defenceCountry);}
        else if(attackCountry.getTroops()==1){System.out.println("You lose this battle... Try more, you could win!\n");}
    }

    /**
     * Compare dices to determine which territory wins this battle.
     *
     * @param attackDice     the attack dice
     * @param defenceDice    the defence dice
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public void compareDices(Dices attackDice,Dices defenceDice,Territory attackCountry,Territory defenceCountry){
        attackDice.diceRolling();
        defenceDice.diceRolling();
        int minDiceAmount = Math.min(attackDice.getDicesAmount(),defenceDice.getDicesAmount());
        for(int i=0;i<minDiceAmount;i++){
            if(attackDice.getIndexDice(i)>defenceDice.getIndexDice(i)){
                defenceCountry.decreaseTroops(1);
                System.out.println(attackCountry.getName()+" rolling "+attackDice.getIndexDice(i)+", and "+defenceCountry.getName()+" rolling "+defenceDice.getIndexDice(i)+".");
                System.out.println(defenceCountry.getName()+" lose one troop by rolling.\n");
            }else{
                attackCountry.decreaseTroops(1);
                System.out.println(attackCountry.getName()+" rolling "+attackDice.getIndexDice(i)+", and "+defenceCountry.getName()+" rolling "+defenceDice.getIndexDice(i)+".");
                System.out.println(attackCountry.getName()+" lose one troop by rolling.\n");
            }
        }
    }

    /**
     * Deploy troops to the defeated territory after the attack player wins the battle.
     *
     * @param attackCountry  the attack country
     * @param defenceCountry the defence country
     */
    public void deployTroops(Territory attackCountry, Territory defenceCountry){
        int deployTroops;
        do {
            System.out.println(attackCountry.getHolder().getName() + " wins the battle! Currently you have "+attackCountry.getTroops()+ " in your original country.");
            do {
                System.out.println(" How many troops deploy to " + defenceCountry.getName());
                deployTroops = scanner.nextInt();
                if(deployTroops<1)System.out.println("Please input valid number");
                scanner.nextLine();
            }while(deployTroops<1);
            if(deployTroops>attackCountry.getTroops()){
                System.out.println("You don't have so much troops, try it again.");
            }else if(deployTroops== attackCountry.getTroops()){
                System.out.println("You have to leave at least one troop in your original country.");
            }else{
                break;
            }
        }while(true);
        attackCountry.decreaseTroops(deployTroops);
        defenceCountry.getHolder().removeTerritory(defenceCountry);
        defenceCountry.setHolder(attackCountry.getHolder());
        attackCountry.getHolder().addTerritory(defenceCountry);
        defenceCountry.increaseTroops(deployTroops);
    }

    /**
     * Fortify stage.
     * Move troops from one territory to the connect territories.
     *
     * @param player the player
     */
    public void fortify(Player player){
        String fortifyCountryString;
        Territory fortifyCountry;
        Territory fortified;
        neighborCountries.clear();
        System.out.println("\nIt's FORTIFY stage, you have these territories can fortify:");
        for(Territory country: player.getTerritories()) {
            if(country.getTroops()>1)System.out.println(country.shortDescription()+" ");
        }
        outerloop:
        while(true) {
            do {
                System.out.println("Which territory you want to fortify? (Type skip to skip this turn.)");
                fortifyCountryString = scanner.nextLine();
                if(fortifyCountryString.equals("skip")) return;
                else if (player.checkTerritoryByString(fortifyCountryString)) {
                    fortifyCountry = player.getTerritoryByString(fortifyCountryString);
                    break;
                } else {
                    System.out.println("Please check your input of territory's name.");
                }
            } while (true);
            neighborCountries.add(fortifyCountry);
            addNeighborCountries(fortifyCountry, player);
            neighborCountries.remove(fortifyCountry);
            System.out.println("You have these territories connect to " + fortifyCountry.getName());
            for (Territory country : neighborCountries) {
                System.out.println(country.shortDescription() + " ");
            }
            do {
                System.out.println("\nWhich territory do you want to be fortified?(Type \"back\" to re-select fortify country.");
                String fortifiedString = scanner.nextLine();
                if(fortifiedString.equals("back"))break;
                else if (fortifiedString.equals(fortifyCountryString)) {
                    System.out.println("The fortify and be fortified country cannot be the same, re-select fortify country please.");
                    break;
                }
                else if (player.checkTerritoryByString(fortifiedString)) {
                    fortified = player.getTerritoryByString(fortifiedString);
                    break outerloop;
                } else System.out.println("Please check your input of territory's name.");
            } while (true);
        }
        int troop;
        do {
            do {
                System.out.println("How many troops do you want to fortify?");
                troop = scanner.nextInt();
                if(troop<1)System.out.println("Please input valid number");
                scanner.nextLine();
            }while(troop<1);
            if (troop > fortifyCountry.getTroops())
                System.out.println("You don't have so much troops in this country..");
            else if (troop > (fortifyCountry.getTroops() - 1))
                System.out.println("Please leave at least one troop to defence");
            else {
                fortifyCountry.decreaseTroops(troop);
                fortified.increaseTroops(troop);
                break;
            }
        } while (true);

    }

    /**
     * Add all connected countries.
     * Used recursion to visit all connected territories and those which holder is this player to HashSet.
     *
     * @param territory the territory
     * @param player    the player
     */
    public void addNeighborCountries(Territory territory, Player player){
        int size = neighborCountries.size();
        for(Territory neighbor: board.getAllNeighbors(territory.getName())){
            for(Player player1:players){
                if(player1.checkTerritoryByString(neighbor.getName())&&(player1.getName().equals(player.getName()))){
                    neighborCountries.add(player1.getTerritoryByString(neighbor.getName()));
                    if(neighborCountries.size()!=size) {
                        addNeighborCountries(player1.getTerritoryByString(neighbor.getName()),player);
                    }
                    else break;
                }
            }
        }
    }

    /**
     * Check winner and print out congratulation message.
     */
    public void checkWinner(){
        for(Player player:players){
            if(player.getTerritories().size()==0){
                System.out.println(player.getName()+" fails...");
                players.remove(player);
            }
        }
        if(players.size()==1){
            System.out.println("Congratulation, we have the winner: "+players.get(0).getName());
            System.exit(-1);
        }
    }




    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        new RiskModel();
    }
}
