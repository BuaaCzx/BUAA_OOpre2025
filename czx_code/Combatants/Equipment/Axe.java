package Combatants.Equipment;

import Combatants.Adventurer;

public class Axe extends Equipment {

    public Axe(int id, String name, int durability, int CE) {
        super(id, name, durability, CE);
    }

    @Override
    public int attack(Adventurer attacker, Adventurer defender) {
        int oldHp = defender.getHitPoint();
        defender.setHitPoint(defender.getHitPoint() / 10);
        return oldHp - defender.getHitPoint();
    }

    @Override
    public boolean tryAttack(Adventurer attacker, Adventurer defender) {
        return defender.getHitPoint() / 10 > 0;
    }

    @Override
    public String getClassName() {
        return "Axe";
    }
}
