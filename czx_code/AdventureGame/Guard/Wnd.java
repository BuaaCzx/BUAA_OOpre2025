package AdventureGame.Guard;

import Combatants.Adventurer;

public class Wnd implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 4000;
    }

    @Override
    public String getType() {
        return "Wnd";
    }
}