package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventorySearchControllerTest {

    @Test
    void searchForName() {
        ItemModel itemModel = new ItemModel();
        InventorySearchController searcher = new InventorySearchController();
        searcher.initialize(itemModel);

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));
        itemModel.add(new Item(300.99, "1234567plk", "ps4"));

        searcher.searchForName("ps4");

        String actual = itemModel.getSearchResults().get(0).getName();

        assertEquals("ps4", actual);
    }

    @Test
    void searchForSerialNumber() {
        ItemModel itemModel = new ItemModel();
        InventorySearchController searcher = new InventorySearchController();
        searcher.initialize(itemModel);

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));
        itemModel.add(new Item(300.99, "1234567plk", "ps4"));

        searcher.searchForSerialNumber("123456789k");

        String actual = itemModel.getSearchResults().get(0).getSerialNumber();

        assertEquals("123456789k", actual);
    }
}