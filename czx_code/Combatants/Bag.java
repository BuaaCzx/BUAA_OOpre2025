package Combatants;

import Combatants.Bottle.Bottle;
import Combatants.Equipment.Equipment;
import DataMaker.ForDataMaker;

import java.util.ArrayList;
import java.util.HashMap;

public class Bag {
    private final HashMap<String, Equipment> equipments;
    private final HashMap<String, HashMap<Integer, Bottle>> bottles;
    private final Adventurer adventurer;

    public Bag(Adventurer adventurer) {
        equipments = new HashMap<>();
        bottles = new HashMap<>();
        this.adventurer = adventurer;
    }

    public void remove(Thing thing) {
        if (thing instanceof Equipment) {
            equipments.remove(thing.getName(), thing);
        } else {
            for (HashMap<Integer, Bottle> bottleMap : bottles.values()) {
                if (bottleMap.containsValue((Bottle) thing)) {
                    bottleMap.remove(thing.getId());
                }
            }
        }
    }

    public void put(int id, Thing thing) {
        if (thing instanceof Equipment) {

            equipments.put(thing.getName(), (Equipment) thing);
        } else {
            Bottle bottle = (Bottle) thing;
            bottles.putIfAbsent(bottle.getName(), new HashMap<>());
            if (bottles.get(bottle.getName()).size() < adventurer.getMaxBottleNum()) {
                bottles.get(bottle.getName()).put(bottle.getId(), bottle);
            }
        }
    }

    public boolean containsValue(Thing thing) {
        if (thing instanceof Equipment) {
            return equipments.containsValue(thing);
        } else {
            Bottle bottle = (Bottle) thing;
            bottles.putIfAbsent(bottle.getName(), new HashMap<>());
            return bottles.get(bottle.getName()).containsValue(bottle);
        }
    }

    public boolean containsValue(int id) {
        for (Equipment equipment : equipments.values()) {
            if (equipment.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Equipment getEquipment(String name) {
        return equipments.getOrDefault(name, null);
    }

    public ArrayList<Equipment> getEquipments() {
        return new ArrayList<>(equipments.values());
    }

    public ArrayList<Thing> getThings() {
        ArrayList<Thing> things = new ArrayList<>(equipments.values());
        for (HashMap<Integer, Bottle> map : bottles.values()) {
            things.addAll(map.values());
        }
        return things;
    }
}
