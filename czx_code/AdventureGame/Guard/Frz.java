package AdventureGame.Guard;

import Combatants.Adventurer;

public class Frz implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 5000;
    }

    @Override
    public String getType() {
        return "Frz";
    }
}
