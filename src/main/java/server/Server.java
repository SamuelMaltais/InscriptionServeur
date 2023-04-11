package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe pour le serveur qui gere les connexions clients et qui traite les commandes reçues.
 */
public class Server {

    /**
     * Ligne commande inscription.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";

    /**
     * Ligne commande chargement des cours.
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Constructeur qui initialise le socket serveur et la liste des gestionnaires d'événements.
     *
     * @param port numéro de port sur lequel le serveur doit écouter les connexions de clients.
     * @throws IOException si une erreur se produit lors de la création du socket serveur.
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute un gestionnaire d'événements à la liste des gestionnaires.
     *
     * @param h le gestionnaire d'événements à ajouter.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Alerte les gestionnaires d'événements lors de la réception d'une commande.
     *
     * @param cmd la commande reçue.
     * @param arg l'argument associé à la commande.
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Démarre le serveur pour écouter les connexions de clients et traiter leurs commandes.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lit la commande envoyée par le client, la traite et alerte les gestionnaires d'événements.
     *
     * @throws IOException si une erreur se produit lors de la lecture ou de l'écriture dans les flux.
     * @throws ClassNotFoundException si la classe de l'objet lu n'est pas trouvée.
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * prend une chaîne de caractères et extrait la commande et ses arguments.
     *
     * @param line la chaîne de caractères à traiter pour extraire la commande et ses arguments
     * @return une paire de chaînes de caractères représentant la commande et ses arguments respectivement
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * ferme les flux de sortie et d'entrée et le socket associé à la connexion client.
     *
     * @throws IOException si une erreur se produit lors de la fermeture des flux de sortie et d'entrée ou du socket de connexion
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * détermine quelle action doit être exécutée en conséquence a la commande recue.
     *
     * @param cmd la commande à exécuter
     * @param arg les arguments associés à la commande
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Lire un fichier texte contenant des informations sur les cours et les
     * transofmer en liste d'objets 'Course'.
     * La méthode filtre les cours par la session spécifiée en argument.
     * Ensuite, elle renvoie la liste des cours pour une session au client en
     * utilisant l'objet 'objectOutputStream'.
     * La méthode gère les exceptions si une erreur se produit lors de la lecture du
     * fichier ou de l'écriture de l'objet dans le flux.
     * 
     * @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        ArrayList<Course> cours = new ArrayList<Course>();
        try {
            File myObj = new File("/data/cours.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] strArr = data.split(" ");
                cours.add(new Course(strArr[0], strArr[1], strArr[2]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant
     * 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     * La méthode gére les exceptions si une erreur se produit lors de la lecture de
     * l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            //read RegistrationForm object input 
            RegistrationForm rc = (RegistrationForm) objectInputStream.readObject();
            //output registration form to file
            FileWriter fw = new FileWriter("/data/inscription.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            //Format: session, code_cours, matricule, prenom, nom, email
            String registrationInfo = (rc.getCourse().getSession() + "\t" + rc.getCourse().getCode() + 
            "\t" + rc.getMatricule() + "\t" + rc.getPrenom() + "\t" + rc.getNom() + "\t" + rc.getEmail() + "\n");
            writer.append(registrationInfo);
            writer.close();

            //send confirmation message
            OutputStream outputStream = client.getOutputStream();
            String message = "inscription confirmee";
            outputStream.write(message.getBytes());

        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        } catch (ClassCastException e) {
            //if format is invalid (cannot cast to RegistrationForm)
            System.out.println("Invalid input");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
