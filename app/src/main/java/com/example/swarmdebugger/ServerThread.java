package com.example.swarmdebugger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Alex on 06/04/2017.
 */

public class ServerThread extends Thread {
    private DatagramSocket socket;
    private int port;

    RobotPacketReader packetReader;
    private boolean running = true;

    RobotPacketReader.OnPacketProcessedListener listener;


    public ServerThread(int port, RobotPacketReader.OnPacketProcessedListener listener) {
        super();
        this.port = port;
        this.listener = listener;
    }

    public void run() {

        try {
            socket = new DatagramSocket(port);
            while (running) {
                byte[] buf = new byte[256];

                // Receive Packet
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // Process data
                packetReader = new RobotPacketReader(packet, listener);
                packetReader.processPacket();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                socket.close();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
