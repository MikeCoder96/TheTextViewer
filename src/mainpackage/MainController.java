package mainpackage;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.jdom2.JDOMException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import utilspackage.Book;
import utilspackage.CellFactoryAdvanced;
import utilspackage.FilterableTreeItem;
import utilspackage.TreeItemPredicate;
import utilspackage.Utils;
import utilspackage.XmlHandler;

public class MainController {

	@FXML
	private Menu themeMenu;
	
    volatile String base64;
    @FXML
    private WebView webView;	
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
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("Book Format", "*.txt", "*.pdf", "*.epub");
        fc.getExtensionFilters().add(extFilter);
		File file = fc.showOpenDialog(null);
		if (file != null && file.exists()) {
			Utils.addBooks(new Book(file.getName(),file), Utils.rootItem);
		}
	}
	
	@FXML
	void loadFromFile() {
		try {
			XmlHandler.callRead(Utils.rootItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void saveToFile() {
		try {
			XmlHandler.callSave(Utils.rootItem);
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
		
		webView.setVisible(false);

		Utils.rootItem = new FilterableTreeItem<Book>(new Book("Books"));
		Utils.rootItem.predicateProperty().bind(Bindings.createObjectBinding(() -> {
			if (textField1.getText() == null || textField1.getText().isEmpty())
				return null;
			//return TreeItemPredicate.create(s -> s.contains(textField1.getText()));
			return TreeItemPredicate.create(x -> ((Book) x).getTitle().contains(textField1.getText()));
		}, textField1.textProperty()));
		treeView1.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (treeView1.getRoot() != null && event.getGestureSource() != Utils.rootItem
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
						if (getExtension(fl.getAbsolutePath()).equals("pdf") || getExtension(fl.getAbsolutePath()).equals("txt") || getExtension(fl.getAbsolutePath()).equals("epub"))
						Utils.addBooks(new Book(fl.getName(), fl), Utils.rootItem);
					}
					// we set the variable to true when we release the mouse click
					success = true;
				}
				// here is end of the drag event
				event.setDropCompleted(success);

				event.consume();
			}
		});

		treeView1.setRoot(Utils.rootItem);
		treeView1.setShowRoot(false);
		treeView1.setEditable(true);
		treeView1.setCellFactory(new CellFactoryAdvanced());
		// TODO try to understand how to handle this but with filterabletreeitem so it
		// does not cause exceptions
		treeView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			OpenText(newValue.getValue());
		});
	}

	private MenuItem createCustomItem(String text) {
		MenuItem res = new MenuItem(text);
		res.setOnAction(this::themeItemHandler);
		return res;
	}
	private void themeItemHandler(ActionEvent event) {
		Utils.changeTheme(Utils.PACKAGEDIR + ((MenuItem)event.getSource()).getText());
	}
	
	@FXML
	void onCreateCategory(ActionEvent event) {
		Utils.addCategory(Utils.rootItem);
	}

	String getExtension(String filename) {
		String extension = "";

		int i = filename.lastIndexOf('.');
		if (i > 0) {
		    extension = filename.substring(i+1);
		    return extension.toLowerCase();
		}
		return null;
	}
	
	void OpenText(Book b) {
		try {
			//File toShow = Utils.getBookFromTitle(title);
			File toShow = b.getPath();
			if (toShow == null)
				return;
			String extension = getExtension(b.getPath().getAbsolutePath());
			if (extension.equals("pdf")) {
				webView.setVisible(true);
		        WebEngine engine = webView.getEngine();
		        String url = getClass().getResource("/pdfreader/web/viewer.html").toExternalForm();
		        engine.setJavaScriptEnabled(true);
		        //� stato un parto farlo funzionare ma � andato
		        //al posto di usare il path si convertono i byte del file in base64
		        //e lo si da in pasto alla pagina web che gestisce tutto

		        Runnable task = () -> {    	
		        	byte[] data = null;
					try {					
						data = Files.readAllBytes(b.getPath().toPath());
			        	base64 = Base64.getEncoder().encodeToString(data);  
			        	engine.load(url + "?file=data:application/pdf;base64," + base64);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        };  
		        task.run();	        
			}
			else if (extension.equals("epub"))
			{
				//Files.copy(b.getPath().toPath(), new File(getClass().getResource("/pdfreader/web/viewer.html").toExternalForm().replace("file:/", "").replace("viewer.html", "temp.pdf")).toPath(), StandardCopyOption.REPLACE_EXISTING);
				webView.setVisible(true);
		        WebEngine engine = webView.getEngine();
		        String url = getClass().getResource("/epubreader/reader/index.html").toExternalForm();
		        engine.setJavaScriptEnabled(true);
		        engine.load(url);
		        //engine.executeScript("window.location = \"file:///C:/Users/mikel/OneDrive/Desktop/Study/asd.epub\";");
//				Alert alert = new Alert(AlertType.INFORMATION);
//				alert.setTitle("Not Implemented!");
//				alert.setHeaderText("Sorry about that but the epub reader is not implemented yet! We're working on it!");
//				//alert.setContentText("I have a great message for you!");
//				alert.showAndWait().ifPresent(rs -> {
//				    if (rs == ButtonType.OK) {
//				        //System.out.println("Pressed OK.");
//				    }
//				});
		    }
			else if (extension.equals("txt")){
				webView.setVisible(false);
				TextFileViewer tfv = new TextFileViewer(toShow);
				textContentPane.getChildren().clear();
				// set to fill AnchorPane
				AnchorPane.setBottomAnchor(tfv, 0.0);
				AnchorPane.setLeftAnchor(tfv, 0.0);
				AnchorPane.setTopAnchor(tfv, 0.0);
				AnchorPane.setRightAnchor(tfv, 0.0);
				textContentPane.getChildren().add(tfv);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void loadStartup() {
		FileFilter ff = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(pathname.getName().startsWith("style_") && pathname.getName().endsWith(".css")) {
					return true;
				}
				return false;
			}
		};
		File dir = new File(getClass().getResource("/formpackage").getPath());
		if(dir.exists() && dir.isDirectory()) {
			for(File f : dir.listFiles(ff)) {
				themeMenu.getItems().add(createCustomItem(f.getName()));
			}
		}
		try {
			XmlHandler.callRead(Utils.rootItem);
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Local library not found!");
			alert.setHeaderText("Do you want to create a new local library?");
			//alert.setContentText("I have a great message for you!");
			alert.showAndWait().ifPresent(rs -> {
			    if (rs == ButtonType.OK) {
			        try {
						XmlHandler.callSave(Utils.rootItem);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			});
		}
	}
}
