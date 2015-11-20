package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by @motjuste on 19/11/15.
 *
 */
class Example {
    private final boolean label;
    private final ArrayList<Boolean> attributes;
    private final int nAttributes;

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

    public ArrayList<Boolean> getAttributes() {
        return this.attributes;
    }
}
