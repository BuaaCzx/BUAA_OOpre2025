package AdventureGame.Guard;

import Combatants.Adventurer;

public interface Guard {
    boolean fight(Adventurer adv); // 守门怪物与冒险者战斗，返回战斗结果
    String getType(); // 返回守门怪物的类型
}
