package com.mlearning.tdidt;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by andyfaizan on 17/11/15.
 *
 */
class FileIO {

    public Examples readFile(String filePath) {
        Examples examples = new Examples();
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
    public void writeFile(Tree tree, String outFilePath) {

        try {
            File outFile = new File(outFilePath);

            if (!outFile.exists()) {
                outFile.createNewFile();
            } else {
                outFile.delete();
            }

            FileWriter fileWriter = new FileWriter(outFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(tree.toString());
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
        Examples values = fileIO.readFile("./data/xor.txt");

        Tree tree = new BinaryDTBuilder(values).buildAndGetTree();
        fileIO.writeFile(tree, "./output/tree.txt");
    }
}
