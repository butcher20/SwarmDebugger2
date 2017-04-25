package com.example.swarmdebugger;

/**
 * Created by Alex on 01/04/2017.
 */
public class DebugInfo {
    String name;
    int val;
    boolean displayAsText = false;
    boolean canBeDisplayedAsGraphic = false;
    boolean displayAsGraphic = false;

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

    public boolean isDisplayAsText() {
        return displayAsText;
    }

    public void setDisplayAsText(boolean displayAsText) {
        this.displayAsText = displayAsText;
    }

    public boolean isDisplayAsGraphic() {
        return displayAsGraphic;
    }

    public void setDisplayAsGraphic(boolean displayAsGraphic) {
        this.displayAsGraphic = displayAsGraphic;
    }

    public boolean isCanBeDisplayedAsGraphic() {
        return canBeDisplayedAsGraphic;
    }

    public void setCanBeDisplayedAsGraphic(boolean canBeDisplayedAsGraphic) {
        this.canBeDisplayedAsGraphic = canBeDisplayedAsGraphic;
    }
}
