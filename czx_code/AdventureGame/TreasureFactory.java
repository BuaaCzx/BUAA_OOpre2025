package AdventureGame;

import AdventureGame.Guard.*;
import AdventureGame.Treasure.*;
import Combatants.Adventurer;

import java.util.ArrayList;

public class TreasureFactory {
    public static Treasure createTreasure(Guard guard) {
        switch (guard.getType()) {
            case "Shd" : return new ShdTreasure();
            case "Flm" : return new FlmTreasure();
            case "Frz" : return new FrzTreasure();
            case "Stn" : return new StnTreasure();
            case "Wnd" : return new WndTreasure();
            default : throw new IllegalArgumentException("Unknown guard type");
        }
    }

    public static void adventure(Adventurer adv) {
        Guard[] guards = {new Shd(), new Flm(), new Stn(), new Wnd(), new Frz()};

        for (Guard guard : guards) {
            if (guard.fight(adv)) {
                Treasure treasure = createTreasure(guard);
                treasure.showInfo();
                treasure.useBy(adv);
            } else {
                break;
            }
        }
    }
}
