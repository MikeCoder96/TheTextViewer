package mainpackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpackage.sceneHandler;
import utilspackage.utils;

public class sceneHandler {
	private static sceneHandler thiscene = null;
	private Stage stage;
	private Scene mainscene, settingsscene;
	
	public Scene getMainScene() {
		return mainscene;
	}
	
	public Scene getSettingsScene() {
		return settingsscene;
	}
	
	public static sceneHandler getInstance() {
		  if (thiscene == null)
			  thiscene = new sceneHandler(); 
		    return thiscene;
	}
	
	public void init(Stage paramStage) throws Exception{
		stage = paramStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/mainform.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scenes = new Scene(root);
		mainscene = scenes;
		mainscene.getStylesheets().add(getClass().getResource(utils.theme2Url).toExternalForm());
		mainscene.getStylesheets().add(getClass().getResource(utils.theme1Url).toExternalForm());
		stage.hide();
		stage.setScene(mainscene);		
		stage.setTitle("TheTextViewer - Alpha 0.1");
		stage.setResizable(false);
		stage.show();
	}
	
	public void getSettingsWindow() throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/settingsform.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
	    Stage stage = new Stage();
	    stage.setResizable(false);
	    stage.initModality(Modality.APPLICATION_MODAL);
	    Scene tmpScene = new Scene(root, 400.0D, 400.0D);
	    settingsscene = tmpScene;
	    settingsscene.getStylesheets().add(getClass().getResource(utils.theme2Url).toExternalForm());
	    settingsscene.getStylesheets().add(getClass().getResource(utils.theme1Url).toExternalForm());
	    //tmpScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	    stage.setScene(settingsscene);
	    stage.showAndWait();
	}
	
}
