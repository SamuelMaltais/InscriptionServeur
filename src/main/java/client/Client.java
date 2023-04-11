package client;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket clientSocket;
    private String IP;;
    private int PORT;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private BufferedWriter writer;
    //constructor called by ClientLauncher
    public Client(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

    //establish connection to server
    public void connect() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(IP, PORT);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        OutputStreamWriter os = new OutputStreamWriter(
                clientSocket.getOutputStream()
        );
        writer = new BufferedWriter(os);

    }

    public void disconnect() throws IOException {
        clientSocket.close();
        System.out.println("dc");
    }

    public void test() throws IOException {
        //var ois = new ObjectInputStream(clientSocket.getInputStream());
//        var ous = new ObjectOutputStream(clientSocket.getOutputStream());
//        ous.writeObject("INSCRIRE");
//        ous.flush();
//        ous.close();
    }
    /*- F1: une première fonctionnalité qui permet au client de récupérer la liste des 
    cours disponibles pour une session donnée. Le client envoie une requête charger
    au serveur. Le serveur doit récupérer la liste des cours du fichier cours.txt et 
    l’envoie au client. Le client récupère les cours et les affiche.*/
    //"REGISTER"
    public ArrayList<Course> getCourse(String session){
        System.out.println("Requesting for classes");
        ArrayList<Course> courses = null;
        try {
            out.writeObject("CHARGER " + session);
            out.flush();
            courses = (ArrayList) in.readObject();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return courses;
    }

    /*- F2: une deuxième fonctionnalité qui permet au client de faire une demande 
    d’inscription à un cours. Le client envoie une requête inscription au serveur. Les 
    informations suivantes sont données nécessaires (voir le format du fichier 
    inscription.txt ci-dessus) en arguments. Le choix du cours doit être valide c.à.d le 
    code du cours doit être présent dans la liste des cours disponibles dans la session en 
    question. Le serveur ajoute la ligne correspondante au fichier inscription.txt et 
    envoie un message de réussite au client. Le client affiche ce message (ou celui de 
    l’échec en cas d’exception). */
    public void registerRequest(RegistrationForm form){


    }
    

}
