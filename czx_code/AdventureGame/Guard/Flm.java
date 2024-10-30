package AdventureGame.Guard;

import Combatants.Adventurer;

public class Flm implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 2000;
    }

    @Override
    public String getType() {
        return "Flm";
    }
}
