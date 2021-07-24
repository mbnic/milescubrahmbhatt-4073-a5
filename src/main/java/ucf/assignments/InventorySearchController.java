package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Locale;

public class InventorySearchController {

    private ItemModel itemModel;

    @FXML private TextField serialNumberTextField;
    @FXML private TextField nameTextField;

    @FXML private Text errorText;

    public void initialize(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

    @FXML
    public void searchSerialButtonClicked(ActionEvent event) {
        String serialQuery = serialNumberTextField.getText();

        if (itemModel.isCorrectSerialFormat(serialQuery)) {
            searchForSerialNumber(serialQuery);
        }

        else {
            errorText.setText("Incorrect Serial Number Format");
            serialNumberTextField.clear();
        }

    }

    @FXML
    public void searchNameButtonClicked(ActionEvent event) {
        String nameQuery = nameTextField.getText();

        if (itemModel.isCorrectNameLength(nameQuery)) {
            searchForName(nameQuery);
        }

        else {
            errorText.setText("Name must be between 2 and 256 characters in length");
            nameTextField.clear();
        }
    }


    public void searchForName(String nameQuery) {
        int inventorySize = itemModel.getInventory().size();

        for (int i = 0; i < inventorySize; i++) {

            String setNames = itemModel.getInventory().get(i).getName().toUpperCase(Locale.ROOT);

            if (nameQuery.toUpperCase(Locale.ROOT).equals(setNames)) {
                Item item = itemModel.getInventory().get(i);
                itemModel.addSearchResult(item);

                        //.add(new Item(item.getSerialNumber(), item.getName(), item.getValue()));
            }
        }

        if (itemModel.getSearchResults().isEmpty())
            errorText.setText("Item Not Found");

        else {
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
        }
    }

    public void searchForSerialNumber(String serialQuery) {
        Item item = itemModel.findItemBySerial(serialQuery);

        if (item != null) {
            itemModel.addSearchResult(item);
            Stage stage = (Stage) serialNumberTextField.getScene().getWindow();
            stage.close();
        }
        else {
            errorText.setText("Item Not Found");
        }
    }
}
