package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ClientLauncher {
    public final static String IP = "127.0.0.1";
    public final static int PORT = 1337;

    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("Sss");
        JButton button = new JButton("click");
        button.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Client client;
                client = new Client(IP, PORT);
                try {
                    client.connect();
                    client.test();
                    System.out.println("client is connected...");
                    client.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(button);
        frame.show();

    }
    
}
