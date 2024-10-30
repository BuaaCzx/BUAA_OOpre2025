package AdventureGame.Treasure;

import Combatants.Adventurer;
import DataMaker.Printer;

public class FlmTreasure implements Treasure {

    @Override
    public void showInfo() {
        Printer.print("Flamebrand Sword");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.addAtk(40);
        for (Adventurer adventurer : adv.getHiredAdvs()) {
            adventurer.addAtk(40);
        }
    }
}
