package pokeRunner;

import java.util.ArrayList;

public class Battle {

	private BattleData bd;

	public Battle() {
		
    }

    public boolean challenge(Player attacker, Player defender, PokeGame gi, boolean eliteFourCheck) {
    	if(defender.underground > 0){
    		attacker.getResults().add(defender.alias1 + " used DIG!");
    		defender.getResults().add("DIG helped you escape a challenge!");
    		defender.setUnderground(defender.underground-1);
    		return false;
    	} else if (defender.avoidChallenge > 0){
    		attacker.getResults().add(defender.alias1 + " escaped!");
    		defender.getResults().add("You used an escape rope to avoid a challenge!");
    		defender.setAvoidChallenge(defender.avoidChallenge-1);
    		defender.getItems()[ItemType.ESCAPEROPE.ordinal()] -= 1;
    		return false;
    	} else {
    		bd = new BattleData(attacker, defender);
    		
    		//TODO: Use pre combat abilities
		    for(int i = 0; i < 3; i++) {
		    	bd = gi.abilities.preCombat(i, bd, gi);
		    	bd = gi.abilities.preCombat(i+3, bd, gi);
		    }
    		
		    //Compare Types
    		for (int i = 0; i < 3; i++) {
		        bd.getaValues()[i] = pBattle(attacker.team[i], defender.team[i], gi, true);
		        bd.getdValues()[i] = pBattle(defender.team[i], attacker.team[i], gi, false);
		    }
		    
		    //TODO: Assign knockouts
		    bd.assignKnockouts();
		    
		    //TODO: Check post combat abilities
		    for(int i = 0; i < 3; i++) {
		    	bd = gi.abilities.postCombat(i, bd, gi);
		    	bd = gi.abilities.postCombat(i+3, bd, gi);
		    }
		    
		    //TODO: Assign knockouts
		    bd.assignKnockouts();
		    
		    //TODO: Check post knockout abilities
		    for(int i = 0; i < 3; i++) {
		    	bd = gi.abilities.postKnockout(i, bd, gi);
		    	bd = gi.abilities.postKnockout(i+3, bd, gi);
		    }
		    
		    //TODO: Apply knockouts
		    bd.applyKnockouts();
		    
		    for(int i = 0; i < 3; i++) {
		    	bd.getAttacker().getTeam()[i].setUsedAbility(false);
		    	bd.getDefender().getTeam()[i].setUsedAbility(false);
		    }
		    
		    int winner = 0;
		    int[] bolds = {1, 1, 1};
		    for (int i = 0; i < 3; i++) {
		        if (bd.getaValues()[i] > bd.getdValues()[i]) {
		            winner += 1;
		        } else if (bd.getdValues()[i] > bd.getaValues()[i]) {
		            winner -= 1;
		            bolds[i] = 0;
		        } else {
		            if (attacker.team[i].happiness > defender.team[i].happiness) {
		                winner += 1;
		            } else if (attacker.team[i].happiness < defender.team[i].happiness) {
		                winner -= 1;
		                bolds[i] = 0;
		            } else {
		                bolds[i] = 2;
		            }
		        }
		    }
		
		    String[] values = convertAStoString(bd.getaValues(), bd.getdValues());
		
		    String win = "tie";
		    boolean winCheck = false;;
		    if (winner < 0) {
		        win = "defender";
		        winCheck = false;
		        if(attacker.getAbility() != TrainerAbilities.FIGHTER)
		        	for(int i = 0; i < 3; i++)
		        		attacker.getTeam()[i].updateHappiness(-1);
		        if(defender.rival.equals(attacker))
		        	gi.rivalReward(defender);
		    } else if (winner > 0) {
		        win = "attacker";
		        winCheck = true;
		        if(attacker.rival.equals(defender))
		        	gi.rivalReward(attacker);
		    }
		    
		    String result = printBResult(attacker, defender, values, win, bolds);
		    attacker.getResults().add(result);
		    defender.getResults().add(result);
		    
		    //Elite Four Check
		    if(defender.role.equalsIgnoreCase("Elite Four") && winCheck && eliteFourCheck){
		    	boolean e4Check = true;
		    	ArrayList<Player> e4 = new ArrayList<Player>();
		    	e4.add(defender);
		    	for(Player p : gi.getPlayers())
		    		if(p.role.equalsIgnoreCase("Elite Four") && !p.equals(defender) && p.location == defender.location && e4Check) {
		    			e4Check = challenge(attacker, p, gi, false);
		    			e4.add(p);
		    		}
		    	if(e4Check)
		    		gi.eliteFourWin(attacker, e4);
		    	else
		    		gi.eliteFourLoss(attacker, e4);
		    }
		    
		    //Gym Leader Check
		    if(winCheck) {
		    	gi.addBadge(attacker, defender);
		    }
		    //Champion Check
		    if(winCheck) {
		    	gi.checkVictory(attacker, defender);
		    }
		    	
		    return winCheck;
    	}
    }

