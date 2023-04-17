package client.JavaFxGui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import server.models.Course;
import server.models.RegistrationForm;

import java.util.ArrayList;

/**
 * La classe EventHandler est responsable de la gestion des événements déclenchés par l'utilisateur sur l'interface graphique.
 */
public class EventHandler {
    Client client = new Client();
    Validation validate = new Validation();
    String session;
    @FXML
    private TableView<Course> table;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField matricule;
    @FXML
    private ChoiceBox<String> chooseSession;
    @FXML
    private TableColumn<Course, String> tableCode;
    @FXML
    private TableColumn<Course, String> tableClass;


    /**
     * Cette méthode est déclenchée lorsque l'utilisateur clique sur le bouton "Soumettre".
     * Elle récupère les informations de l'utilisateur et envoie une demande d'inscription au serveur.
     *
     * @param actionEvent non utilisé et fourni par défaut.
     */
    public void submitRequest(javafx.event.ActionEvent actionEvent) {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String email = this.email.getText();
        String matricule = this.matricule.getText();
        validate.errors = "";
        //Validation
        if (!validate.validateName(firstName) || !validate.validateName(lastName) ||
                !validate.validateEmail(email) || !validate.validateMatricule(matricule)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(validate.errors);
            a.show();
            return;
        }

        //get Course Object from user click
        Course course = table.getSelectionModel().getSelectedItem();
        if (course == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Selectionnez un cours auquel vous inscrire");
            a.show();
            return;
        }
        try {
            //send registrationRequest
            RegistrationForm registrationForm = new RegistrationForm(firstName, lastName, email, matricule, course);
            client.registerRequest(registrationForm);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Félicitations " + firstName + " vous  vous etes inscrits au cours" + course.getName() + " !");
            a.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Echec de connection au serveur, reesayer avec une bonne connection ou contacter les administrateurs");
            a.show();
        }
    }

    /**
     * Cette méthode est déclenchée lorsque l'utilisateur clique sur le bouton "Charger".
     * Elle reçoit les cours du serveur et les affiche dans le TableView.
     *
     * @param actionEvent L'événement déclenché par l'utilisateur en cliquant sur le bouton "Charger".
     */
    public void displayCourses(javafx.event.ActionEvent actionEvent) {
        try {
            tableCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
            tableClass.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
            //obtain courses for specified semester
            if (session != null) {
                ArrayList<Course> classes = client.getCourse(this.session);
                //convert to observableArrayList; format: code + name
                final ObservableList<Course> data = FXCollections.observableArrayList(classes);
                table.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Veuillez selectionner une session pour laquelle vous voulez visionner les cours");
                a.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Echec de connection au serveur, reesayer avec une bonne connection ou contacter les administrateurs");
            a.show();
        }
    }

    /**
     * Change la session lorsque le choiceBox est utilisé.
     *
     * @param actionEvent fourni par défaut.
     */
    public void ChangedSessionChoice(ActionEvent actionEvent) {
        this.session = chooseSession.getSelectionModel().getSelectedItem();
    }

}
