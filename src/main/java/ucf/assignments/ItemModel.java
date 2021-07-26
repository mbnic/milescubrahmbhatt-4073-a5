/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Nicolas Milescu-Brahmbhatt
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class ItemModel {

    private ObservableList<Item> inventory;
    private ObservableList<Item> searchResults;

    public ItemModel() {
        inventory = FXCollections.observableArrayList();
        searchResults = FXCollections.observableArrayList();
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
        if (!newSerial.matches("[a-zA-Z0-9]*"))
            return false;
        else if(newSerial.matches(".*\\s+.*"))
            return false;
        else if(!(newSerial.length() == 10))
            return false;

        else
            return true;
    }

    public boolean isCorrectNameLength (String newName) {
        return newName.length() >= 2 && newName.length() <= 256;
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

    public Item findItemBySerial(String serialQuery) {
        //iterate through inventory to see if there is a match
        for (int i = 0; i < inventory.size(); i++) {

            String setSerialNumbers = inventory.get(i).getSerialNumber().toUpperCase(Locale.ROOT);

            if (serialQuery.toUpperCase(Locale.ROOT).equals(setSerialNumbers)) {
                return inventory.get(i);
            }
        }

        return null;
    }

    public ObservableList<Item> getInventory() {
        return inventory;
    }

    public void addSearchResult(Item item) {
        searchResults.add(item);
    }

    public ObservableList<Item> getSearchResults() {
        return searchResults;
    }

    public void clearSearchResults() {
        searchResults.clear();
    }

    public void setInventory(ObservableList<Item> inventory) {
        this.inventory = inventory;
    }
}
