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
 * The EventHandler class is responsible for handling events triggered by the user on the GUI.
 */
public class EventHandler {
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
    Client client = new Client();
    Validation validate = new Validation();
    String session;
    //Fxml id for session choice
    @FXML
    private ChoiceBox<String> chooseSession;
    //Fxml ids for display code and name
    @FXML
    private TableColumn<Course, String> tableCode;
    @FXML
    private TableColumn<Course, String> tableClass;
    //Fxml ids for registrationForm


    /**
     * This method is triggered when the user clicks on the "Submit" button.
     * It retrieves user information and sends a registration request to the server.
     *
     * @param actionEvent not used and provided by default.
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
     * This method is triggered when the user clicks on the charger button.
     * It receives the courses from the server and displays them on the tableView.
     *
     * @param actionEvent The event triggered by the user clicking the charger button.
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
     * @param actionEvent
     */
    public void ChangedSessionChoice(ActionEvent actionEvent) {
        this.session = chooseSession.getSelectionModel().getSelectedItem();
    }

}
