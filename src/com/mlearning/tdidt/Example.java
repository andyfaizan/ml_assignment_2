package com.mlearning.tdidt;

import java.util.ArrayList;

/**
 * Created by @motjuste on 19/11/15.
 *
 */
public class Example {
    private boolean label;
    private ArrayList<Boolean> attributes;

    public Example(boolean label, ArrayList<Boolean> attributes) {
        this.label = label;
        this.attributes = attributes;
    }

    public boolean isLabel() {
        return label;
    }

    public ArrayList<Boolean> getAttributes() {
        return attributes;
    }
}
