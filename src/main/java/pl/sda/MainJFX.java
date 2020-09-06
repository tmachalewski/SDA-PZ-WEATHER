package pl.sda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //path can be seen in target folder, there is a need to reference sample.fxml from the perspective of MainJFX class
        Parent root = FXMLLoader.load(getClass().getResource("../../sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
