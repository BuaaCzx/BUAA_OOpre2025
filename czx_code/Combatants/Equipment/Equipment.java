package Combatants.Equipment;

import Combatants.Adventurer;
import Combatants.Thing;

public class Equipment extends Thing {
    private int durability;

    public Equipment(int id, String name, int durability, int CE) {
        super(id, name, CE);
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public String getClassName() {
        return "Equipment";
    }

    public int attack(Adventurer attacker, Adventurer defender) {
        return 0;
    }

    public boolean tryAttack(Adventurer attacker, Adventurer defender) {
        return true;
    }
}
