package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class ItemModel {

    private ObservableList<Item> inventory;

    public ItemModel() {
        inventory = FXCollections.observableArrayList();
    }

    public boolean isUniqueSerialNumber (String newSerial){
        for (int i = 0; i < inventory.size(); i++) {

            String setSerialNumbers = inventory.get(i).getSerialNumber().toUpperCase(Locale.ROOT);

            if (newSerial.toUpperCase(Locale.ROOT).equals(setSerialNumbers)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCorrectSerialFormat (String newSerial) {
        if (!newSerial.matches("[a-zA-Z0-9]*")
                && (newSerial.matches(".*\\s+.*"))
                    && !(newSerial.length() == 10)) {

            return false;
        }

        return true;
    }

    public void add(Item item) {
        if (inventory.size() < 101)
            inventory.add(item);
    }

    public void remove(Item item) {
        inventory.remove(item);
    }

    public void eraseAllItems() {
        inventory.clear();
    }

    public ObservableList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ObservableList<Item> inventory) {
        this.inventory = inventory;
    }
}
