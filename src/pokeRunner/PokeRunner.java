package pokeRunner;

public class PokeRunner {

    /**
     * @param args
     */
    public static void main(String[] args) {
		// TODO Auto-generated method stub

        PokeReader reader = new PokeReader();
        //Get Day information
        PokeGame gameInfo = reader.getConfig();

        //Read Pokedex CSV
        gameInfo.setPokedex(reader.getPokedex());
        //Read Player CSV
        reader.getPlayers(gameInfo);
        gameInfo.setRivals();
        //Read Orders
        reader.getFactionInfo(gameInfo);
        Orders orders = reader.getOrders(gameInfo);

        //Process Pokemon non-Battle Abilities
        for (Player p : gameInfo.getPlayers()) {
            p.pokeNonCombat(gameInfo);
        }
        //Process Orders
        orders.processMiscellaneous(gameInfo);
        orders.processSpecialPlayer(gameInfo);
        orders.processBasicPlayer(gameInfo);
        orders.processMoves(gameInfo);
        
        //Cleanup
        gameInfo.cleanUp();

        PokeWriter writer = new PokeWriter();
        //write Player CSV
        writer.writePlayers(gameInfo);
        //write PM CSV
        writer.writePMs(gameInfo);
        //write Results CSV
        writer.writeResults(gameInfo);
        writer.writeFactionData(gameInfo);

    }

}
