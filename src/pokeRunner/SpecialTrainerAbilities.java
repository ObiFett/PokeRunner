package pokeRunner;

import java.util.ArrayList;
import java.util.Arrays;

public enum SpecialTrainerAbilities {

    LEAGUECHAMPION("League Champion",
        "[b]Pokemon Whisperer[/b] (Passive): Your Pokemon are always at maximum happiness.[br]"
        + "[b]Earned Respect[/b] (Passive): You are immune to the vote unless >60% of the playerbase votes you out. You are immune to the first vig every night.[br]"
        + "[b]Tower Defense[/b] (Passive): While at the Tower of Champions you are immune to all player targeted abilities.[br]"
        + "[b]Balancing Act[/b] (Vig): Target a player. They will be removed from the thread.[br]"
        + "[b]Knowledge is Power[/b] (Target{s} Required): Target up to 3 players. You will also recieve their results this night.[br]"),
    ELITEFOUR1("Elite Four",
        "[b]Strong Together[/b] (Passive): When a trainer challenges you they will automatically challenge any other Elite Four members at the same location. Trainers must win all battles with Elite Four for the challenge to count as a victory for the purposes of their win condition.[br]"
        + "[b]League Resources[/b] (Active [2-day cooldown]): This power can be activated to perform the same function as a REST action. Your pokemon will be revived and cleared of status effects. You will also have access to your pokebox. You can still use another basic action (Challenge Explore Capture)."
        + "[b]Read Mind[/b] (Target Required): Choose a player. You will learn their role information.[br]"),
    ELITEFOUR2("Elite Four", 
        "[b]Strong Together[/b] (Passive): When a trainer challenges you they will automatically challenge any other Elite Four members at the same location. Trainers must win all battles with Elite Four for the challenge to count as a victory for the purposes of their win condition.[br]"
        + "[b]League Resources[/b] (Active [2-day cooldown]): This power can be activated to perform the same function as a REST action. Your pokemon will be revived and cleared of status effects. You will also have access to your pokebox. You can still use another basic action (Challenge Explore Capture)."
        + "[b]Buddy Up[/b] (Target Required): Choose a player. They will be protected from the first vig targeting them tonight.[br]"),    
    ELITEFOUR3("Elite Four", 
        "[b]Strong Together[/b] (Passive): When a trainer challenges you they will automatically challenge any other Elite Four members at the same location. Trainers must win all battles with Elite Four for the challenge to count as a victory for the purposes of their win condition.[br]"
        + "[b]League Resources[/b] (Active [2-day cooldown]): This power can be activated to perform the same function as a REST action. Your pokemon will be revived and cleared of status effects. You will also have access to your pokebox. You can still use another basic action (Challenge Explore Capture)."
        + "[b]Connected[/b] (Passive): You start off knowing the name of one Gym Leader.[br]"),    
    ELITEFOUR4("Elite Four", 
        "[b]Strong Together[/b] (Passive): When a trainer challenges you they will automatically challenge any other Elite Four members at the same location. Trainers must win all battles with Elite Four for the challenge to count as a victory for the purposes of their win condition.[br]"
        + "[b]League Resources[/b] (Active [2-day cooldown]): This power can be activated to perform the same function as a REST action. Your pokemon will be revived and cleared of status effects. You will also have access to your pokebox. You can still use another basic action (Challenge Explore Capture)."
        + "[b]Connected[/b] (Passive): You start off knowing the name of one Gym Leader.[br]"),
    GYMLEADER1("Gym Leader", 
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from DARK FIRE STEEL or FIGHTING) and weakness (will be auto knocked out by PSYCHIC or ROCK)."),
    GYMLEADER2("Gym Leader", 
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from STEEL FIGHTING PSYCHIC or ROCK) and weakness (will be auto knocked out by WATER or GHOST)."),
    GYMLEADER3("Gym Leader", 
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from PSYCHIC ROCK WATER or GHOST) and weakness (will be auto knocked out by ELECTRIC or BUG)."),
    GYMLEADER4("Gym Leader", 
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from WATER GHOST ELECTRIC or BUG) and weakness (will be auto knocked out by ICE or GRASS)."),
    GYMLEADER5("Gym Leader", 
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from ELECTRIC BUG ICE or GRASS) and weakness (will be auto knocked out by FAIRY or NORMAL)."),
    GYMLEADER6("Gym Leader", 
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from ICE GRASS FAIRY or NORMAL) and weakness (will be auto knocked out by GROUND or POISON)."),
    GYMLEADER7("Gym Leader",
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from FAIRY NORMAL GROUND or POISON) and weakness (will be auto knocked out by DRAGON or FLYING)."),
    GYMLEADER8("Gym Leader",
        "[b]Home Gym Advantage[/b] (Passive): While at your Gym Location your pokemon will be automatically revived after every fight.[br]"
        + "[b]Singular Focus[/b] (Passive): Your intense training has given your pokemon immunity (will take no damage from GROUND POISON DRAGON or FLYING) and weakness (will be auto knocked out by DARK or FIRE)."),
    FACTIONLEADER("Leader","[b]Lead By Example[/b] (Passive): If you are at the same location as a member of your faction then your pokemon can not lose happiness.[br]"
        + "[b]Connected[/b] (Passive): You start off masoned with your seer.[br]"
        + "[b]Lead By Influence[/b] (Target Required): Choose a player. If they are in your faction their team will deal double damage in challenges tonight. If they are not in your faction they will deal half damage in their challenges tonight.[br]"),
    FACTIONSEER("Psychic","[b]Read Mind[/b] (Target Required): Choose a player. You will learn their trainer name and faction.[br]"
        + "[b]Connected[/b] (Passive): You start off masoned with your Leader.[br]"),
    FACTIONGUARD("Cool Guy","[b]Buddy Up[/b] (Target Required): Choose a player. They will be protected from the first vig targeting them tonight.[br]"),
    FACTIONVIG("Bully","[b]Push Around[/b] (Target Required): Choose a player. If your faction has the least number of badges then you will remove them from the Pokemon League.[br]"),
    MAFIATHRALL("Liar", "[b]Lie[/b] (Passive): When seered you show up as the same faction that seered you."),
    MAFIALEADER("Leader", "[b]Lead By Fear[/b] (Passive): All pokemon not owned by Team Rocket at your location lose one happiness.[br]"
        + "[b]We used to Rule[/b] (Passive): You start off knowing two Gym Leader's trainer names.[br]"
        + "[b]Lead By Influence[/b] (Target Required): Choose a player. If they are in your faction their team will deal double damage in challenges tonight. If they are not in your faction they will deal half damage in their challenges tonight.[br]"),
    MAFIASPY("Sneak", "[b]Spy[/b] (Target Required): Choose a player. You recieve their results that night along with your own.");

