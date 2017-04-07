package com.example.swarmdebugger;

/**
 * Created by Alex on 01/04/2017.
 */
public class DebugInfo {
    String name;
    int val;
    boolean displayAsText = true;

    public DebugInfo(String name) {
        this.name = name;
    }

    public DebugInfo(String name, int val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
