package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
class Examples {
    private final ArrayList<Example> examples;

    private Examples(ArrayList<Example> examples) {
        this.examples = examples;
    }

    public Examples() {
        this.examples = new ArrayList<>();
    }

    public void add(Example e) {
        this.examples.add(e);
    }

    public ArrayList<Example> getExamplesList() {
        return this.examples;
    }

    public Examples getSublistForAttribValue( int attribID, boolean value) {
        ArrayList<Example> examplesSublist = new ArrayList<>();
        for (Example e : this.examples) {
            if(e.getValForAttribute(attribID) == value) {
                examplesSublist.add(e);
            }
        }

        return new Examples(examplesSublist);
    }

    public ArrayList<Boolean> getAllLabels() {
        ArrayList<Boolean> labelsList = new ArrayList<>();

        for (Example e : this.examples) {
            labelsList.add(e.isLabel());
        }

        return labelsList;
    }

    public Example get(int i) {
        return this.examples.get(i);
    }

    public ArrayList<Boolean> getValsForAttrib(int attribID) {
        ArrayList<Boolean> valsList = new ArrayList<>();
        for (Example e: this.examples) {
            valsList.add(e.getValForAttribute(attribID));
        }

        return valsList;
    }

    public int size(){
        return this.examples.size();
    }

    public ArrayList<ArrayList<Boolean>> getAllAttributes() {
        ArrayList<ArrayList<Boolean>> attributesList = new ArrayList<>();
        for (Example e : this.examples) {
            attributesList.add(e.getAttributes());
        }

        return attributesList;
    }

    @Override
    public String toString() {
        String s = "";
        s += this.examples.get(0).getnAttributes() + "\r\n";

        for (Example e: this.examples) {
            s += e.toString() + "\r\n";
        }

        return s;
    }
}
