package pokeRunner;

import java.util.ArrayList;
import java.util.Arrays;

public class Orders {

    public class Order {

        public String subject;
        public String action;
        public String[] predicate;

        public Order(String s, String a, String[] p) {
            subject = s;
            action = a;
            predicate = p;
        }
    }

    public String[] orderOfOperations;

    public ArrayList<Order> orders;

    public Orders() {
        orders = new ArrayList<Order>();
        orderOfOperations = new String[]{"Rest", "Challenge", "Explore", "Capture"};
    }

    public void processMiscellaneous(PokeGame gameInfo) {
        for (Order o : orders) {
            switch (o.action) {//subject, action, predicate0, predicate1, ..., predicatex
                case "GiftItem"://player, GiftItem, item, player, num
                    if (o.predicate[0].substring(0, 2) == "TM") {
                        int index = gameInfo.getPlayer(o.subject).getTms().indexOf(o.predicate[0]);
                        gameInfo.getPlayer(o.predicate[1]).getTms().add(gameInfo.getPlayer(o.subject).getTms().remove(index));
                    } else {
                        gameInfo.getPlayer(o.subject).getItems()[ItemType.valueOf(o.predicate[0]).ordinal()] -= Integer.parseInt(o.predicate[2]);
                        gameInfo.getPlayer(o.predicate[1]).getItems()[ItemType.valueOf(o.predicate[0]).ordinal()] -= Integer.parseInt(o.predicate[2]);
                    }
                    break;
                case "GiftPokemon"://player, GiftPokemon, box||team||captured, num, player
                    if (o.predicate[0] == "box") {
                        gameInfo.getPlayer(o.predicate[2]).getCaptured().add(
                            gameInfo.getPlayer(o.subject).getBox().remove(Integer.parseInt(o.predicate[1])));
                    } else if (o.predicate[0] == "captured") {
                        gameInfo.getPlayer(o.predicate[2]).getCaptured().add(
                            gameInfo.getPlayer(o.subject).getCaptured().remove(Integer.parseInt(o.predicate[1])));
                    } else {
                        gameInfo.getPlayer(o.predicate[2]).getCaptured().add(
                            gameInfo.getPlayer(o.subject).getTeam()[Integer.parseInt(o.predicate[1])]);
                        gameInfo.getPlayer(o.subject).setTeam(Integer.parseInt(o.predicate[1]), null);
                    }
                    gameInfo.getPlayer(o.predicate[2]).getResults().add("Someone gifted you a pokemon!");
                    break;
                case "PokemonSwap": //player, PokemonSwap, team||box, num, player, team||box, num
                    if (o.predicate[0] == "team") {
                        Pokemon m1 = gameInfo.getPlayer(o.subject).getTeam()[Integer.parseInt(o.predicate[1])];
                        if (o.predicate[3] == "team") {
                            gameInfo.getPlayer(o.subject).setTeam(
                                Integer.parseInt(o.predicate[1]),
                                gameInfo.getPlayer(o.predicate[2]).getTeam()[Integer.parseInt(o.predicate[4])]);
                            gameInfo.getPlayer(o.predicate[2]).setTeam(Integer.parseInt(o.predicate[4]), m1);
                        } else {
                            gameInfo.getPlayer(o.subject).setTeam(
                                Integer.parseInt(o.predicate[1]),
                                gameInfo.getPlayer(o.predicate[2]).getBox().remove(Integer.parseInt(o.predicate[4])));
                            gameInfo.getPlayer(o.predicate[2]).getBox().add(m1);
                        }
                    } else {
                        Pokemon m1 = gameInfo.getPlayer(o.subject).getBox().remove(Integer.parseInt(o.predicate[1]));
                        if (o.predicate[3] == "team") {
                            gameInfo.getPlayer(o.subject).getBox().add(
                                gameInfo.getPlayer(o.predicate[2]).getTeam()[Integer.parseInt(o.predicate[4])]);
                            gameInfo.getPlayer(o.predicate[2]).setTeam(Integer.parseInt(o.predicate[4]), m1);
                        } else {
                            gameInfo.getPlayer(o.subject).getBox().add(gameInfo.getPlayer(o.predicate[2]).getBox().remove(Integer.parseInt(o.predicate[4])));
                            gameInfo.getPlayer(o.predicate[2]).getBox().add(m1);
                        }
                    }
                    break;
                case "BoxSwap"://player, UseBox, teamNum, boxpokemonNum
                    if (gameInfo.getPlayer(o.subject).getTeam()[Integer.parseInt(o.predicate[0])] != null) {
                        gameInfo.getPlayer(o.subject).getBox().add(Integer.parseInt(o.predicate[1])+1, gameInfo.getPlayer(o.subject).getTeam()[Integer.parseInt(o.predicate[0])]);
                    }

                    gameInfo.getPlayer(o.subject).setTeam(
                        Integer.parseInt(o.predicate[0]),
                        gameInfo.getPlayer(o.subject).getBox().remove(Integer.parseInt(o.predicate[1])));
                    break;
                case "BoxStore"://player, UseBox, captureNum
                    gameInfo.getPlayer(o.subject).getBox().add(gameInfo.getPlayer(o.subject).getCaptured().remove(Integer.parseInt(o.predicate[0])));
                    break;
                case "CaptureChoice"://player, CaptureChoice, captureNum, name, box||team, teamNum
                    if (gameInfo.getPlayer(o.subject).ability == TrainerAbilities.COLLECTOR) {
                        gameInfo.getPlayer(o.subject).getCaptured().get(Integer.parseInt(o.predicate[0])).setHappiness(4);
                    }
                    gameInfo.getPlayer(o.subject).getCaptured().get(Integer.parseInt(o.predicate[0])).setcName(o.predicate[1]);
                    if (o.predicate[2] == "box") {
                        gameInfo.getPlayer(o.subject).getBox().add(gameInfo.getPlayer(o.subject).getCaptured().remove(Integer.parseInt(o.predicate[0])));
                    } else {
                        gameInfo.getPlayer(o.subject).getBox().add(gameInfo.getPlayer(o.subject).getTeam()[Integer.parseInt(o.predicate[3])]);
                        gameInfo.getPlayer(o.subject).setTeam(
                            Integer.parseInt(o.predicate[3]),
                            gameInfo.getPlayer(o.subject).getCaptured().remove(Integer.parseInt(o.predicate[0])));
                    }
                    break;
                case "UseItem"://player, UseItem, item, target<int>, parameter(s)
                    if (gameInfo.getPlayer(o.subject).getItems()[ItemType.valueOf(o.predicate[0]).ordinal()] > 0) {
                        String target;
                        if (o.predicate.length < 2) {
                            target = "";
                        } else {
                            target = o.predicate[1];
                        }
                        ItemType.valueOf(o.predicate[0]).useItem(gameInfo, gameInfo.getPlayer(o.subject), target);
                        gameInfo.getPlayer(o.subject).getResults().add("Used " + ItemType.valueOf(o.predicate[0]).toString());
                        gameInfo.getPlayer(o.subject).getItems()[ItemType.valueOf(o.predicate[0]).ordinal()] -= 1;
                    } else {
                        gameInfo.getPlayer(o.subject).getResults().add("Unable to use " + ItemType.valueOf(o.predicate[0]).toString());
                    }
                    break;
                case "FreeItem":
                    String target;
                    if (o.predicate.length < 2) {
                        target = "";
                    } else {
                        target = o.predicate[1];
                    }
                    ItemType.valueOf(o.predicate[0]).useItem(gameInfo, gameInfo.getPlayer(o.subject), target);
                    break;
                case "UseTM"://player, UseTM, num, target<int>
                    if (gameInfo.getPlayer(o.subject).getTms().contains(o.predicate[0])) {
                        int tm = Integer.parseInt(gameInfo.getPlayer(o.subject).getTms()
                            .remove(gameInfo.getPlayer(o.subject).getTms().indexOf(o.predicate[0])));
                        int pos = Integer.getInteger(o.predicate[1]);
                        switch (tm) {
                            case 1:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.DARK);
                                break;
                            case 2:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.FIRE);
                                break;
                            case 3:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.BUG);
                                break;
                            case 4:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.FIGHTING);
                                break;
                            case 5:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.PSYCHIC);
                                break;
                            case 6:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.ROCK);
                                break;
                            case 7:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.WATER);
                                break;
                            case 8:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.GHOST);
                                break;
                            case 9:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.ELECTRIC);
                                break;
                            case 10:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.STEEL);
                                break;
                            case 11:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.ICE);
                                break;
                            case 12:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.GRASS);
                                break;
                            case 13:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.FAIRY);
                                break;
                            case 14:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.NORMAL);
                                break;
                            case 15:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.GROUND);
                                break;
                            case 16:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.POISON);
                                break;
                            case 17:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.DRAGON);
                                break;
                            case 18:
                                gameInfo.getPlayer(o.subject).getTeam()[pos].settAbility(Typings.FLYING);
                                break;

                            default:
                                break;
                        }
                    }
                    break;
                case "Eevee": //player,Eevee,evolution
                    for (Pokemon p : gameInfo.getPlayer(o.subject).getTeam()) {
                        if (gameInfo.pokedex.isEevee(p.pdEntry)) {
                            p.setPdEntry(gameInfo.pokedex.getEntry(o.predicate[0]));
                            p.setAbilityType(gameInfo.pokedex);
                        }
                    }
                    break;
                case "Ditto": //player,Ditto,evolution
                    for (Pokemon p : gameInfo.getPlayer(o.subject).getTeam()) {
                        if (p.cName.equalsIgnoreCase("Ditto")) {
                            p.setPdEntry(gameInfo.pokedex.getEntry(o.predicate[0]));
                            p.setAbilityType(gameInfo.pokedex);
                        }
                    }
                    break;
                    
                case "Reorder": //player,Reorder, numnumnum
                    Pokemon[] temp = new Pokemon[3];
                    for(int i = 0; i < 3; i++){
                            temp[i] = gameInfo.getPlayer(o.subject).getTeam()[Integer.parseInt(o.predicate[0].charAt(i) + "")];
                    }
                    gameInfo.getPlayer(o.subject).setTeam(temp);
                    break;

                default:
                    break;
            }
        }
    }

    public void processBasicPlayer(PokeGame gameInfo) {
        Generator g = new Generator(gameInfo.pokedex);
        for (int i = 0; i < this.orderOfOperations.length; i++) {
            for (Player p : gameInfo.getPlayers()) {
                if (p.basicAction.equalsIgnoreCase(this.orderOfOperations[i])) {
                    Order o = new Order(p.paName, p.basicAction, p.baTarget.split("\\|"));
                    switch (o.action) {
                        case "Challenge": //attacker,Challenge,defender,evolvenum,type
                            Battle b = new Battle();
                            b.challenge(gameInfo.getPlayer(o.subject), gameInfo.getPlayer(o.predicate[0]), gameInfo, false);
                            Typings choice = null;
                            if (o.predicate.length > 2) {
                                choice = Typings.valueOf(o.predicate[2]);
                            }
                            gameInfo.getPlayer(o.subject)
                                .getTeam()[Integer.parseInt(o.predicate[1])]
                                .evolve(gameInfo.pokedex, choice);
                            break;
                        case "Explore": //explorer, Explore, current||location
                            if (o.predicate[0].equalsIgnoreCase("current")) {
                                gameInfo.getPlayer(o.subject).getResults().add(gameInfo.getPlayer(o.subject).location.explore());
                            } else {
                                gameInfo.getPlayer(o.subject).getResults().add(Locations.valueOf(o.predicate[0]).explore());
                            }
                            if (gameInfo.getPlayer(o.subject).ability.equals(TrainerAbilities.EXPLORER)) {
                                g.getItems(gameInfo.getPlayer(o.subject), 1);
                            }
                            for (int j = 0; j < gameInfo.getPlayer(o.subject).team.length; j++) {
                                gameInfo.getPlayer(o.subject).getTeam()[j].updateHappiness(1);
                            }
                            break;
                        case "Capture": //player, Capture
                            g.getPokemon(gameInfo.getPlayer(o.subject));
                            break;
                        case "Rest": //player, Rest
                            for (int j = 0; j < gameInfo.getPlayer(o.subject).team.length; j++) {
                                gameInfo.getPlayer(o.subject).getTeam()[j].revive();
                                g.getItems(gameInfo.getPlayer(o.subject), 1);
                            }
                            break;
                    }
                }
            }
        }
    }

    public void processMoves(PokeGame gameInfo) {
        for (Player p : gameInfo.getPlayers()) {
            if (p.move != null) {
                if (p.location.nextTo(p.move, p, p.speed)) {
                    p.setLocation(p.move);
                } else {
                    p.getResults().add("Invalid Move Target");
                }
            }
        }
    }

    public void processSpecialPlayer(PokeGame gameInfo) {
        for (Player p : gameInfo.getPlayers()) {
            if (p.sAbility != null && !p.saTarget[0].isEmpty()) {
                switch (p.sAbility) {
                    case LEAGUECHAMPION:
                        break;
                    case FACTIONLEADER:
                        break;
                    case FACTIONGUARD:
                        break;
                    case FACTIONSEER:
                        break;
                    case FACTIONVIG:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void addOrder(String[] order) {
        String[] p = Arrays.copyOfRange(order, 2, order.length);
        orders.add(new Order(order[0], order[1], p));
    }

}
