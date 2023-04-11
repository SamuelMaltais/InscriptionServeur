package client.JavaFxGui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class vue extends Application {
    public static void main(String[] args) {
        vue.launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 320, 250);
        Text texte = new Text("Hello, World !");
        texte.setFont(Font.font("serif", 25));
        root.getChildren().add(texte);
        primaryStage.setTitle("Titre de la fenêtre");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}