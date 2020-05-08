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
    
    @FXML
    private void initialize() {
    	//TO-DO: reading theme from file settings
    }
    
    @FXML
    void setDarkMode(ActionEvent event) {
        sceneHandler.getInstance().getMainScene().getStylesheets().remove(utils.theme2Url);
        if(!sceneHandler.getInstance().getMainScene().getStylesheets().contains(utils.theme1Url))
        	sceneHandler.getInstance().getMainScene().getStylesheets().add(utils.theme1Url);
        
        sceneHandler.getInstance().getSettingsScene().getStylesheets().remove(utils.theme2Url);
        if(!sceneHandler.getInstance().getSettingsScene().getStylesheets().contains(utils.theme1Url))
        	sceneHandler.getInstance().getSettingsScene().getStylesheets().add(utils.theme1Url);
        
        radioButton1.setSelected(true);
        radioButton2.setSelected(false);
    }

    @FXML
    void setWhiteMode(ActionEvent event) {
    	sceneHandler.getInstance().getMainScene().getStylesheets().remove(utils.theme1Url);
        if(!sceneHandler.getInstance().getMainScene().getStylesheets().contains(utils.theme2Url))
        	sceneHandler.getInstance().getMainScene().getStylesheets().add(utils.theme2Url);
        
    	sceneHandler.getInstance().getSettingsScene().getStylesheets().remove(utils.theme1Url);
        if(!sceneHandler.getInstance().getSettingsScene().getStylesheets().contains(utils.theme2Url))
        	sceneHandler.getInstance().getSettingsScene().getStylesheets().add(utils.theme2Url);
       
        radioButton2.setSelected(true);
        radioButton1.setSelected(false);
    }

}
