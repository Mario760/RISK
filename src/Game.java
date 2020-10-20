import java.io.IOException;
import java.util.*;

public class Game {
    private final Scanner scanner;

    private final ArrayList<Player> players;
    private final ArrayList<Country> allCountries;
    private final ArrayList<Continent> allContinents;

    private final LinkedHashSet<Country> neighborCountries;

    private int numberPlayers;
    private int initialArmies;

    private final Board board;

    public Game() throws IOException {
        players = new ArrayList<>();
        board = new Board();
        allCountries =board.getAllCountries();
        allContinents = board.getAllContinents();
        scanner = new Scanner(System.in);
        neighborCountries = new LinkedHashSet<>();

        initialGame();
        System.out.println("Alright! Let's start battling!");
        processGaming();
    }

    private void initialGame() {
        do{
            boolean Error;
            do {
                try {
                    System.out.println("Please enter the number of players (2-6 players): ");
                    numberPlayers = scanner.nextInt();
                    scanner.nextLine();
                    Error = false;
                } catch (Exception e) {
                    Error = true;
                    System.out.println("Please enter an legal positive integer to present the number of players!");
                    scanner.next();
                }
            } while (Error);
        }while(!(numberPlayers>1 && numberPlayers<7));
        addPlayers(numberPlayers);
        System.out.println("Awesome, we have "+ numberPlayers +" players, each player will have "+setArmies()+" troops.");
        assignCountriesRandomly();
        for(Player player:players){
            checkContinent(player);
            assignArmies(player);
            player.printPlayerInfo();
            player.addContinentBonus();
        }
    }

