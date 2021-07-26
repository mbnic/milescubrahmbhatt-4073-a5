package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainWindowControllerTest {

    @Test
    void deleteItem() {
        ItemModel itemModel = new ItemModel();
        SceneManager sceneManager = new SceneManager();
        MainWindowController main = new MainWindowController(itemModel, sceneManager);

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));
        main.addNewItem(3.99, "123456789O", "ps4");

        Item item = itemModel.getInventory().get(1);
        main.deleteItem(item);

        int actual = itemModel.getInventory().size();

        assertEquals(1, actual);
    }

    @Test
    void addNewItem() {
        ItemModel itemModel = new ItemModel();
        SceneManager sceneManager = new SceneManager();
        MainWindowController main = new MainWindowController(itemModel, sceneManager);

        itemModel.add(new Item(2.99, "123456789k", "xbox 360"));

        main.addNewItem(3.99, "123456789O", "ps4");

        int actual = itemModel.getInventory().size();

        assertEquals(2, actual);
    }
}