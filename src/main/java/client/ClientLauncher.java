package client;

import server.models.Course;
import java.util.ArrayList;


public class ClientLauncher {

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client();
            client.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
    

