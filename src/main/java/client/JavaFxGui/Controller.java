package client.JavaFxGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Controller extends Application {
    /**
     * Starts the UI and initializes ChoiceBox values
     *
     * @param primaryStage
     */
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


