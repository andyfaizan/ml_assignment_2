package com.mlearning.tdidt;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by andyfaizan on 17/11/15.
 *
 */
class FileIO {

    public ArrayList<Example> readFile(String filePath) {
        ArrayList<Example> examples = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            // TODO: The first line is expected to have the number of attributes per class
            int nAttributes = -1;
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (nAttributes < 0) {
                    nAttributes = Integer.parseInt(currentLine);
                } else {
                    String[] stringValues = currentLine.split(",");
                    ArrayList<Boolean> list = new ArrayList<>();
                    for (String element : stringValues) {
                        list.add(Integer.parseInt(element) > 0);
                    }
                    examples.add(new Example(list, nAttributes));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return examples;
    }

    /*
    Untested function
     */
    public void writeFile(ArrayList<Node> nodes, String outFilePath) {

        try {
            File outFile = new File(outFilePath);

            if (!outFile.exists()) {
                outFile.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(outFile.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Node node : nodes) {
                bufferedWriter.write(node.toString());
            }
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    For testing read file
     */
    public static void main(String[] args) {
        FileIO fileIO = new FileIO();
        ArrayList<Example> values = fileIO.readFile("./data/xor.txt");
        for (Example e : values) {
            System.out.print(e.toString());
        }
        System.out.println();
    }
}
