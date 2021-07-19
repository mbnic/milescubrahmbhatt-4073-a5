package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemModel {

    private ObservableList<Item> inventory;

    public ItemModel() {
        inventory = FXCollections.observableArrayList();
    }

    public void add(Item item) {
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
