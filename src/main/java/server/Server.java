package server;

import java.io.*;
import java.net.*;
// MULTITHREAD DEMO DANS JAVADOC FOLDER
class Server {
    private static int port;

    public Server(int port) {
        this.port = port;
    }

    public static void run() {
        ServerSocket server = null;

        try {
            //On initie le serveur
            server = new ServerSocket(port);
            server.setReuseAddress(true);
            //Loop qui recupere tous les nouvelles requetes
            while (true) {

                Socket client = server.accept();
                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}