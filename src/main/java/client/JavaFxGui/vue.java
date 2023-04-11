package client.JavaFxGui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;


public class vue extends Application {
    public static void main(String[] args) {
        vue.launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new URL("file:///C:/Users/leuma/Documents/Ecole/1025/InscriptionServeur/src/main/java/client/JavaFxGui/baseScene.fxml"));
            SplitPane splitPane = loader.<SplitPane>load();
            Scene scene = new Scene(splitPane);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}