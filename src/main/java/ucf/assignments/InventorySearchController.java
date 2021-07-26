/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Nicolas Milescu-Brahmbhatt
 */

package ucf.assignments;

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
        //to access inventory later
        this.itemModel = itemModel;
    }

    @FXML
    public void searchSerialButtonClicked(ActionEvent event) {
        String serialQuery = serialNumberTextField.getText();

        //if new serial is correct format send to Search method
        if (itemModel.isCorrectSerialFormat(serialQuery)) {
            if(searchForSerialNumber(serialQuery))
                closeStage();
        }

        //display error otherwise
        else {
            errorText.setText("Incorrect Serial Number Format");
            serialNumberTextField.clear();
        }

    }

    @FXML
    public void searchNameButtonClicked(ActionEvent event) {
        String nameQuery = nameTextField.getText();

        //if user enters right length then send to Search method
        if (itemModel.isCorrectNameLength(nameQuery)) {
            if(searchForName(nameQuery))
                closeStage();
        }

        //otherwise dispay error
        else {
            errorText.setText("Name must be between 2 and 256 characters in length");
            nameTextField.clear();
        }
    }


    public boolean searchForName(String nameQuery) {
        int inventorySize = itemModel.getInventory().size();

        //iterate through inventory to see if there is a name that matches
        for (int i = 0; i < inventorySize; i++) {

            String setNames = itemModel.getInventory().get(i).getName().toUpperCase(Locale.ROOT);

            if (nameQuery.toUpperCase(Locale.ROOT).equals(setNames)) {
                Item item = itemModel.getInventory().get(i);
                itemModel.addSearchResult(item);
            }
        }

        //display error if not found
        if (itemModel.getSearchResults().isEmpty()) {
            displayError();
            return false;
        }

        else
            return true;
    }

    public boolean searchForSerialNumber(String serialQuery) {
        Item item = itemModel.findItemBySerial(serialQuery);

        if (item != null) {
            itemModel.addSearchResult(item);
            return true;
        }
        else {
            errorText.setText("Item Not Found");
            return false;
        }
    }

    public void closeStage() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    public void displayError() {
        errorText.setText("Item Not Found");
    }
}
