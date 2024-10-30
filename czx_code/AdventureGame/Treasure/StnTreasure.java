package AdventureGame.Treasure;

import Combatants.Adventurer;
import DataMaker.Printer;

public class StnTreasure implements Treasure {

    @Override
    public void showInfo() {
        Printer.print("Stoneheart Amulet");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.addDef(40);
        for (Adventurer adventurer : adv.getHiredAdvs()) {
            adventurer.addDef(40);
        }
    }
}
