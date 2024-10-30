package Combatants.Bottle;

import Combatants.Adventurer;

public class AtkBottle extends Bottle {
    public AtkBottle(int id, String name, int capacity, int CE) {
        super(id, name, capacity, CE);
    }

    @Override
    public int getIncrease() {
        return super.isEmpty() ? 0 : super.getCapacity() / 100 + super.getCE();
    }

    @Override
    public String getClassName() {
        return "AtkBottle";
    }

    @Override
    public void use(Adventurer adv) {
        adv.addAtk(this.getIncrease());
        super.use(adv);
    }
}
