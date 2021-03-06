package com.mlearning.tdidt;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
class Main {
    public static void main(String[] args) {
        String filepath1 = "./data/SPECT.train";
        String filepath2 = "./data/SPECT.test";

        FileIO fileIO = new FileIO();
        Examples examples = fileIO.readFile(filepath1);
        Examples examples1 = fileIO.readFile(filepath2);

        // join the two sets of examples
        examples1.getExamplesList().forEach(examples::add);

        spect(examples, 3, 10, "./output/", "SPECT-", ".tree");

//        String fp = "/Users/myrmidon/Delve/studies-mi/ml-bonn/sheet-02/ml_assignment_2/data/kr-vs-kp.data.binarized";
//
//        FileIO fileIO = new FileIO();
//        Examples examples = fileIO.readFile(fp);
//        BinaryDTBuilder btb = new BinaryDTBuilder(examples);
////        Tree tree = btb.buildAndGetTree();
//
//        spect(examples, 3, 10, "./output/", "exercise-02-03", ".tree");

    }

    private static void spect(Examples examples, int kFolds, int nIterations, String outDir, String outFilePrefix, String outFilePostfix) {
        // run nIterations of kfolds cross validation
        KFoldCrossValidation kf = new KFoldCrossValidation(examples, kFolds);

        ArrayList<Double> accuracyList = new ArrayList<>();

        String outFilePath = "";
        if (!outDir.equals("")) {
            outFilePath += outDir + outFilePrefix;
        }

        for (int i = 0; i < nIterations; i++) {
            String outFP = "";
            if (!outFilePath.equals("")) {
                outFP += outFilePath + (i + 1);
            }
            accuracyList.add(kf.getPredictionAccuracy(outFP + outFilePostfix));
        }
        System.out.println();
        System.out.println("######## Accuracy of Predictions for " + nIterations + " iterations of " + kFolds + " fold cross validation");
        System.out.println();

        // calculate metrics for accuracy
        double minAccuracy = Collections.min(accuracyList);
        double maxAccuracy = Collections.max(accuracyList);
        double meanAccuracy = 0.0;
        int i = 1;

        for (double accu : accuracyList) {
            if (i < 10) { System.out.print("0"); }
            System.out.print("" + i + ". " + accu);
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

        if (!outDir.equals("")) {
            System.out.println();
            System.out.println("Output trees have been saved as " + outFilePath + "*" + outFilePostfix);
        }
    }
}
