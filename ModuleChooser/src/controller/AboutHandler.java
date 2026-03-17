package controller; 

import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType; 


public class AboutHandler implements EventHandler<ActionEvent> {

   
    @Override
    public void handle(ActionEvent event) {
        // Create an information alert dialog
        Alert alert = new Alert(AlertType.INFORMATION);
        
        // Set the title of the alert dialog
        alert.setTitle("About");
        
        // Set the header text for the alert dialog
        alert.setHeaderText("Application Details");
        
        // Set the content text that provides information about the application
        alert.setContentText("This application is designed to help students select and manage their modules.\nVersion: 1.0\nAuthor: Anas Abjaou");

        // Display the alert and wait for the user to close it
        alert.showAndWait();
    }
}