    private final String powerDescription;
    private final String pmName;
    private final ArrayList<String> factionTypes;
    private final ArrayList<String> modifyPMTypes;

    SpecialTrainerAbilities(String pn, String pd) {
        powerDescription = pd;
        pmName = pn;
        factionTypes = new ArrayList<String>(Arrays.asList("Leader", "Psychic", "Cool Guy", "Bully"));
        modifyPMTypes = new ArrayList<String>(Arrays.asList("Elite Four", "Gym Leader"));
    }

    public String description(PokeGame gi) {
        if (modifyPMTypes.contains(pmName)) {
            String result = "";
            switch (this.name()) {
                case "ELITEFOUR1":
                    break;
                case "ELITEFOUR2":
                    break;
                case "ELITEFOUR3":
                    break;
                case "ELITEFOUR4":
                    break;
                case "GYMLEADER1":
                    break;
                case "GYMLEADER2":
                    break;
                case "GYMLEADER3":
                    break;
                case "GYMLEADER4":
                    break;
                case "GYMLEADER5":
                    break;
                case "GYMLEADER6":
                    break;
                case "GYMLEADER7":
                    break;
                case "GYMLEADER8":
                    break;

            }

            return result;
        } else {
            return powerDescription;
        }
    }

    public String pmName(String faction, Player p) {
        if (this.pmName.equalsIgnoreCase("Gym Leader")) {
            return p.saTarget[0].toString() + " " + pmName;
        } else {
            return pmName;
        }
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    public String getPmName() {
        return pmName;
    }

    public ArrayList<String> getFactionTypes() {
        return factionTypes;
    }

    public ArrayList<String> getModifyPMTypes() {
        return modifyPMTypes;
    }
    
    public void use(){
        
    }

}
