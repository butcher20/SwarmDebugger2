//package com.example.swarmdebugger;
//
//import android.os.AsyncTask;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.io.StringReader;
//import java.net.DatagramPacket;
//import java.util.List;
//
///**
// * Created by Alex on 06/04/2017.
// */
//
//public class RobotPacketReader extends AsyncTask<String, void, Robot> {
//    DatagramPacket packet;
//    Reader reader;
//    StringBuilder builder;
//    Robot robot;
//    Boolean newRobot = false;
//    int index, c;
//
//    OnPacketProcessedListener listener;
//
//    @Override
//    protected Robot doInBackground(String... params) {
//
//        reader = new StringReader(params[0]);
//        builder = new StringBuilder();
//
//        try {
//            c = reader.read();
//
//            // Get robot ID
//            while (c != '#') {
//                builder.append((char) c);
//                c = reader.read();
//            }
//            int id = Integer.parseInt(builder.toString());
//
//            // Check if robot already exists in robot list.
//            if ((index = MainActivity.getRobotIndexFromId(id)) == -1 ) {
//                // no matching robot in list so create a new Robot object
//                robot = new Robot(id);
//                newRobot = true;
//            }
//
//            //Process the remaing debug information
//            builder.setLength(0);
//            c = reader.read();
//            //continue until end of string
//            while (c != -1) {
//                // Get item name.
//                while (c != ':') {
//                    builder.append((char) c);
//                    c = reader.read();
//                }
//                //Check if extracted element was the status
//                if (builder.toString().equals("status")) {
//                    readData();
//                    robot.setStatus(builder.toString());
//                } else if (builder.toString().equals("xPos")) {
//                    readData();
//                    robot.setPosX(Integer.parseInt(builder.toString()));
//                } else if (builder.toString().equals("yPos")) {
//                    readData();
//                    robot.setPosY(Integer.parseInt(builder.toString()));
//                } else {
//                    //Custom debug info
//                    // Name of the custom debug info
//                    String name = builder.toString();
//                    //Read the value of the custom debug info
//                    readData();
//                    // Check if DebugInfo object already exists for received debug info.
//                    int i;
//                    for (i = 0; i < robot.getDebugInfo().size(); i++) {
//                        if (robot.getDebugInfo().get(i).getName().equals(name)) {
//                            // Debug info exists so update value
//                            robot.getDebugInfo().get(i).setVal(Integer.parseInt(builder.toString()));
//                            break;
//                        }
//                    }
//                    // No existing debug info
//                    if (i == robot.getDebugInfo().size()) {
//                        // Create a new DebugInfo object with the extracted name
//                        DebugInfo debugInfo = new DebugInfo(name);
//                        // Set the value in the DebugInfo object
//                        debugInfo.setVal(Integer.parseInt(builder.toString()));
//                        //Add the debuginfo to the list in the robot object
//                        robot.getDebugInfo().add(debugInfo);
//                    }
//                }
//                builder.setLength(0);
//                c = reader.read();
//            }
//            // Entire string processed so either update a robot in the robotlist or add it to the list.
//            if (newRobot) {
//                // New robot so add to the end of list
//                MainActivity.robotList.add(robot);
//            } else {
//                // Existing robot so update robot information in list
//                MainActivity.robotList.set(index, robot);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return robot;
//    }
//
//    protected void onPostExecute(Long result) {
//        ;
//    }
//
//
//    public interface OnPacketProcessedListener {
//        public void onPacketProcessed(Robot robot);
//    }
//
//    public RobotPacketReader(DatagramPacket packet, OnPacketProcessedListener listener) {
//        this.packet = packet;
//        this.listener = listener;
//    }
//
//    public void processPacket() throws IOException {
//
//        //Parse string from the packet
//        String received = new String(packet.getData(), 0, packet.getLength());
//
//        reader = new StringReader(received);
//        builder = new StringBuilder();
//
//
//        c = reader.read();
//
//        // Get robot ID
//        while (c != '#') {
//            builder.append((char) c);
//            c = reader.read();
//        }
//        int id = Integer.parseInt(builder.toString());
//
//        // Check if robot already exists in robot list.
//        for (index = 0; index < MainActivity.robotList.size(); index++) {
//            if (MainActivity.robotList.get(index).getId() == id) {
//                robot = MainActivity.robotList.get(index);
//                break;
//            }
//        }
//        //If no robot with ID exists in list create a new robot object.
//        if (robot == null) {
//            robot = new Robot(id);
//            newRobot = true;
//        }
//
//        //Process the remaing debug information
//        builder.setLength(0);
//        c = reader.read();
//        //continue until end of string
//        while (c != -1) {
//            // Get current element.
//            while (c != ':') {
//                builder.append((char) c);
//                c = reader.read();
//            }
//            //Check if extracted element was the status
//            if (builder.toString().equals("status")) {
//                readData();
//                robot.setStatus(builder.toString());
//            } else if (builder.toString().equals("xPos")) {
//                readData();
//                robot.setPosX(Integer.parseInt(builder.toString()));
//            } else if (builder.toString().equals("yPos")) {
//                readData();
//                robot.setPosY(Integer.parseInt(builder.toString()));
//            } else {
//                String name = builder.toString();
//                readData();
//                // Check if DebugInfo object already exists for received debug info.
//                int i;
//                for (i = 0; i < robot.getDebugInfo().size(); i++) {
//                    if (robot.getDebugInfo().get(i).getName().equals(name)) {
//                        // Debug info exists so update value
//                        robot.getDebugInfo().get(i).setVal(Integer.parseInt(builder.toString()));
//                        break;
//                    }
//                }
//                // No existing debug info
//                if (i == robot.getDebugInfo().size()) {
//                    // Create a new DebugInfo object with the extracted name
//                    DebugInfo debugInfo = new DebugInfo(name);
//                    // Set the value in the DebugInfo object
//                    debugInfo.setVal(Integer.parseInt(builder.toString()));
//                    //Add the debuginfo to the list in the robot object
//                    robot.getDebugInfo().add(debugInfo);
//                }
//            }
//            builder.setLength(0);
//            c = reader.read();
//        }
//        if (newRobot) {
//            // New robot so add to the end of list
//            MainActivity.robotList.add(robot);
//        } else {
//            // Existing robot so update robot information in list
//            MainActivity.robotList.set(index, robot);
//        }
//        listener.onPacketProcessed(robot);
//    }
//
//
//    private void readData() throws IOException {
//        builder.setLength(0);
//        c = reader.read();
//        while (c != '#') {
//            builder.append((char) c);
//            c = reader.read();
//        }
//    }
//}
