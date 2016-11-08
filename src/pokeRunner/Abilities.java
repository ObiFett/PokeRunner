package pokeRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Abilities {

    public String trigger;
    Random rand;
    Pokedex pokedex;

    Map<Typings, String[]> poAbilityInfo;
    ArrayList<Typings> preCombatTypes;
    ArrayList<Typings> midCombatTypes;
    ArrayList<Typings> postCombatTypes;
    ArrayList<Typings> postKnockoutTypes;
    ArrayList<Typings> stealableTypes;

    public Abilities(Pokedex p) {
        rand = new Random();
        createAbilityInfo();
        initializeTypes();
        pokedex = p;
    }

    public String abilityType(Pokemon mon) {
        Typings ability;
        if (mon.tempType == null) {
            ability = mon.tAbility;
        } else {
            ability = mon.tempType;
        }

        if (preCombatTypes.contains(ability)) {
            return "PreCombat";
        } else if (midCombatTypes.contains(ability)) {
            return "MidCombat";
        } else if (postCombatTypes.contains(ability)) {
            return "PostCombat";
        } else if (postKnockoutTypes.contains(ability)) {
            return "PostKnockout";
        } else {
            return "NonCombat";
        }
    }

    public BattleData preCombat(int pos, BattleData bd, PokeGame gi) {
        if (pos < 3) {
            if (abilityType(bd.getAttacker().getTeam()[pos]).equalsIgnoreCase("PreCombat")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        } else {
            if (abilityType(bd.getDefender().getTeam()[pos - 3]).equalsIgnoreCase("PreCombat")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        }
        return bd;
    }

    public BattleData midCombat(int pos, BattleData bd, PokeGame gi) {
        if (pos < 3) {
            if (abilityType(bd.getAttacker().getTeam()[pos]).equalsIgnoreCase("MidCombat")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        } else {
            if (abilityType(bd.getDefender().getTeam()[pos - 3]).equalsIgnoreCase("MidCombat")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        }
        return bd;
    }

    public BattleData postCombat(int pos, BattleData bd, PokeGame gi) {
        if (pos < 3) {
            if (abilityType(bd.getAttacker().getTeam()[pos]).equalsIgnoreCase("PostCombat")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        } else {
            if (abilityType(bd.getDefender().getTeam()[pos - 3]).equalsIgnoreCase("PostCombat")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        }
        return bd;
    }

    public BattleData postKnockout(int pos, BattleData bd, PokeGame gi) {
        if (pos < 3) {
            if (abilityType(bd.getAttacker().getTeam()[pos]).equalsIgnoreCase("PostKnockout")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        } else {
            if (abilityType(bd.getDefender().getTeam()[pos - 3]).equalsIgnoreCase("PostKnockout")) {
                bd = useCombatAbility(pos, bd, gi);
            }
        }
        return bd;
    }

    public BattleData useCombatAbility(int pos, BattleData bd, PokeGame gi) {
        Pokemon typeTest;

        boolean aggressor = false;
        int newPos = pos;
        boolean isActive = true;
        boolean usedAbility = false;

        if (pos < 3) {
            typeTest = bd.getAttacker().getTeam()[pos];
            aggressor = true;
            isActive = bd.getAttacker().getTeam()[pos].isActive();
            usedAbility = bd.getAttacker().getTeam()[pos].isUsedAbility();
        } else {
            typeTest = bd.getDefender().getTeam()[pos - 3];
            newPos = pos - 3;
            isActive = bd.getDefender().getTeam()[pos - 3].isActive();
            usedAbility = bd.getDefender().getTeam()[pos - 3].isUsedAbility();
        }

        if (isActive && !usedAbility) {
            switch (typeTest.getCombatType()) {
                case FIRE:
                    bd = fire(newPos, bd, gi, aggressor);
                    break;
                case ELECTRIC:
                    bd = electric(newPos, bd, gi, aggressor);
                    break;
                case ICE:
                    bd = ice(newPos, bd, gi, aggressor);
                    break;
                case POISON:
                    bd = poison(newPos, bd, gi, aggressor);
                    break;
                case DRAGON:
                    bd = dragon(newPos, bd, gi, aggressor);
                    break;
                case DARK:
                    bd = dark(newPos, bd, gi, aggressor);
                    break;
                case STEEL:
                    bd = steel(newPos, bd, gi, aggressor);
                    break;
                case ROCK:
                    bd = rock(newPos, bd, gi, aggressor);
                    break;
                case GHOST:
                    bd = ghost(newPos, bd, gi, aggressor);
                    break;
                default:
                    break;

            }
        }

        return bd;
    }

    public String targetType(Typings type) {
        Typings[] poTargetsArray = new Typings[]{Typings.BUG, Typings.NORMAL, Typings.FIGHTING, Typings.ROCK};
        ArrayList<Typings> poTargetsList = new ArrayList<Typings>(Arrays.asList(poTargetsArray));
        if (poTargetsList.contains(type)) {
            return "Pokemon";
        } else {
            return "Player";
        }
    }

    public boolean checkCondition(Pokemon mon, Pokemon[] team, Pokemon[] oppTeam) {
        boolean result = false;

        switch (mon.tAbility) {
            case ROCK:
                break;
            case STEEL:
                break;
            default:
                break;

        }
        return result;
    }

    public void activateAbility(Pokemon mon, Pokemon target) {
        switch (mon.tAbility) {
            case FIGHTING:
                fighting(mon, target);
                break;
            case BUG:
                bug(mon, target);
                break;
            case NORMAL:
                normal(mon, target);
                break;
            default:
                break;

        }
    }

    public void activateAbility(Pokemon mon, Player target) {
        switch (mon.tAbility) {
            case WATER:
                water(mon, target);
                break;
            case GROUND:
                ground(mon, target);
                break;
            case PSYCHIC:
                psychic(mon, target);
                break;
            case FAIRY:
                fairy(mon, target);
                break;
            case GRASS:
                grass(mon, target);
                break;
            case FLYING:
                flying(mon, target);
            default:
                break;

        }

    }

    public void activateAbility(Pokemon mon, Object[] targets) {
        //TODO: randomize based on power level?

        if (targets != null) {
            int i = targets.length - 1;

            if (targets[i] instanceof Pokemon) {
                for (int j = 0; j < targets.length; j++) {
                    activateAbility(mon, (Pokemon) targets[j]);
                }
            } else {
                for (int j = 0; j < targets.length; j++) {
                    activateAbility(mon, (Player) targets[j]);
                }
            }
        }
    }

    private void createAbilityInfo() {
        poAbilityInfo = new TreeMap<Typings, String[]>();
        for (Typings t : Typings.values()) {
            poAbilityInfo.put(t, new String[4]);
        }

        poAbilityInfo.get(Typings.valueOf("NORMAL"))[0] = "[b]Recover[/b] [i](Battle Passive)[/i]: Ignore the first pokemon ability in each fight";
        poAbilityInfo.get(Typings.valueOf("NORMAL"))[1] = "[b]Disable[/b] [i](Targetable)[/i]: Target pokemon is immune to all pokemon abilities during battles that night.";
        poAbilityInfo.get(Typings.valueOf("NORMAL"))[2] = "[b]Safeguard[/b] [i](Battle Passive)[/i]: All of your pokemon are protected from all pokemon abilities during battle.";

        poAbilityInfo.get(Typings.valueOf("FIRE"))[0] = "[b]Fire Punch[/b] [i](Battle Passive)[/i]: 50% chance to Burn (reduce attack effectiveness by one factor) one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("FIRE"))[1] = "[b]Flamethrower[/b] [i](Battle Passive)[/i]: Burn (reduce attack effectiveness by one factor) one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("FIRE"))[2] = "[b]Blast Burn[/b] [i](Battle Passive)[/i]: Burn (reduce attack effectiveness by one factor) two of your Opponent's pokemon (random)";

        poAbilityInfo.get(Typings.valueOf("WATER"))[0] = "[b]Bubble[/b] [i](Targetable)[/i]: Target one trainer in your area - all of their pokemon are cured of status effects.";
        poAbilityInfo.get(Typings.valueOf("WATER"))[1] = "[b]Soak[/b] [i](Targetable)[/i]: Target two trainers in your area - all of their pokemon are cured of status effects.";
        poAbilityInfo.get(Typings.valueOf("WATER"))[2] = "[b]Rain Dance[/b] [i](Targetable)[/i]: Target any number of trainers in your area - all of their pokemon are cured of all status effects. ";

        poAbilityInfo.get(Typings.valueOf("ELECTRIC"))[0] = "[b]Spark[/b] [i](Battle Passive)[/i]: 50% chance to Paralyze (won't fight every other night) one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("ELECTRIC"))[1] = "[b]Thunder[/b] [i](Battle Passive)[/i]: Paralyze (won't fight every other night) one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("ELECTRIC"))[2] = "[b]Bolt Strike[/b] [i](Battle Passive)[/i]: Paralyze (won't fight every other night) two of your Opponent's pokemon (random)";

        poAbilityInfo.get(Typings.valueOf("GRASS"))[0] = "[b]Synthesis[/b] [i](Passive)[/i]: Will find two random items each night.";
        poAbilityInfo.get(Typings.valueOf("GRASS"))[1] = "[b]Grass Pledge[/b] [i](Passive)[/i]: Will find three random items each night";
        poAbilityInfo.get(Typings.valueOf("GRASS"))[2] = "[b]Magical Leaf[/b]: Gain 8 points worth in items where point values are as follows - common(1) uncommon(2) rare(4) super rare(8)";

        poAbilityInfo.get(Typings.valueOf("ICE"))[0] = "[b]Ice Punch[/b] [i](Battle Passive)[/i]: 50%  chance to Freeze (won't fight for 3 nights) one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("ICE"))[1] = "[b]Ice Beam[/b] [i](Battle Passive)[/i]: Freeze (won't fight for 3 nights) one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("ICE"))[2] = "[b]Freeze Shock[/b] [i](Battle Passive)[/i]: Freeze (won't fight for 3 nights) two of your Opponent's pokemon (random)";

        poAbilityInfo.get(Typings.valueOf("FIGHTING"))[0] = "[b]Detect[/b] [i](Targetable)[/i]: Target one of your pokemon. Target evolves.";
        poAbilityInfo.get(Typings.valueOf("FIGHTING"))[1] = "[b]Bulk Up[/b] [i](Targetable)[/i]: Target any pokemon. Target evolves.";
        poAbilityInfo.get(Typings.valueOf("FIGHTING"))[2] = "[b]Final Gambit[/b] [i](Targetable)[/i]: Target any pokemon. Target evolves to max level";

        poAbilityInfo.get(Typings.valueOf("POISON"))[0] = "[b]Poison Sting[/b] [i](Battle Passive)[/i]: 50% chance to Poison (take 2x damage) a Opponent's pokemon (random).";
        poAbilityInfo.get(Typings.valueOf("POISON"))[1] = "[b]Acid Spray[/b] [i](Battle Passive)[/i]: Poison (take 2x damage) a Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("POISON"))[2] = "[b]Belch[/b] [i](Battle Passive)[/i]: Poison (take 2x damage) two of your Opponent's pokemon (random)";

        poAbilityInfo.get(Typings.valueOf("GROUND"))[0] = "[b]Dig[/b] [i](Targetable)[/i]: Protect target trainer from one challenge tonight";
        poAbilityInfo.get(Typings.valueOf("GROUND"))[1] = "[b]Rototiller[/b] [i](Targetable)[/i]: Protect target trainer from three challenges tonight";
        poAbilityInfo.get(Typings.valueOf("GROUND"))[2] = "[b]Sand Tomb[/b] [i](Targetable)[/i]: Protect target trainer from all challenges";

        poAbilityInfo.get(Typings.valueOf("FLYING"))[0] = "[b]Gust[/b] [i](Passive)[/i]: This pokemon's trainer can move up to two areas in a night";
        poAbilityInfo.get(Typings.valueOf("FLYING"))[1] = "[b]Tailwind[/b] [i](Passive)[/i]: This pokemon's trainer can move up to three areas in a night";
        poAbilityInfo.get(Typings.valueOf("FLYING"))[2] = "[b]Fly[/b] [i](Targetable Move)[/i]: This pokemon's trainer can move to any area and/or may move one other trainer to their location.";

        poAbilityInfo.get(Typings.valueOf("PSYCHIC"))[0] = "[b]Trick[/b] [i](Targetable Seer)[/i]: Target one trainer in your area to learn one of their normal trainer abilities and any previous aliases.";
        poAbilityInfo.get(Typings.valueOf("PSYCHIC"))[1] = "[b]Dream Eater[/b] [i](Targetable Seer)[/i]: Target one trainer in your area to learn one of their trainer abilities (50% chance to learn special abilities) and any previous aliases.";
        poAbilityInfo.get(Typings.valueOf("PSYCHIC"))[2] = "[b]Miracle Eye[/b] [i](Targetable Seer)[/i]: Target two trainers in any area and seer all of their abilities and any previous aliases.";

        poAbilityInfo.get(Typings.valueOf("BUG"))[0] = "[b]Bug Buzz[/b] [i](Targetable Self)[/i]: Target pokemon's happiness gains are doubled tonight";
        poAbilityInfo.get(Typings.valueOf("BUG"))[1] = "[b]Powder[/b] [i](Targetable Self/Other)[/i]: Target trainer gains max happiness with a chosen pokemon";
        poAbilityInfo.get(Typings.valueOf("BUG"))[2] = "[b]Quiver Dance[/b] [i](Passive / Target Required)[/i]: Always at maximum happiness. Choose a trainer: they gain max happiness with all of their pokemon.";

        poAbilityInfo.get(Typings.valueOf("ROCK"))[0] = "[b]Rock Tomb[/b] [i](Targetable)[/i]: Target one of your pokemon. If that pokemon is knocked out tonight then this pokemon is knocked out instead.";
        poAbilityInfo.get(Typings.valueOf("ROCK"))[1] = "[b]Wide Guard[/b] [i](Passive)[/i]: If any of your pokemon are knocked out tonight then this pokemon is knocked out instead.";
        poAbilityInfo.get(Typings.valueOf("ROCK"))[2] = "[b]Ancient Power[/b] [i](Passive)[/i]: Prevent your other pokemon from being knocked out.";

        poAbilityInfo.get(Typings.valueOf("GHOST"))[0] = "[b]Night Shade[/b] [i](Battle Passive)[/i]: 50% chance to Curse (If this pokemon ever knocks out another pokemon it is also knocked out) one of your Opponent's pokemon";
        poAbilityInfo.get(Typings.valueOf("GHOST"))[1] = "[b]Shadow Sneak[/b] [i](Battle Passive)[/i]: Curse (If this pokemon ever knocks out another pokemon it is also knocked out) one of your Opponent's pokemon";
        poAbilityInfo.get(Typings.valueOf("GHOST"))[2] = "[b]Trick-or-Treat[/b] [i](Battle Passive)[/i]: Curse (If this pokemon ever knocks out another pokemon it is also knocked out) two of your Opponent's pokemon";

        poAbilityInfo.get(Typings.valueOf("DRAGON"))[0] = "[b]Dragon Breath[/b] [i](Battle Passive)[/i]: 50% chance to cause a normal attack to one of your Opponent's pokemon (random)";
        poAbilityInfo.get(Typings.valueOf("DRAGON"))[1] = "[b]Dragon Pulse[/b] [i](Battle Passive)[/i]: Deal a normal attack to one of your Opponent's pokemon";
        poAbilityInfo.get(Typings.valueOf("DRAGON"))[2] = "[b]Roar of Time[/b] [i](Battle Passive)[/i]: Deal a normal attack to two of your opponent's pokemon";

        poAbilityInfo.get(Typings.valueOf("DARK"))[0] = "[b]Thief[/b] [i](Battle Passive)[/i]: 50% chance to Siphon (copy and use) one of your Opponent's pokemon's ability for that fight only";
        poAbilityInfo.get(Typings.valueOf("DARK"))[1] = "[b]Snatch[/b] [i](Battle Passive)[/i]: Siphon (copy and use) one of your Opponent's pokemon ability for that fight only";
        poAbilityInfo.get(Typings.valueOf("DARK"))[2] = "[b]Nasty Plot[/b] [i](Battle Passive)[/i]: Steal (disable and then use) one of your Opponent's pokemon ability for that fight only";

        poAbilityInfo.get(Typings.valueOf("STEEL"))[0] = "[b]Iron Defense[/b] [i](Battle Passive)[/i]: When this pokemon is knocked out - Your other pokemons' received damage is reduced by one level for that battle only";
        poAbilityInfo.get(Typings.valueOf("STEEL"))[1] = "[b]Iron Head[/b] [i](Battle Passive)[/i]: When this pokemon is knocked out - Your other pokemons' received damage is reduced to weak for that battle only";
        poAbilityInfo.get(Typings.valueOf("STEEL"))[2] = "[b]King's Shield[/b] [i](Battle Passive)[/i]: When this pokemon is knocked out - All your other pokemon are immune to damage for that battle only";

        poAbilityInfo.get(Typings.valueOf("FAIRY"))[0] = "[b]Charm[/b] [i](Targetable)[/i]: Target one trainer in your area and learn their team lineup";
        poAbilityInfo.get(Typings.valueOf("FAIRY"))[1] = "[b]Draining Kiss[/b] [i](Targetable)[/i]: Target two trainers in your area and learn their team lineup";
        poAbilityInfo.get(Typings.valueOf("FAIRY"))[2] = "[b]Baby-Doll Eyes[/b] [i](Targetable)[/i]: Learn the team lineup of all trainers in your area";

    }

    private void initializeTypes() {
        Typings[] preTypes = {Typings.DARK};
        preCombatTypes = new ArrayList<Typings>(Arrays.asList(preTypes));
        Typings[] mTypes = {Typings.FIRE, Typings.ELECTRIC, Typings.POISON};
        midCombatTypes = new ArrayList<Typings>(Arrays.asList(mTypes));
        Typings[] postTypes = {Typings.STEEL, Typings.ROCK, Typings.ICE, Typings.DRAGON};
        postCombatTypes = new ArrayList<Typings>(Arrays.asList(postTypes));
        Typings[] koTypes = {Typings.ROCK};
        postKnockoutTypes = new ArrayList<Typings>(Arrays.asList(koTypes));

        stealableTypes = new ArrayList<Typings>(Arrays.asList(mTypes));
        stealableTypes.addAll(Arrays.asList(postTypes));
        stealableTypes.addAll(Arrays.asList(koTypes));
    }

    private BattleData fire(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(2);
        switch (powerLevel) {
            case 1:
                if (r.nextInt(1) == 1) {
                    if (aggressor) {
                        bd.getDefender().getTeam()[tRoll].burn();
                    } else {
                        bd.getAttacker().getTeam()[tRoll].burn();
                    }
                }
                break;
            case 3:
                if (aggressor) {
                    bd.getDefender().getTeam()[tRoll].burn();
                } else {
                    bd.getAttacker().getTeam()[tRoll].burn();
                }
            case 2:
                if (aggressor) {
                    bd.getDefender().getTeam()[(tRoll + 1) % 3].burn();
                } else {
                    bd.getAttacker().getTeam()[(tRoll + 1) % 3].burn();
                }
            default:
                break;
        }

        return bd;
    }

    private BattleData electric(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(2);
        switch (powerLevel) {
            case 1:
                if (r.nextInt(1) == 1) {
                    if (aggressor) {
                        bd.getDefender().getTeam()[tRoll].paralyze();
                    } else {
                        bd.getAttacker().getTeam()[tRoll].paralyze();
                    }
                }
                break;
            case 3:
                if (aggressor) {
                    bd.getDefender().getTeam()[tRoll].paralyze();
                } else {
                    bd.getAttacker().getTeam()[tRoll].paralyze();
                }
            case 2:
                if (aggressor) {
                    bd.getDefender().getTeam()[(tRoll + 1) % 3].paralyze();
                } else {
                    bd.getAttacker().getTeam()[(tRoll + 1) % 3].paralyze();
                }
            default:
                break;
        }

        return bd;
    }

    private BattleData ice(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(2);
        switch (powerLevel) {
            case 1:
                if (r.nextInt(1) == 1) {
                    if (aggressor) {
                        bd.getDefender().getTeam()[tRoll].freeze(3);
                    } else {
                        bd.getAttacker().getTeam()[tRoll].freeze(3);
                    }
                }
                break;
            case 3:
                if (aggressor) {
                    bd.getDefender().getTeam()[tRoll].freeze(3);
                } else {
                    bd.getAttacker().getTeam()[tRoll].freeze(3);
                }
            case 2:
                if (aggressor) {
                    bd.getDefender().getTeam()[(tRoll + 1) % 3].freeze(3);
                } else {
                    bd.getAttacker().getTeam()[(tRoll + 1) % 3].freeze(3);
                }
            default:
                break;
        }

        return bd;
    }

    private void fighting(Pokemon mon, Pokemon target) {
        switch (mon.pdEntry.pLevel) {
            case 1:
                target.evolve(pokedex, null);
                break;
            case 2:
                target.evolve(pokedex, null);
            case 3:
                while (target.pdEntry.canEvolve) {
                    target.evolve(pokedex, null);
                }
                break;
            default:
                break;
        }
    }

    private BattleData poison(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(2);
        switch (powerLevel) {
            case 1:
                if (r.nextInt(1) == 1) {
                    if (aggressor) {
                        bd.getDefender().getTeam()[tRoll].poison();
                    } else {
                        bd.getAttacker().getTeam()[tRoll].poison();
                    }
                }
                break;
            case 3:
                if (aggressor) {
                    bd.getDefender().getTeam()[tRoll].poison();
                } else {
                    bd.getAttacker().getTeam()[tRoll].poison();
                }
            case 2:
                if (aggressor) {
                    bd.getDefender().getTeam()[(tRoll + 1) % 3].poison();
                } else {
                    bd.getAttacker().getTeam()[(tRoll + 1) % 3].poison();
                }
            default:
                break;
        }

        return bd;
    }

    private void bug(Pokemon mon, Pokemon target) {
        switch (mon.pdEntry.pLevel) {
            case 1:
                target.setHappyMod(2);
                break;
            case 2:
                target.setHappiness(5);
                break;
            case 3:
                for (Pokemon p : target.trainer.team) {
                    p.setHappiness(5);
                }
                break;
            default:
                break;
        }
    }

    private void flying(Pokemon mon, Player target) {
        switch (mon.pdEntry.pLevel) {
            case 1:
                target.setSpeed(2);
            case 2:
                target.setSpeed(3);
                break;
            case 3:
                mon.getTrainer().setSpeed(10);
                if (mon.getTrainer().getMove() != null) {
                    target.setSpeed(10);
                    target.setMove(mon.getTrainer().getMove());
                } else {
                    target.setSpeed(10);
                    target.setMove(mon.getTrainer().getLocation());
                }
                break;
            default:
                break;
        }
    }

    private BattleData ghost(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(3);
        switch (powerLevel) {
            case 3:
                if (aggressor) {
                    bd.getDefender().getTeam()[tRoll].setCursed(true);
                } else {
                    bd.getAttacker().getTeam()[tRoll].setCursed(true);
                }
            case 2:
                if (aggressor) {
                    bd.getDefender().getTeam()[(tRoll + 1) % 3].setCursed(true);
                } else {
                    bd.getAttacker().getTeam()[(tRoll + 1) % 3].setCursed(true);
                }
            case 1:
                if (aggressor) {
                    bd.getDefender().getTeam()[(tRoll + 1) % 3].setCursed(true);
                } else {
                    bd.getAttacker().getTeam()[(tRoll + 1) % 3].setCursed(true);
                }
            default:
                break;
        }

        return bd;
    }

    private BattleData rock(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;
        Pokemon poke = null;
        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
            poke = bd.getAttacker().getTeam()[pos].getPoTarget()[0];
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
            poke = bd.getDefender().getTeam()[pos].getPoTarget()[0];
        }

        int o1 = (pos + 1) % 3;
        int o2 = (o1 + 1) % 3;

        switch (powerLevel) {
            case 1:
                if (aggressor && poke != null) {
                    for (int i = 0; i < bd.getAttacker().getTeam().length; i++) {
                        if (bd.getAttacker().getTeam()[i] == poke && bd.getdKO()[i]) {
                            bd.setdKO(i, false);
                            bd.setdKO(pos, true);
                        }
                    }
                } else if (poke != null) {
                    for (int i = 0; i < bd.getDefender().getTeam().length; i++) {
                        if (bd.getDefender().getTeam()[i] == poke && bd.getaKO()[i]) {
                            bd.setaKO(i, false);
                            bd.setaKO(pos, true);
                        }
                    }
                }
                break;
            case 2:
                if (aggressor) {
                    if (bd.getdKO()[o1]) {
                        bd.setdKO(o1, false);
                        bd.setdKO(pos, true);
                    }
                    if (bd.getdKO()[o2]) {
                        bd.setdKO(o2, false);
                        bd.setdKO(pos, true);
                    }

                } else {
                    if (bd.getaKO()[o1]) {
                        bd.setaKO(o1, false);
                        bd.setaKO(pos, true);
                    }
                    if (bd.getaKO()[o2]) {
                        bd.setaKO(o2, false);
                        bd.setaKO(pos, true);
                    }
                }
                break;
            case 3:
                if (aggressor) {
                    bd.setdKO(o1, false);
                    bd.setdKO(o2, false);

                } else {
                    bd.setaKO(o1, false);
                    bd.setaKO(o2, false);
                }
                break;
            default:
                break;
        }

        return bd;
    }

    private BattleData dragon(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(2);
        switch (powerLevel) {
            case 1:
                if (r.nextInt(1) == 1) {
                    if (aggressor) {
                        bd.getaValues()[tRoll] += 1;
                    } else {
                        bd.getdValues()[tRoll] += 1;
                    }
                }
                break;
            case 3:
                if (aggressor) {
                    bd.getaValues()[tRoll] += 1;
                } else {
                    bd.getdValues()[tRoll] += 1;
                }
            case 2:
                if (aggressor) {
                    bd.getaValues()[(tRoll + 1) % 3] += 1;
                } else {
                    bd.getdValues()[(tRoll + 1) % 3] += 1;
                }
            default:
                break;
        }

        return bd;
    }

    private BattleData dark(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        boolean canUse = false;
        int powerLevel = 0;

        if (aggressor) {
            canUse = bd.getAttacker().getTeam()[pos].usedAbility;
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
        } else {
            canUse = bd.getDefender().getTeam()[pos].usedAbility;
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
        }

        Random r = new Random();
        int tRoll = r.nextInt(2);
        if (canUse) {
            switch (powerLevel) {
                case 1://50% chance to steal and use
                    if (tRoll == 1) {
                        if (aggressor) {
                            bd.getAttacker().getTeam()[pos].setTempType(getStolenType(bd.getDefender().getTeam(), false));
                        } else {
                            bd.getAttacker().getTeam()[pos].setTempType(getStolenType(bd.getDefender().getTeam(), false));
                        }
                    }
                    break;
                case 2: //Steal and use
                    if (aggressor) {
                        bd.getAttacker().getTeam()[pos].setTempType(getStolenType(bd.getDefender().getTeam(), false));
                    } else {
                        bd.getAttacker().getTeam()[pos].setTempType(getStolenType(bd.getDefender().getTeam(), false));
                    }
                    break;
                case 3://Disable and use
                    if (aggressor) {
                        bd.getAttacker().getTeam()[pos].setTempType(getStolenType(bd.getDefender().getTeam(), true));
                    } else {
                        bd.getAttacker().getTeam()[pos].setTempType(getStolenType(bd.getDefender().getTeam(), true));
                    }
                    break;
                default:
                    break;

            }
        }
        return bd;
    }

    private Typings getStolenType(Pokemon[] team, boolean disable) {

        Random r = new Random();
        int tRoll = r.nextInt(3);
        for (int i = 0; i < 3; i++) {
            if (hasStealableType(team[tRoll])) {
                if (disable) {
                    team[tRoll].setUsedAbility(true);
                }
                return team[tRoll].tAbility;
            }
            tRoll = (tRoll + 1) % 3;

        }
        return Typings.DARK;
    }

    private boolean hasStealableType(Pokemon mon) {
        if (stealableTypes.contains(mon.tAbility)) {
            return true;
        } else {
            return false;
        }
    }

    private BattleData steel(int pos, BattleData bd, PokeGame gi, boolean aggressor) {
        int powerLevel = 0;
        boolean knockedOut = false;

        if (aggressor) {
            powerLevel = bd.getAttacker().getTeam()[pos].pdEntry.pLevel;
            knockedOut = bd.getdKO()[pos];
        } else {
            powerLevel = bd.getDefender().getTeam()[pos].pdEntry.pLevel;
            knockedOut = bd.getaKO()[pos];
        }

        int o1 = (pos + 1) % 3;
        int o2 = (o1 + 1) % 3;

        if (knockedOut) {
            switch (powerLevel) {
                case 1:
                    if (aggressor) {
                        bd.getdValues()[o1] *= .5;
                        bd.getdValues()[o2] *= .5;
                    } else {
                        bd.getaValues()[o1] *= .5;
                        bd.getaValues()[o2] *= .5;
                    }
                    break;
                case 2:
                    if (aggressor) {
                        bd.getdValues()[o1] = .5;
                        bd.getdValues()[o2] = .5;
                    } else {
                        bd.getaValues()[o1] = .5;
                        bd.getaValues()[o2] = .5;
                    }
                    break;
                case 3:
                    if (aggressor) {
                        bd.getdValues()[o1] = 0;
                        bd.getdValues()[o2] = 0;
                    } else {
                        bd.getaValues()[o1] = 0;
                        bd.getaValues()[o2] = 0;
                    }
                    break;
                default:
                    break;
            }
        }

        return bd;

    }

    private void normal(Pokemon mon, Pokemon target) {
        switch (mon.pdEntry.pLevel) {
            case 1:
                mon.setGuarded(true);
                break;
            case 2:
                target.setGuarded(true);
                break;
            case 3:
                for (Pokemon p : mon.trainer.team) {
                    p.setGuarded(true);
                }
                break;
            default:
                break;
        }
    }

    private void water(Pokemon mon, Player target) {
        for (Pokemon p : target.team) {
            p.clearStatus();
        }
    }

    private void ground(Pokemon mon, Player target) {
        switch (mon.pdEntry.pLevel) {
            case 1:
                target.setUnderground(target.underground + 1);
                break;
            case 2:
                target.setUnderground(target.underground + +3);
                break;
            case 3:
                target.setUnderground(target.underground + +99);
                break;
            default:
                break;
        }
    }

    private void psychic(Pokemon mon, Player target) {
        switch (mon.pdEntry.pLevel) {
            case 1:
                mon.getTrainer().getResults().add(target.abilitySeer("rand"));
            case 2:
            case 3:
                mon.getTrainer().getResults().add(target.abilitySeer("all"));
            default:
                break;
        }

    }

    private void fairy(Pokemon mon, Player target) {
        mon.getTrainer().getResults().add(target.teamSeer());
    }

    private void grass(Pokemon mon, Player target) {
        Generator g = new Generator(pokedex);
        switch (mon.pdEntry.pLevel) {
            case 1:
                g.getItems(target, 2);
                break;
            case 2:
                g.getItems(target, 3);
                break;
            default:
                break;
        }
    }

}
