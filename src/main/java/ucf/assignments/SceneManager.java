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
        //ItemEditController itemEditController = new ItemEditController(itemModel, this);
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


//        loader = new FXMLLoader(getClass().getResource("itemEditWindow.fxml"));
//        loader.setController(itemEditController);
//        try {
//            root = loader.load();
//            scenes.put("itemEditWindow", new Scene(root));
//
//        } catch(IOException e) {
//            e.printStackTrace();
//        }


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
