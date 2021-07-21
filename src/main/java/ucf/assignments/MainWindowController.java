package ucf.assignments;

import com.sun.source.doctree.SystemPropertyTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private ItemModel itemModel;
    private SceneManager sceneManager;

    @FXML private TableView<Item> itemsTableView;
    @FXML private TableColumn<Item, String> itemsSerialNumberColumn;
    @FXML private TableColumn<Item, String> itemsNameColumn;
    @FXML private TableColumn<Item, Double> itemsValueColumn;

    @FXML private TextField itemSerialNumberTextField;
    @FXML private TextField itemNameTextField;
    @FXML private TextField itemPriceTextField;

    public MainWindowController(ItemModel item_model, SceneManager scene_manager) {
        this.itemModel = item_model;
        this.sceneManager = scene_manager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemsSerialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        itemsSerialNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        itemsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemsNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        itemsValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        itemsNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        itemsTableView.setItems(itemModel.getInventory());
    }
    
    
    @FXML
    void addNewItemButtonClicked(ActionEvent event) {
        boolean valueFlag = true;
        boolean nameFlag = true;
        boolean serialFlag = true;

        String serial = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();

        //check if value entered is a number
        try {
            Double checkValue = Double.parseDouble(itemPriceTextField.getText());

        } catch (NumberFormatException e) {
            valueFlag = false;
            Stage stage = new Stage();
            stage.setTitle("ERROR");
            stage.setScene(sceneManager.getScene("valueEnteredError"));
            stage.show();
            itemPriceTextField.clear();
        }

        //check if serial format is correct
        if (!itemModel.isCorrectSerialFormat(serial)) {
            serialFlag = false;
            Stage stage = new Stage();
            stage.setTitle("ERROR");
            stage.setScene(sceneManager.getScene("serialNumberEnteredError"));
            stage.show();
            itemSerialNumberTextField.clear();
        }

        //check if serial number is a duplicate
        if (!itemModel.isUniqueSerialNumber(serial)) {
            serialFlag = false;
            Stage stage = new Stage();
            stage.setTitle("ERROR");
            stage.setScene(sceneManager.getScene("duplicateSerialNumberEnteredError"));
            stage.show();
            itemSerialNumberTextField.clear();
        }

        //check name is correct format
        if (name.length() < 2 || name.length() > 256) {
            nameFlag = false;
            Stage stage = new Stage();
            stage.setTitle("ERROR");
            stage.setScene(sceneManager.getScene("nameEnteredError"));
            stage.show();
            itemNameTextField.clear();
        }


        if (valueFlag && nameFlag && serialFlag) {
            Double value = Double.parseDouble(itemPriceTextField.getText());
            addNewItem(serial, name, value);
            itemNameTextField.clear();
            itemSerialNumberTextField.clear();
            itemPriceTextField.clear();
        }
    }

    @FXML
    void deleteItemButtonClicked(ActionEvent event) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        deleteItem(item);
    }

    @FXML
    void newFileMenuItemClicked(ActionEvent event) {
        itemModel.eraseAllItems();
    }

    @FXML
    void searchInventoryMenuItemClicked(ActionEvent event) {
        // make new scene for searching
    }

    @FXML
    void editItemButtonClicked(ActionEvent event) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("itemEditWindow.fxml"));
            Parent root = loader.load();

            ItemEditController controller = loader.getController();
            controller.initialize(item, itemModel);


            Stage stage = new Stage();
            stage.setTitle("Edit Item");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void deleteItem(Item item) {
        itemModel.remove(item);
    }

    public void addNewItem(String serial, String name, double value) {
        Item item = new Item(serial, name, value);
        itemModel.add(item);
    }
}
