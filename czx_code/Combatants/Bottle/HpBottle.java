package Combatants.Bottle;

import Combatants.Adventurer;

public class HpBottle extends Bottle {
    public HpBottle(int id, String name, int capacity, int CE) {
        super(id, name, capacity, CE);
    }

    @Override
    public String getClassName() {
        return "HpBottle";
    }

    @Override
    public void use(Adventurer adv) {
        adv.addHp(this.getIncrease());
        super.use(adv);
    }
}
