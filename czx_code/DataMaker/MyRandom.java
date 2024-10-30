package DataMaker;

import java.util.Random;

public class MyRandom extends Random {

    // 生成一个指定范围内的随机整数 [min, max)
    public int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        // 计算范围大小并生成一个随机数
        int range = max - min;
        return min + nextInt(range);
    }
}
