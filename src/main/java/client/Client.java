package client;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Client {
    private Socket clientSocket;
    public final static String IP = "127.0.0.1";
    public final static int PORT = 1337;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    Scanner scanner = new Scanner(System.in);

    public void run(){
        System.out.println("***Bienvenue au portail d'inscription de cours de l'UDEM*** \n");
        displayClasses();    
    }

    public String displayClasses(){
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:" + "\n" +
        "1. Automne" + "\n" + "2.Hiver" + "\n" + "3. Ete" + "\n" + ">Choix: ");

        String choice = scanner.nextLine();
        switch (choice){
            case "1":
                return "Automne";
            case "2":
                return "Hiver";
            case "3":
                return "Ete";
            default:
                System.out.println("Choix invalide...");
                return displayClasses();
        }
    }

    //establish connection to server
    public void connect() throws IOException, ClassNotFoundException {
        clientSocket = new Socket(IP, PORT);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void disconnect() throws IOException {
        clientSocket.close();
        System.out.println("dc");
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
            this.connect();
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
        try {
            this.connect();
            out.writeObject("INSCRIRE");
            out.flush();
            out.writeObject(form);
            out.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    

}
