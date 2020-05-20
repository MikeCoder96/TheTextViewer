package mainpackage;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import utilspackage.CellFactoryAdvanced;
import utilspackage.TreeCategory;

public class MainController {
	
	private ArrayList<String> books = new ArrayList<String>();
	private TreeItem<String> rootItem;

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
	void initialize() {
		rootItem = new TreeItem<String>("Books");
		treeView1.setRoot(rootItem);
		treeView1.setShowRoot(false);
		treeView1.setEditable(true);
		treeView1.setCellFactory(new CellFactoryAdvanced());
		
		
		
		addBooks("a");
		addBooks("B");
		addBooks("c");
		addBooks("D");
		addBooks("t");
		addBooks("w");
		addBooks("e");
		/*addBooks("r");
		addBooks("y");
		addBooks("u");
		addBooks("i");
		addBooks("o");
		addBooks("p");
		addBooks("1");
		addBooks("2");
		addBooks("3");
		addBooks("4");
		addBooks("5");
		addBooks("6");
		addBooks("7");
		addBooks("8");
		addBooks("9");
		*/

		showBooksToTreeView();
	}

	public void addBooks(String Title) {
		books.add(Title);
	}

	void showBooksToTreeView() {
		for (int i = 0; i < books.size(); i++) {
			TreeItem<String> item = new TreeItem<String>(books.get(i));
			rootItem.getChildren().add(item);
		}
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
	
	@FXML
    void tempOpenText(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	try {
    		TextFileViewer tfv = new TextFileViewer(fc.showOpenDialog(null));
    		textContentPane.getChildren().clear();
    		//set to fill AnchorPane
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