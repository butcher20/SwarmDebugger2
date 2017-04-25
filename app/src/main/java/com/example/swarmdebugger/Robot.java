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
    boolean showPosX = false;
    private int posY;
    boolean showPosY = false;
    private float orientation;
    private List<DebugInfo> infraRedSensor = new ArrayList<DebugInfo>();
    private List<DebugInfo> debugInfo = new ArrayList<DebugInfo>();

    public Robot(int id) {
        this.id = id;
    }

    public Robot(int id, String status, int posX, int posY, float orientation) {
        this.id = id;
        this.status = status;
        this.posX = posX;
        this.posY = posY;
        this.orientation = orientation;
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

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public List<DebugInfo> getInfraRedSensor() {
        return infraRedSensor;
    }

    public void setInfraRedSensor(List<DebugInfo> infraRedSensor) {
        this.infraRedSensor = infraRedSensor;
    }

    public List<DebugInfo> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List<DebugInfo> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public boolean isShowId() {
        return showId;
    }

    public void setShowId(boolean showId) {
        this.showId = showId;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
    }

    public boolean isShowPosX() {
        return showPosX;
    }

    public void setShowPosX(boolean showPosX) {
        this.showPosX = showPosX;
    }

    public boolean isShowPosY() {
        return showPosY;
    }

    public void setShowPosY(boolean showPosY) {
        this.showPosY = showPosY;
    }
}
