package view; 

import java.time.LocalDate; 

import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.geometry.HPos; 
import javafx.geometry.Pos; 
import javafx.scene.control.Button; 
import javafx.scene.control.ComboBox; 
import javafx.scene.control.DatePicker; 
import javafx.scene.control.Label; 
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints; 
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.HBox; 
import model.Course; 
import model.Name; 


public class CreateStudentProfilePane extends GridPane {

    
    private ComboBox<Course> cboCourses; // Combo box for course selection
    private DatePicker inputDate; // Date picker for date input
    private TextField txtFirstName, txtSurname, txtPnumber, txtEmail; // Text fields for student details
    private Button btnCreateProfile; // Button to create profile

    // Constructor to initialize the pane and its components
    public CreateStudentProfilePane() {
        // Styling the grid
        this.setVgap(15); // Vertical gap between rows
        this.setHgap(20); // Horizontal gap between columns
        this.setAlignment(Pos.CENTER); // Center alignment for the grid

        // Setting up column constraints
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setHalignment(HPos.RIGHT); // Right alignment for column 0
        this.getColumnConstraints().addAll(column0); // Add constraints to the grid
        
        // Create labels for each input field
        Label lblTitle = new Label("Select course: ");
        Label lblPnumber = new Label("Input P number: ");
        Label lblFirstName = new Label("Input first name: ");
        Label lblSurname = new Label("Input surname: ");
        Label lblEmail = new Label("Input email: ");
        Label lblDate = new Label("Input date: ");
        
        // Initialize the combo box for courses
        cboCourses = new ComboBox<Course>(); // This will be populated via a method later
        
        // Set up text fields for user input
        txtFirstName = new TextField(); // Field for first name
        txtSurname = new TextField(); // Field for surname
        txtPnumber = new TextField(); // Field for P number
        txtEmail = new TextField(); // Field for email
        
        inputDate = new DatePicker(); // Initialize the date picker
        
        // Initialize the button for creating the profile
        btnCreateProfile = new Button("Create Profile");

        // Add controls and labels to the grid
        this.add(lblTitle, 0, 0); // Add course label
        this.add(cboCourses, 1, 0); // Add course combo box
        this.add(lblPnumber, 0, 1); // Add P number label
        this.add(txtPnumber, 1, 1); // Add P number text field
        this.add(lblFirstName, 0, 2); // Add first name label
        this.add(txtFirstName, 1, 2); // Add first name text field
        this.add(lblSurname, 0, 3); // Add surname label
        this.add(txtSurname, 1, 3); // Add surname text field
        this.add(lblEmail, 0, 4); // Add email label
        this.add(txtEmail, 1, 4); // Add email text field
        this.add(lblDate, 0, 5); // Add date label
        this.add(inputDate, 1, 5); // Add date picker
        this.add(new HBox(), 0, 6); // Add a spacer
        this.add(btnCreateProfile, 1, 6); // Add create profile button
    }
    
    // Method to allow the controller to add courses to the combo box
    public void addCourseDataToComboBox(Course[] courses) {
        cboCourses.getItems().addAll(courses); // Add courses to the combo box
        cboCourses.getSelectionModel().select(0); // Select first course by default
    }
    
    // Methods to retrieve input from the form
    public void setStudentPnumber(String pNumber) {
        txtPnumber.setText(pNumber); // Set P number text field
    }

    public void setStudentName(Name studentName) {
        txtFirstName.setText(studentName.getFirstName()); // Set first name text field
        txtSurname.setText(studentName.getFamilyName()); // Set surname text field
    }

    public void setStudentEmail(String email) {
        txtEmail.setText(email); // Set email text field
    }

    public void setStudentDate(LocalDate date) {
        inputDate.setValue(date); // Set date picker value
    }

    public void setSelectedCourse(Course course) {
        cboCourses.getSelectionModel().select(course); // Set selected course in combo box
    }
    
    // Methods to get user input from the form
    public Course getSelectedCourse() {
        return cboCourses.getSelectionModel().getSelectedItem(); // Get selected course
    }
    
    public String getStudentPnumber() {
        return txtPnumber.getText(); // Get P number text
    }
    
    public Name getStudentName() {
        return new Name(txtFirstName.getText(), txtSurname.getText()); // Get student name
    }

    public String getStudentEmail() {
        return txtEmail.getText(); // Get email text
    }
    
    public LocalDate getStudentDate() {
        return inputDate.getValue(); // Get selected date
    }
    
    // Method to attach the create student profile button event handler
    public void addCreateStudentProfileHandler(EventHandler<ActionEvent> handler) {
        btnCreateProfile.setOnAction(handler); // Set action handler for button
    }
}
