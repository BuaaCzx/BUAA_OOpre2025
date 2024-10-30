package AdventureGame.Treasure;

import Combatants.Adventurer;
import DataMaker.Printer;

public class WndTreasure implements Treasure {
    @Override
    public void showInfo() {
        Printer.print("Windrunner Boots");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.addDef(30);
        for (Adventurer adventurer : adv.getHiredAdvs()) {
            adventurer.addDef(30);
        }
    }
}
