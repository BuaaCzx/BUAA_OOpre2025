package Combatants.Bottle;

import Combatants.Adventurer;

public class DefBottle extends Bottle {

    public DefBottle(int id, String name, int capacity, int CE) {
        super(id, name, capacity, CE);
    }

    @Override
    public int getIncrease() {
        return super.isEmpty() ? 0 : super.getCapacity() / 100 + getCE();
    }

    @Override
    public String getClassName() {
        return "DefBottle";
    }

    @Override
    public void use(Adventurer adv) {
        adv.addDef(getIncrease());
        super.use(adv);
    }
}
