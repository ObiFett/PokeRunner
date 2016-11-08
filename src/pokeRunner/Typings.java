package pokeRunner;

public enum Typings {

    NORMAL,
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY;

    public String description(int i) {
        String d = "";

        String type = Typings.values()[i].toString().substring(0, 1).toUpperCase() + Typings.values()[i].toString().substring(1).toLowerCase();
        String[] colors = {"#A8A77A", "#EE8130", "#6390F0", "#F7D02C", "#7AC74C", "#96D9D6", "#C22E28", "#A33EA1",
            "#E2BF65", "#A98FF3", "#F95587", "#A6B91A", "#B6A136", "#735797", "#6F35FC", "#705746",
            "#B7B7CE", "#D685AD"};
        d += "[color=" + colors[i] + "]" + type + "[/color]";
        return d;
    }
    
    @Override
    public String toString(){
        return name().toString().substring(0, 1).toUpperCase() + name().toString().substring(1).toLowerCase();
    }
}
