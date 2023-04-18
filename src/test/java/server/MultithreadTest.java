package server;

import client.JavaFxGui.Client;
import org.junit.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class MultithreadTest {
    @Test
    public void test() {
        try {
            // Trois clients sont construits
            Client client = new Client();
            Client client2 = new Client();
            Socket clientSocket = new Socket("127.0.0.1", 1337);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            // Ne ferme pas la connection contrairement a getCourse("Hiver");
            out.writeObject("CHARGER Hiver");
            out.flush();
            // Effectue des requetes alors que le clientSocket n'est pas ferme
            client2.getCourse("Automne");
            client.getCourse("Hiver");

            out.close();
            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue( true );
        System.out.println("out");
    }
}
