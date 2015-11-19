package com.mlearning.tdidt;

/**
 * Created by andyfaizan on 17/11/15.
 */
public class Node {

    private int id;
    private boolean parentsTest;
    private int testAttrib;
    private int leftChild;
    private int rightChild;
    private boolean isLeafNode;
    private int leafClass;

    @Override
    public String toString() {
        String pTest = "no";
        if(parentsTest){
            pTest = "yes";
        }
        return id + " " + pTest + " " + testAttrib +
                " " + leftChild + " " + rightChild + "/r/n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isParentsTest() {
        return parentsTest;
    }

    public void setParentsTest(boolean parentsTest) {
        this.parentsTest = parentsTest;
    }

    public int getTestAttrib() {
        return testAttrib;
    }

    public void setTestAttrib(int testAttrib) {
        this.testAttrib = testAttrib;
    }

    public int getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(int leftChild) {
        this.leftChild = leftChild;
    }

    public int getRightChild() {
        return rightChild;
    }

    public void setRightChild(int rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isLeafNode() {
        return isLeafNode;
    }

    public void setLeafNode(boolean leafNode) {
        isLeafNode = leafNode;
    }

    public int getLeafClass() {
        // TODO: throw proper error if not a leaf node
        return leafClass;
    }

    public void setLeafClass(int leafClass) {
        // TODO: throw proper error if not a leaf node
        this.leafClass = leafClass;
    }
}
