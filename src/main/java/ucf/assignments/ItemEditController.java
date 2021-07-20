package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemEditController implements Initializable {

    private ItemModel itemModel;
    private SceneManager sceneManager;

    @FXML private Text oldSerialNumberText;
    @FXML private Text oldNameText;
    @FXML private Text oldPriceText;

    public ItemEditController(ItemModel item_model, SceneManager scene_manager) {
        this.itemModel = item_model;
        this.sceneManager = scene_manager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