    private void processGaming(){
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
                player.gainArmiesFromTerritory();
                player.printPlayerInfo();
                player.addContinentBonus();
            }
        }
    }

    private void addPlayers(int numberPlayers){
        players.clear();
        for(int i = 0; i < numberPlayers; i++){
            System.out.println("Please enter Player "+ (i+1) + " name:");
            players.add(new Player(scanner.nextLine()));
        }
    }

    private int setArmies(){
        switch (numberPlayers) {
            case 2 -> {
                for (Player player : players) {
                    player.setArmies(50);
                }
                initialArmies = 50;
            }
            case 3 -> {
                for (Player player : players) {
                    player.setArmies(35);
                }
                initialArmies = 35;
            }
            case 4 -> {
                for (Player player : players) {
                    player.setArmies(30);
                }
                initialArmies = 30;
            }
            case 5 -> {
                for (Player player : players) {
                    player.setArmies(25);
                }
                initialArmies = 25;
            }
            case 6 -> {
                for (Player player : players) {
                    player.setArmies(20);
                }
                initialArmies = 20;
            }
        }
        return initialArmies;
    }

    private void assignCountriesRandomly(){
        int averageStates = allCountries.size()/numberPlayers;
        int extraStates = allCountries.size()%numberPlayers;
        ArrayList<Country> tempAllCountries = allCountries;
        Random random = new Random();
        for(Player player:players) {
            for (int i = 0; i < averageStates; i++) {
                Country tempCountry = tempAllCountries.get(random.nextInt(tempAllCountries.size()));
                player.addCountry(tempCountry);
                tempCountry.setHolder(player);
                tempAllCountries.remove(tempCountry);
            }
        }
        for(int i = 0;i<extraStates;i++){
            Country tempCountry = tempAllCountries.get(random.nextInt(tempAllCountries.size()));
            players.get(i).addCountry(tempCountry);
            tempAllCountries.remove(tempCountry);
        }
    }

    private void checkContinent(Player player){
            for (Continent continent : allContinents) {
                if (player.getCountriesString().containsAll(continent.memberString())) {
                    player.addContinent(continent);
                }
            }
            for (Continent continent : player.getContinents()) {
                if (!player.getCountriesString().containsAll(continent.memberString())) {
                    player.removeContinent(continent);
                }
            }
    }

    private void assignArmies(Player player){
            int averageArmiesEachState = initialArmies / player.getCountries().size();
            for(Country country:player.getCountries()){
                int signedArmies =(int)(Math.random()*averageArmiesEachState+1);
                country.setArmies(signedArmies);
                player.decreaseArmy(signedArmies);
            }
            Random random = new Random();
            int leftArmies = player.getArmies();
            for(int i = 0; i < leftArmies;i++){
                player.getCountries().get(random.nextInt(player.getCountries().size())).increaseArmies(1);
                player.decreaseArmy(1);
            }
    }



    private void draft(Player player){
        String territory;
        int troops = 0;
        player.gainArmiesFromTerritory();
        System.out.println("It's DRAFT stage, you have "+ player.getArmies()+" troops to add to any of your territory.");
        while(player.getArmies()!=0) {
            do {
                System.out.println("Please enter the territory name that you want to add troops to. Enter \"quit\" to quit game");
                territory = scanner.nextLine();
                if(territory.equals("quit")) System.exit(0);
                if(!player.checkCountryByString(territory)) System.out.println("Please enter your own territory's name...");
            }while(!player.checkCountryByString(territory));
            try {
                System.out.println("Please enter the number of troops you want to send.");
                troops = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter invalid input!");
                scanner.next();
            }
            if(troops <= player.getArmies()) {
                player.getCountryByString(territory).increaseArmies(troops);
                player.decreaseArmy(troops);
            }else{
                System.out.println("Sorry,,, you don't have so much territories...");
            }
        }
    }

    public void attack(Player player){
        System.out.println("\nIt's ATTACK stage, you have these territory can attack:");
        printAttackableInfo(player);
        attackStage(player);
    }

    public void attackStage(Player player){
        String attackCountryString;
        String defenceCountryString = null;
        Country attackCountry = null;
        Country defenceCountry;
        Outterloop:
        do{
            while(true) {
                System.out.println("Please enter your territory that you want to launch an offense. (Enter finish to finish this stag)");
                attackCountryString = scanner.nextLine();
                if (attackCountryString.equals("finish")) break Outterloop;
                else if (!player.checkCountryByString(attackCountryString)) {
                    System.out.println("Please enter your own territory's name...");
                    continue;
                } else if (player.getCountryByString(attackCountryString).getArmies() == 1)
                    System.out.println("You have to leave at least one troop to guard this city!");
                attackCountry = player.getCountryByString(attackCountryString);
                for (Country neighbor : board.getAllNeighbors(attackCountry.getName())) {
                    for (Player player1 : players) {
                        if (player1.checkCountryByString(neighbor.getName()) && (!player1.getName().equals(player.getName()))) {
                            System.out.println(player1.getCountryByString(neighbor.getName()).shortDescription());
                        }
                    }
                }
                System.out.println("Which territory do you want to attack?");
                defenceCountryString = scanner.nextLine();
                if (!defenceCountryString.equals(attackCountryString)) break;
            }

        for(Player player1:players){
            if(player1.checkCountryByString(defenceCountryString)){
                defenceCountry=player1.getCountryByString(defenceCountryString);
                battle(attackCountry,defenceCountry);
                break;
            }
        }
        printAttackableInfo(player);
        }while(player.checkAbilityToAttack());
    }

    public void printAttackableInfo(Player player){
        for(Country country:player.getCountries()){
            if(country.getArmies() == 1)continue;
            System.out.print(country.shortDescription()+"--");
            for(Country neighbor:board.getAllNeighbors(country.getName())){
                for(Player player1:players){
                    if(player1.checkCountryByString(neighbor.getName())&&(!player1.getName().equals(player.getName()))){
                        System.out.print(player1.getCountryByString(neighbor.getName()).shortDescription()+" ");
                    }
                }
            }
            System.out.println();
        }
    }

    public void battle(Country attackCountry,Country defenceCountry){
        if(attackCountry.getHolder()==defenceCountry.getHolder()){
            System.out.println("You cannot attack yourself!!");
            return;
        }
        int defenceArmies;
        int attackArmies=1;
        if(defenceCountry.getArmies()>=2)defenceArmies=2;
        else defenceArmies=1;
        System.out.println("How many territories(types) do you want to attack? (one/two/three/blitz/finish)");
        String type = scanner.nextLine();
        switch (type){
            case "one":attackArmies=1; break;
            case "two":attackArmies=2; break;
            case "three": attackArmies =3; break;
            case "blitz": blitz(attackCountry,defenceCountry);
                return;
            case "finish": return;
        }
        if(attackArmies>attackCountry.getArmies()){
            System.out.println("Sorry, you only have "+attackCountry.getArmies()+" troops in this country");
            attackArmies = attackCountry.getArmies();
        }

        Dices attackDice = new Dices(attackArmies);
        Dices defenceDice = new Dices(defenceArmies);
        compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        if(defenceCountry.getArmies()==0){deployTroops(attackCountry,defenceCountry);}
    }

    public void blitz(Country attackCountry,Country defenceCountry){
        while((attackCountry.getArmies()>1)&&(defenceCountry.getArmies()>0)) {
            int attack = attackCountry.getArmies();
            int defence = defenceCountry.getArmies();
            if(attack>3)attack=3;
            if(defence>2)defence=2;
            Dices attackDice = new Dices(attack);
            Dices defenceDice = new Dices(defence);
            compareDices(attackDice,defenceDice,attackCountry,defenceCountry);
        }
        if(defenceCountry.getArmies()==0){deployTroops(attackCountry,defenceCountry);}
        else if(attackCountry.getArmies()==1){System.out.println("You lose this battle... Try more, you could win!\n");}
    }

    private void compareDices(Dices attackDice,Dices defenceDice,Country attackCountry,Country defenceCountry){
        attackDice.diceRolling();
        defenceDice.diceRolling();
        int minDiceAmount = Math.min(attackDice.getDicesAmount(),defenceDice.getDicesAmount());
        for(int i=0;i<minDiceAmount;i++){
            if(attackDice.getIndexDice(i)>defenceDice.getIndexDice(i)){
                defenceCountry.decreaseArmies(1);
                System.out.println(attackCountry.getName()+" rolling "+attackDice.getIndexDice(i)+", and "+defenceCountry.getName()+" rolling "+defenceDice.getIndexDice(i)+".");
                System.out.println(defenceCountry.getName()+" lose one troop by rolling.\n");
            }else{
                attackCountry.decreaseArmies(1);
                System.out.println(attackCountry.getName()+" rolling "+attackDice.getIndexDice(i)+", and "+defenceCountry.getName()+" rolling "+defenceDice.getIndexDice(i)+".");
                System.out.println(attackCountry.getName()+" lose one troop by rolling.\n");
            }
        }
    }

    public void deployTroops(Country attackCountry, Country defenceCountry){
        int deployTroops;
        do {
            System.out.println(attackCountry.getHolder().getName() + " wins the battle! Currently you have "+attackCountry.getArmies()+ " in your original country.");
            System.out.println(" How many troops deploy to " + defenceCountry.getName());
            deployTroops = scanner.nextInt();
            scanner.nextLine();
            if(deployTroops>attackCountry.getArmies()){
                System.out.println("You don't have so much troops, try it again.");
            }else if(deployTroops== attackCountry.getArmies()){
                System.out.println("You have to leave at least one troop in your original country.");
            }else{
                break;
            }
        }while(true);
        attackCountry.decreaseArmies(deployTroops);
        defenceCountry.getHolder().removeCountry(defenceCountry);
        defenceCountry.setHolder(attackCountry.getHolder());
        attackCountry.getHolder().addCountry(defenceCountry);
        defenceCountry.increaseArmies(deployTroops);
    }

    public void fortify(Player player){
        String fortifyCountryString;
        Country fortifyCountry;
        Country fortified;
        neighborCountries.clear();
        System.out.println("It's FORTIFY stage, you have these territories can fortify:");
        for(Country country: player.getCountries()) {
            if(country.getArmies()>1)System.out.println(country.shortDescription()+" ");
        }
        outerloop:
        while(true) {
            do {
                System.out.println("Which territory you want to fortify?");
                fortifyCountryString = scanner.nextLine();
                if (player.checkCountryByString(fortifyCountryString)) {
                    fortifyCountry = player.getCountryByString(fortifyCountryString);
                    break;
                } else {
                    System.out.println("Please check your input of territory's name.");
                }
            } while (true);
            addNeighborCountries(fortifyCountry, player);
            neighborCountries.remove(fortifyCountry);
            System.out.println("You have these territories connect to " + fortifyCountry.getName());
            for (Country country : neighborCountries) {
                System.out.println(country.shortDescription() + " ");
            }
            do {
                System.out.println("Which territory do you want to be fortified?");
                String fortifiedString = scanner.nextLine();
                if (fortifiedString.equals(fortifyCountryString)) break;
                else if (player.checkCountryByString(fortifiedString)) {
                    fortified = player.getCountryByString(fortifiedString);
                    break outerloop;
                } else System.out.println("Please check your input of territory's name.");
            } while (true);
        }

        do {
            System.out.println("How many troops do you want to fortify?");
            int troop = scanner.nextInt();
            scanner.nextLine();
            if (troop > fortifyCountry.getArmies())
                System.out.println("You don't have so much troops in this country..");
            else if (troop > (fortifyCountry.getArmies() - 1))
                System.out.println("Please leave at least one troop to defence");
            else {
                fortifyCountry.decreaseArmies(troop);
                fortified.increaseArmies(troop);
                break;
            }
        } while (true);

    }

    public void addNeighborCountries(Country country, Player player){
        int size = neighborCountries.size();
        for(Country neighbor: board.getAllNeighbors(country.getName())){
            for(Player player1:players){
                if(player1.checkCountryByString(neighbor.getName())&&(player1.getName().equals(player.getName()))){
                    neighborCountries.add(player1.getCountryByString(neighbor.getName()));
                    if(neighborCountries.size()!=size) addNeighborCountries(player1.getCountryByString(neighbor.getName()),player);
                    else break;
                }
            }
        }
    }

    public void checkWinner(){
        for(Player player:players){
            if(player.getCountries().size()==0){
                players.remove(player);
            }
        }
        if(players.size()==1){
            System.out.println("Congratulation, we have the winner: "+players.get(0).getName());
            System.exit(-1);
        }
    }


    public static void main(String[] args) throws IOException {
        new Game();
    }
}
