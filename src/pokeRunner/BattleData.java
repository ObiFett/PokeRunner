package pokeRunner;

public class BattleData {

	private Player attacker;
	private Player defender;
	private double[] aValues;
	private double[] dValues;
	
	private boolean[] aKO;
	private boolean[] dKO;
	
	public BattleData(Player a, Player d){
		attacker = a;
		defender = d;
		aValues = new double[]{1,1,1};
		dValues = new double[]{1,1,1};
		aKO = new boolean[]{false, false, false};
		dKO = new boolean[]{false, false, false};
	}
	
	public void assignKnockouts(){
		for(int i = 0; i < 3; i++) {
	    	if(this.getaValues()[i] > 1.99)
	    		this.getaKO()[i] = true;
	    	if(this.getdValues()[i] > 1.99)
	    		this.getdKO()[i] = true;
	    }
	}
	
	public void applyKnockouts(){
		for(int i = 0; i < 3; i++) {
	    	if(this.getdKO()[i]) {
	    		attacker.getTeam()[i].knockOut();
	    		if(defender.getTeam()[i].isCursed())
	    			defender.getTeam()[i].knockOut();
	    	}
	    	if(this.getaKO()[i]) {
	    		defender.getTeam()[i].knockOut();
	    		if(attacker.getTeam()[i].isCursed())
	    			attacker.getTeam()[i].knockOut();
	    	}
	    }
	}

	public Player getAttacker() {
		return attacker;
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}

	public Player getDefender() {
		return defender;
	}

	public void setDefender(Player defender) {
		this.defender = defender;
	}

	public double[] getaValues() {
		return aValues;
	}

	public void setaValues(double[] aValues) {
		this.aValues = aValues;
	}

	public double[] getdValues() {
		return dValues;
	}

	public void setdValues(double[] dValues) {
		this.dValues = dValues;
	}

	public boolean[] getaKO() {
		return aKO;
	}

	public void setaKO(boolean[] aKO) {
		this.aKO = aKO;
	}
	
	public void setaKO(int pos, boolean aKO) {
		this.aKO[pos] = aKO;
	}

	public boolean[] getdKO() {
		return dKO;
	}

	public void setdKO(boolean[] dKO) {
		this.dKO = dKO;
	}
	
	public void setdKO(int pos, boolean dKO) {
		this.dKO[pos] = dKO;
	}
	
}
