package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Block;
import model.Name;
import model.Course;
import model.Module;
import model.StudentProfile;
import view.ModuleChooserRootPane;
import view.ReserveModulesPane;
import view.CreateStudentProfilePane;
import view.ModuleChooserMenuBar;
import view.SelectModulesPane;
import view.OverviewSelectionPane;

public class ModuleChooserController {

	//fields to be used throughout class
	private StudentProfile model;
	private ModuleChooserRootPane view;
	private SelectModulesPane smp;
	private CreateStudentProfilePane cspp;
	private ModuleChooserMenuBar mstmb;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;

	public ModuleChooserController(StudentProfile model, ModuleChooserRootPane view) {
		//initialise model and view fields
		this.model = model;
		this.view = view;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		mstmb = view.getModuleSelectionToolMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane(); 
		osp = view.getOverviewSelectionPane();			

		//add courses to combobox in create student profile pane using the generateAndGetCourses helper method below
		cspp.addCourseDataToComboBox(generateAndGetCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
		
		rmp.addAddHandler1(new AddHandler1());
	    rmp.addRemoveHandler1(new RemoveHandler1());
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {	
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		
		//attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		smp.addResetHandler(new ResetHandler());
        smp.addRemoveHandler(new RemoveHandler());
        smp.addHandler(new addHandler());
		smp.addSubmitHandler(new SubmitHandler());
		rmp.addConfirmHandler(new ConfirmHandler());
		osp.addSaveOverviewHandler(new SaveOverviewHandler());
		mstmb.addAboutHandler(new AboutHandler());	
		mstmb.addSaveHandler(new SaveProfileHandler(model));
		mstmb.addLoadHandler(new LoadProfileHandler(model, view));
	}
	
	
	
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			String pNumber = cspp.getStudentPnumber();
	        Name studentName = cspp.getStudentName();
	        String email = cspp.getStudentEmail();
	        LocalDate date = cspp.getStudentDate();
	        Course course = cspp.getSelectedCourse();

	        model.setStudentPnumber(pNumber);
	        model.setStudentName(studentName);
	        model.setStudentEmail(email);																							
	        model.setSubmissionDate(date);
	        model.setStudentCourse(course);

	        // Clear current module selections
	        model.clearSelectedModules();
	        model.clearReservedModules();

	        // Populate modules based on the selected course using a for loop
	        for (Module module : course.getAllModulesOnCourse()) {
	            if (module.isMandatory()) {
	                model.addSelectedModule(module);
	            } else {
	                model.addReservedModule(module);
	            }
	        }

	        // Update the SelectModulesPane with the selected and unselected modules
	        smp.populateSelectedModules(model.getAllSelectedModules());
	        smp.populateUnselectedModules(model.getAllReservedModules());
			
			view.changeTab(1);		
		}	
	}
	
