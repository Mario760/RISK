import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Scanner scanner;

    private ArrayList<Player> players;
    private ArrayList<Country> allCountries;
    private ArrayList<Continent> allContinents;

    private int numberPlayers;
    private int averageStates;
    private int extraStates;

    private Board board;

    public Game() throws IOException {
        players = new ArrayList<>();
        board = new Board();
        allCountries =board.getAllCountries();
        allContinents = board.getAllContinents();
        scanner = new Scanner(System.in);
        initialGame();
    }

    private void initialGame(){
        boolean Error = false;
        do {
            try {
                System.out.println("Please enter the number of players (2-6 players): ");
                numberPlayers = scanner.nextInt();
                Error = false;
            } catch (Exception e) {
                Error = true;
                System.out.println("Please enter an legal positive integer to present the number of players!");
                scanner.next();
            }
        }while(Error);
        if(numberPlayers>1 && numberPlayers<7){ addPlayers(numberPlayers);}
        System.out.println("Awesome, we have "+ numberPlayers +" players, each player will have "+assignArmies()+" units of armies.");
        assignCountriesRandomly();
        for(Player player:players){
            checkContinent(player);
            player.printPlayerInfo();
        }
    }

    private void addPlayers(int numberPlayers){
        players.clear();
        for(int i = 0; i < numberPlayers; i++){
            System.out.println("Please enter Player "+ (i+1) + " name:");
            players.add(new Player(scanner.next()));
        }
    }

    private int assignArmies(){
        switch(numberPlayers){
            case 2:
                for(Player player:players){player.setArmies(50);}
                return 50;
            case 3:
                for(Player player:players){player.setArmies(35);}
                return 35;
            case 4:
                for(Player player:players){player.setArmies(30);}
                return 30;
            case 5:
                for(Player player:players){player.setArmies(25);}
                return 25;
            case 6:
                for(Player player:players){player.setArmies(20);}
                return 20;
        }
        return -1;
    }

    private void assignCountriesRandomly(){
        averageStates = allCountries.size()/numberPlayers;
        extraStates = allCountries.size()%numberPlayers;
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

    public void checkContinent(Player player){
        for(Continent continent: allContinents) {
            if (player.getCountries().containsAll(continent.getMembers())) {
                player.addContinent(continent);
            }
        }
        for(Continent continent:player.getContinents()){
            if(!player.getCountries().containsAll(continent.getMembers())){
                player.removeContinent(continent);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();
    }
}
