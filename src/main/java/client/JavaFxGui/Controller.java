package client.JavaFxGui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import client.JavaFxGui.models.Course;

import java.util.ArrayList;

public class Controller extends Application {
    Client client = new Client();
    @FXML private Button submit;
    @FXML private Button loadClasses;
    @FXML private TableView<Course> table;
    @FXML private TableColumn<Course, String> tableCode;
    @FXML private TableColumn<Course, String> tableClass;

    @FXML private ObservableList<Course> data;
    private static Stage pStage;


    public static void main(String[] args) {
        Controller.launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/JavaFxGui/baseScene.fxml"));
            SplitPane splitPane = loader.<SplitPane>load();
            Scene scene = new Scene(splitPane);
            primaryStage.setScene(scene);
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
            ArrayList<Course> classes = client.getCourse("Automne");
            //convert to observableArrayList; format: code + name
            final ObservableList<Course> data = FXCollections.observableArrayList(classes);
            table.setItems(data);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Click on submit
    public void handleButtonPress(javafx.event.ActionEvent actionEvent) {
        submit.setVisible(false);

    }
    public void chooseSession(javafx.event.ActionEvent actionEvent) {

    }

}


