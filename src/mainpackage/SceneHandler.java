package mainpackage;

import java.io.IOException;

import org.jdom2.JDOMException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mainpackage.SceneHandler;
import utilspackage.MainFormSettings;
import utilspackage.Utils;
import utilspackage.XmlHandler;

public class SceneHandler {
	private static SceneHandler SH = null;
	private Stage stage;
	private Scene mainscene;
	private Stage notfounddialog;
	
	public Scene getMainScene() {
		return mainscene;
	}
	
	public Stage getDialogStage() {
		return notfounddialog;
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
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
					try {
						XmlHandler.saveSettings();
					} catch (JDOMException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		stage.setResizable(true);
		stage.show();
	}
	
	private void setMainScene() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/mainform.fxml"));
		Pane root = (AnchorPane)loader.load();
		Scene scenes = new Scene(root);
		mainscene = scenes;
		try {
			Utils.changeTheme(XmlHandler.loadSettings());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.setScene(mainscene);
		//set stage size bounds
		stage.setMinHeight(MainFormSettings.MINHEIGHT);
		stage.setMinWidth(MainFormSettings.MINWIDTH);
		stage.setMaxHeight(MainFormSettings.MAXHEIGHT);
		stage.setMaxWidth(MainFormSettings.MAXWIDTH);
	}

	
	public void initBookDialog() {
		notfounddialog = new Stage();
		notfounddialog.setTitle("Find not found books");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/bookdialog.fxml"));
		try {
			Pane dialog = (AnchorPane) loader.load();
			Scene scene = new Scene(dialog);
			scene.getStylesheets().add(mainscene.getStylesheets().get(0));
			notfounddialog.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notfounddialog.initModality(Modality.APPLICATION_MODAL);
		notfounddialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				XmlHandler.nonfoundbooks.clear();
			}
		});
		notfounddialog.showAndWait();
	}
}