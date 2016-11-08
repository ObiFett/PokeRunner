package pokeRunner;

import java.util.Random;

public class Pokemon {
    
    public enum PH {
        PNUM, SHIN, NAM, HAP, STAT, ABT, TGT, HLD
    };

    public PDEntry pdEntry;
    public String cName;
    public Player trainer;
    
    public Typings tAbility;
    
    //Freaking DARK Pokemons
    public boolean usedAbility;
    public Typings tempType;

	//order information
    public Pokemon[] poTarget;
    public Player[] plTarget;

    public boolean isPlayerTarget;

    public boolean shiny;
    public int happiness;
    public int happyMod;
    
    public HeldItems held;

    //statuses
    public boolean guarded;
    public boolean paralyzed;
    public boolean paralyzedActive;
    public boolean burned;
    public boolean frozen;
    public int fTime;
    public boolean poisoned;
    public boolean knockedOut;
    public boolean cursed;
    
    //special rules
    public PDEntry dittoInfo;

    public Pokemon(String[] readerData, PokeGame gameInfo, Player p) {
        pdEntry = gameInfo.pokedex.getEntry(Integer.parseInt(readerData[PH.PNUM.ordinal()]));
        if(readerData[PH.SHIN.ordinal()] == "y")
            shiny = true;
        else
            shiny = false;
        cName = readerData[PH.NAM.ordinal()];
        happiness = Integer.parseInt(readerData[PH.HAP.ordinal()]);
        loadStatus(readerData[PH.STAT.ordinal()]);
        tAbility = Typings.valueOf(readerData[PH.ABT.ordinal()]);
        if(gameInfo.abilities.targetType(tAbility) == "Pokemon") {
        	String[] poTargetStrings = readerData[PH.TGT.ordinal()].split("|");
        	poTarget = new Pokemon[poTargetStrings.length];
        	for(int i = 0; i < poTargetStrings.length; i++)
        		poTarget[i] = gameInfo.getPokemon(poTargetStrings[i]);
        } else {
        	String[] plTargetStrings = readerData[PH.TGT.ordinal()].split("|");
        	plTarget = new Player[plTargetStrings.length];
        	for(int i = 0; i < plTargetStrings.length; i++)
        		plTarget[i] = gameInfo.getPlayer(plTargetStrings[i]);
        }
        trainer = p;
        if(!readerData[PH.HLD.ordinal()].isEmpty())
        	held = HeldItems.valueOf(readerData[PH.HLD.ordinal()]);
        if(cName.equalsIgnoreCase("Ditto"))
        	dittoInfo = gameInfo.pokedex.getEntry(132);
        if(p.role.equalsIgnoreCase("Champion")){
        	happiness = 6;
        }
        usedAbility = false;
    }

    public Pokemon(PDEntry pd) {
        pdEntry = pd;
        if(pd.captureType == 'L')
            setAbilityType(pd.legendAbility);
        else {
            Typings test = null;
            setAbilityType(test);
        }
        happiness = 2;
        shiny = false;
        cName = "NAMELESS";
        initializeStatus();
    }
    
    public Pokemon(PokeGame gameInfo, int entryNum, String name, Typings abilityType){
    	this(gameInfo.pokedex.getEntry(entryNum));
    	cName = name;
    	setAbilityType(abilityType);
    }
    
    @Override
    public boolean equals(Object o) {
        boolean result = false;

        if (o instanceof String) {
            String test = (String) o;
            if (test.equalsIgnoreCase(cName)) {
                result = true;
            }
        } else if (o instanceof Pokemon) {
            if (this.equals(((Pokemon) o).cName)) {
                result = true;
            }
        }
        return result;
    }

    public void setAbilityType(Pokedex pdex){
        if(pdex.lTypes.containsKey(pdEntry.name))
            setAbilityType(pdex.lTypes.get(pdEntry.name));
        else {
            Typings test = null;
            setAbilityType(test);
        }
    }
    
