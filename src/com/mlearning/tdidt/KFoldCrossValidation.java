package com.mlearning.tdidt;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
public class KFoldCrossValidation {
    private Examples examples;
    private int K = 3;


    public KFoldCrossValidation(Examples examples) {
        this.examples = examples;
    }

    public KFoldCrossValidation(Examples examples, int K) {
        this.examples = examples;
        this.K = K;
    }

    public ArrayList<Examples> shuffleAndGetTrainTestExamples(){
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < examples.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        int testSize = examples.size() / 3;

        Examples testExamples = new Examples();
        for (int i = 0; i < testSize; i++) {
            testExamples.add(examples.get(indices.get(i)));
        }

        Examples trainExamples = new Examples();
        for (int i = testSize; i < examples.size(); i++) {
            trainExamples.add(examples.get(indices.get(i)));
        }

        ArrayList<Examples> examplesArrayList = new ArrayList<>();
        examplesArrayList.add(trainExamples);
        examplesArrayList.add(testExamples);

        return examplesArrayList;
    }

    public double getPredictionAccuracy() {
        ArrayList<Examples> examplesArrayList = shuffleAndGetTrainTestExamples();
        Examples trainExamples = examplesArrayList.get(0);
        Examples testExamples = examplesArrayList.get(1);

        // Build a decision tree
        BinaryDTBuilder bdtb = new BinaryDTBuilder(trainExamples);
        Tree tree = bdtb.buildAndGetTree();

        // Test accuracy of the tree
        ArrayList<Boolean> testLabels = testExamples.getAllLabels();
        ArrayList<ArrayList<Boolean>> testAttributes = testExamples.getAllAttributes();

        int testSize = testLabels.size();
        int truePositives = 0;

        for (int t = 0; t < testSize; t++) {
            boolean truth = testLabels.get(t);
            boolean prediction = tree.predictLabel(testAttributes.get(t));
            if (truth && prediction) { truePositives++; }
        }

        return (double) truePositives / testSize;

    }
}
