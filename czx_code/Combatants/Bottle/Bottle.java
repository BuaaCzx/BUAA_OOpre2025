package Combatants.Bottle;

import Combatants.Adventurer;
import Combatants.Thing;

public class Bottle extends Thing {
    private final int capacity;
    private boolean isEmpty; // 是否为空

    public Bottle(int id, String name, int capacity, int CE) {
        super(id, name, CE);
        this.capacity = capacity;
        this.isEmpty = false;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getIncrease() {
        return isEmpty ? 0 : capacity;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void use(Adventurer adv) {
        isEmpty = true;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
