package view;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import model.Block;
import model.Module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SelectModulesPane extends GridPane {

    private ListView<Module> listViewTopLeft;
    private ListView<Module> listViewTopRight;
    private ListView<Module> listViewBottomLeft;
    private ListView<Module> listViewBottomRight;
    private Button addButton;
    private Button removeButton;
    private Button resetButton;
    private Button submitButton;
    private TextField creditsField;

    
    public SelectModulesPane() {
        // Create ListViews
        listViewTopLeft = new ListView<>();
        listViewTopRight = new ListView<>();
        listViewBottomLeft = new ListView<>();
        listViewBottomRight = new ListView<>();

        listViewTopLeft.setPrefSize(400, 200);
        listViewTopRight.setPrefSize(350, 170);
        listViewBottomLeft.setPrefSize(400, 200);
        listViewBottomRight.setPrefSize(350, 170);

        // Labels
        Label labelTopLeft = new Label("Selected Block 1 modules");
        Label labelTopRight = new Label("Unselected Block 3/4 modules");
        Label labelBottomLeft = new Label("Selected Block 2 modules");
        Label labelBottomRight = new Label("Selected Block 3/4 modules");

        // VBoxes for Labels and ListViews
        VBox vBoxTopLeft = new VBox(5, labelTopLeft, listViewTopLeft);
        VBox vBoxTopRight = new VBox(5, labelTopRight, listViewTopRight);
        VBox vBoxBottomLeft = new VBox(5, labelBottomLeft, listViewBottomLeft);
        VBox vBoxBottomRight = new VBox(5, labelBottomRight, listViewBottomRight);

        // Add and Remove buttons with "Block 3/4" label
        Label blockLabel = new Label("Block 3/4");
        addButton = new Button("Add");
        removeButton = new Button("Remove");

        addButton.setPrefWidth(80); 
        removeButton.setPrefWidth(80); 

        // Align label and buttons in HBox
        HBox buttonBox = new HBox(10, blockLabel, addButton, removeButton);
        buttonBox.setPadding(new Insets(0, 0, 0, 20));  
        buttonBox.setAlignment(Pos.TOP_CENTER);

        // Add components to the GridPane
        this.add(vBoxTopLeft, 0, 0);       // Top-left corner
        this.add(vBoxTopRight, 1, 0);      // Top-right corner
        this.add(buttonBox, 1, 1);         // Center row for label and buttons
        this.add(vBoxBottomLeft, 0, 2);    // Bottom-left corner
        this.add(vBoxBottomRight, 1, 2);   // Bottom-right corner

        VBox vBoxBottomRightContainer = new VBox(5, vBoxBottomRight);
        vBoxBottomRightContainer.setPadding(new Insets(30, 0, 0, 0)); // Adjust the padding as needed
        this.add(vBoxBottomRightContainer, 1, 2);   // Bottom-right corner
        
        // Create Reset and Submit buttons
        resetButton = new Button("Reset");
        submitButton = new Button("Submit");

        HBox buttonBoxBottom = new HBox(10, resetButton, submitButton);
        buttonBoxBottom.setAlignment(Pos.CENTER);


        // Create "Current credits" label and TextField
        Label creditsLabel = new Label("Current credits:");
        creditsField = new TextField("0"); // Assign to the class-level variable
        creditsField.setPrefWidth(50);
        creditsField.setEditable(false);

        
        HBox creditsBox = new HBox(10, creditsLabel, creditsField);
        creditsBox.setAlignment(Pos.CENTER);
        creditsBox.setPadding(new Insets(0, 0, 50, 0));

        // Add creditsBox and buttonBoxBottom to a VBox for vertical alignment
        VBox bottomBox = new VBox(-20, creditsBox, buttonBoxBottom);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(5, 0, 0, 0));

        // Place bottomBox at the bottom, spanning across both columns
        this.add(bottomBox, 0, 3, 2, 1); 

       
       
        
        
        // Set up row and column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();

        col1.setPercentWidth(50); // Left side ListViews
        col2.setPercentWidth(50); // Right side ListViews and buttons

        this.getColumnConstraints().addAll(col1, col2);

        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();

        row1.setPercentHeight(35); // Top row for ListViews
        row2.setPercentHeight(10); // Middle row for label and buttons
        row3.setPercentHeight(35); // Bottom row for ListViews
        row4.setPercentHeight(20); // Bottom row for current credits and buttons

        this.getRowConstraints().addAll(row1, row2, row3, row4);

        // Set padding and gaps
        this.setPadding(new Insets(20, 20, -10, 20)); 
        this.setHgap(15);
        this.setVgap(-70);
    }

    public int calculateTotalCredits() {
        int totalCredits = 0;
        for (Module module : getSelectedModules()) {
            totalCredits += module.getModuleCredits();
        }
        return totalCredits;
    }
    
    public void updateCreditsDisplay() {
        int totalCredits = calculateTotalCredits();
        System.out.println("Total Credits: " + totalCredits); // Debug statement
        creditsField.setText(String.valueOf(totalCredits));
    }
    
    
    // Method to set the add button handler
   
    public void addHandler(EventHandler<ActionEvent> handler) {
    	addButton.setOnAction(handler);
    }
    
    // Method to set the remove button handler
    public void addRemoveHandler(EventHandler<ActionEvent> handler) {
        removeButton.setOnAction(handler);
    }
    
    public void removeModuleFromUnselected(Module module) {
        listViewTopRight.getItems().remove(module);
    }

    // Method to set the reset button handler
    public void addResetHandler(EventHandler<ActionEvent> handler) {
        resetButton.setOnAction(handler);
    }

    // Methods to add and remove modules
    public void selectModule(Module module) {
        switch (module.getRunPlan()) {
            case BLOCK_1:
                listViewTopLeft.getItems().add(module);
                break;
            case BLOCK_2:
                listViewBottomLeft.getItems().add(module);
                break;
            case BLOCK_3_4:
                listViewBottomRight.getItems().add(module);
                break;
            default:
                // Handle other cases if necessary
                break;
        }
        updateCreditsDisplay(); // Update credits display
    }

    public void removeModuleFromSelected(Module module) {
        switch (module.getRunPlan()) {
            case BLOCK_1:
                listViewTopLeft.getItems().remove(module);
                break;
            case BLOCK_2:
                listViewBottomLeft.getItems().remove(module);
                break;
            case BLOCK_3_4:
                listViewBottomRight.getItems().remove(module);
                break;
            default:
                // Handle other cases if necessary
                break;
        }
        updateCreditsDisplay(); // Update credits display
    }

    public void addModuleToUnselected(Module module) {
        listViewTopRight.getItems().add(module);
        updateCreditsDisplay(); // Update credits display
    }
    
   
    
    

    // Method to get the selected module from the selected modules list
    public Module getSelectedSelectedModule() {
        Module selectedModule = listViewTopLeft.getSelectionModel().getSelectedItem();
        if (selectedModule == null) {
            selectedModule = listViewBottomLeft.getSelectionModel().getSelectedItem();
        }
        if (selectedModule == null) {
            selectedModule = listViewBottomRight.getSelectionModel().getSelectedItem();
        }
        return selectedModule;
    }
    
    
    
    // Method to get the selected module from the unselected modules list
    public Module getSelectedUnselectedModule() {
        return listViewTopRight.getSelectionModel().getSelectedItem();
    }

    // Method to clear all selections
    public void clearSelections() {
        listViewTopLeft.getItems().clear();
        listViewTopRight.getItems().clear();
        listViewBottomLeft.getItems().clear();
        listViewBottomRight.getItems().clear();
    }

    // Method to reset the selections
    public void resetSelections(Collection<Module> compulsoryModules) {
        clearSelections();
        for (Module module : compulsoryModules) {
        	selectModule(module);
        }
    }
    
   
 // Method to populate selected modules
    public void populateSelectedModules(Set<Module> selectedModules) {
        clearSelections();
        for (Module module : selectedModules) {
            selectModule(module);
        }
    }

    // Method to populate unselected modules
    public void populateUnselectedModules(Set<Module> unselectedModules) {
        listViewTopRight.getItems().clear();
        for (Module module : unselectedModules) {
            if (module.getRunPlan() == Block.BLOCK_3_4) {
                listViewTopRight.getItems().add(module);
            }
        }
    }
    
    public void addSubmitHandler(EventHandler<ActionEvent> handler) {
    	submitButton.setOnAction(handler);
    }
    
    public List<Module> getSelectedModules() {
    	List<Module> selectedModules = new ArrayList<>();
        selectedModules.addAll(listViewTopLeft.getItems());
        selectedModules.addAll(listViewBottomLeft.getItems());
        selectedModules.addAll(listViewBottomRight.getItems());    
        return selectedModules;
    }

    public List<Module> getUnselectedModules() {
        return new ArrayList<>(listViewTopRight.getItems());
    }    
}
