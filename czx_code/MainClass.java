import DataMaker.DataMaker;
import DataMaker.Printer;
import DataMaker.GlobalData;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainClass {

    public static void setArgs(
            boolean makerMode,
            int maxFileId,
            int optionNum,
            int minLines,
            int maxLines,
            String folder
    ) {
        GlobalData.makerMode = makerMode; // 是否开启数据生成模式
        GlobalData.curFileId = 1;
        GlobalData.maxFileId = GlobalData.makerMode ? maxFileId : 1; // 生成数据条数
        GlobalData.optionNum = optionNum; // 本次作业的指令总数
        GlobalData.minLines = minLines;
        GlobalData.maxLines = maxLines;
        GlobalData.folderName = folder;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // System.setOut(new PrintStream("output.txt")); // debug

        setArgs(true, 1, 12,9000, 10000, "."); // 设置数据生成相关参数
        // 注意每次写入是追加写入，因此记得先删一遍文件，或者换新的目录（现在不需要了）

        for (; GlobalData.curFileId <= GlobalData.maxFileId; GlobalData.curFileId++) {
            Solver solver = new Solver();
            DataMaker dataMaker = new DataMaker(solver.getAdventurers());
            ArrayList<ArrayList<String>> inputInfo;
            int n;

            GlobalData.isDataLegal = true;
            GlobalData.helpDel = true;

            if (!GlobalData.makerMode) { // 如果不是数据生成模式，那么从标准输入读取数据
                inputInfo = parseInput();
                n = inputInfo.size();
            } else {
                Printer.delFile(GlobalData.getInputFileName());
                Printer.delFile(GlobalData.getOutputFileName());
                GlobalData.attackCnt = 0;
                GlobalData.adventureCnt = 0;
                inputInfo = new ArrayList<>();
                n = GlobalData.rand.nextInt(GlobalData.minLines, GlobalData.maxLines + 1);
                Printer.print(String.valueOf(n), true, GlobalData.getInputFileName(), false);
            }

            for (int i = 0; i < n; i++) {
                if (GlobalData.makerMode) {
                    inputInfo.add(dataMaker.generateData());
                    Printer.printStdIn(inputInfo.get(i));
                }
                ArrayList<String> row = inputInfo.get(i);

                solver.solve(row);
            }

            if (!GlobalData.isDataLegal) {
                if (GlobalData.makerMode) {
                    System.out.println("Making data " + GlobalData.curFileId + " failed! Try to recreate data!");
                    Printer.delFile(GlobalData.getInputFileName());
                    Printer.delFile(GlobalData.getOutputFileName());
                    GlobalData.curFileId--;
                } else {
                    System.out.println("Data is illegal! Some adventurer's HP is not over 0!");
                }
            }

            if (GlobalData.makerMode && !GlobalData.helpDel) {
                System.out.println("Making data " + GlobalData.curFileId + " not test help del! Try to recreate data!");
                Printer.delFile(GlobalData.getInputFileName());
                Printer.delFile(GlobalData.getOutputFileName());
                GlobalData.curFileId--;
            }
        }
    }

    private static ArrayList<ArrayList<String>> parseInput() {
        ArrayList<ArrayList<String>> inputInfo = new ArrayList<>(); // 解析后的输入将会存进该容器中, 类似于c语言的二维数组
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim()); // 读取行数
        for (int i = 0; i < n; ++i) {
            String nextLine = scanner.nextLine(); // 读取本行指令
            String[] strings = nextLine.trim().split(" +"); // 按空格对行进行分割
            inputInfo.add(new ArrayList<>(Arrays.asList(strings))); // 将指令分割后的各个部分存进容器中
        }
        return inputInfo;
    }
}
