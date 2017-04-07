package com.example.swarmdebugger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 31/03/2017.
 */
public class Robot {

    private int id;
    boolean showId = true;
    private String status;
    boolean showStatus = true;
    private int posX;
    private int posY;
    private List<DebugInfo> debugInfo = new ArrayList<DebugInfo>();

    public Robot(int id) {
        this.id = id;
    }
    public Robot(int id, String status, int posX, int posY) {
        this.id = id;
        this.status = status;
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public List<DebugInfo> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<DebugInfo> debugInfo) {
        this.debugInfo = debugInfo;
    }
}
