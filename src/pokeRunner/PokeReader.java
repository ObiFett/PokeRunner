package pokeRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PokeReader {

    public BufferedReader br;
    public String line = "";
    public String csvSplitBy = ",";

    //private String directory = "C:\\Users\\Steven Lowder\\Documents\\PA Stuff\\Pokemafia\\";
    //private String directory = "C:\\Users\\Steven Lowder\\Documents\\PA Stuff\\Pokemafia\\";
    
    public PokeReader() {

    }

    public void openFile(String fileName) {
        br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeReader() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PokeGame getConfig() {
        openFile("Config.csv");
        PokeGame game = new PokeGame();
        try {
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] gameInfo = line.split(csvSplitBy);
                game.setDay(Integer.parseInt(gameInfo[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeReader();
        return game;
    }

    public void getPlayers(PokeGame gameInfo) {
        openFile("Day" + gameInfo.getDay() + "\\PlayerData.csv");
        try {
            br.readLine();
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] playerInfo = line.split(csvSplitBy);
                Player player = new Player(playerInfo, gameInfo);
                gameInfo.getPlayers().add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeReader();
    }
    
    public void getFactionInfo (PokeGame gameInfo){
        openFile("Day" + gameInfo.getDay() + "\\FactionData.csv");
        try {
            while ((line = br.readLine()) != null) {
            	String[] factionInfo = line.split(csvSplitBy);
            	gameInfo.addFactionWins(factionInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeReader();
    }

    public Orders getOrders(PokeGame gameInfo) {
        openFile("Day" + gameInfo.day + "\\Orders.csv");
        Orders orders = new Orders();
        try {
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                orders.addOrder(line.split(csvSplitBy));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeReader();
        return orders;
    }

    public Pokedex getPokedex() {
        //openFile(directory + "Pokedex.csv");
        openFile("Pokedex.csv");
        Pokedex pokedex = new Pokedex();
        try {
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                pokedex.addEntry(line.split(csvSplitBy));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeReader();
        return pokedex;
    }
}
