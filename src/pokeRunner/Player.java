package pokeRunner;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    public enum RH {

        PNAME, AONE, ATWO, F, ALIGN, COVER, ROLE, INFO, TYPE,
        LOC, RIV, ACT, TAR1, SPACT, TAR2, MV,
        P1, S1, N1, H1, ST1, A1, T1, HD1, //Pokemon 1
        P2, S2, N2, H2, ST2, A2, T2, HD2, //Pokemon 2
        P3, S3, N3, H3, ST3, A3, T3, HD3, //Pokemon 3
        RE, LE, ER, LU, PB, FH, CA, TM, // Inventory
        CAP, BOX
    };

    public String faction;
    public String alignment;
    public String cover;
    public String role;
    public Player rival;
    public String rivalString;

    public Pokemon[] team;
    public ArrayList<Pokemon> box;
    public ArrayList<Pokemon> captured;

    
    public String basicAction;
    public String baTarget;
    
    public String specialAction;
    public String[] saTarget;
    public Locations saLocation;
    
    public String paName;
    public String alias1;
    public String alias2;

    public int[] items;
    public ArrayList<String> tms;
    
    public Locations location;
    public Locations move;
    public int speed;

    //item flags
    public String lure;

    public TrainerAbilities ability;
    public SpecialTrainerAbilities sAbility;

    //battle flags
    public int underground;
    public int avoidChallenge;
    public double aTeamMod;
    public double dTeamMod;
    public ArrayList<Typings> immune;
    public ArrayList<Typings> vulnerable;
    
    private ArrayList<String> results;

    public Player(String[] playerInfo, PokeGame gameInfo) {

        paName = playerInfo[RH.PNAME.ordinal()];
        alias1 = playerInfo[RH.AONE.ordinal()];
        alias2 = playerInfo[RH.ATWO.ordinal()];
        
        faction = playerInfo[RH.F.ordinal()];
        alignment = playerInfo[RH.ALIGN.ordinal()];
        cover = playerInfo[RH.COVER.ordinal()];
        role = playerInfo[RH.ROLE.ordinal()];
        rivalString = playerInfo[RH.RIV.ordinal()];

        ability = TrainerAbilities.valueOf(playerInfo[RH.TYPE.ordinal()]);
        if(!playerInfo[RH.SPACT.ordinal()].isEmpty())
            sAbility = SpecialTrainerAbilities.valueOf(playerInfo[RH.SPACT.ordinal()]);
        location = Locations.valueOf(playerInfo[RH.LOC.ordinal()]);
        avoidChallenge = 0;
        underground = 0;
        
        basicAction = playerInfo[RH.ACT.ordinal()];
        baTarget = playerInfo[RH.TAR1.ordinal()];
        
        specialAction = playerInfo[RH.SPACT.ordinal()];
        saTarget = playerInfo[RH.TAR2.ordinal()].split("\\|");
        
        team = new Pokemon[3];
        addTeam(playerInfo, gameInfo);
        
        int itemNum = 7;
        items = new int[itemNum];
        for (int i = 0; i < itemNum; i++) {
            String amt = playerInfo[RH.RE.ordinal() + i];
            if ("".equals(amt)) {
                items[i] = 0;
            } else {
                items[i] = Integer.parseInt(amt);
            }
        }
        
        if(!playerInfo[RH.TM.ordinal()].isEmpty()) {
            tms = new ArrayList<String>(Arrays.asList(playerInfo[RH.TM.ordinal()].split("\\|")));
            if(tms.size() < 2 && tms.get(0).length() < 2 && !tms.isEmpty())
                tms.set(0, "0" + tms.get(0));
        } else {
            tms = new ArrayList<String>();
        }
        immune = new ArrayList<Typings>();
        vulnerable = new ArrayList<Typings>();
        if(!playerInfo[RH.INFO.ordinal()].isEmpty() && role.equalsIgnoreCase("Gym Leader")) {
            ArrayList<String> saInfo = new ArrayList<String>(Arrays.asList(playerInfo[RH.INFO.ordinal()].split("\\|")));
            immune.add(Typings.valueOf(saInfo.get(0)));
            immune.add(Typings.valueOf(saInfo.get(1)));
            immune.add(Typings.valueOf(saInfo.get(2)));
            immune.add(Typings.valueOf(saInfo.get(3)));
            vulnerable.add(Typings.valueOf(saInfo.get(4)));
            vulnerable.add(Typings.valueOf(saInfo.get(5)));
            saLocation = Locations.valueOf(saInfo.get(6));
        } else {
            immune = new ArrayList<Typings>();
            vulnerable = new ArrayList<Typings>();
        }
        
        box = new ArrayList<>();
        if(!playerInfo[RH.BOX.ordinal()].isEmpty())
            addBox(playerInfo[RH.BOX.ordinal()], gameInfo);
        
        captured = new ArrayList<Pokemon>();        
        if(!playerInfo[RH.CAP.ordinal()].isEmpty())
            addCapd(playerInfo[RH.CAP.ordinal()], gameInfo);
        
        results = new ArrayList<>();
        
        aTeamMod = 1;
        dTeamMod = 1;
        
        if(role.equalsIgnoreCase("Champion") && ability == TrainerAbilities.FIGHTER)
        	dTeamMod  = .75;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;

        if (o instanceof String) {
            String test = (String) o;
            if (test.equalsIgnoreCase(paName) || test.equalsIgnoreCase(alias1) || test.equalsIgnoreCase(alias2)) {
                result = true;
            }
        } else if (o instanceof Player) {
            if (this.equals(((Player) o).paName)) {
                result = true;
            }
        }
        return result;
    }

    private void addBox(String boxInfo, PokeGame gi) {
        String[] boxArray = boxInfo.split("\\|");
        for (int i = 0; i < boxArray.length; i++) {
            String[] boxArrayPart = boxArray[i].split(":");
            Pokemon mon = new Pokemon(gi, Integer.parseInt(boxArrayPart[0]), boxArrayPart[1], Typings.valueOf(boxArrayPart[2]));
            box.add(mon);
        }
    }

    private void addCapd(String capInfo, PokeGame gi) {
        String[] capArray = capInfo.split("\\|");
        for (int i = 0; i < capArray.length; i++) {
            String[] capArrayPart = capArray[i].split(":");
            Pokemon mon = new Pokemon(gi, Integer.parseInt(capArrayPart[0]), capArrayPart[1], Typings.valueOf(capArrayPart[2]));
            captured.add(mon);
        }
    }

    private void addTeam(String[] pokemonTeam, PokeGame gameInfo) {
        int pDataSize = Pokemon.PH.values().length;
        int startColumn = 16;
        for (int i = 0; i < 3; i++) {
            String[] pokemon = new String[pDataSize];
            System.arraycopy(pokemonTeam, i * pDataSize + startColumn, pokemon, 0, pDataSize);
            addPokemonToTeam(pokemon, i, gameInfo);
        }
    }

    public void addPokemonToTeam(String[] pokeData, int pos, PokeGame gameInfo) {
        Pokemon poke = new Pokemon(pokeData, gameInfo, this);
        team[pos] = poke;
    }

    public void pokeNonCombat(PokeGame gi) {
        for (int i = 0; i < team.length; i++) {
            if (team[i].isActive() && gi.abilities.abilityType(team[i]).equals("NonCombat")) {
                gi.abilities.activateAbility(team[i], team[i].getTarget());
            }
        }
    }

    public String teamSeer() {
        String seerResult = "";
        seerResult += alias2;
        seerResult += " has the following team:";
        for (int i = 0; i < team.length; i++) {
            seerResult += team[i].pdEntry.name + ", ";
        }
        return seerResult.substring(0, seerResult.length() - 2);
    }

    public String abilitySeer(String type) {
        if ("type".equalsIgnoreCase("rand")) {
            return "One ability chosen at random";
        } else {
            return "Put ability seer info here";
        }
    }
    
    public boolean immuneToType(Pokemon p){
    	boolean result = false;
    	for(Typings t : immune)
    		if(p.typeMatch(t))
    			result = true;
    	return result;
    }
    
    public boolean vulnerableToType(Pokemon p){
    	boolean result = false;
    	for(Typings t : vulnerable)
    		if(p.typeMatch(t))
    			result = true;
    	return result;
    }

    public String printItemsPM() {
        String itemResult = "";
        for (int i = 0; i < items.length; i++) {
            if (items[i] > 0) {
                itemResult += ItemType.printPM(i) + "(" + items[i] + ") ";
            }
        }
        if (!tms.isEmpty()) {
            itemResult += "TMs: [";
            for (int i = 0; i < tms.size(); i++) {
                itemResult += tms.get(i) + " ";
            }
            itemResult = itemResult.substring(0, itemResult.length() - 1) + "]";
        }

        return itemResult;
    }

    public String getWinconPM() {
        String pmInfo = "";
        switch (faction) {
            case "[b][color=dodgerblue]Windy City Charizards[/color][/b]":
            case "[b][color=green]Pokemon Sans Frontiers[/color][/b]":
            case "[b][color=goldenrod]Poketrainers of No Quarter[/color][/b]":
            case "[b][color=red]Team Rocket[/color][/b]":
                pmInfo += faction + " is the first team to complete the following steps in order:[indent][br]";
                pmInfo += "1: Gain all 8 Badges by defeating each Gym Leader. " +
                			"Defeating a Gym Leader will award their badge to your entire team. " +
                			"Team members who have been voted out or vigged can still gain Badges for their faction by defeating Gym Leaders on the Adventure Board. " +
                			"Voting out a Gym Leader gives their badge to all Teams.[br]";
                pmInfo += "2: Defeat each member of the Elite Four. " +
                			"Defeating a member of the Elite Four counts as defeating that Elite Four for your entire team. " +
                			"Team members who have been voted out or vigged who then defeat a member of the Elite Four on the Adventure Board still count as contributed to this step. " +
                			"Voting out a member of the Elite Four counts as defeating that member of the Elite Four for all Teams.[br]";
                pmInfo += "3: Defeat the Champion. " +
                			"Voting out the Champion does not help any faction complete this step. " +
                			"Only players who have not been voted out or vigged can complete this step for their Team.[/indent]";
                break;
            case "[b][color=purple]Defender of the League[/color][/b]":
                pmInfo += "No faction is able to attempt step 3 (challenge the Champion) of their wincon[br]";
                break;
            case "[b]Pokemon League Champion[/b]":
                pmInfo += "At least one trainer challenges you for the Championship AND you are still the Champion at the end of the game.[br]";
            default:
                break;
        }
        return pmInfo;
    }

    public String printPokeBox(PokeGame gi) {
        String pb = "";
        for (Pokemon p : box) {
            pb += p.printBoxPM(gi) + "[br]";
        }
        return pb;
    }

    public String dataDump() {
//        public enum RH {
//
//            PNAME, AONE, ATWO, F, ALIGN, COVER, ROLE, INFO, TYPE,
//            LOC, RIV, ACT, TAR1, SPACT, TAR2, MV,
//            P1, S1, N1, H1, ST1, A1, T1, HLD1 //Pokemon 1
//            P2, S2, N2, H2, ST2, A2, T2, HLD2 //Pokemon 2
//            P3, S3, N3, H3, ST3, A3, T3, HLD3 //Pokemon 3
//            RE, LE, ER, LU, PB, FH, CA, TM, // Inventory
//            CAP, BOX
//        };
        String dataStream = "";
        dataStream += this.paName + ",";
        dataStream += this.alias1 + ",";
        dataStream += this.alias2 + ",";
        dataStream += this.faction + ",";
        dataStream += this.alignment + ",";
        dataStream += this.cover + ",";
        dataStream += this.role + ",";
        for(Typings t : this.immune) {
        	dataStream += t.name() + "|";
        }
        for(Typings t : this.vulnerable){
        	dataStream += t.name() + "|";
        }
        dataStream += ",";
        dataStream += this.ability + ",";
        dataStream += this.location + ",";
        if(this.rival != null)
            dataStream += this.rival.alias2;
        dataStream += ",";
        dataStream += ",";
        dataStream += ",";
        if(this.sAbility != null)
        	dataStream += this.sAbility;
        dataStream += ",";
        dataStream += ",";
        dataStream += ",";
        for (Pokemon p : this.getTeam()) {
            dataStream += p.dataDump();
        }
        for (int i = 0; i < this.items.length; i++) {
            dataStream += items[i] + ",";
        }
        if(tms.isEmpty())
            dataStream += ",";
        for (int i = 0; i < tms.size(); i++) {
            dataStream += tms.get(i);
                if(i < tms.size()-1)
                    dataStream += "|";
                else
                    dataStream += ",";
                    
        }
        if(captured.isEmpty())
            dataStream += ",";
        for (int i = 0; i < captured.size(); i++){
            dataStream += captured.get(i).secDataDump();
            if(i < captured.size()-1)
                    dataStream += "|";
                else
                    dataStream += ",";
        }
        if(box.isEmpty())
            dataStream += ",";
        for (int i = 0; i < box.size(); i++){
            dataStream += box.get(i).secDataDump();
            if(i < box.size()-1)
                    dataStream += "|";
                else
                    dataStream += ",";
        }
        return dataStream;
    }

    public String printFactionPM() {
        return faction;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public Player getRival() {
        return rival;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public Pokemon[] getTeam() {
        return team;
    }

    public void setTeam(Pokemon[] team) {
        this.team = team;
    }

    public void setTeam(int pos, Pokemon poke) {
        team[pos] = poke;
    }

    public ArrayList<Pokemon> getBox() {
        return box;
    }

    public void setBox(ArrayList<Pokemon> box) {
        this.box = box;
    }

    public ArrayList<Pokemon> getCaptured() {
        return captured;
    }

    public void setCaptured(ArrayList<Pokemon> captured) {
        this.captured = captured;
    }

    public String getPaName() {
        return paName;
    }

    public void setPaName(String paName) {
        this.paName = paName;
    }

    public String getAlias1() {
        return alias1;
    }

    public void setAlias1(String alias1) {
        this.alias1 = alias1;
    }

    public String getAlias2() {
        return alias2;
    }

    public void setAlias2(String alias2) {
        this.alias2 = alias2;
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
    }

    public void setItems(int pos, int value) {
        this.items[pos] = value;
    }

    public ArrayList<String> getTms() {
        return tms;
    }

    public void setTms(ArrayList<String> tms) {
        this.tms = tms;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public int getAvoidChallenge() {
        return avoidChallenge;
    }

    public void setAvoidChallenge(int avoidChallenge) {
        this.avoidChallenge = avoidChallenge;
    }

    public TrainerAbilities getAbility() {
        return ability;
    }

    public void setAbility(TrainerAbilities ability) {
        this.ability = ability;
    }

    public SpecialTrainerAbilities getsAbility() {
        return sAbility;
    }

    public void setsAbility(SpecialTrainerAbilities sAbility) {
        this.sAbility = sAbility;
    }

    public int isUnderground() {
        return underground;
    }

    public void setUnderground(int underground) {
        this.underground = underground;
    }

    public ArrayList<String> getResults() {
        return results;
    }

    public void setResults(ArrayList<String> results) {
        this.results = results;
    }

    public void setLure(String l) {
        this.lure = l;
    }
    
    public double getaTeamMod() {
		return aTeamMod;
	}

	public void setaTeamMod(double aTeamMod) {
		this.aTeamMod = aTeamMod;
	}

	public double getdTeamMod() {
		return dTeamMod;
	}

	public void setdTeamMod(double dTeamMod) {
		this.dTeamMod = dTeamMod;
	}

	public ArrayList<Typings> getImmune() {
		return immune;
	}

	public void setImmune(ArrayList<Typings> immune) {
		this.immune = immune;
	}

	public ArrayList<Typings> getVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(ArrayList<Typings> vulnerable) {
		this.vulnerable = vulnerable;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getBasicAction() {
		return basicAction;
	}

	public void setBasicAction(String basicAction) {
		this.basicAction = basicAction;
	}

	public String getBaTarget() {
		return baTarget;
	}

	public void setBaTarget(String baTarget) {
		this.baTarget = baTarget;
	}

	public String getSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(String specialAction) {
		this.specialAction = specialAction;
	}

	public String[] getSaTarget() {
		return saTarget;
	}

	public void setSaTarget(String saTarget, int pos) {
		this.saTarget[pos] = saTarget;
	}

	public Locations getMove() {
		return move;
	}

	public void setMove(Locations move) {
		this.move = move;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getLure() {
		return lure;
	}

	public int getUnderground() {
		return underground;
	}
}
