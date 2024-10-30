package Combatants;

public class Thing implements Combatant {
    private final int id;
    private final String name;
    private final int CE;

    public Thing(int id, String name, int CE) {

        this.id = id;
        this.name = name;
        this.CE = CE;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCE() {
        return CE;
    }

    public String getClassName() {
        return "Thing";
    }
}
