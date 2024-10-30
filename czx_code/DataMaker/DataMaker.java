package DataMaker;

import Combatants.Adventurer;
import Combatants.Bottle.Bottle;
import Combatants.Combatant;
import Combatants.Equipment.Equipment;
import Combatants.Thing;

import java.util.*;

import static java.util.Collections.shuffle;

@ForDataMaker
public class DataMaker {
    private final HashMap<Integer, GenerateFunction> functions = new HashMap<>();
    private final HashMap<Integer, Adventurer> adventurers;
    private final HashSet<Integer> ids = new HashSet<>();
    private final HashSet<String> advNames = new HashSet<>();
    private final HashSet<String> bottleNames = new HashSet<>();
    private final HashSet<String> equNames = new HashSet<>();
    private final HashSet<String> fragNames = new HashSet<>();

    public DataMaker(HashMap<Integer, Adventurer> adventurers) {
        this.adventurers = adventurers;

        this.functions.put(1, this::generate1);
        this.functions.put(2, this::generate2);
        this.functions.put(3, this::generate3);
        this.functions.put(4, this::generate4);
        this.functions.put(5, this::generate5);
        this.functions.put(6, this::generate6);
        this.functions.put(7, this::generate7);
        this.functions.put(8, this::generate8);
        this.functions.put(9, this::generate9);
        this.functions.put(10, this::generate10);
        this.functions.put(11, this::generate11);
        this.functions.put(12, this::generate12);
    }

    public ArrayList<String> generateData() {
        ArrayList<String> row = null;

        while (true) {
            int option = GlobalData.rand.nextInt(1, GlobalData.optionNum + 1);
            // int[] options = {1, 3, 4, 6, 6, 6, 6, 10, 10, 10, 10, 10, 11, 11, 11};
            // option = options[GlobalData.rand.nextInt(options.length)]; // for special data
            row = new ArrayList<>();
            row.add(String.valueOf(option));
            GenerateFunction function = functions.get(option);
            if (function.generate(row)) {
                break;
            }
        }

        return row;
    }

    private boolean generate1(ArrayList<String> row) {
        if (adventurers.size() >= 100) { // for special data
            return false;
        }
        int advId = generateId();
        String advName = generateAdvName();
        row.add(String.valueOf(advId));
        row.add(advName);
        return true;
    }

