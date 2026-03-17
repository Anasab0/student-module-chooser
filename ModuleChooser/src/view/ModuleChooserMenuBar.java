package view; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar; 
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem; 
import javafx.scene.input.KeyCombination; 


public class ModuleChooserMenuBar extends MenuBar {

    // Menu items for the menu bar
    private MenuItem saveItem, loadItem, aboutItem, exitItem;

    // Constructor to initialize the menu bar and its items
    public ModuleChooserMenuBar() { 

        // Temporary variable for menus
        Menu menu;

        // Create the File menu
        menu = new Menu("_File");

        // Create and configure the Load Student Data menu item
        loadItem = new MenuItem("_Load Student Data");
        loadItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L")); // Set keyboard shortcut
        menu.getItems().add(loadItem); // Add item to the menu

        // Create and configure the Save Student Data menu item
        saveItem = new MenuItem("_Save Student Data");
        saveItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S")); // Set keyboard shortcut
        menu.getItems().add(saveItem); // Add item to the menu

        menu.getItems().add(new SeparatorMenuItem()); // Add a separator between menu items

        // Create and configure the Exit menu item
        exitItem = new MenuItem("E_xit");
        exitItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X")); // Set keyboard shortcut
        menu.getItems().add(exitItem); // Add item to the menu

        this.getMenus().add(menu); // Add the File menu to the menu bar

        // Create the Help menu
        menu = new Menu("_Help");

        // Create and configure the About menu item
        aboutItem = new MenuItem("_About");
        aboutItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A")); // Set keyboard shortcut
        menu.getItems().add(aboutItem); // Add item to the Help menu

        this.getMenus().add(menu); // Add the Help menu to the menu bar
    }

    // Method to attach a handler for the Save menu item
    public void addSaveHandler(EventHandler<ActionEvent> handler) {
        saveItem.setOnAction(handler); // Set action handler for Save item
    }
    
    // Method to attach a handler for the Load menu item
    public void addLoadHandler(EventHandler<ActionEvent> handler) {
        loadItem.setOnAction(handler); // Set action handler for Load item
    }
    
    // Method to attach a handler for the About menu item
    public void addAboutHandler(EventHandler<ActionEvent> handler) {
        aboutItem.setOnAction(handler); // Set action handler for About item
    }

    // Method to attach a handler for the Exit menu item
    public void addExitHandler(EventHandler<ActionEvent> handler) {
        exitItem.setOnAction(handler); // Set action handler for Exit item
    }
}
