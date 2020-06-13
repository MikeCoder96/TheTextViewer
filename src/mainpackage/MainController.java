package mainpackage;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import utilspackage.Book;
import utilspackage.CellFactoryAdvanced;
import utilspackage.FilterableTreeItem;
import utilspackage.TreeItemPredicate;
import utilspackage.Utils;
import utilspackage.XmlHandler;

public class MainController {

	private FilterableTreeItem<Book> rootItem;
	private String theme;


    @FXML
    private MenuItem darkBtn;
    @FXML
    private MenuItem whiteBtn;
	@FXML
	private TextField textField1;
	@FXML
	private ContextMenu contextMenu1;
	@FXML
	private TreeView<Book> treeView1;
	@FXML
	private AnchorPane textContentPane;
	@FXML
	private SplitPane splitPane1;
	@FXML
	private MenuItem menuItem1;

	@FXML
	void onOpenFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(null);
		if (file != null) {
			Utils.addBooks(file.getName(), file, rootItem);
		}
	}
	
	@FXML
	void loadFromFile() {
		try {
			XmlHandler.Read(rootItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void saveToFile() {
		try {
			XmlHandler.Save(rootItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() {
		/*
		 * using FilterableTreeItem to support incremental search from now on use
		 * getInternalChildren to get the filtered items
		 */
		theme = "Dark";
		//setDarkMode();

		rootItem = new FilterableTreeItem<Book>(new Book("Books"));
		rootItem.predicateProperty().bind(Bindings.createObjectBinding(() -> {
			if (textField1.getText() == null || textField1.getText().isEmpty())
				return null;
			//return TreeItemPredicate.create(s -> s.contains(textField1.getText()));
			return TreeItemPredicate.create(x -> ((Book) x).getTitle().contains(textField1.getText()));
		}, textField1.textProperty()));
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
				// We use dragboard "a java clipboard class" to store the files
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					for (File fl : db.getFiles()) {
						Utils.addBooks(fl.getName(), fl, rootItem);
					}
					// we set the variable to true when we release the mouse click
					success = true;
				}
				// here is end of the drag event
				event.setDropCompleted(success);

				event.consume();
			}
		});

		treeView1.setRoot(rootItem);
		treeView1.setShowRoot(false);
		treeView1.setEditable(true);
		treeView1.setCellFactory(new CellFactoryAdvanced());
		// TODO try to understand how to handle this but with filterabletreeitem so it
		// does not cause exceptions
		treeView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			OpenText(newValue.getValue());
		});
	}

	

	@FXML
	void onCreateCategory(ActionEvent event) {
		Utils.addCategory(rootItem);
	}

	@FXML
	void ChangeTheme(ActionEvent event) {
		if (theme.equals("White")) {
			setDarkMode();
		} else {
			setWhiteMode();
		}
	}

    @FXML
	void setDarkMode() {
		SceneHandler.getInstance().getMainScene().getStylesheets().remove(Utils.WHITETHEME);
		if (!SceneHandler.getInstance().getMainScene().getStylesheets().contains(Utils.DARKTHEME))
			SceneHandler.getInstance().getMainScene().getStylesheets().add(Utils.DARKTHEME);
	}

	@FXML
	void setWhiteMode() {
		SceneHandler.getInstance().getMainScene().getStylesheets().remove(Utils.DARKTHEME);
		if (!SceneHandler.getInstance().getMainScene().getStylesheets().contains(Utils.WHITETHEME))
			SceneHandler.getInstance().getMainScene().getStylesheets().add(Utils.WHITETHEME);
	}

	void OpenText(Book b) {
		try {
			//File toShow = Utils.getBookFromTitle(title);
			File toShow = b.getPath(); 
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
