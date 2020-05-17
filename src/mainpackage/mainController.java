package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class mainController {

    @FXML
    private MenuItem settingsButton;

    @FXML
    private AnchorPane textContentPane;
    
    @FXML
    void openSettings(ActionEvent event) throws Exception {
    	sceneHandler.getInstance().getSettingsWindow();
    }
    
    @FXML
    void tempOpenText(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	try {
    		TextFileViewer tfv = new TextFileViewer(fc.showOpenDialog(null));
    		textContentPane.getChildren().clear();
    		//set to fill AnchorPane
    		AnchorPane.setBottomAnchor(tfv, 0.0);
    		AnchorPane.setLeftAnchor(tfv, 0.0);
    		AnchorPane.setTopAnchor(tfv, 0.0);
    		AnchorPane.setRightAnchor(tfv, 0.0);
    		textContentPane.getChildren().add(tfv);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

}
