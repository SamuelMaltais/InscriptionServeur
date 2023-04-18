package client.JavaFxGui;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * La classe Client représente une application cliente qui communique avec un
 * serveur via un réseau en utilisant des sockets.
 * Elle fournit des méthodes pour se connecter et se déconnecter du serveur,
 * obtenir les cours pour une session donnée, et envoyer des demandes
 * d'inscription au serveur.
 */
public class Client {
    private Socket clientSocket;
    private String IP = "127.0.0.1";
    private int PORT = 1337;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Se connecte au serveur.
     *
     * @throws IOException            si une erreur d'E/S se produit lors de la
     *                                création du socket ou des flux.
     * @throws ClassNotFoundException si la classe de l'objet sérialisé reçu du
     *                                serveur ne peut pas être trouvée.
     */
    public void connect() throws IOException {
        clientSocket = new Socket(IP, PORT);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Se déconnecte du serveur.
     *
     * @throws IOException si une erreur d'E/S se produit lors de la fermeture du
     *                     socket ou des flux.
     */
    public void disconnect() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }

    /**
     * Obtient une liste de cours pour une session donnée à partir du serveur.
     *
     * @param session la session pour laquelle récupérer les cours.
     * @return une ArrayList d'objets Course représentant les cours disponibles pour
     *         la session donnée.
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
     * Envoie un formulaire d'inscription au serveur pour traitement.
     *
     * @param form le formulaire d'inscription à envoyer.
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
