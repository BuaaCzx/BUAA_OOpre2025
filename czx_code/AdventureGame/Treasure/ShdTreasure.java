package AdventureGame.Treasure;

import Combatants.Adventurer;
import DataMaker.Printer;

public class ShdTreasure implements Treasure {

    @Override
    public void showInfo() {
        Printer.print("Cloak of Shadows");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.addDef(40);
        for (Adventurer adventurer : adv.getHiredAdvs()) {
            adventurer.addDef(40);
        }
    }
}
