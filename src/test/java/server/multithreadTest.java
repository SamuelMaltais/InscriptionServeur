package server;

import client.JavaFxGui.Client;

public class multithreadTest {
    public void main(String args[]) {
        try {
            Client client = new Client();
            client.connect();
            Client client2 = new Client();
            client2.connect();
        } catch (Exception e) {

        }
    }
}
