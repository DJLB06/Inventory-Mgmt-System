package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/** This class creates an inventory management system. */

public class Main extends Application {

    /** This loads the main form of the inventory management system. */

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /** This is the main method. This is the first method that gets called when the program is run. */
    public static void main(String[] args) {


        launch(args);
    }
}