    private void setAbilityType(Typings choice){
        
        if(choice != null && ((choice == pdEntry.type1) || (choice == pdEntry.type2)) ) {
    		tAbility = choice;
    	} else if(pdEntry.type2 != null){
            Random r = new Random();
            int aRoll = r.nextInt(2);
            if(aRoll == 1)
                tAbility = pdEntry.type1;
            else
                tAbility = pdEntry.type2;
       } else
           tAbility = pdEntry.type1;
    }

    private void initializeStatus() {
        loadStatus("R");
    }

    private void loadStatus(String statusData) {
        char[] stats = statusData.toCharArray();
        for(int i = 0; i < stats.length; i++){
            switch(stats[i]){
                case 'r':
                    break;
                case 'f':
                    this.frozen = true;
                    this.fTime = Integer.parseInt(stats[i+1]+"");
                    break;
                case 'b':
                    this.burned = true;
                    break;
                case 'p':
                    this.poisoned = true;
                    break;
                case 'k':
                    this.knockedOut = true;
                    break;
                case 'z':
                    this.paralyzed = true;
                    this.paralyzedActive = (stats[i+1] == 'a');
                    break;
                case 'c':
                	this.cursed = true;
                break;
                    
            }
        }
    }
    
    private String statDataDump(){
        String dataStream = "";
        if (paralyzed) {
            dataStream += "z";
            if (paralyzedActive) {
                dataStream += "a";
            } else {
                dataStream += "i";
            }
        }
        if (burned) {
            dataStream += "b";
        }
        if (frozen) {
            dataStream += "f" + this.fTime;
        }
        if (poisoned) {
            dataStream += "p";
        }
        if (knockedOut) {
            dataStream += "k";
        }
        if(cursed) {
        	dataStream += "c";
        }
        if (dataStream == "") {
            dataStream += "r";
        }
        return dataStream; 
    }
    
    public void evolve(Pokedex dex, Typings choice){
        if (pdEntry.canEvolve) {
            pdEntry = dex.getEntry(pdEntry.evolTo);
            clearStatus();
            setAbilityType(choice);
        }
    }

    public String printPM(boolean capture, PokeGame gi) {
        String d = "";
        
        //NON-MOVING SPRITE
        //d += "[img]http://www.serebii.net/";
        
        /*if (shiny) {
            d += "SHINY/XY/";
        } else {
            d += "xy/pokemon/";
        }*/
        //d += pdEntry.spriteNum + ".png[/img][br]";
        
        //SMALL MOVING SPRITE
        /*d += "[img]http://randompokemon.com/";
        if (shiny) {
            d += "sprites/animated/shiny/";
        } else {
            d += "sprites/animated/";
        }
        d += pdEntry.number + ".gif[/img][br]";*/
        
        //COOL MOVING SPRITE
        d += "[img]http://www.pkparaiso.com/imagenes/xy/sprites/animados/";
        /*if (shiny) {
            d += "sprites/animated/shiny/";
        } else {
            d += "sprites/animated/";
        }*/
        d += pdEntry.name.toLowerCase().replaceAll(" ", "_") + ".gif[/img][br]";
        
        if(!capture)
        	d += "[b]" + cName + "[/b] - " + pdEntry.name.toUpperCase();
        d += " ([b]" + pdEntry.type1.description(pdEntry.type1.ordinal());
        if(pdEntry.type2 != null)
            d+= "/" + pdEntry.type2.description(pdEntry.type2.ordinal());
        d += "[/b])[br]";
        if(!capture){
            if(this.pdEntry.canEvolve)
                d += "Can evolve to " + gi.pokedex.getEntry(this.pdEntry.evolTo).name;
            d += "[indent][i]Status[/i] = " + statusPM() + "[br]";
            d += "[i]Happiness[/i] = " + happinessPM() + "[br]";
        }
        if(held != null)
        	d += "[i]Held Item[/i] - " + held.pmPrint() + "[br]";
        if(pdEntry.pLevel > 0)
            d += gi.abilities.poAbilityInfo.get(tAbility)[pdEntry.pLevel-1] + "[br]";
        if(pdEntry.captureType == 'L')
            d += "[b]LEGENDARY[/b]: This pokemon's attack effectiveness is doubled during challenges[br]";
        if(dittoInfo != null)
            d += "[b]Ditto[/b] - Name a pokemon in the region. Ditto will "
                + "take that form (along with all abilities) the next day.[br]";
        if(gi.pokedex.isEevee(pdEntry))
        	d += "[b]Eevee[/b] (Choose) - Can change into any Eevee evolution at will. This will take place at the beginning of the night.";
        if(!capture)
        	d += "[/indent]";
        return d;
    }
    
