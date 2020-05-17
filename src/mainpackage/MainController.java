package mainpackage;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import utilspackage.TaskCellFactory;

public class MainController {

	private ArrayList<String> books = new ArrayList<String>();
	private TreeItem<String> rootItem;
	
	@FXML
	private TreeView<String> treeView1;
	
	@FXML
	private SplitPane splitPane1;
	
    @FXML
    private MenuItem settingsButton;
    
    @FXML
    void initialize(){
    	treeView1.setCellFactory(new TaskCellFactory());
    	rootItem = new TreeItem<String>("Books");
    	treeView1.setRoot(rootItem);
    	rootItem.setExpanded(true);
        
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
    		TreeItem<String> item = new TreeItem<String>(books.get(i));
    		rootItem.getChildren().add(item);
    	}
    }
    
    @FXML
    void openSettings(ActionEvent event) throws Exception {
    	SceneHandler.getInstance().getSettingsWindow();
    }

}
