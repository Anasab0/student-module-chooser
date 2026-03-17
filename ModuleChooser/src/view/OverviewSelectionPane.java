package view; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.control.Button; 
import javafx.scene.control.ListView; 
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox; 
import javafx.scene.layout.StackPane; 
import javafx.scene.layout.VBox; 
import model.Course; 
import model.Module; 
import model.Name; 

import java.time.LocalDate; 
import java.util.List; 


public class OverviewSelectionPane extends GridPane {

    // Fields for list views and buttons
    private ListView<String> listViewTop; // ListView for displaying top overview details
    private ListView<String> listViewBottomLeft; // ListView for selected modules
    private ListView<String> listViewBottomRight; // ListView for reserved modules
    private Button saveButton; // Button to save the overview

    // Constructor to initialize the overview selection pane
    public OverviewSelectionPane() {
        // Initialize ListViews and the save button
        listViewTop = new ListView<>();
        listViewBottomLeft = new ListView<>();
        listViewBottomRight = new ListView<>();
        saveButton = new Button("Save Overview");

        // Set preferred size for the top ListView
        listViewTop.setPrefSize(500, 100);

        // Create StackPanes to hold the ListViews
        StackPane stackPaneTop = new StackPane();
        stackPaneTop.getChildren().add(listViewTop);

        StackPane stackPaneBottomLeft = new StackPane();
        stackPaneBottomLeft.getChildren().add(listViewBottomLeft);

        StackPane stackPaneBottomRight = new StackPane();
        stackPaneBottomRight.getChildren().add(listViewBottomRight);

        // Create VBox layouts for the top and bottom ListViews
        VBox vBoxTop = new VBox(5, stackPaneTop);
        VBox vBoxBottomLeft = new VBox(5, stackPaneBottomLeft);
        VBox vBoxBottomRight = new VBox(5, stackPaneBottomRight);

        // Set preferred size for bottom ListViews
        vBoxBottomLeft.setPrefSize(200, 180);
        vBoxBottomRight.setPrefSize(200, 180);

        // Create HBox for the save button
        HBox buttonBox = new HBox(saveButton);
        buttonBox.setAlignment(Pos.CENTER); // Center align the button
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Set padding

        // Add the components to the GridPane
        this.add(vBoxTop, 0, 0, 2, 1); // Top ListView
        this.add(vBoxBottomLeft, 0, 2); // Left ListView
        this.add(vBoxBottomRight, 1, 2); // Right ListView
        this.add(buttonBox, 0, 3, 2, 1); // Save button

        // Set padding and gaps for the GridPane
        this.setPadding(new Insets(40, 20, 10, 20)); // Set padding
        this.setHgap(30); // Set horizontal gap
        this.setVgap(30); // Set vertical gap

        // Define column constraints for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50); // Set width for first column
        column2.setPercentWidth(50); // Set width for second column
        this.getColumnConstraints().addAll(column1, column2); // Add constraints
    }

    // Method to update the overview with student and module data
    public void updateOverview(String pNumber, Name studentName, String email, LocalDate date, Course course, List<Module> selectedModules, List<Module> reservedModules) {
        // Clear existing items from ListViews
        listViewTop.getItems().clear();
        listViewBottomLeft.getItems().clear();
        listViewBottomRight.getItems().clear();

        // Add student details to the top ListView
        listViewTop.getItems().add("PNumber: " + pNumber);
        listViewTop.getItems().add("Name: " + studentName.getFullName());
        listViewTop.getItems().add("Email: " + email);
        listViewTop.getItems().add("Date: " + date);
        listViewTop.getItems().add("Course: " + course.getCourseName());

        // Add modules in the course to the top ListView
        listViewTop.getItems().add("Modules in Course:");
        for (Module module : course.getAllModulesOnCourse()) {
            listViewTop.getItems().add(module.getModuleCode() + " - " + module.getModuleName());
        }

        // Add selected modules to the left ListView
        listViewBottomLeft.getItems().add("\nSelected Modules:");
        for (Module module : selectedModules) {
            listViewBottomLeft.getItems().add("Module code: " + module.getModuleCode() + 
                                               ", Module name: " + module.getModuleName() + 
                                               ", Credits: " + module.getModuleCredits() + 
                                               ", Mandatory on your course? " + module.isMandatory() + 
                                               ", Block: " + module.getRunPlan());
        }

        // Add reserved modules to the right ListView
        listViewBottomRight.getItems().add("\nReserved Modules:");
        for (Module module : reservedModules) {
            listViewBottomRight.getItems().add("Module code: " + module.getModuleCode() + 
                                                ", Module name: " + module.getModuleName() + 
                                                ", Credits: " + module.getModuleCredits() + 
                                                ", Mandatory on your course? " + module.isMandatory() + 
                                                ", Block: " + module.getRunPlan());
        }
    }

    // Method to attach a handler for the save button
    public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
        saveButton.setOnAction(handler); // Set action handler for the save button
    }
}
