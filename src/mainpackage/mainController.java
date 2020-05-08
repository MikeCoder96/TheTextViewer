package mainpackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class mainController {

    @FXML
    private MenuItem settingsButton;

    @FXML
    void openSettings(ActionEvent event) throws Exception {
    	sceneHandler.getInstance().getSettingsWindow();
    }

}
