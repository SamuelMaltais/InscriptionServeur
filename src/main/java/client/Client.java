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
    private String IP = "127.0.0.1";
    private int PORT = 1337;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    Scanner scanner = new Scanner(System.in);

    public void run(){
        System.out.println("***Bienvenue au portail d'inscription de cours de l'UDEM***");
        displayClasses();
    }

    public void displayClasses(){
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:" + "\n" +
                "1. Automne" + "\n" + "2.Hiver" + "\n" + "3. Ete" + "\n" + ">Choix: ");
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Automne");
        map.put(2, "Hiver");
        map.put(3, "Ete");
        int choice = scanner.nextInt();
        if (map.containsKey(choice)) {
            String session = map.get(choice);
            ArrayList<Course> courses = getCourse(session);
            displayCourses(session, courses);
        }
        else {
            //user input is invalid -> reset
            System.out.println(String.valueOf(choice) + " n'est pas une option valide");
            displayClasses();
        }


    }

    public void displayCourses(String session, ArrayList<Course> courses) {
        System.out.println("Les cours offerts pour la session d'" + session + " sont:" );
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            String display = course.getCode() + "\t" + course.getName();
            System.out.println(String.valueOf(i + 1) + '.' + display);
        }
        System.out.println("\n>Choix: " + "\n" +
                "1.Consulter les cours offerts pour une autre session" + "\n" +
                "2.Inscription a un cours");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                displayClasses();
                return;
            case 2:
                makeNewRegistration(courses);
                return;
            default:
                System.out.println(String.valueOf(choice) + " n'est pas une option valide");
                displayCourses(session, courses);
        }
    }

    public void makeNewRegistration(ArrayList<Course> courses){
        scanner.nextLine();
        System.out.println("Veuillez saisir votre prenom: ");
        String prenom = scanner.nextLine();
        System.out.println("Veuillez saisir votre nom: ");
        String nom = scanner.nextLine();
        System.out.println("Veuillez saisir votre email: ");
        String email = scanner.nextLine();
        System.out.println("Veuillez saisir votre matricule: ");
        String matricule = scanner.nextLine();
        String codeCours = getCode();

        Course course = findCourse(codeCours, courses);
        while (course == null){
            System.out.println(codeCours + " n'est pas un code de cours valide");
            codeCours = getCode();
            course = findCourse(codeCours, courses);
        }
        RegistrationForm registrationForm = new RegistrationForm(prenom, nom, email, matricule, course);
        registerRequest(registrationForm);
        System.out.println("Felicitation! Inscription reussie de " + prenom + " au cours " + codeCours + ".");
    }

    public String getCode(){
        System.out.println("Veuillez saisir le code du cours: ");
        String codeCours = scanner.nextLine();
        return codeCours;
    }
    public Course findCourse(String codeCours, ArrayList<Course> courses){
        for (int i = 0; i < courses.size(); i++) {
            Course matchCourse = courses.get(i);
            System.out.println(matchCourse.getName() + "   " + codeCours);
            if (matchCourse.getCode().equals(codeCours)){
                return matchCourse;
            }
        }
        return null;
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
