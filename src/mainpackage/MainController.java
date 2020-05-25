package mainpackage;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import utilspackage.CellFactoryAdvanced;
import utilspackage.TreeCategory;
import utilspackage.Utils;

public class MainController {

	private TreeItem<String> rootItem;

    @FXML
    private TextField textField1;	
	@FXML
	private ContextMenu contextMenu1;
	@FXML
	private TreeView<String> treeView1;
	@FXML
	private AnchorPane textContentPane;
	@FXML
	private SplitPane splitPane1;

	@FXML
	private MenuItem settingsButton;

	@FXML
	private MenuItem menuItem1;

	@FXML
	void onOpenFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(null);
		if (file != null) {
			addBooks(file.getName(), file);
			System.out.println(file.getPath());
		}
	}

	@FXML
	void initialize() {
		rootItem = new TreeItem<String>("Books");
		treeView1.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (treeView1.getRoot() != null && event.getGestureSource() != rootItem
                        && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });
		
		treeView1.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
            	//We use dragboard "a java clipboard class" to store the files
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    for (File fl : db.getFiles()) {
                    	addBooks(fl.getName(), fl);
                    }
                    //we set the variable to true when we release the mouse click
                	success = true;
                }
                //here is end of the drag event
                event.setDropCompleted(success);

                event.consume();
            }
        });
		
		treeView1.setRoot(rootItem);
		treeView1.setShowRoot(false);
		treeView1.setEditable(true);
		treeView1.setCellFactory(new CellFactoryAdvanced());
		treeView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> 
		{
			OpenText(newValue.getValue());
			System.out.println("Selected Text : " + newValue.getValue());
		});
	}

	public void addBooks(String Title, File file) {
		Boolean res = Utils.addBooks(Title, file);
		
		if (res)
		{
			TreeItem<String> item = new TreeItem<String>(Title);
			rootItem.getChildren().add(item);
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Book Already Exist!");
			alert.setHeaderText("The book: " + Title + " is already present in your library!");
			alert.showAndWait();
		}

	}

    @FXML
    void searchBook(KeyEvent event) {
    	//TreeItem<String> tmpSearch = new TreeItem<String>();
    	//treeView1.setRoot(null);	
    	
    }
	
	@FXML
	void openSettings(ActionEvent event) throws Exception {
		SceneHandler.getInstance().getSettingsWindow();
	}

	@FXML
	void onCreateCategory(ActionEvent event) {
		TreeCategory prova = new TreeCategory("Inserisci nome");
		rootItem.getChildren().add(prova);
	}

	void OpenText(String title) {
		try {
			File toShow = Utils.getBookFromTitle(title);
			if (toShow == null)
				return;
			TextFileViewer tfv = new TextFileViewer(toShow);
			textContentPane.getChildren().clear();
			// set to fill AnchorPane
			AnchorPane.setBottomAnchor(tfv, 0.0);
			AnchorPane.setLeftAnchor(tfv, 0.0);
			AnchorPane.setTopAnchor(tfv, 0.0);
			AnchorPane.setRightAnchor(tfv, 0.0);
			textContentPane.getChildren().add(tfv);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}