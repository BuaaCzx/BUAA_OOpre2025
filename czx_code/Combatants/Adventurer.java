package Combatants;

import Combatants.Bottle.Bottle;
import Combatants.Bottle.HpBottle;
import Combatants.Equipment.Equipment;
import DataMaker.ForDataMaker;
import DataMaker.GlobalData;

import java.util.*;

public class Adventurer implements Combatant {
    private final int id;
    private final String name;
    private final HashMap<Integer, Thing> things = new HashMap<>();
    private final HashMap<String, Integer> fragments = new HashMap<>();
    private final HashMap<Integer, Adventurer> hiredAdvs = new HashMap<>();
    private final HashMap<Integer, Integer> helpCnt = new HashMap<>();
    private final Bag bag = new Bag(this);
    private int hitPoint;
    private int atk;
    private int def;
    private Adventurer watchingPerson;
    private int watchingPersonOldHp;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.hitPoint = 500;
        this.atk = 1;
        this.def = 0;
    }

    public Bag getBag() {
        return bag;
    }

    public void addHp(int num) {
        this.hitPoint += num;
    }

    public void addAtk(int num) {
        this.atk += num;
    }

    public void addDef(int num) {
        this.def += num;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    public Equipment addEquDurability(int equId) {
        Equipment equipment = (Equipment) things.get(equId);
        equipment.setDurability(equipment.getDurability() + 1);
        return equipment;
    }

    @Override
    public int getCE() {
        return atk + def;
    }

    public void addThing(Thing thing) {
        things.put(thing.getId(), thing);
    }

    public Thing delThing(int id) {
        Thing thing = things.get(id);
        bag.remove(thing);
        return things.remove(id);
    }

    public Thing getThing(int id) {
        return things.get(id);
    }

    public void bringThing(Thing thing) {
        bag.put(thing.getId(), thing);
        assert bag.containsValue(thing);
    }

    public boolean useBottle(Bottle bottle) {
        if (bag.containsValue(bottle)) {
            if (bottle.isEmpty()) {
                delThing(bottle.getId());
            } else {
                bottle.use(this);
            }
            return true;
        } else {
            return false;
        }
    }

    public int getMaxBottleNum() {
        return getCE() / 5 + 1;
    }

    public void addFragment(String fragName) {
        fragments.put(fragName, fragments.getOrDefault(fragName, 0) + 1);
    }

    public Map.Entry<Thing, String> useFragment(String fragName, int welfareId) {
        if (fragments.get(fragName) >= 5) {
            fragments.put(fragName, fragments.get(fragName) - 5); // 这里应该有减法操作，
            Thing thing = things.getOrDefault(welfareId, null);
            if (thing instanceof Bottle) {
                ((Bottle) thing).setEmpty(false);
                return new AbstractMap.SimpleEntry<>(thing, "a");
            } else if (thing instanceof Equipment) {
                ((Equipment) thing).setDurability(((Equipment) thing).getDurability() + 1);
                return new AbstractMap.SimpleEntry<>(thing, "b");
            } else {
                Bottle bottle = new HpBottle(welfareId, fragName, 100, 0);
                addThing(bottle);
                return new AbstractMap.SimpleEntry<>(bottle, "c");
            }
        } else {
            return new AbstractMap.SimpleEntry<>(null, String.valueOf(fragments.get(fragName)));
        }
    }

    @ForDataMaker
    public Bottle getRandBottle() {
        ArrayList<Bottle> bottleList = new ArrayList<>();
        for (Thing thing : things.values()) {
            if (thing instanceof Bottle) {
                bottleList.add((Bottle) thing);
            }
        }
        if (bottleList.isEmpty()) {
            return null;
        }
        int randIndex = GlobalData.rand.nextInt(bottleList.size());
        return bottleList.get(randIndex);
    }

    // 从拥有的物品中随机选择一个装备，50%从背包里选
    @ForDataMaker
    public Equipment getRandEqu() {
        ArrayList<Equipment> equList = new ArrayList<>();

        if (GlobalData.rand.nextInt(2) == 0) {
            for (Thing thing : things.values()) {
                if (thing instanceof Equipment) {
                    equList.add((Equipment) thing);
                }
            }
        } else {
            equList = bag.getEquipments();
        }

        if (equList.isEmpty()) {
            return null;
        }
        int randIndex = GlobalData.rand.nextInt(equList.size());
        return equList.get(randIndex);
    }

    @ForDataMaker
    public Thing getRandThing() {
        ArrayList<Thing> thingList = new ArrayList<>(things.values());
        if (thingList.isEmpty()) {
            return null;
        }
        int randIndex = GlobalData.rand.nextInt(thingList.size());
        return thingList.get(randIndex);
    }

    @ForDataMaker
    public String getRandFrag() {
        ArrayList<String> fragList = new ArrayList<>();
        for (String frag : fragments.keySet()) {
            if (fragments.get(frag) > 0) {
                fragList.add(frag);
            }
        }
        if (fragList.isEmpty()) {
            return null;
        }
        int randIndex = GlobalData.rand.nextInt(fragList.size());
        return fragList.get(randIndex);
    }

    public Map.Entry<Boolean, Integer> attack(String equName, Collection<Adventurer> underAttackAdvs, String attackType) {
        int hpDecrease = 0;
        Equipment equipment = bag.getEquipment(equName);
        if (equipment == null) {
            return new AbstractMap.SimpleEntry<>(false, hpDecrease);
        }
        int def = Integer.MIN_VALUE;
        for (Adventurer defender : underAttackAdvs) {
            def = Integer.max(def, defender.getDef());
        }
        if (equipment.getCE() + this.atk <= def) {
            return new AbstractMap.SimpleEntry<>(false, hpDecrease);
        }

        equipment.setDurability(equipment.getDurability() - 1);

        if (equipment.getDurability() <= 0) {
            delThing(equipment.getId());
        }

        for (Adventurer defender : underAttackAdvs) {
            if (attackType.equals("normal")) {
                // System.err.println(hiredAdvs.values());
                for (Adventurer defenderHelper : defender.hiredAdvs.values()) {
                    defenderHelper.watch(defender);
                }
            }
            hpDecrease += equipment.attack(this, defender);
            if (attackType.equals("normal")) { // 对援助进行结算
                ArrayList<Adventurer> delList = new ArrayList<>();
                for (Adventurer defenderHelper : defender.hiredAdvs.values()) {
                    if (defenderHelper.unwatch()) {
                        GlobalData.helpDel = true;
                        delList.add(defenderHelper);
                    }
                }
                for (Adventurer defenderHelper : delList) {
                    defender.hiredAdvs.remove(defenderHelper.getId());
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(true, hpDecrease);
    }

    public HashSet<Adventurer> getAllHiredAdvs(int depth) {
        HashSet<Adventurer> allHiredAdvs = new HashSet<>();
        allHiredAdvs.add(this);
        if (depth == 5) {
            return allHiredAdvs;
        }
        for (Adventurer adventurer : hiredAdvs.values()) {
            allHiredAdvs.addAll(adventurer.getAllHiredAdvs(depth + 1));
        }
        return allHiredAdvs;
    }

    @ForDataMaker
    public HashSet<Adventurer> getAllHiredAdvs(HashSet<Adventurer> visited) {
        HashSet<Adventurer> allHiredAdvs = new HashSet<>();
        allHiredAdvs.add(this);
        visited.add(this);
        for (Adventurer adventurer : hiredAdvs.values()) {
            if (!visited.contains(adventurer)) {
                allHiredAdvs.addAll(adventurer.getAllHiredAdvs(visited));
            }
        }
        return allHiredAdvs;
    }

    @ForDataMaker
    public boolean tryAttack(String equName, ArrayList<Adventurer> underAttackAdvs, String attackType) {
        Equipment equipment = bag.getEquipment(equName);
        if (equipment == null) {
            return true;
        }
        HashSet<Adventurer> defenders = new HashSet<>();
        if (attackType.equals("chain")) {
            for (Adventurer adventurer : underAttackAdvs) {
                defenders.addAll(adventurer.getAllHiredAdvs(1));
            }
        } else {
            defenders.addAll(underAttackAdvs);
        }
        int def = Integer.MIN_VALUE;
        for (Adventurer adventurer : defenders) {
            def = Integer.max(def, adventurer.getDef());
        }
        if (equipment.getCE() + this.atk <= def) {
            return true;
        }
        for (Adventurer adventurer : defenders) {
            if (!equipment.tryAttack(this, adventurer)) {
                return false;
            }
        }
        return true;
    }

    public void setHitPoint(int hp) {
        this.hitPoint = hp;
        checkHp();
    }

    public void checkHp() {
        if (this.hitPoint <= 0) {
            GlobalData.isDataLegal = false;
        }
    }

    public void hireAdv(Adventurer adv) {
        hiredAdvs.put(adv.getId(), adv);
    }

    public ArrayList<Adventurer> getHiredAdvs() {
        return new ArrayList<>(hiredAdvs.values());
    }

    public void watch(Adventurer adv) {
        // System.err.println(this.name + " is watching " + adv.getName());
        watchingPerson = adv;
        watchingPersonOldHp = adv.getHitPoint();
    }

    public boolean unwatch() {
        boolean shouldDel = false;
        if (watchingPerson.getHitPoint() <= watchingPersonOldHp / 2) {
            ArrayList<Equipment> equipments = bag.getEquipments();

            for (Equipment equipment : equipments) {
                watchingPerson.addThing(equipment);
                this.delThing(equipment.getId());
            }

            helpCnt.put(watchingPerson.getId(), helpCnt.getOrDefault(watchingPerson.getId(), 0) + 1);
            if (helpCnt.get(watchingPerson.getId()) > 3) {
                // this.delHiredAdv(watchingPerson.getId());
                shouldDel = true;
                this.helpCnt.remove(watchingPerson.getId()); // 从雇主列表中移除
                // System.out.println("Adv " + this.name + " leave Adv " + watchingPerson.name); // debug
            }
        }
        watchingPersonOldHp = 0;
        watchingPerson = null;
        return shouldDel;
    }

    public int getComprehensiveCE() {
        int comprehensiveCE = this.getCE();
        for (Thing thing : bag.getThings()) {
            comprehensiveCE += thing.getCE();
        }
        for (Adventurer adv : hiredAdvs.values()) {
            comprehensiveCE += adv.getCE();
        }
        return comprehensiveCE;
    }
}
