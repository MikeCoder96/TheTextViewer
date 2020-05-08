package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import utilspackage.utils;

public class settingsController {

	@FXML
	private RadioButton radioButton1;
	
	@FXML
	private RadioButton radioButton2;
	
    private ToggleGroup group = new ToggleGroup();
    
    @FXML
    void initialize() {
    	radioButton1.setToggleGroup(group);
    	radioButton2.setToggleGroup(group);
    	radioButton1.setSelected(true);
    }
    
    @FXML
    void setDarkMode(ActionEvent event) {
        utils.actualScene.getStylesheets().remove(utils.theme2Url);
        if(!utils.actualScene.getStylesheets().contains(utils.theme1Url))
        	utils.actualScene.getStylesheets().add(utils.theme1Url);

    }

    @FXML
    void setWhiteMode(ActionEvent event) {
    	utils.actualScene.getStylesheets().remove(utils.theme1Url);
        if(!utils.actualScene.getStylesheets().contains(utils.theme2Url))
        	utils.actualScene.getStylesheets().add(utils.theme2Url);
    }
}
