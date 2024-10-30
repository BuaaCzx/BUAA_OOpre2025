package Combatants.Equipment;

import Combatants.Adventurer;

public class Sword extends Equipment {

    public Sword(int id, String name, int durability, int CE) {
        super(id, name, durability, CE);
    }

    @Override
    public int attack(Adventurer attacker, Adventurer defender) {
        int oldHp = defender.getHitPoint();
        defender.setHitPoint(defender.getHitPoint() - (super.getCE() + attacker.getAtk() - defender.getDef()));
        return oldHp - defender.getHitPoint();
    }

    @Override
    public boolean tryAttack(Adventurer attacker, Adventurer defender) {
        return defender.getHitPoint() - (super.getCE() + attacker.getAtk() - defender.getDef()) > 0;
    }

    @Override
    public String getClassName() {
        return "Sword";
    }
}