    public double pBattle(Pokemon a, Pokemon d, PokeGame gi, boolean aggressor) {
        double result = gi.pokedex.typeChart[a.pdEntry.type1.ordinal()][d.pdEntry.type1.ordinal()];
        if (a.pdEntry.type2 != null) {
            result *= gi.pokedex.typeChart[a.pdEntry.type2.ordinal()][d.pdEntry.type1.ordinal()];
        }
        if (d.pdEntry.type2 != null) {
            result *= gi.pokedex.typeChart[a.pdEntry.type1.ordinal()][d.pdEntry.type2.ordinal()];
            if (a.pdEntry.type2 != null) {
                result *= gi.pokedex.typeChart[a.pdEntry.type2.ordinal()][d.pdEntry.type2.ordinal()];
            }
        }
        //check burn
        if(a.isBurned())
        	result *= .5;
        //check poison
        if(d.isPoisoned())
        	result *= 2;
        
        if (a.pdEntry.captureType == 'L')
            result *= 2;
        
        result *= a.getTrainer().aTeamMod;
        result *= d.getTrainer().dTeamMod;
        
        if(a.held != null && a.typeMatch(a.held.getType()))
        	result *= 2;
        
        if(d.getTrainer().immuneToType(a) && aggressor)
        	result = 0;
        if(d.getTrainer().vulnerableToType(a) && aggressor)
        	result = 16;
        
        if(!a.isActive())
        	result = 0;
        
        return result;
    }

    public String printBResult(Player a, Player d, String[] values, String winner, int[] bolds) {

        String aStrings = "";
        String dStrings = "";

        for (int i = 0; i < bolds.length; i++) {
            if (bolds[i] == 1) {
                aStrings += "[b]" + values[i] + "[/b] | ";
                dStrings += values[i + 3] + " | ";
            } else if (bolds[i] == 0) {
                dStrings += "[b]" + values[i + 3] + "[/b] | ";
                aStrings += values[i] + " | ";
            } else {
                aStrings += values[i] + " | ";
                dStrings += values[i + 3] + "| ";
            }
        }
        aStrings = aStrings.substring(0, aStrings.length() - 2);
        dStrings = dStrings.substring(0, dStrings.length() - 2);

        String result = "[b]" + a.alias2 + "[/b] (";
        result += aStrings + ") ";
        if (winner.equals("tie")) {
            result += "tied ";
        } else if (winner.equals("attacker")) {
            result += "defeated ";
        } else {
            result += "defeated by ";
        }
        result += "[b]" + d.alias2 + "[/b] (";
        result += dStrings + ") ";
        return result;
    }

    public String[] convertAStoString(double[] av, double[] dv) {
        String[] results = new String[6];

        int aLen = av.length;
        int bLen = dv.length;
        double[] temp = new double[aLen + bLen];
        System.arraycopy(av, 0, temp, 0, aLen);
        System.arraycopy(dv, 0, temp, aLen, bLen);

        for (int i = 0; i < temp.length; i++) {
            results[i] = avToString(temp[i]);
        }

        return results;
    }

    public String avToString(double val) {
        String result = "";

        if (val == 0) {
            result = "Immune";
        } else if (val < .125) {
            result = "Weak x4";
        } else if (val < .25) {
            result = "Weak x3";
        } else if (val < .5) {
            result = "Super Weak";
        } else if (val < 1) {
            result = "Weak";
        } else if (val < 2) {
            result = "Normal";
        } else if (val < 4) {
            result = "Effective";
        } else if (val < 8) {
            result = "Super Effective";
        } else if (val < 16) {
            result = "Strong x3";
        } else {
            result = "Strong x4";
        }

        return result;
    }
    
    public BattleData getBd() {
		return bd;
	}

	public void setBd(BattleData bd) {
		this.bd = bd;
	}

}
