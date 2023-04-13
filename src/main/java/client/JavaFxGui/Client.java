package client.JavaFxGui;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket clientSocket;
    private String IP = "127.0.0.1";
    private int PORT = 1337;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public void connect() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(IP, PORT);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void disconnect() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }

    public ArrayList<Course> getCourse(String session){
        ArrayList<Course> courses = null;
        // Traite l'exeption de l'accent
        if(session.equals("Été")){
            session = "Ete";
        }
        try {
            this.connect();
            out.writeObject("CHARGER " + session);
            out.flush();
            courses = (ArrayList) in.readObject();
            this.disconnect();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }

    public void registerRequest(RegistrationForm form){
        try {
            this.connect();
            out.writeObject("INSCRIRE");
            out.flush();
            out.writeObject(form);
            out.flush();
            this.disconnect();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
