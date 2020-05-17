package mainpackage;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import utilspackage.CellFactoryAdvanced;

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
	void initialize() {
		rootItem = new TreeItem<String>("Books");
		treeView1.setRoot(rootItem);

		/*treeView1.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

			@Override
			public TreeCell<String> call(TreeView<String> param) {
				TreeCell<String> newcell = new TreeCell<String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty){
							setText("");
							setGraphic(null);
							return;
						}
						setText(item);
					}
				};
				return newcell;
			}
		});*/
		treeView1.setCellFactory(new CellFactoryAdvanced());
		
		addBooks("a");
		addBooks("B");
		addBooks("c");
		addBooks("D");
		addBooks("e");

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

}
