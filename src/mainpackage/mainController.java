package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;

public class mainController {

	private boolean mouseDragOnDivider = false;
	
	@FXML
	private SplitPane splitPane1;
	
    @FXML
    private MenuItem settingsButton;

    @FXML
    void initialize()
    {
    	
    }
    
    @FXML
    void openSettings(ActionEvent event) throws Exception {
    	sceneHandler.getInstance().getSettingsWindow();
    }

}
