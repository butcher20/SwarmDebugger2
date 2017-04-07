package com.example.swarmdebugger;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.DatagramPacket;

/**
 * Created by Alex on 06/04/2017.
 */

public class RobotPacketReader {
    DatagramPacket packet;
    Reader reader;
    StringBuilder builder;
    Robot robot;
    Boolean newRobot = false;
    int index, c;

    OnPacketProcessedListener listener;

    public interface OnPacketProcessedListener {
        public void onPacketProcessed(Robot robot);
    }

    public RobotPacketReader (DatagramPacket packet, OnPacketProcessedListener listener) {
        this.packet = packet;
        this.listener = listener;
    }

    public void processPacket() throws IOException {

        //Parse string from the packet
        String received = new String(packet.getData(), 0, packet.getLength());

        reader = new StringReader(received);
        builder = new StringBuilder();


        c = reader.read();

        // Get robot ID
        while (c != '#') {
            builder.append(c);
            c = reader.read();
        }
        int id = Integer.parseInt(builder.toString());

        // Check if robot already exists in robot list.
        for (index = 0; index < MainActivity.robotList.size(); index++) {
            if (MainActivity.robotList.get(index).getId() == id) {
                robot = MainActivity.robotList.get(index);
                break;
            }
        }
        //If no robot with ID exists in list create a new robot object.
        if (robot == null) {
            robot = new Robot(id);
            newRobot = true;
        }

        //Process the remaing debug information
        builder.setLength(0);
        c = reader.read();
        //continue until end of string
        while (c != -1) {
            // Get current element.
            while (c != ':') {
                builder.append(c);
                c = reader.read();
            }
            //Check if extracted element was the status
            if (builder.toString().equals("status")) {
                readData();
                robot.setStatus(builder.toString());
            } else if (builder.toString().equals("xPos")) {
                readData();
                robot.setPosX(Integer.parseInt(builder.toString()));
            } else if (builder.toString().equals("yPos")) {
                readData();
                robot.setPosY(Integer.parseInt(builder.toString()));
            } else {
                // Create a new DebugInfo object with the extracted name
                DebugInfo debugInfo = new DebugInfo(builder.toString());
                readData();
                // Set the value in the DebugInfo object
                debugInfo.setVal(Integer.parseInt(builder.toString()));
                //Add the debuginfo to the list in the robot object
                robot.getDebugInfo().add(debugInfo);
            }
            builder.setLength(0);
            c = reader.read();
        }
        if (newRobot) {
            // New robot so add to the end of list
            MainActivity.robotList.add(robot);
        } else {
            // Existing robot so update robot information in list
            MainActivity.robotList.set(index, robot);
        }
    }


    private void readData() throws IOException {
        builder.setLength(0);
        c = reader.read();
        while (c != '#') {
            builder.append(c);
            c = reader.read();
        }
    }
}
