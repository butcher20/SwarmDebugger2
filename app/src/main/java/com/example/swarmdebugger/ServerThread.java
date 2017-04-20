package com.example.swarmdebugger;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Alex on 06/04/2017.
 */

public class ServerThread extends Thread {
    private DatagramSocket socket;
    private int port;

    private boolean running = true;

    OnPacketReceivedListener listener;

    public interface OnPacketReceivedListener {
        public void onPacketReceived(String data);
    }


    public ServerThread(int port, OnPacketReceivedListener listener) {
        super();
        this.port = port;
        this.listener = listener;
    }

    public void run() {

        try {
            Log.d("server", "Creating socket with port" + port);
            socket = new DatagramSocket(port);
            while (running) {
                byte[] buf = new byte[256];

                // Receive Packet
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                Log.d("server", "packet received");

                //Extract Data
                String packetData = new String(packet.getData(), 0, packet.getLength());

                // Process received data
                listener.onPacketReceived(packetData);
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
