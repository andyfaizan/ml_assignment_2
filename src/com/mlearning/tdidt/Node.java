package com.mlearning.tdidt;

/**
 * Created by andyfaizan on 17/11/15.
 *
 */
class Node {

    private int id;
    private boolean parentsTest;
    private int testAttrib;
    private int leftChild;
    private int rightChild;
    private boolean isLeafNode = false;
    private boolean leafClass;
    private boolean isRoot = false;

    @Override
    public String toString() {
        String pTest = "no";
        if(parentsTest){
            pTest = "yes";
        }
        return id + " " + pTest + " " + testAttrib +
                " " + leftChild + " " + rightChild + "\r\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentsTest(boolean parentsTest) {
        this.parentsTest = parentsTest;
    }

    public void setTestAttrib(int testAttrib) {
        this.testAttrib = testAttrib;
    }

    private void setLeftChild(int leftChild) {
        this.leftChild = leftChild;
    }

    private void setRightChild(int rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeafClass(boolean leafClass) {
        // TODO: throw proper error if not a leaf node
        this.leafClass = leafClass;
    }

    public void setChildForValue(int childId, boolean test) {
        if (test) {
            setRightChild(childId);
        } else {
            setLeftChild(childId);
        }
    }

    public void setAsRoot() {
        this.isRoot = true;
    }

    public void setAsLeaf() {
        this.isLeafNode = true;
    }
}
