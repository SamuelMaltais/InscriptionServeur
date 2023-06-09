package client.JavaFxGui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Controller extends Application {
    /**
     * Démarre l'Interface et initialise les choix de session pour le bouton
     * déroulant.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            // Load l'object FXML baseScene comme la racine
            loader.setLocation(getClass().getResource("/JavaFxGui/baseScene.fxml"));
            SplitPane splitPane = loader.<SplitPane>load();
            Scene scene = new Scene(splitPane);
            primaryStage.setScene(scene);
            // Ajoute les valeures pour le ChoiceBox
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
