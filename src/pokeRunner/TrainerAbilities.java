package pokeRunner;

public enum TrainerAbilities {

    FIGHTER,
    EXPLORER,
    COLLECTOR;

    public String description(int i) {
        String d = "";
        switch (i) {
            case 0:
                d += "Your pokemon don't lose happiness when you lose a challenge.";
                break;
            case 1:
                d += "You can explore adjacent areas during your turn. Gain a random item while exploring.";
                break;
            case 2:
                d += "The capture action gives the choice of two pokemon and you can keep one. Captured pokemon start at Friendly Happiness. You have a chance of capturing evolved pokemon.";
                break;
            default:
                break;
        }

        return d;
    }
    
    @Override
    public String toString(){
        return name().toString().substring(0, 1).toUpperCase() + name().toString().substring(1).toLowerCase();
    }
}
