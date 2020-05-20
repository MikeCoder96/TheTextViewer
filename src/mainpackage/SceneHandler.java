package mainpackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpackage.SceneHandler;
import utilspackage.MainFormSettings;
import utilspackage.Utils;

public class SceneHandler {
	private static SceneHandler SH = null;
	private Stage stage;
	private Scene mainscene, settingsscene;
	
	public Scene getMainScene() {
		return mainscene;
	}
	
	public Scene getSettingsScene() {
		return settingsscene;
	}
	
	public static SceneHandler getInstance() {
		  if (SH == null)
			  SH = new SceneHandler(); 
		    return SH;
	}
	
	public void init(Stage paramStage) throws Exception{
		stage = paramStage;
		stage.hide();
		setMainScene();
		stage.setTitle("TheTextViewer");
		stage.setResizable(true);
		stage.show();
	}
	
	private void setMainScene() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/mainform.fxml"));
		Pane root = (AnchorPane)loader.load();
		Scene scenes = new Scene(root);
		mainscene = scenes;
		
		mainscene.getStylesheets().add(getClass().getResource(Utils.WHITETHEME).toExternalForm());
		mainscene.getStylesheets().add(getClass().getResource(Utils.DARKTHEME).toExternalForm());
		stage.setScene(mainscene);
		//set stage size bounds
		stage.setMinHeight(MainFormSettings.MINHEIGHT);
		stage.setMinWidth(MainFormSettings.MINWIDTH);
		stage.setMaxHeight(MainFormSettings.MAXHEIGHT);
		stage.setMaxWidth(MainFormSettings.MAXWIDTH);
	}
	
	public void getSettingsWindow() throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/settingsform.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
	    Stage stage = new Stage();
	    stage.setResizable(true);
	    stage.initModality(Modality.APPLICATION_MODAL);
	    Scene tmpScene = new Scene(root, 400.0D, 400.0D);
	    settingsscene = tmpScene;
	    settingsscene.getStylesheets().add(getClass().getResource(Utils.WHITETHEME).toExternalForm());
	    settingsscene.getStylesheets().add(getClass().getResource(Utils.DARKTHEME).toExternalForm());
	    //tmpScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	    stage.setScene(settingsscene);
	    stage.showAndWait();
	}
	
}