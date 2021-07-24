package ucf.assignments;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.source.doctree.SystemPropertyTree;
import javafx.collections.ObservableList;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

        itemModel.add(new Item("123456789K", "nic", 2.959999));
        itemModel.add(new Item("1234567OOO", "nic", 2.99));
        itemModel.add(new Item("1234567UUU", "bry", 16.99));
        itemModel.add(new Item("1234567WWW", "bry", 16.99));

        itemsTableView.setItems(itemModel.getInventory());
    }


    @FXML
    public void addNewItemButtonClicked(ActionEvent event) {
        boolean valueFlag = true;
        boolean nameFlag = true;
        boolean serialFlag = true;

        String serial = itemSerialNumberTextField.getText();
        String name = itemNameTextField.getText();

        //check if value entered is a number
        try {
            double checkValue = Double.parseDouble(itemPriceTextField.getText());

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
        if (!itemModel.isCorrectNameLength(name)) {
            nameFlag = false;
            Stage stage = new Stage();
            stage.setTitle("ERROR");
            stage.setScene(sceneManager.getScene("nameEnteredError"));
            stage.show();
            itemNameTextField.clear();
        }


        if (valueFlag && nameFlag && serialFlag) {
            double value = Double.parseDouble(itemPriceTextField.getText());
            addNewItem(serial, name, value);
            itemNameTextField.clear();
            itemSerialNumberTextField.clear();
            itemPriceTextField.clear();
        }
    }

    @FXML
    public void deleteItemButtonClicked(ActionEvent event) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        deleteItem(item);
    }

    @FXML
    public void newFileMenuItemClicked(ActionEvent event) {
        itemModel.eraseAllItems();
    }

    @FXML
    public void searchInventoryMenuItemClicked(ActionEvent event) {
        //Item item = itemsTableView.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inventorySearchWindow.fxml"));
            Parent root = loader.load();

            InventorySearchController controller = loader.getController();

            //clear old search results
            itemModel.getSearchResults().clear();
            controller.initialize(itemModel);


            Stage stage = new Stage();
            stage.setTitle("Search Inventory");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        itemsTableView.setItems(itemModel.getSearchResults());


    }

    @FXML
    public void editItemButtonClicked(ActionEvent event) {
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

    @FXML
    public void showAllButtonClicked() {
        itemsTableView.setItems(itemModel.getInventory());
    }

    @FXML
    public void saveAsMenuItemClicked() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT Files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        extensionFilter = new FileChooser.ExtensionFilter("HTML", "*.html");
        fileChooser.getExtensionFilters().add(extensionFilter);

        extensionFilter = new FileChooser.ExtensionFilter("JSON", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveFile(file);
        }


    }



    public void saveFile(File file) {
        if (file.getName().endsWith(".txt"))
            saveTXT(file);

        if (file.getName().endsWith(".html"))
            saveHTML(file);

        if (file.getName().endsWith(".json"))
            saveJSON(file);

    }

    public void saveTXT(File file) {
        StringBuilder inventoryData = new StringBuilder();
        String serialNumber;
        String name;
        String value;

        ObservableList<Item> inventory = itemModel.getInventory();

        for (Item items : inventory) {
            serialNumber = items.getSerialNumber();
            name = items.getName();
            value = items.getValue().substring(1);

            inventoryData.append(value
                    + "\t" + serialNumber
                    + "\t" + name + "\n");
        }

        try {
            PrintWriter write = new PrintWriter(file);
            write.println(inventoryData);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHTML(File file) {
        StringBuilder inventoryData = new StringBuilder();
        String serialNumber;
        String name;
        String value;

        ObservableList<Item> inventory = itemModel.getInventory();

        inventoryData.append("<table>" + "\n");

        for (Item items : inventory) {
            serialNumber = items.getSerialNumber();
            name = items.getName();
            value = items.getValue().substring(1);

            inventoryData.append("\t<tr>" + "\n");

            inventoryData.append("\t\t<td>" + value + "</td>\n");
            inventoryData.append("\t\t<td>" + serialNumber + "</td>\n");
            inventoryData.append("\t\t<td>" + name + "</td>\n");

            inventoryData.append("\t</tr>" + "\n");
        }

        inventoryData.append("</table>");

        try {
            PrintWriter write = new PrintWriter(file);
            write.println(inventoryData);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void saveJSON(File file) {
        String serialNumber;
        String name;
        String value;

        ObservableList<Item> inventory = itemModel.getInventory();

        JsonObject inventoryJSON = new JsonObject();
        JsonArray items = new JsonArray();

        for (int i = 0; i < inventory.size(); i++) {
            serialNumber = inventory.get(i).getSerialNumber();
            name = inventory.get(i).getName();
            value = inventory.get(i).getValue().substring(1);

            JsonObject itemObj = new JsonObject();

            itemObj.addProperty("value", value);
            itemObj.addProperty("serial number", serialNumber);
            itemObj.addProperty("name", name);

            items.add(itemObj);
        }

        inventoryJSON.add("Inventory", items);

        try {
            PrintWriter write = new PrintWriter(file);
            write.println(inventoryJSON);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySearchedItems(ObservableList<Item> foundItems) {
        itemsTableView.getItems().clear();
        itemsTableView.setItems(foundItems);
    }

    public void deleteItem(Item item) {
        itemModel.remove(item);
    }

    public void addNewItem(String serial, String name, double value) {
        Item item = new Item(serial, name, value);
        itemModel.add(item);
    }
}
