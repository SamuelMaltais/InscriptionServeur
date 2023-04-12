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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import server.models.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.ArrayList;


public class Controller extends Application {
    Client client = new Client();
    @FXML private Button submit;
    @FXML private Button loadClasses;
    private TableView<Course> table;
    private TableColumn<Course, String> code;
    private TableColumn<Course, String> name;
    private ObservableList<Course> data;

    public static void main(String[] args) {
        Controller.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/JavaFxGui/baseScene.fxml"));
            SplitPane splitPane = loader.<SplitPane>load();
            table = (TableView) loader.getNamespace().get("table");
            code = (TableColumn) loader.getNamespace().get("tableCode");
            name = (TableColumn) loader.getNamespace().get("tableClass");
            code.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
            name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
            Scene scene = new Scene(splitPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayCourses(javafx.event.ActionEvent actionEvent) throws Exception{
        //retrieve classes information
        //ArrayList courses = client.getCourse("Automne");
        final ObservableList<Course> data = FXCollections.observableArrayList(
        new Course("some","random", "stuff")    );
        //associate data with columns

        table.setItems(data);
    }
    //Click on submit
    public void handleButtonPress(javafx.event.ActionEvent actionEvent) {
        submit.setVisible(false);

    }
    public void chooseSession(javafx.event.ActionEvent actionEvent) {

    }

}