    public String printPM(PokeGame gi){
    	return printPM(false, gi);
    }
    
    public String printCapPM(PokeGame gi){
    	return printPM(true, gi);
    }

    public String printBoxPM(PokeGame gi) {
        String bpm = "";

        bpm += "[b]" + cName + "[/b] - " + pdEntry.name.toUpperCase();
        bpm += " ([b]" + pdEntry.type1.description(pdEntry.type1.ordinal());
        if(pdEntry.type2 != null) 
        	bpm += "/" + pdEntry.type2.description(pdEntry.type2.ordinal());
        bpm += "[/b])";
        if(pdEntry.pLevel > 0)
            bpm += " : " + gi.abilities.poAbilityInfo.get(this.tAbility)[pdEntry.pLevel-1];
        return bpm;
    }

    public String statusPM() {
        String s = "";
        if (paralyzed) {
            s += "Paralyzed ";
            if (paralyzedActive) {
                s += "(Unable to Fight), ";
            } else {
                s += "(Able to Fight), ";
            }
        }
        if (burned) {
            s += "Burned";
        }
        if (frozen) {
            s += "Frozen (" + fTime + "), ";
        }
        if (poisoned) {
            s += "Poisoned, ";
        }
        if(cursed) {
        	s += "Cursed, ";
        }
        if (knockedOut) {
            s += "Knocked Out, ";
        }
        if (s == "") {
            s += "Ready!  ";
        }
        return s.substring(0, s.length() - 2);
    }
    
    public String dataDump(){
        
//        public enum PH {
//            PNUM, SHIN, NAM, HAP, STAT, ABT, TGT, HLD
//        };
    	String dataStream = "";
    	dataStream += this.pdEntry.number + ",";
        if(this.shiny)
            dataStream += "y,";
        else
            dataStream += "n,";
        dataStream += this.cName + ",";
        dataStream += this.happiness + ",";
        dataStream += statDataDump() + ",";
        dataStream += this.tAbility + ",";
        dataStream += ",";
        if(this.held != null)
        	dataStream += this.held.name();
        dataStream += ",";
    	return dataStream;
    }
    
    public String secDataDump(){
        return pdEntry.number + ":" + this.cName + ":" + this.tAbility;
    }

    public String happinessPM() {
        String[] hString = {"Dead", "Detest", "Scared", "Neutral", "Friendly", "Very Close", "Favorite!"};
        return hString[happiness];
    }

    public void updateHappiness(int num) {
    	if(num < 0)
    		num *= 2;
    	if(happiness != 6) {
	        happiness += num * happyMod;
	        if (happiness < 1) {
	            happiness = 1;
	        } else if (happiness > 5) {
	            happiness = 5;
	        }
    	}
    }

    public void makeFavorite() {
        happiness = 6;
    }

    public Object[] getTarget() {
        if (isPlayerTarget) {
            return plTarget;
        } else {
            return poTarget;
        }
    }

    public boolean isActive() {
        if (!paralyzedActive && !frozen && !knockedOut) {
            return true;
        } else {
            return false;
        }
    }

    public void clearStatus() {
        paralyzed = false;
        paralyzedActive = false;
        burned = false;
        frozen = false;
        fTime = 0;
        poisoned = false;
        cursed = false;
    }
    
    public void knockOut(){
    	if(happiness != 6) {
    		knockedOut = true;
    		clearStatus();
    		happiness = 3;
    		this.getTrainer().getResults().add(this.cName + " was Knocked Out!");
    	}
    }
    
    public void revive(){
    	if(knockedOut) {
			knockedOut = false;
			if(happiness != 6)
				setHappiness(3);
			this.getTrainer().getResults().add(this.cName + " was Revived!");
    	} else
    		clearStatus();
    }

    public void freeze(int time) {
        if (!guarded) {
            frozen = true;
            fTime = time;
            this.getTrainer().getResults().add(this.cName + " is Frozen!");
        }
    }

