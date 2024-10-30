package AdventureGame.Treasure;

import Combatants.Adventurer;

public interface Treasure {
    void showInfo(); // 显示宝物信息的方法
    void useBy(Adventurer adv); // 让该冒险者获得这个宝物的加成
}
