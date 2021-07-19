package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    Map<String, Scene> scenes = new HashMap<>();

    void load() {
        ItemModel itemModel = new ItemModel();

        MainWindowController mainWindowController = new MainWindowController(itemModel, this);
        //add controller for whatever new windows you decide to add

        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setController(mainWindowController);


        try {
            root = loader.load();
            scenes.put("MainWindow", new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Scene getScene(String sceneName) {
        return scenes.get(sceneName);
    }


}
