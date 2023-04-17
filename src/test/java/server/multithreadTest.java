package server;

import client.JavaFxGui.Client;
import org.junit.Test;

public class multithreadTest {
    @Test
    public void test() {
        try {
            Client client = new Client();
            Client client2 = new Client();
            client2.connect();client.connect();
            client2.getCourse("Automne"); client.getCourse("Hiver");

        } catch (Exception e) {

        }
    }
}
