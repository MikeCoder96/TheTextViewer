package mainpackage;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		//handle single execution only
		File p = new File(".app.lock");
		try {
			FileChannel v = FileChannel.open(p.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			if(v.tryLock() == null) {
				Alert inst = new Alert(AlertType.ERROR);
				inst.setTitle("ERROR");
				inst.setHeaderText("Another instance is already running");
				inst.showAndWait();
				System.exit(-1);
			}
			SceneHandler.getInstance().init(primaryStage);
		} catch (Exception e) {
			Alert inst = new Alert(AlertType.ERROR);
			inst.setTitle("ERROR");
			inst.setHeaderText("Something stange has occurred");
			inst.showAndWait();
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
