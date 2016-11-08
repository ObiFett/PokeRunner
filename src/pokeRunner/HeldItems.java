package pokeRunner;

public enum HeldItems {
	BLACKBELT ("Black Belt", Typings.FIGHTING),
	BLACKGLASSES ("Black Glasses", Typings.DARK),
	CHARCOAL ("Charcoal", Typings.FIRE),
	DRAGONFANG ("Dragon Fang", Typings.DRAGON),
	HARDSTONE ("Hard Stone", Typings.ROCK),
	MAGNET ("Magnet", Typings.ELECTRIC),
	METALCOAT ("Metal Coat", Typings.STEEL),
	MIRACLESEED ("Miracle Seed", Typings.GRASS),
	MYSTICWATER ("Mystic Water", Typings.WATER),
	NEVERMELTICE ("Nevermelt Ice", Typings.ICE),
	SILKSCARF ("Silk Scarf", Typings.NORMAL),
	PINKBOW ("Pink Bow", Typings.FAIRY),
	SHARPBEAK ("Sharp Beak", Typings.FLYING),
	SILVERPOWDER ("Silver Powder", Typings.BUG),
	SOFTSAND ("Soft Sand", Typings.GROUND),
	SPELLTAG ("Spell Tag", Typings.GHOST),
	TWISTEDSPOON ("Twisted Spoon", Typings.PSYCHIC),
	POISONBARB ("Poison Barb", Typings.POISON);

	private final String pmName;
	private final Typings type;
	
	HeldItems(String pn, Typings t){
		pmName = pn;
		type = t;
	}
	
	public String getPmName() {
		return pmName;
	}

	public Typings getType() {
		return type;
	}
	
	public String pmPrint(){
		return "[b]" + pmName + "[/b]: Can be held by a Pokemon. If held by a " + type + " Pokemon then their attack strength will be doubled in challenges";
	}
}
