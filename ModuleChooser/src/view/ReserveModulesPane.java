package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ReserveModulesPane extends GridPane {

    private ListView<Module> listViewLeft;  // ListView for unselected modules
    private ListView<Module> listViewRight; // ListView for reserved modules
   
    private Button addButton;     // Button to add selected module to reserved list
    private Button removeButton;  // Button to remove selected module from reserved list
    private Button confirmButton;  // Button to confirm the selection

    public ReserveModulesPane() {
        listViewLeft = new ListView<>(); // Initialize ListView for unselected modules
        listViewRight = new ListView<>(); // Initialize ListView for reserved modules
        this.getChildren().add(listViewLeft); // Add left ListView to the pane
        this.getChildren().add(listViewRight); // Add right ListView to the pane

        listViewLeft.setPrefSize(380, 400); // Set preferred size for the left ListView
        listViewRight.setPrefSize(380, 400); // Set preferred size for the right ListView

        // Create labels for the ListViews
        Label labelLeft = new Label("Unselected Block 3/4 modules");
        Label labelRight = new Label("Reserved Block 3/4 modules");

        // Create VBox to hold the left ListView and its label
        VBox vBoxLeft = new VBox(5, labelLeft, listViewLeft);
        // Create VBox to hold the right ListView and its label
        VBox vBoxRight = new VBox(5, labelRight, listViewRight);

        // Label for module reservation
        Label blockLabel = new Label("Reserve one optional module");
        
        // Initialize buttons
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        confirmButton = new Button("Confirm");

        // Set preferred width for buttons
        addButton.setPrefWidth(80);
        removeButton.setPrefWidth(80);
        confirmButton.setPrefWidth(80);

        // Create HBox to hold the buttons and label
        HBox buttonBox = new HBox(15, blockLabel, addButton, removeButton, confirmButton);
        buttonBox.setPadding(new Insets(0, 0, 0, 20)); // Set padding for button box
        buttonBox.setAlignment(Pos.CENTER); // Center align the buttons

        // Add components to the GridPane
        this.add(vBoxLeft, 0, 0); // Add left VBox
        this.add(vBoxRight, 1, 0); // Add right VBox
        this.add(buttonBox, 0, 1, 2, 1); // Add button box spanning both columns

        // Set padding and gaps for the GridPane
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(30);

        // Set column constraints for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50); // Set width of the first column to 50%
        column2.setPercentWidth(50); // Set width of the second column to 50%
        this.getColumnConstraints().addAll(column1, column2); // Add constraints to the GridPane
    }

    // Method to get the list of reserved modules
    public List<Module> getReservedModules() {
        return new ArrayList<>(listViewRight.getItems());
    }

    // Method to add event handler for confirm button
    public void addConfirmHandler(EventHandler<ActionEvent> handler) {
        confirmButton.setOnAction(handler);
    }

    // Method to add event handler for add button
    public void addAddHandler1(EventHandler<ActionEvent> handler) {
        addButton.setOnAction(handler);
    }

    // Method to add event handler for remove button
    public void addRemoveHandler1(EventHandler<ActionEvent> handler) {
        removeButton.setOnAction(handler);
    }

    // Method to populate the left ListView with unselected modules
    public void populateUnselectedModules(List<Module> unselectedModules) {
        listViewLeft.getItems().clear(); // Clear existing items
        listViewLeft.getItems().addAll(unselectedModules); // Add unselected modules
    }

    // Get the left ListView
    public ListView<Module> getListViewLeft() {
        return listViewLeft;
    }

    // Get the right ListView
    public ListView<Module> getListViewRight() {
        return listViewRight;
    }
    
    // Method to populate the right ListView with reserved modules
    public void populateReservedModules(Set<Module> reservedModules) {
        listViewLeft.getItems().clear(); // Clear existing items in left ListView
        for (Module module : reservedModules) {
            listViewRight.getItems().add(module); // Add reserved modules to right ListView
        }
    }
}
