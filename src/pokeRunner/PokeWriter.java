package pokeRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PokeWriter {

    FileWriter writer;

    public PokeWriter() {

    }

    public void writePlayers(PokeGame gameInfo) {
        try {
            PrintWriter writer = new PrintWriter("Day" + gameInfo.day + "\\PLAYERS.csv", "UTF-8");
            String header = "";
            for (Player.RH rh : Player.RH.values()) {
                header += rh.toString() + ", ";
            }
            writer.println(header.substring(0, header.length() - 2)); //Header
            for (Player p : gameInfo.getPlayers()) {
                writer.println(p.dataDump());
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePMs(PokeGame gameInfo) {
        // TODO Auto-generated method stub
        try {
            PrintWriter writer = new PrintWriter("Day" + gameInfo.day + "\\STATUSPM.csv", "UTF-8");
            for (Player p : gameInfo.getPlayers()) {
                String newline = "[br]";
                String statusPM = "";
                statusPM += p.paName + ",";
                statusPM += "Day " + (gameInfo.day + 1) + " Status PM for [b]" + p.paName + "[/b]" + newline;
                statusPM += "[size=4][b][u]NIGHT " + gameInfo.day + " RESULTS[/b][/u][/size][indent]" + newline;
                for (String s : p.getResults()) {
                    statusPM += s + newline;
                }
                if (!p.captured.isEmpty()) {
                    statusPM += "DECIDE WHAT TO DO WITH THE FOLLOWING POKEMON:" + newline;
                    for (int i = 0; i < p.captured.size(); i++) {
                        statusPM += p.captured.get(i).printCapPM(gameInfo);
                    }
                }
                statusPM += "[/indent]" + newline;
                statusPM += "[size=4][b][u]CURRENT STATUS[/b][/u][/size]" + newline;
                statusPM += "[b]Trainer Name[/b] - " + p.alias2;
                if (!p.alias2.equalsIgnoreCase(p.alias1)) {
                    statusPM += " (AKA: " + p.alias1 + ")";
                }
                statusPM += "[br]";
                if(p.rival.equals(p))
                    statusPM += "[indent][i]Rival[/i] - You are above having a rival. But be aware that most trainers do have a rival and in this place it would normally be a trainer name." + "[br]";
                else
                    statusPM += "[indent][i]Rival[/i] - " + p.rival.alias2 + "[br]";
                statusPM += "[i]Location[/i] - " + p.location + "[br]";
                statusPM += "[i]Items[/i] - " + p.printItemsPM() + "[/indent][br][br]";
                statusPM += "[b]Trainer Team[/b] (Faction) - " + p.getFaction() + "[br]";
                if(p.sAbility != null)
                    statusPM += "[b]Role[/b] - " + p.sAbility.pmName(p.getFaction(), p) +"[br]";
                if(!p.cover.isEmpty())
                    statusPM += "[b]Cover Faction[/b] - " + p.cover + "[br]";
                statusPM += "[b]Wincon[/b] - " + p.getWinconPM() + "[br]";
                statusPM += "[b]Trainer Abilities[/b][indent]";
                statusPM += "[b]" + p.ability + "[/b] - " + p.ability.description(p.ability.ordinal()) + "[br]";
                if (p.sAbility != null) {
                    statusPM += p.sAbility.getPowerDescription();
                }
                statusPM += "[/indent][br]";
                statusPM += "[b]CURRENT TEAM[/b][br]----------------------------- -----------------------------[br]";
                statusPM += "*First Position*[br]";
                statusPM += p.team[0].printPM(gameInfo);
                statusPM += "-----------------------------[br]";
                statusPM += "*Second Position*[br]";
                statusPM += p.team[1].printPM(gameInfo);
                statusPM += "-----------------------------[br]";
                statusPM += "*Third Position*[br]";
                statusPM += p.team[2].printPM(gameInfo);
                statusPM += "----------------------------- ";
                statusPM += "-----------------------------[br]";
                statusPM += "[b]POKEBOX[/b][br]-----------------------------[br]";
                statusPM += p.printPokeBox(gameInfo);
                statusPM += "----------------------------- ";
                statusPM += "-----------------------------[br]";
                writer.println(statusPM);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeResults(PokeGame gameInfo) {
        // TODO Auto-generated method stub
        try {
            PrintWriter writer = new PrintWriter("Day" + gameInfo.day + "\\RESULTS" + gameInfo.day + ".txt", "UTF-8");
            writer.println("Results" + '\n');
            for (Player p : gameInfo.getPlayers()) {
                writer.println(p.paName + "Results:");
                for (String s : p.getResults()) {
                    writer.println(s);
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeFactionData(PokeGame gameInfo) {
    	try {
            PrintWriter writer = new PrintWriter("Day" + (gameInfo.day+1) + "\\FactionData.csv", "UTF-8");
            Iterator<Entry<String, ArrayList<Player>>> iter = gameInfo.factionBadges.entrySet().iterator();
            while (iter.hasNext()){
            	Map.Entry<String, ArrayList<Player>> pair = (Map.Entry<String, ArrayList<Player>>)iter.next();
            	writer.print(pair.getKey() + ",");
            	for(Player p : pair.getValue())
            		writer.print(p.paName + "|");
            	writer.print(",");
            	for(Player p : gameInfo.factionE4Wins.get(pair.getKey()))
            		writer.print(p.paName + "|");
            	writer.print('\n');
            }
            	
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
