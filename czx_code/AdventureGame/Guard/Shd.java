package AdventureGame.Guard;

import Combatants.Adventurer;

public class Shd implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 1000;
    }

    @Override
    public String getType() {
        return "Shd";
    }
}
