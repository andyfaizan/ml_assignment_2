package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
class Tree {
    ArrayList<Node> nodes;

    public Tree() {
        this.nodes = new ArrayList<>();
    }

    public void add(Node node){
        this.nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public String toString() {
        String s = "";
        for (Node n : this.nodes) {
            s += n.toString();
        }
        return s;
    }

    public Node getRootNode() {
        return getNodeByID(0);
    }

    public Node getNodeByID(int id) {
        for (Node n : this.nodes) {
            if (n.getId() == id) {
                return n;
            }
        }
        // FIXME: What if the node with ID does not exist
        return new Node();
    }

    public boolean predictLabel(ArrayList<Boolean> attributes) {
        Node currentNode = getRootNode();
        while (!currentNode.isLeafNode()) {
            int attributeToCheck = currentNode.getTestAttrib();
            currentNode = this.getNodeByID(currentNode.getChildForAttributeValue(attributes.get(attributeToCheck)));
        }
        return currentNode.getLeafClass();
    }
}
