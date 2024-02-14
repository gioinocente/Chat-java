package repo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class messageHandler extends Thread {
    private Socket socket;
    private PrintWriter out;

    public messageHandler(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String message = in.readLine();
                System.out.println(message);
                if (message == null) {
                    break;
                }
                Server.broadcastMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}