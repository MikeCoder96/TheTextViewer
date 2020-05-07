package mainpackage;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/formpackage/mainform.fxml"));
	        AnchorPane root = (AnchorPane) loader.load();
	        Scene scene = new Scene(root, 800, 800);
	        scene.getStylesheets().add(getClass().getResource("/formpackage/style.css").toExternalForm());
	        primaryStage.initStyle(StageStyle.UNDECORATED);
	        primaryStage.setScene(scene);        
	        primaryStage.setTitle("Prova");
	        primaryStage.setMinHeight(400);
	        primaryStage.setMinWidth(400);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
