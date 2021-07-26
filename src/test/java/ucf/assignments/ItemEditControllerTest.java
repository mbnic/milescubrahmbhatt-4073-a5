package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemEditControllerTest {

    @Test
    void changeName() {
        ItemModel itemModel = new ItemModel();

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));
        itemModel.add(new Item(300.99, "1234567plk", "ps4"));

        Item item = itemModel.getInventory().get(1);

        ItemEditController editor =  new ItemEditController();
        editor.initialize(item, itemModel);

        editor.changeName("nintendo64");

        String actual = itemModel.getInventory().get(1).getName();

        assertEquals("nintendo64", actual);
    }

    @Test
    void changeSerialNumber() {
        ItemModel itemModel = new ItemModel();

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));
        itemModel.add(new Item(300.99, "1234567plk", "ps4"));

        Item item = itemModel.getInventory().get(1);

        ItemEditController editor =  new ItemEditController();
        editor.initialize(item, itemModel);

        editor.changeSerialNumber("123456DDDD");

        String actual = itemModel.getInventory().get(1).getSerialNumber();

        assertEquals("123456DDDD", actual);
    }

    @Test
    void changeValue() {
        ItemModel itemModel = new ItemModel();

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));
        itemModel.add(new Item(300.99, "1234567plk", "ps4"));

        Item item = itemModel.getInventory().get(1);

        ItemEditController editor =  new ItemEditController();
        editor.initialize(item, itemModel);

        editor.changeValue(999.99);

        String actual = itemModel.getInventory().get(1).getValue();

        assertEquals("$999.99", actual);
    }
}