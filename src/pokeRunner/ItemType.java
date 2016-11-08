package pokeRunner;

public enum ItemType {
    REVIVE, LUCKYEGG, ESCAPEROPE, LURE, POKEBALLBELT, FULLHEAL, CANDY;

    public static String printPM(int i){
        return ItemType.values()[i].toString().substring(0, 1) + ItemType.values()[i].toString().substring(1).toLowerCase();
    }
    
    public void useItem(PokeGame gi, Player p, String target){
    	switch(this.ordinal()){
    		case 0:
    			p.getTeam()[Integer.parseInt(target)].setKnockedOut(false);
    			break;
    		case 1:
    			p.getTeam()[Integer.parseInt(target)].evolve(gi.pokedex, null);
    			break;
    		case 2:
    			p.setAvoidChallenge(p.avoidChallenge+1);
    			break;
    		case 3:
    			p.setLure(target);
    			break;
    		case 4:
    			Pokemon[] temp = new Pokemon[3];
    			for(int i = 0; i < 3; i++){
    				temp[i] = p.getTeam()[Integer.parseInt(target.charAt(i) + "")];
    			}
    			p.setTeam(temp);
    			break;
    		case 5:
    			p.getTeam()[Integer.parseInt(target)].clearStatus();
    			break;
    		case 6:
    			for(int i = 0; i < p.getTeam().length; i++)
    				if(p.getTeam()[i].happiness == 6)
    					p.getTeam()[i].setHappiness(3);
    			p.getTeam()[Integer.parseInt(target)].setHappiness(6);
    			break;
    	}
    }
}
