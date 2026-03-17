package view; 
import javafx.scene.control.Tab; 
import javafx.scene.control.TabPane; 
import javafx.scene.control.TabPane.TabClosingPolicy; 
import javafx.scene.layout.BorderPane; 


public class ModuleChooserRootPane extends BorderPane {

    // Fields for sub-panes and menu bar
    private CreateStudentProfilePane cspp; // Pane for creating student profiles
    private SelectModulesPane smp; // Pane for selecting modules
    private ReserveModulesPane rmp; // Pane for reserving modules
    private OverviewSelectionPane osp; // Pane for overview selection
    private ModuleChooserMenuBar mstmb; // Menu bar for module chooser
    private TabPane tp; // TabPane to hold tabs

    // Constructor to initialize the root pane
    public ModuleChooserRootPane() {
        // Create tab pane and disable tabs from being closed
        tp = new TabPane();
        tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        // Create panes
        cspp = new CreateStudentProfilePane(); // Initialize create profile pane
        smp = new SelectModulesPane(); // Initialize select modules pane
        rmp = new ReserveModulesPane(); // Initialize reserve modules pane
        osp = new OverviewSelectionPane(); // Initialize overview selection pane
        
        // Create tabs with the corresponding panes added
        Tab t1 = new Tab("Create Profile", cspp); // Tab for creating profile
        Tab t2 = new Tab("Select ModulesPane", smp); // Tab for selecting modules
        Tab t3 = new Tab("Reserve ModulesPane", rmp); // Tab for reserving modules
        Tab t4 = new Tab("Overview Selection", osp); // Tab for overview selection
        
        // Add tabs to tab pane
        tp.getTabs().addAll(t1, t2, t3, t4);
        
        // Create menu bar
        mstmb = new ModuleChooserMenuBar();
        
        // Add menu bar and tab pane to this root pane
        this.setTop(mstmb); // Set menu bar at the top
        this.setCenter(tp); // Set tab pane at the center
    }

    // Methods allowing sub-containers to be accessed by the controller
    public CreateStudentProfilePane getCreateStudentProfilePane() {
        return cspp; // Return create student profile pane
    }
    
    public SelectModulesPane getSelectModulesPane() {
        return smp; // Return select modules pane
    }
    
    public ReserveModulesPane getReserveModulesPane() {
        return rmp; // Return reserve modules pane
    }
    
    public OverviewSelectionPane getOverviewSelectionPane() {
        return osp; // Return overview selection pane
    }
    
    public ModuleChooserMenuBar getModuleSelectionToolMenuBar() {
        return mstmb; // Return module selection menu bar
    }
    
    // Method to allow the controller to change tabs
    public void changeTab(int index) {
        tp.getSelectionModel().select(index); // Change the selected tab
    }
}
