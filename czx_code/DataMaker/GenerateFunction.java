package DataMaker;

import java.util.ArrayList;

@FunctionalInterface
public interface GenerateFunction {
    boolean generate(ArrayList<String> data);
}
