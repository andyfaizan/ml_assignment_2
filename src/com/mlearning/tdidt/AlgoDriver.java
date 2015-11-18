package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by andyfaizan on 18/11/15.
 */
public class AlgoDriver {

    private ArrayList<Node> tree;
    private ArrayList<ArrayList<Integer>> values;

    public AlgoDriver(ArrayList<ArrayList<Integer>> values) {
        this.values = values;
    }

    public ArrayList<Node> TDIDT(ArrayList<ArrayList<Integer>> examples, ArrayList<Integer> attribs,
                                 ArrayList<Node> tree, Node node) {

        //TODO the algorithm LOL
        return tree;
    }

    private double getEntropy(ArrayList<Integer> S) {
        int n = S.size();
        int posCount = 0;
        int negCount = 0;
        for (int i = 0; i < n; i++) {
            if (S.get(i) == 1) {
                posCount++;
            } else {
                negCount++;
            }
        }
        int totalCount = posCount + negCount;
        double posProb = (double) posCount / totalCount;
        double negProb = (double) negCount / totalCount;
        //System.out.println("posCount is : " + posCount + ", negCount is : " + negCount);
        double entropy = (-1.0 * posProb) * log2(posProb) + (-1.0 * negProb) * log2(negProb);
        return entropy;
    }

    private double log2(double arg) {
        if (arg == 0) {
            return 0;
        }
        return Math.log(arg) / Math.log(2);
    }

    // This function needs refactoring
    private double getInformationGain(ArrayList<Integer> S, int attribute, int value) {
        double lCount = 0;
        double hCount = 0;
        ArrayList<Integer> lowerS = new ArrayList<Integer>();
        ArrayList<Integer> higherS = new ArrayList<Integer>();

        int n = S.size();
        for (int i = 0; i < n; i++) {
            if (S.get(i) <= value) {
                //System.out.println("Lower add");
                lCount++;
                lowerS.add(S.get(i));
            } else {
                //System.out.println("Upper add");
                hCount++;
                higherS.add(S.get(i));
            }
        }

        double count = lCount + hCount;
        double lProb = lCount / count;
        double hProb = hCount / count;

        double entropyHigher = getEntropy(higherS);
        double entropyLower = getEntropy(lowerS);

        double entropyInitial = getEntropy(S);
        double entropyChange = lProb * entropyLower + hProb * entropyHigher;

        return entropyInitial - entropyChange;

    }

    public static void main(String[] args) {
        FileIO fileIO = new FileIO();
        if (args.length < 2) {
            System.err.println("Not enough arguments provided");
            System.exit(0);
        }
        ArrayList<ArrayList<Integer>> values = fileIO.readFile(args[0]);
        AlgoDriver driver = new AlgoDriver(values);
    }
}
