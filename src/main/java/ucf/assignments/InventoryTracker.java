package ucf.assignments;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryTracker extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        SceneManager sceneManager = new SceneManager();
        sceneManager.load();

        Scene scene = sceneManager.getScene("MainWindow");

        primaryStage.setTitle("Inventory Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
