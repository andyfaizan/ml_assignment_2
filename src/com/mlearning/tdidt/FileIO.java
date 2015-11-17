package com.mlearning.tdidt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyfaizan on 17/11/15.
 */
public class FileIO {

    public ArrayList<ArrayList<Integer>> readFile(String filePath){
        ArrayList<ArrayList<Integer>> values = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath)))
        {
            String currentLine;
            values = new ArrayList<ArrayList<Integer>>();
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] stringValues = currentLine.split(",");
                ArrayList list = new ArrayList<Integer>();
                for (String element : stringValues) {
                    list.add(Integer.parseInt(element));
                }
                values.add(list);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

//    public void writeFile(){
//
//    }

    /*
    For testing read file
     */
    public static void main(String []args){
        FileIO fileIO = new FileIO();
        ArrayList<ArrayList<Integer>> values = fileIO.readFile("/home/andyfaizan/Downloads/SPECT/SPECT.train");
        for (ArrayList<Integer> list: values) {
            for (Integer ele : list) {
                System.out.print(ele);
            }
            System.out.println();
        }
    }
}
