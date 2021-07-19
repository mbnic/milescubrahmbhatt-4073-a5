package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

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

        String serial = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();
        Double value = Double.parseDouble(itemPriceTextField.getText());

        //check if value and stuff is correct than send to addNewItem
        addNewItem(serial, name, value);

        itemNameTextField.clear();
        itemSerialNumberTextField.clear();
        itemPriceTextField.clear();
    }

    @FXML
    void deleteItemButtonClicked(ActionEvent event) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        deleteItem(item);
    }





    public void deleteItem(Item item) {
        itemModel.remove(item);
    }

    public void addNewItem(String serial, String name, double value) {
        Item item = new Item(serial, name, value);
        itemModel.add(item);
    }


}
