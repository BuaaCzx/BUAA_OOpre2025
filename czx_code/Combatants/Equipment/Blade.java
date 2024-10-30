package Combatants.Equipment;

import Combatants.Adventurer;

public class Blade extends Equipment {

    public Blade(int id, String name, int durability, int CE) {
        super(id, name, durability, CE);
    }

    @Override
    public int attack(Adventurer attacker, Adventurer defender) {
        int oldHp = defender.getHitPoint();
        defender.setHitPoint(defender.getHitPoint() - (attacker.getAtk() + super.getCE()));
        return oldHp - defender.getHitPoint();
    }

    @Override
    public boolean tryAttack(Adventurer attacker, Adventurer defender) {
        return defender.getHitPoint() - (attacker.getAtk() + super.getCE()) > 0;
    }

    @Override
    public String getClassName() {
        return "Blade";
    }
}
