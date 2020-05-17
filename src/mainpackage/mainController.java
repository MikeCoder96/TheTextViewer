package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class mainController {

    @FXML
    private MenuItem settingsButton;

    @FXML
    private Pane textContentPane;
    
    @FXML
    void openSettings(ActionEvent event) throws Exception {
    	sceneHandler.getInstance().getSettingsWindow();
    }
    
    @FXML
    void tempOpenText(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	textContentPane.getChildren().clear();
    	try {
    		textContentPane.getChildren().add(new TextFileViewer(fc.showOpenDialog(null)));
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
