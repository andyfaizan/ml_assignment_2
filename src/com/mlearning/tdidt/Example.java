package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by @motjuste on 19/11/15.
 *
 */
public class Example {
    private boolean label;
    private ArrayList<Boolean> attributes;
    private int nAttributes;

    public Example(boolean label, ArrayList<Boolean> attributes, int nAttributes) {
        this.label = label;
        this.attributes = attributes;
        this.nAttributes = nAttributes;
    }

    public Example(ArrayList<Boolean> labelAndAttributes, int nAttributes) {
        this.label = labelAndAttributes.get(0);
        this.nAttributes = nAttributes;
        this.attributes = new ArrayList<>();
        for (int i = 1; i < labelAndAttributes.size(); i++) {
            this.attributes.add(labelAndAttributes.get(i));
        }
   }

    public boolean isLabel() {
        return label;
    }

    public ArrayList<Boolean> getAttributes() {
        return attributes;
    }

    public int getnAttributes() {
        return nAttributes;
    }

    public boolean getValForAttribute(int a) {
        return attributes.get(a);
    }

    @Override
    public String toString() {
        String s = "";
        if (this.label) { s += "1 "; } else { s += "0 "; }
        for (boolean a : this.attributes) {
            if (a) { s += "1 "; } else { s+= "0 "; }
        }
        s += "\n";
        return s;
    }
}
