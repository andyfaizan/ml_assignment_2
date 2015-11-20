package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by @motjuste on 20/11/15.
 *
 */
class Examples {
    private ArrayList<Example> examples;

    public Examples(ArrayList<Example> examples) {
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
}
