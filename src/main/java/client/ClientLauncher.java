package client;

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
    

