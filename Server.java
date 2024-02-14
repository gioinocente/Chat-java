package repo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    
    static ServerSocket serverSocket;
    static List<messageHandler> handlers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(8084);
        System.out.println("Server is running...");
        while (true) {
            try {
               Socket socket = serverSocket.accept();
               messageHandler handler = new messageHandler(socket);
               handlers.add(handler);
               handler.start();
               System.out.println("New connection: " + socket.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                return;
            }
        }
    }

    public static void broadcastMessage(String message) {
        for (messageHandler handler : handlers) {
            handler.sendMessage(message);
        }
    }
}