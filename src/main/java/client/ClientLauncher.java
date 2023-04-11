package client;

import server.models.Course;
import java.util.ArrayList;


public class ClientLauncher {
    public final static String IP = "127.0.0.1";
    public final static int PORT = 1337;
    public static void main(String[] args) {
        Client client;
        try {
            client = new Client();
            client.run();
            
            ArrayList<Course> courses = client.getCourse("Automne");
            for(int i=0; i<courses.size(); i++){
                System.out.println(courses.get(i).getName());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
    

