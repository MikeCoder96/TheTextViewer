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
    	final ToggleGroup tg = new ToggleGroup();
    	radioButton1.setToggleGroup(tg);
    	radioButton2.setToggleGroup(tg);
    }
    
    @FXML
    void setDarkMode(ActionEvent event) {
        sceneHandler.getInstance().getMainScene().getStylesheets().remove(utils.WHITETHEME);
        if(!sceneHandler.getInstance().getMainScene().getStylesheets().contains(utils.DARKTHEME))
        	sceneHandler.getInstance().getMainScene().getStylesheets().add(utils.DARKTHEME);
        
        sceneHandler.getInstance().getSettingsScene().getStylesheets().remove(utils.WHITETHEME);
        if(!sceneHandler.getInstance().getSettingsScene().getStylesheets().contains(utils.DARKTHEME))
        	sceneHandler.getInstance().getSettingsScene().getStylesheets().add(utils.DARKTHEME);
    }

    @FXML
    void setWhiteMode(ActionEvent event) {
    	sceneHandler.getInstance().getMainScene().getStylesheets().remove(utils.DARKTHEME);
        if(!sceneHandler.getInstance().getMainScene().getStylesheets().contains(utils.WHITETHEME))
        	sceneHandler.getInstance().getMainScene().getStylesheets().add(utils.WHITETHEME);
        
    	sceneHandler.getInstance().getSettingsScene().getStylesheets().remove(utils.DARKTHEME);
        if(!sceneHandler.getInstance().getSettingsScene().getStylesheets().contains(utils.WHITETHEME))
        	sceneHandler.getInstance().getSettingsScene().getStylesheets().add(utils.WHITETHEME);
    }

}
