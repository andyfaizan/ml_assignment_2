package com.mlearning.tdidt;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
class BinaryDTBuilder {
    private final ArrayList<Example> examples;
    private ArrayList<Node> tree;
    private int maxNodeId = -1;

    private BinaryDTBuilder(ArrayList<Example> examples) {
        this.examples = examples;
    }

    private int getNewNodeId() {
        maxNodeId++;
        return maxNodeId;
    }

    private ArrayList<Node> buildAndGetTree() {
        buildTree();
        return this.tree;
    }

    private void buildTree() {
        ArrayList<Integer> attributes = new ArrayList<>();
        int nAttributes = examples.get(0).getnAttributes();
        for (int a = 0; a < nAttributes; a++) {
            attributes.add(a);
        }

        Node rootNode = new Node();
        rootNode.setAsRoot();
        rootNode.setId(getNewNodeId());

        this.tree = new ArrayList<>();
        this.tree.add(rootNode);

        TDIDT(examples, attributes, rootNode);
    }

    private void TDIDT(ArrayList<Example> examples, ArrayList<Integer> attributesToCheck, Node nodeToCheckFor) {
        // Check if class labels are the same for the examples
        ArrayList<Boolean> labelsList = getAllLabels(examples);
        if(allValuesSame(labelsList)) {
            nodeToCheckFor.setAsLeaf();
            nodeToCheckFor.setLeafClass(labelsList.get(0));
            return;
        }

        // Check if any attribute splits data
        boolean splitAvailable = false;
        for (int a : attributesToCheck) {
            if (!allValuesSame(getValsForAttrib(examples, a))) {
                splitAvailable = true;
                break;
            }
        }

        if (!splitAvailable) {
            nodeToCheckFor.setAsLeaf();
            ArrayList<Integer> labelsCount = getPosNegCount(labelsList);
            if (labelsCount.get(0) >= labelsCount.get(1)) {
                nodeToCheckFor.setLeafClass(true);
            } else {
                nodeToCheckFor.setLeafClass(false);
            }

            return;
        }

        // select best attribute to test for the node based on Information Gain


        int bestAttributeID;
        if (attributesToCheck.size() == 1) {
            bestAttributeID = attributesToCheck.get(0);
            System.out.println("//////// Choosing the only attribute left : " + bestAttributeID + " for node " + nodeToCheckFor.getId());
            System.out.println();
        } else {
            System.out.println("//////// Information Gain Calculations for Node " + nodeToCheckFor.getId());
            bestAttributeID = getAttribWithMaxInfoGain(examples, attributesToCheck);
            System.out.println("Chosen attribute " + bestAttributeID + " for node " + nodeToCheckFor.getId());
            System.out.println();
        }

        // remove this attribute for next recursion
        ArrayList<Integer> newAttributesToCheck = new ArrayList<>();
        for (int a : attributesToCheck) {
            if (a != bestAttributeID) {
                newAttributesToCheck.add(a);
            }
        }

        nodeToCheckFor.setTestAttrib(bestAttributeID);

        ArrayList<Boolean> possibleValues = new ArrayList<>();
        possibleValues.add(false);
        possibleValues.add(true);

        for (boolean v : possibleValues) {
            ArrayList<Example> examplesSubList = getSublistForAttribValue(examples, bestAttributeID, v);
            if (examplesSubList.size() > 0) {
                Node childNode = new Node();
                childNode.setId(getNewNodeId());
                childNode.setParentsTest(v);

                this.tree.add(childNode);

                nodeToCheckFor.setChildForValue(childNode.getId(), v);
                TDIDT(examplesSubList, newAttributesToCheck, childNode);
            }
        }

    }