	  // Event handler for resetting module selections
	 private class ResetHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            resetModules();
	        }
	    }
	 // Method to reset modules to default state
	 private void resetModules() {
	        Course selectedCourse = cspp.getSelectedCourse();
	        if (selectedCourse != null) {
	            // Clear current module selections															
	            smp.clearSelections();

	            // Add compulsory modules
	            for (Module module : selectedCourse.getAllModulesOnCourse()) {
	                if (module.isMandatory()) {
	                    smp.selectModule(module);
	                } else {
	                    smp.addModuleToUnselected(module);
	                }
	            }
	        }
	    }
	 
	 
	// Handler for adding a module														 
		 private class addHandler implements EventHandler<ActionEvent> {
		        @Override
		        public void handle(ActionEvent event) {
		            addModule(); 	// Call method to add the selected module
		        } 
		    }
		
																

	    // Method to add the selected module
	    private void addModule() {
	        Module selectedModule = smp.getSelectedUnselectedModule();
	        if (selectedModule != null) {
	            smp.removeModuleFromUnselected(selectedModule);
	            smp.selectModule(selectedModule);
	    }
	    }
	
	    private class RemoveHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            removeModule();
	        }
	    }

	    // Method to remove the selected module
	    private void removeModule() {
	        Module selectedModule = smp.getSelectedSelectedModule();
	        if (selectedModule != null) {
	            smp.removeModuleFromSelected(selectedModule);
	            smp.addModuleToUnselected(selectedModule);
	        }
	    }
	    
	    private class SubmitHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            // Capture selected modules
	            List<Module> selectedModules = smp.getSelectedModules();
	            List<Module> unselectedModules = smp.getUnselectedModules();

	            // Update the model with selected modules
	            model.clearSelectedModules();
	            for (Module module : selectedModules) {
	                model.addSelectedModule(module);
	            }

	            // Update the model with unselected modules
	            model.clearReservedModules();
	            for (Module module : unselectedModules) {
	                model.addReservedModule(module);
	            }

	            // Dynamically populate the reserve modules tab with unselected modules
	            rmp.populateUnselectedModules(unselectedModules);

	            
	            view.changeTab(2); 
	        }
	    }
	    
	    private class ConfirmHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            // Capture reserved modules
	            List<Module> reservedModules = rmp.getReservedModules();

	            
	            model.clearReservedModules();
	            for (Module module : reservedModules) {
	                model.addReservedModule(module);
	            }

	            
	            List<Module> selectedModulesList = new ArrayList<>(model.getAllSelectedModules());
	            List<Module> reservedModulesList = new ArrayList<>(model.getAllReservedModules());
	            
	            System.out.println("Selected Modules: " + selectedModulesList);
	            System.out.println("Reserved Modules: " + reservedModulesList);
	            
	            String pNumber = model.getStudentPnumber();
	            Name studentName = model.getStudentName();
	            String email = model.getStudentEmail();
	            LocalDate date = model.getSubmissionDate();
	            Course course = model.getStudentCourse();
	            
	            System.out.println("Student PNumber: " + pNumber);
	            System.out.println("Student Name: " + studentName);
	            System.out.println("Student Email: " + email);
	            System.out.println("Submission Date: " + date);
	            System.out.println("Course: " + course.getCourseName());
	            System.out.println("Selected Modules: " + selectedModulesList);
	            System.out.println("Reserved Modules: " + reservedModulesList);
	            

	            // Update the overview selection tab with student details, selected modules, and reserved modules
	            view.getOverviewSelectionPane().updateOverview(
	                model.getStudentPnumber(),
	                model.getStudentName(),
	                model.getStudentEmail(),
	                model.getSubmissionDate(),
	                model.getStudentCourse(),
	                selectedModulesList,
	                reservedModulesList
	            );

	          
	            view.changeTab(3); 
	        }
	    }
	    
	    private class SaveOverviewHandler implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            try (PrintWriter writer = new PrintWriter("student_overview.txt")) {
	                // Write student details
	                writer.println("Student PNumber: " + model.getStudentPnumber());
	                writer.println("Student Name: " + model.getStudentName());
	                writer.println("Student Email: " + model.getStudentEmail());
	                writer.println("Submission Date: " + model.getSubmissionDate());
	                writer.println("Course: " + model.getStudentCourse().getCourseName());

	                // Write selected modules
	                writer.println("\nSelected Modules:");
	                for (Module module : model.getAllSelectedModules()) {
	                    writer.println(module.getModuleCode() + " - " + module.getModuleName());
	                }

	                // Write reserved modules
	                writer.println("\nReserved Modules:");
	                for (Module module : model.getAllReservedModules()) {
	                    writer.println(module.getModuleCode() + " - " + module.getModuleName());
	                }

	                writer.flush();
	                System.out.println("Overview saved successfully.");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    private class AddHandler1 implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            Module selectedModule = rmp.getListViewLeft().getSelectionModel().getSelectedItem();
	            if (selectedModule != null) {
	                rmp.getListViewLeft().getItems().remove(selectedModule);
	                rmp.getListViewRight().getItems().add(selectedModule);
	            }
	        }
	    }

	    private class RemoveHandler1 implements EventHandler<ActionEvent> {
	        @Override
	        public void handle(ActionEvent event) {
	            Module selectedModule = rmp.getListViewRight().getSelectionModel().getSelectedItem();
	            if (selectedModule != null) {
	                rmp.getListViewRight().getItems().remove(selectedModule);
	                rmp.getListViewLeft().getItems().add(selectedModule);
	            }
	        }
	    }
	    
	    public class SaveProfileHandler implements EventHandler<ActionEvent> {
	        private StudentProfile model;

	        public SaveProfileHandler(StudentProfile model) {
	            this.model = model;
	        }

	        @Override
	        public void handle(ActionEvent event) {
	            try (FileOutputStream fileOut = new FileOutputStream("studentProfile.ser");
	                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	                out.writeObject(model);
	                System.out.println("Student profile saved successfully.");
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    public class LoadProfileHandler implements EventHandler<ActionEvent> {
	        private StudentProfile model;
	        private ModuleChooserRootPane view;

	        public LoadProfileHandler(StudentProfile model, ModuleChooserRootPane view) {
	            this.model = model;
	            this.view = view;
	        }

	        @Override
	        public void handle(ActionEvent event) {
	            try (FileInputStream fileIn = new FileInputStream("studentProfile.ser");
	                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
	                StudentProfile loadedProfile = (StudentProfile) in.readObject();
	                updateModelAndView(loadedProfile);
	                System.out.println("Student profile loaded successfully.");
	            } catch (IOException | ClassNotFoundException e) {
	                e.printStackTrace();
	            }
	        }

	        private void updateModelAndView(StudentProfile loadedProfile) {
	            // Update the model
	            model.setStudentPnumber(loadedProfile.getStudentPnumber());
	            model.setStudentName(loadedProfile.getStudentName());
	            model.setStudentEmail(loadedProfile.getStudentEmail());
	            model.setSubmissionDate(loadedProfile.getSubmissionDate());
	            model.setStudentCourse(loadedProfile.getStudentCourse());
	            model.setSelectedModules(loadedProfile.getAllSelectedModules());
	            model.setReservedModules(loadedProfile.getAllReservedModules());

	            // Update the view
	            view.getCreateStudentProfilePane().setStudentPnumber(loadedProfile.getStudentPnumber());
	            view.getCreateStudentProfilePane().setStudentName(loadedProfile.getStudentName());
	            view.getCreateStudentProfilePane().setStudentEmail(loadedProfile.getStudentEmail());
	            view.getCreateStudentProfilePane().setStudentDate(loadedProfile.getSubmissionDate());
	            view.getCreateStudentProfilePane().setSelectedCourse(loadedProfile.getStudentCourse());

	            view.getSelectModulesPane().populateSelectedModules(loadedProfile.getAllSelectedModules());
	            view.getSelectModulesPane().populateUnselectedModules(loadedProfile.getAllReservedModules());

	            view.getOverviewSelectionPane().updateOverview(
	                    loadedProfile.getStudentPnumber(),
	                    loadedProfile.getStudentName(),
	                    loadedProfile.getStudentEmail(),
	                    loadedProfile.getSubmissionDate(),
	                    loadedProfile.getStudentCourse(),
	                    new ArrayList<>(loadedProfile.getAllSelectedModules()),
	                    new ArrayList<>(loadedProfile.getAllReservedModules())
	                );
	            // Switch to the appropriate tab 
	            view.changeTab(1); 
	        }
	    }
	
	   
	            
	//helper method - generates modules and course data and returns courses within an array
	private Course[] generateAndGetCourses() {
		Module ctec3701 = new Module("CTEC3701", "Software Development: Methods & Standards", 30, true, Block.BLOCK_1);

		Module ctec3702 = new Module("CTEC3702", "Big Data and Machine Learning", 30, true, Block.BLOCK_2);
		Module ctec3703 = new Module("CTEC3703", "Mobile App Development and Big Data", 30, true, Block.BLOCK_2);

		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Block.BLOCK_3_4);

		Module ctec3704 = new Module("CTEC3704", "Functional Programming", 30, false, Block.BLOCK_3_4);
		Module ctec3705 = new Module("CTEC3705", "Advanced Web Development", 30, false, Block.BLOCK_3_4);

		Module imat3711 = new Module("IMAT3711", "Privacy and Data Protection", 30, false, Block.BLOCK_3_4);
		Module imat3722 = new Module("IMAT3722", "Fuzzy Logic and Inference Systems", 30, false, Block.BLOCK_3_4);

		Module ctec3706 = new Module("CTEC3706", "Embedded Systems and IoT", 30, false, Block.BLOCK_3_4);


		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3701);
		compSci.addModule(ctec3702);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3704);
		compSci.addModule(ctec3705);
		compSci.addModule(imat3711);
		compSci.addModule(imat3722);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3701);
		softEng.addModule(ctec3703);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3704);
		softEng.addModule(ctec3705);
		softEng.addModule(ctec3706);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
