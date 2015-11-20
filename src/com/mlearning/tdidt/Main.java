package com.mlearning.tdidt;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
public class Main {
    public static void main(String[] args) {
        String filepath1 = "./data/SPECT.train";
        String filepath2 = "./data/SPECT.test";

        FileIO fileIO = new FileIO();
        Examples examples = fileIO.readFile(filepath1);
        Examples examples1 = fileIO.readFile(filepath2);

        // join the two sets of examples
        examples1.getExamplesList().forEach(examples::add);

        // run 10 iterations of 3 fold cross validation
        KFoldCrossValidation kf = new KFoldCrossValidation(examples, 3);

        ArrayList<Double> accuracyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            accuracyList.add(kf.getPredictionAccuracy());
        }
        System.out.println();
        System.out.println("######## Accuracy of Prediction");
        System.out.println();

        // calculate metrics for accuracy
        double minAccuracy = Collections.min(accuracyList);
        double maxAccuracy = Collections.max(accuracyList);
        double meanAccuracy = 0.0;
        int i = 1;

        for (double accu : accuracyList) {
            System.out.print(accu);
            if (accu == minAccuracy) {
                System.out.print("  <-- min\n");
            } else if (accu == maxAccuracy) {
                System.out.print("  <-- max\n");
            } else {
                System.out.print("\n");
            }
            meanAccuracy = (meanAccuracy * (i - 1) + accu) / i;
            i++;
        }
        System.out.println();
        System.out.println("" + meanAccuracy + "  <-- mean");
    }
}