    private int getAttribWithMaxInfoGain(ArrayList<Example> examples, ArrayList<Integer> attribsToCheck) {
        int bestAttribID = -1;
        double bestInfoGain = -1.0;

        double baseEntropy = getBaseEntropy(examples);

        System.out.println();
        System.out.println("Base Entropy : " + baseEntropy);
        System.out.println();

        for (int a : attribsToCheck) {
            double infoGain = getInformationGainForAttrib(examples, a, baseEntropy);

            System.out.println("Information Gain for Attribute " + a + " : " + infoGain);

            if (infoGain > bestInfoGain) {
                bestInfoGain = infoGain;
                bestAttribID = a;
            }
        }

        System.out.println();

        return bestAttribID;
    }

    private double getInformationGainForAttrib(ArrayList<Example> examples, int attrib, double baseEntropy) {
        ArrayList<Boolean> posValLabels = getAllLabels(getSublistForAttribValue(examples, attrib, true));
        ArrayList<Boolean> negValLabels = getAllLabels(getSublistForAttribValue(examples, attrib, false));

        int posCount = posValLabels.size();
        int negCount = negValLabels.size();
        int totalCount = examples.size();

        double posProb = (double) posCount / totalCount;
        double negProb = (double) negCount / totalCount;

        return baseEntropy - (posProb * getEntropy(posValLabels) + negProb * getEntropy(negValLabels));
    }

    private double getBaseEntropy(ArrayList<Example> examples) {
        ArrayList<Boolean> labelsList = getAllLabels(examples);
        return getEntropy(labelsList);
    }

    private double getEntropy(ArrayList<Boolean> values) {
        ArrayList<Integer> valsCount = getPosNegCount(values);
        int posValCount = valsCount.get(0);
        int negValCount = valsCount.get(1);
        int totalCount = posValCount + negValCount;

        double posProb = (double) posValCount / totalCount;
        double negProb = (double) negValCount / totalCount;

        return (-1.0 * posProb) * log2(posProb) + (-1.0 * negProb) * log2(negProb);
    }

    private double log2(double d) {
        if (d == 0.0) {
            return 0.0;
        } else {
            return Math.log(d) / Math.log(2);
        }
    }

    private ArrayList<Example> getSublistForAttribValue(ArrayList<Example> examples, int attribID, boolean value) {
        ArrayList<Example> examplesSublist = new ArrayList<>();
        for (Example e : examples) {
            if(e.getValForAttribute(attribID) == value) {
                examplesSublist.add(e);
            }
        }

        return examplesSublist;
    }

    private ArrayList<Integer> getPosNegCount(ArrayList<Boolean> boolsList) {
        int posCount = 0;
        int negCount = 0;
        for (boolean b : boolsList) {
            if (b) {
                posCount++;
            } else {
                negCount++;
            }
        }

        ArrayList<Integer> countsPosNeg = new ArrayList<>();
        countsPosNeg.add(posCount);
        countsPosNeg.add(negCount);

        return countsPosNeg;
    }

    private ArrayList<Boolean> getValsForAttrib(ArrayList<Example> examples, int attribID) {
        ArrayList<Boolean> valsList = new ArrayList<>();
        for (Example e: examples) {
            valsList.add(e.getValForAttribute(attribID));
        }

        return valsList;
    }

    private boolean allValuesSame(ArrayList<Boolean> arrayList) {
        boolean val0 = arrayList.get(0);
        for (boolean v : arrayList) {
            if (v != val0) { return false; }
        }

        return true;
    }

    private ArrayList<Boolean> getAllLabels(ArrayList<Example> examples) {
        ArrayList<Boolean> labelsList = new ArrayList<>();

        for (Example e : examples) {
            labelsList.add(e.isLabel());
        }

        return labelsList;
    }

    public static void main(String[] args) {
        FileIO fileIO = new FileIO();

        // TODO: Check args and get filename
        ArrayList<Example> examples = fileIO.readFile("./data/xor.txt");
        BinaryDTBuilder bdtb = new BinaryDTBuilder(examples);
        ArrayList<Node> tree = bdtb.buildAndGetTree();

        System.out.println("//////// Final Tree");
        System.out.println();

        for (Node n : tree) {
            System.out.print(n.toString());
        }
    }
}
