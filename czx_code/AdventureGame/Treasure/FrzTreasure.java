package AdventureGame.Treasure;

import Combatants.Adventurer;
import DataMaker.Printer;

public class FrzTreasure implements Treasure {
    @Override
    public void showInfo() {
        Printer.print("Frostbite Staff");
    }

    @Override
    public void useBy(Adventurer adv) {
        adv.addAtk(50);
        for (Adventurer adventurer : adv.getHiredAdvs()) {
            adventurer.addAtk(50);
        }
    }
}