    private boolean generate2(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        int bottleId = generateId();
        String bottleName = generateBottleName();
        // type 从 "HpBottle", "AtkBottle", "DefBottle" 三种中随机选取一个
        int typeRand = GlobalData.rand.nextInt(0, 7);
        String type = typeRand < 2 ? "HpBottle" : typeRand < 5 ? "AtkBottle" : "DefBottle";
        int CE = type.equals("HpBottle") ? 0 : GlobalData.rand.nextInt(0, (int) 1e3);
        int capacity = type.equals("HpBottle") ? GlobalData.rand.nextInt((int) 1, (int) 1e3) : GlobalData.rand.nextInt(1, (int) 1e3);
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(bottleId));
        row.add(bottleName);
        row.add(String.valueOf(capacity));
        row.add(type);
        row.add(String.valueOf(CE));
        return true;
    }

    private boolean generate3(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        int equId = generateId();
        String equName = generateEquName();
        int durability = GlobalData.rand.nextInt(1, 3);
        int typeRand = GlobalData.rand.nextInt(0, 3);
        String type = typeRand == 0 ? "Axe" : typeRand == 1 ? "Sword" : "Blade";
        int CE = GlobalData.rand.nextInt(0, (int) 1e3);
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(equId));
        row.add(equName);
        row.add(String.valueOf(durability));
        row.add(type);
        row.add(String.valueOf(CE));
        return true;
    }

    private boolean generate4(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        Equipment equipment = adv.getRandEqu();
        if (equipment == null) {
            return false;
        }
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(equipment.getId()));
        return true;
    }

    private boolean generate5(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        Thing thing = adv.getRandThing();
        if (thing == null) {
            return false;
        }
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(thing.getId()));
        return true;
    }

    private boolean generate6(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        Thing thing = adv.getRandThing();
        if (thing == null) {
            return false;
        }
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(thing.getId()));
        return true;
    }

    private boolean generate7(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        Bottle bottle = adv.getRandBottle();
        if (bottle == null) {
            return false;
        }
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(bottle.getId()));
        return true;
    }

    private boolean generate8(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        int fragId = generateId();
        String fragName = adv.getRandFrag();
        if (fragName == null || GlobalData.rand.nextInt(10) == 0) {
            fragName = generateFragName();
        }
        row.add(String.valueOf(adv.getId()));
        row.add(String.valueOf(fragId));
        row.add(fragName);
        return true;
    }

    private boolean generate9(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        String fragName = adv.getRandFrag();
        if (fragName == null) {
            return false;
        }
        int welfareId;
        if (GlobalData.rand.nextBoolean()) {
            welfareId = generateId();
        } else {
            Thing thing = adv.getRandThing();
            if (thing == null) {
                welfareId = generateId();
            } else {
                welfareId = thing.getId();
            }
        }
        row.add(String.valueOf(adv.getId()));
        row.add(fragName);
        row.add(String.valueOf(welfareId));
        return true;
    }

    private boolean generate10(ArrayList<String> row) {
        Adventurer attacker = getRandAdv();
        if (attacker == null) {
            return false;
        }
        Equipment equipment = attacker.getRandEqu();
        if (equipment == null) {
            return false;
        }
        String attackType = GlobalData.rand.nextInt(5) == 0 ? "chain" : "normal";
        // attackType = "normal"; // FOR SPECIAL DATA

        ArrayList<Adventurer> defenders = new ArrayList<>();
        ArrayList<Adventurer> advList = new ArrayList<>(adventurers.values());
        advList.remove(attacker);
        if (advList.isEmpty()) {
            return false;
        }
        // 随机打乱 advList
        shuffle(advList);
        int k = GlobalData.rand.nextInt(1, Integer.min(advList.size(), 10) + 1);
        for (int i = 0; i < k; i++) {
            defenders.add(advList.get(i));
        }
        row.add(String.valueOf(attacker.getId()));
        row.add(String.valueOf(equipment.getName()));
        row.add(attackType);
        row.add(String.valueOf(defenders.size()));
        for (Adventurer defender : defenders) {
            row.add(String.valueOf(defender.getId()));
        }
        if (!attacker.tryAttack(equipment.getName(), defenders, attackType)) {
            return false;
        }
        if (attackType.equals("chain")) {
            if (GlobalData.attackCnt >= 50) {
                return false;
            }
            GlobalData.attackCnt++;
        }
        return true;
    }

    private boolean generate11(ArrayList<String> row) {
        Adventurer adv1 = getRandAdv();
        Adventurer adv2 = getRandAdv();
        if (adv1 == null || adv2 == null) {
            return false;
        }
        if (adv1.getHiredAdvs().size() >= 10 || adv1.getHiredAdvs().contains(adv2) || adv2.getAllHiredAdvs(new HashSet<>()).contains(adv1)) {
            return false; // 雇佣关系本来就存在，或adv2已经间接雇佣了adv1
        }
        row.add(String.valueOf(adv1.getId()));
        row.add(String.valueOf(adv2.getId()));
        return true;
    }

    private boolean generate12(ArrayList<String> row) {
        Adventurer adv = getRandAdv();
        if (adv == null) {
            return false;
        }
        if (GlobalData.adventureCnt >= 50) {
            return false;
        } else {
            GlobalData.adventureCnt++;
        }
        row.add(String.valueOf(adv.getId()));
        return true;
    }


    // 生成新物品使用的，全局不重 id
    private int generateId() {
        int id;
        do {
            id = GlobalData.rand.nextInt(0, Integer.MAX_VALUE);
        } while (ids.contains(id));
        ids.add(id);
        return id;
    }

    private String generateName(HashSet<String> names) {
        // 75 概率从已有生成重复，25 概率生成新的
        if (names.isEmpty() || GlobalData.rand.nextInt(0, 5) == 1) {
            StringBuilder name = new StringBuilder();
            int length = GlobalData.rand.nextInt(1, 40);
            for (int i = 0; i < length; i++) {
                name.append((char) (GlobalData.rand.nextInt(33, 126)));
            }
            names.add(name.toString());
            return name.toString();
        } else {
            // 从 set 里随机选一个返回
            int index = GlobalData.rand.nextInt(0, names.size());
            ArrayList<String> namesList = new ArrayList<>(names);
            return namesList.get(index);
        }
    }

    private String generateName(HashSet<String> names, int percent) { // 传入概率
        if (names.isEmpty() || GlobalData.rand.nextInt(0, percent) == 1) {
            StringBuilder name = new StringBuilder();
            int length = GlobalData.rand.nextInt(1, 40);
            for (int i = 0; i < length; i++) {
                name.append((char) (GlobalData.rand.nextInt(33, 126)));
            }
            names.add(name.toString());
            return name.toString();
        } else {
            // 从 set 里随机选一个返回
            int index = GlobalData.rand.nextInt(0, names.size());
            ArrayList<String> namesList = new ArrayList<>(names);
            return namesList.get(index);
        }
    }

    private String generateFragName() {
        return generateName(fragNames);
    }

    private String generateAdvName() {
        return generateName(advNames);
    }

    private String generateBottleName() {
        return generateName(bottleNames);
    }

    private String generateEquName() {
        return generateName(equNames);
    }

    private Adventurer getRandAdv() {
        // 从已有的map中随机抽一个人出来
        if (adventurers.isEmpty()) {
            return null;
        } else {
            ArrayList<Adventurer> advs = new ArrayList<>(adventurers.values());
            int index = GlobalData.rand.nextInt(0, advs.size());
            return advs.get(index);
        }
    }

    public boolean isAdvHpOver0() {
        for (Adventurer adv : adventurers.values()) {
            if (adv.getHitPoint() <= 0) {
                return false;
            }
        }
        return true;
    }
}
