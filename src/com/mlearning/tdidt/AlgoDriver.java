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

    public ArrayList<Node> solveAndGetTree() {
        ArrayList<Integer> attribs = new ArrayList<Integer>();
        for (int a = 0; a < values.get(0).size() - 1) {
            attribs.add(a);
        }

        this.tree = TDIDT(this.values, attribs);

        return this.tree;
    }

    private ArrayList<Node> TDIDT(ArrayList<ArrayList<Integer>> examples, ArrayList<Integer> attribs) {

        ArrayList<Node> tree = new ArrayList<Node>();
        Node rootNode = new Node();
        rootNode.setId(0);

        tree = TDIDT__(examples, attribs, rootNode, tree);

        return tree;
    }

    private ArrayList<Node> TDIDT__(ArrayList<ArrayList<Integer>> S, ArrayList<Integer> attribsToCheck,
                                     Node nodeToCheckFor, ArrayList<Node> tree) {
        // TODO: raise error if S is empty, shouldn't happen BTW

        // Check if all classes are the same
        ArrayList<Integer> classesList = getAllValuesFori(S, 0);

        if (allValuesSame(classesList)) {
            nodeToCheckFor.setLeafNode(true);
            nodeToCheckFor.setLeafClass(classesList.get(0));
            tree.add(nodeToCheckFor);
            return tree;
        }

        // Check if any attribute splits data
        boolean splitAvailable = false;
        for (int a = 0; a < attribsToCheck.size(); a++) {
            int attribID = attribsToCheck.get(a);
            if (!allValuesSame(getAllValuesFori(S, attribID))) {
                splitAvailable = true;
                break;
            }
        }

        if (!splitAvailable) {
            nodeToCheckFor.setLeafNode(true);
            int poscount = 0;
            int negcount = 0;
            for (int i = 0; i < S.size(); i++) {
                if (S.get(i) == 1) {
                    poscount++;
                } else {
                    negcount++;
                }
            }

            if poscount >= negcount {
                nodeToCheckFor.setLeafClass(1);
            } else {
                nodeToCheckFor.setLeafClass(0);
            }

            tree.add(nodeToCheckFor);
            return tree;
        }

        // select the best test for the node based on information gain for attrib
        int bestAttribID = getAttribWithMaxInfoGain(S, attribsToCheck);

        ArrayList<Integer> newAttribsToCheck = new ArrayList<Integer>();
        for (int a = 0; a < attribsToCheck.size(); a++) {
            if (attribsToCheck.get(a) != bestAttribID) {
                newAttribsToCheck.add(attribsToCheck.get(a))
            }
        }

        int currMaxNodeID = nodeToCheckFor.getId();
        nodeToCheckFor.setLeafNode(false);

        ArrayList<Node> childNodes = new ArrayList<Node>();
        for (int v = 0; v < 2; v++) {  // binary attrib
            ArrayList<ArrayList<Integer>> subList = getSublistForAttribValue(S, bestAttribID, v);
            if (subList.size() > 0) {  // I have no Idea why I am checking this but it may break things
                Node childNode = new Node();
                currMaxNodeID++;
                childNode.setId(currMaxNodeID);
                childNode.setParentsTest(v == 1);
                childNode.setTestAttrib(bestAttribID);

                nodeToCheckFor.setChildForValue(childNode.getId(), childNode.isParentsTest());
                childNodes.add(childNode);
            }

        }

        for (int c = 0; c < childNodes.size(); c++) {  // ;p
            TDIDT__(subList, newAttribsToCheck, childNodes.get(c), tree);
        }

        tree.add(nodeToCheckFor);

        // append the node to the tree before returning
        return tree;
    }

    private ArrayList<ArrayList<Integer>> getSublistForAttribValue(ArrayList<ArrayList<Integer>> S, int attrID, int value) {
        ArrayList<ArrayList<Integer>> subList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < S.size(); i++) {
            if (S.get(i).get(attrID) == value) {
                subList.add(S.get(i));
            }
        }

        return subList;
    }

    private int getAttribWithMaxInfoGain(ArrayList<ArrayList<Integer>> S, ArraList<Integer> attribsToCheck) {
        int bestAttrib;
        double bestInfoGain = 0.0;

        int value = 1;  // work around
        for (int a = 0; a < attribsToCheck.size(); a++) {
            double infoGain = getInformationGain(S, attribsToCheck.get(a), value);
            if (infoGain > bestInfoGain) {
                bestInfoGain = infoGain;
                bestAttrib = attribsToCheck.get(a);
            } else if (bestInfoGain + infoGain == 0.0) {  // info gain always >= 0
                bestInfoGain = infoGain;
                bestAttrib = attribsToCheck.get(a);
            }
        }

        return bestAttrib;
    }

    private ArrayList<Integer> getAllValuesFori(ArrayList<ArrayList<Integer>> S, int i) {
        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int j = 0; j < S.size(); j++) {
            values.add(S.get(j).get(i));
        }

        return values
    }

    private boolean allValuesSame(ArrayList<Integer> L) {
        int val0 = L.get(0);
        for (int j = 1; j < L.size(); j++) {
            if (L.get(j) != val0) {
                return false;
            }
        }

        return true;
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
    // Also, value does not need to be passed for boolean attribs
    //      working around for now
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
        ArrayList<Node> solutionTree = driver.solveAndGetTree();

        for(int n = 0; n < solutionTree.size(); n++) {
            // print solutionTree.get(n).toString()
        }
    }
}