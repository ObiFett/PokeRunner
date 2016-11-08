package pokeRunner;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class PokeGame {

    public int day;
    private ArrayList<Player> players;
    public Map<String, ArrayList<Player>> factionBadges;
    public Map<String, ArrayList<Player>> factionE4Wins;
    public Pokedex pokedex;
    public Abilities abilities;
    
    public int glNum;

    public PokeGame() {
        players = new ArrayList<Player>();
        factionBadges = new TreeMap<String, ArrayList<Player>>();
        factionE4Wins = new TreeMap<String, ArrayList<Player>>();
    }
    
    public void setRivals(){
    	for(Player p : players)
    		p.setRival(getPlayer(p.rivalString));
    }

    public Player getPlayer(String name) {
        for (Player p : players) {
            if (p.equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    public Pokemon getPokemon(String name) {
    	Pokemon poke = null;
    	for(Player p : players)
    		for (Pokemon po : p.getTeam()) {
    			if(po.cName.equalsIgnoreCase(name))
    				poke = po;
    		}
    	return poke;
    }

    public void cleanUp() {
        for (Player p : players) {
            for (Pokemon po : p.getTeam()) {
                if (po.paralyzed) {
                    po.setParalyzedActive(!po.paralyzedActive);
                }
                if (po.frozen) {
                    po.setfTime(po.fTime - 1);
                }
                if (po.fTime == 0) {
                    po.setFrozen(false);
                }
            }
            for (Pokemon po : p.getBox()) {
                po.revive();
            }
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Pokedex getPokedex() {
        return pokedex;
    }

    public void setPokedex(Pokedex pokedex) {
        this.pokedex = pokedex;
        abilities = new Abilities(this.pokedex);
    }
    
    public void addBadge(Player a, Player d){
    	if(d.role.equalsIgnoreCase("Gym Leader")) {
    		if(!factionBadges.get(a.faction).contains(d)) {
    			factionBadges.get(a.faction).add(d);
    			a.getResults().add("You earned the " + d.getsAbility() + " badge for " + a.faction + "!");
    		} else {
    			a.getResults().add("You earned the " + d.getsAbility() + " badge! " + a.faction + " already has that badge.");
    		}
    	}
    }
    
    public boolean checkGLWins(Player a){
    	if(factionBadges.containsKey(a.faction) && factionBadges.get(a.faction).size() == glNum)
    		return true;
    	else
    		return false;
    }
    public boolean checkE4Wins(Player a){
    	if(factionE4Wins.containsKey(a.faction) && factionE4Wins.get(a.faction).size() == 4)
    		return true;
    	else
    		return false;
    }
    
    public void eliteFourWin(Player a, ArrayList<Player> d){
    	if(checkGLWins(a))
			for(Player p: d) {
				if(factionE4Wins.get(a.faction).contains(p))
					a.getResults().add("You beat a member of the Elite Four (" + p.alias2 + 
							") but " + a.faction + " has already defeated that Elite Four Member.");
				else {
					a.getResults().add("You beat a member of the Elite Four (" + p.alias2 + 
							") and brought your " + a.faction + " one step closer to Victory!");
					factionE4Wins.get(a.faction).add(p);
				}
			}
    }
    
    public void eliteFourLoss(Player a, ArrayList<Player> d){
    	if(checkGLWins(a) && d.size() > 1){
    		String result = "You must defeat all members of the Elite Four in the area in order to beat any of them. You beat ";
    		int marker = 1;
			for(Player p: d) {
				if(marker < d.size())
					result += p.alias2;
				else
					result += ". But you lost to " + p.alias2;
				
				if((marker + 1) < d.size())
					result += " and ";
				marker++;
			}
			a.getResults().add(result);
    	}
    }
    
    public void checkVictory(Player a, Player d){
    	if(checkGLWins(a) && checkE4Wins(a) && d.role.equalsIgnoreCase("Champion"))
    		a.getResults().add("You defeated the Champion and have won the game for your Pokemon Team!");
    		
    }
    
    public void addFactionWins(String[] factionInfo){
    	glNum = 0;
    	for(Player p : players){
    		if(p.sAbility != null && p.sAbility.getPmName().equalsIgnoreCase("Gym Leader"))
    			glNum++;
    	}
    	if(factionInfo.length > 1){
	    	factionBadges.put(factionInfo[0], new ArrayList<Player>());
	    	String[] glWins = factionInfo[1].split("\\|");
	    	for(String p : glWins)
	    		if(!p.isEmpty())
	    			factionBadges.get(factionInfo[0]).add(this.getPlayer(p));
    	}
    	if(factionInfo.length > 2){
	    	factionE4Wins.put(factionInfo[0], new ArrayList<Player>());
	    	String[] e4Wins = factionInfo[2].split("\\|");
	    	for(String p : e4Wins)
	    		if(!p.isEmpty())
	    			factionE4Wins.get(factionInfo[0]).add(this.getPlayer(p));
    	}
    }

	public int getGlNum() {
		return glNum;
	}

	public void setGlNum(int glNum) {
		this.glNum = glNum;
	}
	
	public void rivalReward(Player p) {
		ArrayList<Player> factionB = factionBadges.get(p.faction);
		Random r = new Random();
		int roll = r.nextInt(players.size());
		int playerbase = players.size();
		boolean found = false;
		for(int i = 0; i < playerbase; i++) {
			if(players.get(roll%playerbase).role.equals("Gym Leader") && !factionB.contains(players.get(roll%playerbase)) && !found) {
				p.getResults().add("You beat your Rival and learned that " 
						+ players.get(roll%playerbase).alias2
						+ " is a Gym Leader with the following powers: "
						+ players.get(roll%playerbase).sAbility.getPowerDescription());
				found = true;
			}
			roll++;
		}
		
		ArrayList<Player> factionE4 = factionE4Wins.get(p.faction);
		if(!found){
			for(int i = 0; i < playerbase; i++) {
				if(players.get(roll%playerbase).role.equals("Elite Four") && !factionE4.contains(players.get(roll%playerbase)) && !found) {
					p.getResults().add("You beat your Rival and learned that " 
							+ players.get(roll%playerbase).alias2 
							+ " is a member of the Elite Four with the following powers: "
							+ players.get(roll%playerbase).sAbility.getPowerDescription());
					found = true;
				}
				roll++;
			}
		}
	}
}
