/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokeRunner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Steven Lowder
 */
public enum Locations {
    STEAMYPONDS ("SteamyPonds", 
    		"", 
    		new ArrayList<String>(Arrays.asList("DESTROYEDFACTORY", "BUZZINGGRASSLAND", "ABANONEDRUINS", "MISTYSWAMP"))),
    DESTROYEDFACTORY ("Destroyed Factory", 
    		"", 
    		new ArrayList<String>(Arrays.asList("STEAMYPONDS", "ABANDONEDRUINS", "BUZZINGGRASSLAND", "CRUMBLINGMOUNTAIN", "SHADOWYTOWER"))),
    SHADOWYTOWER ("Shadowy Tower", 
    		"", 
    		new ArrayList<String>(Arrays.asList("DESTROYEDFACTORY", "CRUMBLINGMOUNTAIN"))),
    CLOUDYHEIGHTS ("Cloudy Heights", 
    		"", 
    		new ArrayList<String>(Arrays.asList("FROZENDEPTHS", "MISTYSWAMP"))),
    FROZENDEPTHS ("Frozen Depths", 
    		"", 
    		new ArrayList<String>(Arrays.asList("CLOUDYHEIGHTS", "MISTYSWAMP", "BUZZINGGRASSLAND", "CRUMBLINGMOUNTAIN", "TOWEROFCHAMPIONS"))),
    BUZZINGGRASSLAND ("Buzzing Grassland", 
    		"", 
    		new ArrayList<String>(Arrays.asList("STEAMYPONDS", "DESTROYEDFACTORY", "ABANDONEDRUINS", "CRUMBLINGMOUNTAIN", "FROZENDEPTHS", "TOWEROFCHAMPIONS"))),
    CRUMBLINGMOUNTAIN ("Crumbling Mountain", 
    		"", 
    		new ArrayList<String>(Arrays.asList("DESTROYEDFACTORY", "SHADOWYTOWER", "BUZZINGGRASSLAND", "FROZENDEPTHS", "TOWEROFCHAMPIONS"))),
    ABANDONEDRUINS ("Abandoned Ruins", 
    		"", 
    		new ArrayList<String>(Arrays.asList("STEAMYPONDS", "DESTROYEDFACTORY", "BUZZINGGRASSLAND", "MISTYSWAMP"))),
    MISTYSWAMP ("Misty Swamp", 
    		"", 
    		new ArrayList<String>(Arrays.asList("STEAMYPONDS", "CLOUDYHEIGHTS", "FROZENDEPTHS", "ABANDONEDRUINS"))),
    TOWEROFCHAMPIONS ("Tower of Champions", 
    		"", 
    		new ArrayList<String>(Arrays.asList("FROZENDEPTHS", "BUZZINGGRASSLAND", "CRUMBLINGMOUNTAIN")));
    
    private final String pmName;
    private final String exploreResult;
    private final ArrayList<String> nextTo;
    
    Locations(String n, String er, ArrayList<String> nT){
        pmName = n;
        exploreResult = er;
        nextTo = nT;
    }
    
    @Override
    public String toString(){
        return pmName;
    }
    
    public String explore(){
        return exploreResult;
    }
    
    public boolean nextTo(Locations moveTo, Player p, int speed){
    	if(speed == 0)
    		return false;
    	else if(nextTo.contains(p.move.name()))
    		return true;
    	else {
    		boolean recursiveCheck = false;
    		for(String s: nextTo){
    			if(!recursiveCheck)
    				recursiveCheck = Locations.valueOf(s).nextTo(moveTo, p, speed-1);
    		}
    		return recursiveCheck;
    	}   		
    }
}
