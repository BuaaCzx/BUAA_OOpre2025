package DataMaker;

import java.util.Random;

@ForDataMaker
public class GlobalData {

    public static boolean makerMode = false;
    public static int curFileId = 1;
    public static int maxFileId = 10;
    public static int optionNum = 1;
    public static int minLines = 1;
    public static int maxLines = 2000;
    public static String folderName = "data";
    public static MyRandom rand = new MyRandom();
    public static boolean isDataLegal = true;
    public static int attackCnt = 0;
    public static int adventureCnt = 0;
    public static boolean helpDel = false;

    public static String getInputFileName() {
        return folderName + "/input" + curFileId + ".txt";
    }

    public static String getOutputFileName() {
        return folderName + "/output" + curFileId + ".txt";
    }
}
