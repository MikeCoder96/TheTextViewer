package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import utilspackage.Utils;

public class SettingsController {

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
        SceneHandler.getInstance().getMainScene().getStylesheets().remove(Utils.WHITETHEME);
        if(!SceneHandler.getInstance().getMainScene().getStylesheets().contains(Utils.DARKTHEME))
        	SceneHandler.getInstance().getMainScene().getStylesheets().add(Utils.DARKTHEME);
        
        SceneHandler.getInstance().getSettingsScene().getStylesheets().remove(Utils.WHITETHEME);
        if(!SceneHandler.getInstance().getSettingsScene().getStylesheets().contains(Utils.DARKTHEME))
        	SceneHandler.getInstance().getSettingsScene().getStylesheets().add(Utils.DARKTHEME);
    }

    @FXML
    void setWhiteMode(ActionEvent event) {
    	SceneHandler.getInstance().getMainScene().getStylesheets().remove(Utils.DARKTHEME);
        if(!SceneHandler.getInstance().getMainScene().getStylesheets().contains(Utils.WHITETHEME))
        	SceneHandler.getInstance().getMainScene().getStylesheets().add(Utils.WHITETHEME);
        
    	SceneHandler.getInstance().getSettingsScene().getStylesheets().remove(Utils.DARKTHEME);
        if(!SceneHandler.getInstance().getSettingsScene().getStylesheets().contains(Utils.WHITETHEME))
        	SceneHandler.getInstance().getSettingsScene().getStylesheets().add(Utils.WHITETHEME);
    }

}
