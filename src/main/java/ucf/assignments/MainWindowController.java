/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Nicolas Milescu-Brahmbhatt
 */

package ucf.assignments;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

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
            addNewItem(value, serial, name);
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
    public void searchInventoryMenuItemClicked(ActionEvent event) {

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
            controller.setPromptText();


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

    @FXML
    public void loadMenuItemClicked() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT Files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        extensionFilter = new FileChooser.ExtensionFilter("HTML", "*.html");
        fileChooser.getExtensionFilters().add(extensionFilter);

        extensionFilter = new FileChooser.ExtensionFilter("JSON", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null)
            loadFile(file);
    }

    @FXML
    public void resetListMenuItemClicked() {
        itemModel.getInventory().clear();
    }

    public void loadFile(File file) {
        if (file.getName().endsWith(".txt"))
            loadTXT(file);

        if (file.getName().endsWith(".html"))
            loadHTML(file);

        if (file.getName().endsWith(".json"))
            loadJSON(file);
    }

    public void loadTXT(File file) {
        List<String> lines = new ArrayList<>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.isEmpty())
                lines.add(line);
        }

        for (String line : lines) {
            String[] splits = line.split("\t");

            itemModel.getInventory().add(new Item(Double.parseDouble(splits[0]), splits[1], splits[2]));
        }
    }

    public void loadHTML(File file) {
        List<String> lines = new ArrayList<>();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("<td>")) {
                    lines.add(line);
                }
            }

            int counter = 0;
            List<String> info = new ArrayList<>(3);

            for (int i = 0; i < lines.size(); i++) {
                if (counter == 3) {
                    itemModel.getInventory().add(new Item(Double.parseDouble(info.get(0)), info.get(1), info.get(2)));
                    counter = 0;
                }
                else {
                    String str = lines.get(i);
                    info.add(str.substring(6, str.length() - 5));
                    counter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadJSON(File file) {

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray itemArray = fileObject.get("Inventory").getAsJsonArray();

            for (JsonElement itemElement: itemArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();

                String value = itemObject.get("value").getAsString();
                String serialNumber = itemObject.get("serial number").getAsString();
                String name = itemObject.get("name").getAsString();

                itemModel.add(new Item(Double.parseDouble(value), serialNumber, name));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

        for (Item item : inventory) {
            serialNumber = item.getSerialNumber();
            name = item.getName();
            value = item.getValue().substring(1);

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


    public void deleteItem(Item item) {
        itemModel.remove(item);
    }

    public void addNewItem(double value, String serial, String name) {
        Item item = new Item(value, serial, name);
        itemModel.add(item);
    }
}
