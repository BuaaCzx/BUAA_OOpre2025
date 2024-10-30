import AdventureGame.TreasureFactory;
import Combatants.*;
import Combatants.Bottle.AtkBottle;
import Combatants.Bottle.Bottle;
import Combatants.Bottle.DefBottle;
import Combatants.Bottle.HpBottle;
import Combatants.Equipment.Axe;
import Combatants.Equipment.Blade;
import Combatants.Equipment.Equipment;
import Combatants.Equipment.Sword;
import DataMaker.ForDataMaker;
import DataMaker.Printer;

import java.util.*;

public class Solver {
    private final HashMap<Integer, SolveFunction> solvers = new HashMap<>();
    private final HashMap<Integer, Adventurer> adventurers = new HashMap<>();

    public Solver() {
        solvers.put(1, this::solve1);
        solvers.put(2, this::solve2);
        solvers.put(3, this::solve3);
        solvers.put(4, this::solve4);
        solvers.put(5, this::solve5);
        solvers.put(6, this::solve6);
        solvers.put(7, this::solve7);
        solvers.put(8, this::solve8);
        solvers.put(9, this::solve9);
        solvers.put(10, this::solve10);
        solvers.put(11, this::solve11);
        solvers.put(12, this::solve12);
    }

    public void solve(ArrayList<String> input) {
        int id = Integer.parseInt(input.get(0));
        SolveFunction func = solvers.get(id);
        if (func != null) {
            func.solve(input);
        } else {
            System.err.println("Error! No solver for " + id);
        }
    }

    private void solve1(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        String advName = input.get(2);
        Adventurer adventurer = new Adventurer(advId, advName);
        adventurers.put(advId, adventurer);
    }

    private void solve2(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int botId = Integer.parseInt(input.get(2));
        String botName = input.get(3);
        int botCapacity = Integer.parseInt(input.get(4));
        String botType = input.get(5);
        int botCE = Integer.parseInt(input.get(6));

        Adventurer adventurer = adventurers.get(advId);
        Bottle bottle = null;
        if (botType.equals("HpBottle")) {
            bottle = new HpBottle(botId, botName, botCapacity, botCE);
        } else if (botType.equals("AtkBottle")) {
            bottle = new AtkBottle(botId, botName, botCapacity, botCE);
        } else {
            bottle = new DefBottle(botId, botName, botCapacity, botCE);
        }

        adventurer.addThing(bottle);
    }

    private void solve3(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int equId = Integer.parseInt(input.get(2));
        String equName = input.get(3);
        int equDurability = Integer.parseInt(input.get(4));
        String equType = input.get(5);
        int equCE = Integer.parseInt(input.get(6));

        Adventurer adventurer = adventurers.get(advId);
        Equipment equipment = null;

        if (equType.equals("Axe")) {
            equipment = new Axe(equId, equName, equDurability, equCE);
        } else if (equType.equals("Blade")) {
            equipment = new Blade(equId, equName, equDurability, equCE);
        } else {
            equipment = new Sword(equId, equName, equDurability, equCE);
        }

        adventurer.addThing(equipment);
    }

    private void solve4(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int equId = Integer.parseInt(input.get(2));

        Adventurer adventurer = adventurers.get(advId);
        Equipment equipment = adventurer.addEquDurability(equId);

        Printer.print(equipment.getName() + " " + equipment.getDurability());
        // System.out.println(equipment.getName() + " " + equipment.getDurability());
    }

    private void solve5(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int id = Integer.parseInt(input.get(2));

        Adventurer adventurer = adventurers.get(advId);
        Thing thing = adventurer.delThing(id);
        String message = thing instanceof Bottle ?
                thing.getClassName() + " " + thing.getName() + " " + ((Bottle) thing).getCapacity() :
                thing.getClassName() + " " + thing.getName() + " " + ((Equipment) thing).getDurability();

        Printer.print(message);
    }

    private void solve6(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int id = Integer.parseInt(input.get(2));

        Adventurer adventurer = adventurers.get(advId);
        Thing thing = adventurer.getThing(id);

        adventurer.bringThing(thing);
    }

    private void solve7(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int botId = Integer.parseInt(input.get(2));

        Adventurer adventurer = adventurers.get(advId);
        Bottle bottle = (Bottle) adventurer.getThing(botId);

        if (adventurer.useBottle(bottle)) {
            Printer.print(adventurer.getName() + " " + adventurer.getHitPoint() + " " + adventurer.getAtk() + " " + adventurer.getDef());
        } else {
            Printer.print(adventurer.getName() + " fail to use " + bottle.getName());
        }
    }

    private void solve8(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int fragId = Integer.parseInt(input.get(2));
        String fragName = input.get(3);

        Adventurer adventurer = adventurers.get(advId);
        adventurer.addFragment(fragName);
    }

    private void solve9(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        String fragName = input.get(2);
        int welfareId = Integer.parseInt(input.get(3));

        Adventurer adventurer = adventurers.get(advId);

        Map.Entry<Thing, String> entry = adventurer.useFragment(fragName, welfareId);

        if (entry.getKey() != null) {
            switch (entry.getValue()) {
                case "a" : {
                    Bottle bottle = (Bottle) entry.getKey();
                    Printer.print(bottle.getName() + " " + bottle.getCapacity());
                    break;
                }
                case "b" : {
                    Equipment equipment = (Equipment) entry.getKey();
                    Printer.print(equipment.getName() + " " + equipment.getDurability());
                    break;
                }
                case "c" : {
                    Bottle bottle = (Bottle) entry.getKey();
                    Printer.print("Congratulations! HpBottle " + bottle.getName() + " acquired");
                    break;
                }
            }
        } else {
            Printer.print(entry.getValue() + ": Not enough fragments collected yet");
        }
    }

    private void solve10(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        String equName = input.get(2);
        String attackType = input.get(3);
        int num = Integer.parseInt(input.get(4));
        Collection<Adventurer> underAttackAdvs = attackType.equals("normal") ? new ArrayList<>() : new HashSet<>();

        for (int i = 5; i < 5 + num; i++) {
            int advId2 = Integer.parseInt(input.get(i));
            underAttackAdvs.add(adventurers.get(advId2));
            if (attackType.equals("chain")) {
                underAttackAdvs.addAll(adventurers.get(advId2).getAllHiredAdvs(1));
            }
        }
        Adventurer adventurer = adventurers.get(advId);

        Map.Entry<Boolean, Integer> attackResult = adventurer.attack(equName, underAttackAdvs, attackType);

        if (attackResult.getKey()) {
            if (attackType.equals("normal")) {
                for (Adventurer adv : underAttackAdvs) {
                    Printer.print(adv.getName() + " " + adv.getHitPoint());
                }
            } else {
                 Printer.print(String.valueOf(attackResult.getValue()));
//                Printer.print("attack " + underAttackAdvs.size() + "people, make damage: " + String.valueOf(attackResult.getValue()));
            }
        } else {
            Printer.print("Adventurer " + advId + " defeated");
        }
    }

    private void solve11(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        int hiredId = Integer.parseInt(input.get(2));

        Adventurer adventurer = adventurers.get(advId);
        Adventurer hiredAdv = adventurers.get(hiredId);

        adventurer.hireAdv(hiredAdv);
    }

    private void solve12(ArrayList<String> input) {
        int advId = Integer.parseInt(input.get(1));
        Adventurer adventurer = adventurers.get(advId);
        TreasureFactory.adventure(adventurer);
    }

    @ForDataMaker
    public HashMap<Integer, Adventurer> getAdventurers() {
        return adventurers;
    }
}
