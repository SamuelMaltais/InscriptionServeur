package client.JavaFxGui;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The Client class represents a client application that communicates with a server over a network using sockets.
 * It provides methods to connect and disconnect from the server, get courses for a given session, and send registration
 * requests to the server.
 */
public class Client {
    private Socket clientSocket;
    private String IP = "127.0.0.1";
    private int PORT = 1337;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Connects to the server
     *
     * @throws IOException            if an I/O error occurs when creating the socket or streams.
     * @throws ClassNotFoundException if the class of the serialized object received from the server cannot be found.
     */
    public void connect() throws IOException {
        clientSocket = new Socket(IP, PORT);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Disconnects from the server
     *
     * @throws IOException if an I/O error occurs when closing the socket or streams.
     */
    public void disconnect() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }

    /**
     * Gets a list of courses for a given session from the server.
     *
     * @param session the session for which to retrieve courses.
     * @return an ArrayList of Course objects representing the courses available for the given session.
     */
    public ArrayList<Course> getCourse(String session) {
        ArrayList<Course> courses = null;
        // Traite l'exeption de l'accent
        if (session.equals("Été")) {
            session = "Ete";
        }
        try {
            this.connect();
            out.writeObject("CHARGER " + session);
            out.flush();
            courses = (ArrayList) in.readObject();
            this.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Sends a registration form to the server to be processed.
     *
     * @param form the registration form to be sent.
     */
    public void registerRequest(RegistrationForm form) {
        try {
            this.connect();
            out.writeObject("INSCRIRE");
            out.flush();
            out.writeObject(form);
            out.flush();
            this.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
