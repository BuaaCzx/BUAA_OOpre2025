package DataMaker;

import DataMaker.ForDataMaker;
import DataMaker.GlobalData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Printer {
    public static void print(String message, boolean toFile, String fileName, boolean toConsole) {
        if (toFile) {
            try {
                PrintWriter currentFileWriter = new PrintWriter(new FileWriter(fileName, true)); // 追加模式
                currentFileWriter.println(message);
                currentFileWriter.flush();
                currentFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (toConsole) {
            System.out.println(message);
        }
    }

    public static void print(String message) {
        print(message, GlobalData.makerMode, GlobalData.getOutputFileName(), !GlobalData.makerMode);
    }

    @ForDataMaker
    public static void printStdIn(ArrayList<String> row) {
        StringBuilder sb = new StringBuilder();
        for (String s : row) {
            sb.append(s).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        print(sb.toString(), true, GlobalData.getInputFileName(), false);
    }

    public static void delFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}