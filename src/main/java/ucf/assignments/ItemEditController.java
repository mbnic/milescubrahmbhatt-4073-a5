package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ItemEditController {

    private ItemModel itemModel;
    private Item selectedItem;

    @FXML private TextField serialNumberTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField valueTextField;

    @FXML private Text errorText;


    public void initialize(Item item, ItemModel itemModel) {
        this.itemModel = itemModel;
        this.selectedItem = item;

        serialNumberTextField.setPromptText(item.getSerialNumber());
        nameTextField.setPromptText(item.getName());
        valueTextField.setPromptText(String.valueOf(item.getValue()));
    }


    public void saveChangesButtonClicked(ActionEvent event) {
        boolean valueFlag = true;
        boolean nameFlag = true;
        boolean serialDuplicateFlag = true;
        boolean serialFormatFlag = true;
        double value = 0.0;
        
        String serial = serialNumberTextField.getText();
        String name = nameTextField.getText();
        String valueSTR = valueTextField.getText();

        //check if value entered is a number
        if (!valueSTR.isBlank()) {
            try {
                value = Double.parseDouble(valueTextField.getText());
                valueFlag = true;
            } catch (NumberFormatException e) {
                valueFlag = false;
                errorText.setText("Incorrect Value Format");
                valueTextField.clear();
            }
        }


        //check if serial format is correct
        if (!serial.isBlank()) {
            if (!itemModel.isCorrectSerialFormat(serial)){
                serialFormatFlag = false;
                errorText.setText("Incorrect Serial Number Format");
                serialNumberTextField.clear();
            }

            if (itemModel.isUniqueSerialNumber(serial)) {
                serialDuplicateFlag = true;
            } else {
                serialDuplicateFlag = false;
                errorText.setText("Serial Number already in Inventory");
                serialNumberTextField.clear();
            }
        }


        if (!name.isBlank()) {
            //check name is correct format
            if (!itemModel.isCorrectNameLength(name)) {
                nameFlag = false;
                errorText.setText("Name must be between 2 and 256 characters in length");
                nameTextField.clear();
            }
        }

        
        if (valueFlag && nameFlag && serialFormatFlag && serialDuplicateFlag) {
            
            if (!name.isBlank())
                selectedItem.setName(name);
            
            if (!serial.isBlank())
                selectedItem.setSerialNumber(serial);
            
            if (!valueSTR.isBlank())
                selectedItem.setValue(value);

            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
        }
    }
}
