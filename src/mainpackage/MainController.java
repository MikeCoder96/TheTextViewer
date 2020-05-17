package mainpackage;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class MainController {

	private boolean mouseDragOnDivider = false;
	
	private ArrayList<String> books = new ArrayList<String>();
	private TreeItem rootItem;
	
	@FXML
	private TreeView treeView1;
	
	@FXML
	private SplitPane splitPane1;
	
    @FXML
    private MenuItem settingsButton;
    
    @FXML
    void initialize(){
    	rootItem = new TreeItem("Books");
    	treeView1.setRoot(rootItem);
    	addBooks("a");
    	addBooks("B");
    	addBooks("c");
    	addBooks("D");
    	addBooks("e");
    	
    	showBooksToTreeView();
    }
    
    public void addBooks(String Title){
    	books.add(Title);
    }
    
    void showBooksToTreeView() {
    	for (int i = 0; i < books.size(); i++) {
    		TreeItem item = new TreeItem(books.get(i));
    		rootItem.getChildren().add(item);
    	}
    }
    
    @FXML
    void openSettings(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().getSettingsWindow();
    }

}
