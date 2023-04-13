package client.JavaFxGui;
import server.models.Course;
import server.models.RegistrationForm;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    String session;
    //Fxml ids for display code and name
    @FXML private TableView<Course> table;
    @FXML private TableColumn<Course, String> tableCode;
    @FXML private TableColumn<Course, String> tableClass;
    @FXML private ChoiceBox<String> chooseSession;
    //Fxml ids for registrationForm
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField matricule;


    //public static void main(String[] args) {
    //    Controller.launch(args);
    //}
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
            ArrayList<Course> classes = client.getCourse(this.session);
            //convert to observableArrayList; format: code + name
            final ObservableList<Course> data = FXCollections.observableArrayList(classes);
            table.setItems(data);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Click on submit, calls registerRequest
    public void handleButtonPress(javafx.event.ActionEvent actionEvent) {
            String firstName = this.firstName.getText();
            String lastName = this.lastName.getText();
            String email = this.email.getText();
            String matricule = this.matricule.getText();

            //Validation
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || matricule.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Vous ne pouvez pas avoir un champ vide");
            a.show();
            return;
        }
        //TODO REGEX FOR EMAIL AND MATRICULE

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
            a.setContentText("Vous etes inscrits !!");
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