    public void burn() {
        if (!guarded && !knockedOut) {
            burned = true;
            this.getTrainer().getResults().add(this.cName + " is Burned!");
        }
    }

    public void paralyze() {
        if (!guarded && !paralyzedActive) {
            paralyzed = true;
            paralyzedActive = false;
            this.getTrainer().getResults().add(this.cName + " is Paralyzed!");
        }
    }

    public void poison() {
        if (!guarded) {
            poisoned = true;
            this.getTrainer().getResults().add(this.cName + " is Poisoned!");
        }
    }

    public PDEntry getPdEntry() {
        return pdEntry;
    }

    public void setPdEntry(PDEntry pdEntry) {
        this.pdEntry = pdEntry;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Player getTrainer() {
        return trainer;
    }

    public void setTrainer(Player trainer) {
        this.trainer = trainer;
    }

    public Typings gettAbility() {
        return tAbility;
    }

    public void settAbility(Typings tAbility) {
        this.tAbility = tAbility;
    }

    public Pokemon[] getPoTarget() {
        return poTarget;
    }

    public void setPoTarget(Pokemon[] poTarget) {
        this.poTarget = poTarget;
    }

    public Player[] getPlTarget() {
        return plTarget;
    }

    public void setPlTarget(Player[] plTarget) {
        this.plTarget = plTarget;
    }

    public boolean isPlayerTarget() {
        return isPlayerTarget;
    }

    public void setPlayerTarget(boolean isPlayerTarget) {
        this.isPlayerTarget = isPlayerTarget;
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHappyMod() {
        return happyMod;
    }

    public void setHappyMod(int happyMod) {
        this.happyMod = happyMod;
    }

    public boolean isGuarded() {
        return guarded;
    }

    public void setGuarded(boolean guarded) {
        this.guarded = guarded;
    }

    public boolean isParalyzed() {
        return paralyzed;
    }

    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
        if(paralyzed)
			this.getTrainer().getResults().add(this.cName + " is Paralyzed!");
    }

    public boolean isParalyzedActive() {
        return paralyzedActive;
    }

    public void setParalyzedActive(boolean paralyzedActive) {
        this.paralyzedActive = paralyzedActive;
    }

    public boolean isBurned() {
        return burned;
    }

    public void setBurned(boolean burned) {
        this.burned = burned;
        if(burned)
			this.getTrainer().getResults().add(this.cName + " is Burned!");
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
        if(frozen)
			this.getTrainer().getResults().add(this.cName + " is Frozen!");
    }

    public int getfTime() {
        return fTime;
    }

    public void setfTime(int fTime) {
        this.fTime = fTime;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
        if(poisoned)
			this.getTrainer().getResults().add(this.cName + " is Poisoned!");
    }

    public boolean isKnockedOut() {
        return knockedOut;
    }

    public void setKnockedOut(boolean knockedOut) {
        this.knockedOut = knockedOut;
        if(knockedOut)
			this.getTrainer().getResults().add(this.cName + " is Knocked Out!");
    }
    
    public boolean typeMatch(Typings t){
    	boolean result = false;
    	if(pdEntry.type1 != null && pdEntry.type1 == t)
    		result = true;
    	if(pdEntry.type2 != null && pdEntry.type2 == t)
    		result = true;
    	return result;
    }
    public boolean isUsedAbility() {
		return usedAbility;
	}

	public void setUsedAbility(boolean usedAbility) {
		this.usedAbility = usedAbility;
	}
	
	public Typings getCombatType(){
		if(tempType == null)
			return tAbility;
		else
			return tempType;
	}

	public Typings getTempType() {
		return tempType;
	}

	public void setTempType(Typings tempType) {
		this.tempType = tempType;
	}

	public HeldItems getHeld() {
		return held;
	}

	public void setHeld(HeldItems held) {
		this.held = held;
	}

	public boolean isCursed() {
		return cursed;
	}

	public void setCursed(boolean cursed) {
		this.cursed = cursed;
		if(cursed)
			this.getTrainer().getResults().add(this.cName + " is Cursed!");
	}
}
