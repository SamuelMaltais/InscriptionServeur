package client.JavaFxGui;
import server.models.Course;
import server.models.RegistrationForm;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class Controller extends Application {
    Client client = new Client();
    Validation validate = new Validation();
    String session;
    //Fxml id for session choice
    @FXML private ChoiceBox<String> chooseSession;
    //Fxml ids for display code and name
    @FXML private TableView<Course> table;
    @FXML private TableColumn<Course, String> tableCode;
    @FXML private TableColumn<Course, String> tableClass;
    //Fxml ids for registrationForm
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField matricule;

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/JavaFxGui/baseScene.fxml"));
            SplitPane splitPane = loader.<SplitPane>load();
            Scene scene = new Scene(splitPane);
            primaryStage.setScene(scene);
            ChoiceBox chooseSession = (ChoiceBox) loader.getNamespace().get("chooseSession");
            chooseSession.getItems().add("Automne");
            chooseSession.getItems().add("Été");
            chooseSession.getItems().add("Hiver");
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayCourses(javafx.event.ActionEvent actionEvent){
        try {
            tableCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
            tableClass.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
            //obtain courses for specified semester
            if (session != null){
                ArrayList<Course> classes = client.getCourse(this.session);
                //convert to observableArrayList; format: code + name
                final ObservableList<Course> data = FXCollections.observableArrayList(classes);
                table.setItems(data);
            }
            else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Veuillez selectionner une session pour laquelle vous voulez visionner les cours");
                a.show();
            }
        }

        catch (Exception e){
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Echec de connection au serveur, reesayer avec une bonne connection ou contacter les administrateurs");
            a.show();
        }
    }

    //Click on submit, calls registerRequest
    public void submitRequest(javafx.event.ActionEvent actionEvent) {
            String firstName = this.firstName.getText();
            String lastName = this.lastName.getText();
            String email = this.email.getText();
            String matricule = this.matricule.getText();

            //Validation
        if(!validate.validateName(firstName) || !validate.validateName(lastName) ||
                !validate.validateEmail(email) || !validate.validateMatricule(matricule)){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("un champ est vide ou invalide");
            a.show();
            return;
        }

        //get Course Object from user click
        Course course = table.getSelectionModel().getSelectedItem();
        if(course == null){
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
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Echec de connection au serveur, reesayer avec une bonne connection ou contacter les administrateurs");
            a.show();
        }
    }
    public void ChangedSessionChoice(ActionEvent actionEvent) {
        this.session = chooseSession.getSelectionModel().getSelectedItem();
    }
}


