/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Nicolas Milescu-Brahmbhatt
 */
package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.text.NumberFormat;
import java.util.Locale;

public class Item {
    private SimpleStringProperty name;
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty value;

    public Item(double value, String serialNumber, String name) {
        this.name = new SimpleStringProperty(name);
        this.serialNumber = new SimpleStringProperty(serialNumber);

        //convert double to currency format
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        this.value = new SimpleStringProperty(formatter.format(value));
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public SimpleStringProperty serialNumberProperty() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber.set(serialNumber);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        this.value.set(formatter.format(value));
    }
}
