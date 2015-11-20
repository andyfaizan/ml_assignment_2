package com.mlearning.tdidt;

/**
 * Created by andyfaizan on 17/11/15.
 *
 */
class Node {

    private int id;
    private boolean parentsTest;
    private int testAttributeID;
    private int leftChild = -1;
    private int rightChild = -1;
    private boolean isLeafNode = false;
    private boolean leafClass;
    private boolean isRoot = false;

    @Override
    public String toString() {
        String s = "";
        s += id + " ";

        if(isRoot) {
            s += "- ";
        } else if(parentsTest){
            s += "yes ";
        } else {
            s += "no ";
        }

        if(isLeafNode) {
            s += "- - ";
            if (leafClass) { s+= "yes"; } else { s+= "no"; }
        } else {
            s += testAttributeID + " " ;
            if (leftChild == -1) {
                s += "!! ";
            } else {
                s += leftChild + " ";
            }
            if (rightChild == -1) {
                s += "!! ";
            } else {
                s += rightChild;
            }
        }

        return s + "\r\n";
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

    public void setTestAttributeID(int testAttributeID) {
        this.testAttributeID = testAttributeID;
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

    public boolean isLeafNode() {
        return this.isLeafNode;
    }

    public int getTestAttributeID() {
        return this.testAttributeID;
    }

    public int getChildForAttributeValue(Boolean attributeValue) {
        if (attributeValue) {
            return this.rightChild;
        } else {
            return this.leftChild;
        }
    }

    public boolean getLeafClass() {
        return this.leafClass;
    }
}
