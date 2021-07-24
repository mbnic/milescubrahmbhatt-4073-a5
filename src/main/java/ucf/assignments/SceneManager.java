/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Nicolas Milescu-Brahmbhatt
 */
package ucf.assignments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    Map<String, Scene> scenes = new HashMap<>();

    void load() {
        ItemModel itemModel = new ItemModel();

        MainWindowController mainWindowController = new MainWindowController(itemModel, this);

        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        loader.setController(mainWindowController);
        try {
            root = loader.load();
            scenes.put("MainWindow", new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


        loader = new FXMLLoader(getClass().getResource("valueEnteredError.fxml"));
        try {
            root = loader.load();
            scenes.put("valueEnteredError", new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loader = new FXMLLoader(getClass().getResource("nameEnteredError.fxml"));
        try {
            root = loader.load();
            scenes.put("nameEnteredError", new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loader = new FXMLLoader(getClass().getResource("serialNumberEnteredError.fxml"));
        try {
            root = loader.load();
            scenes.put("serialNumberEnteredError", new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loader = new FXMLLoader(getClass().getResource("duplicateSerialNumberEnteredError.fxml"));
        try {
            root = loader.load();
            scenes.put("duplicateSerialNumberEnteredError", new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public ItemEditController getItemEditController() {
//
//    }

    public Scene getScene(String sceneName) {
        return scenes.get(sceneName);
    }


}
