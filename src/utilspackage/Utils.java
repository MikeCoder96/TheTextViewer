package utilspackage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeItem;
import mainpackage.MainController;
import mainpackage.SceneHandler;

public class Utils {
	final public static String DEFAULTTHEME = "styles/style_white.css";
	final public static Set<File> libri = new HashSet<File>();
	public static FilterableTreeItem<Book> rootItem;
	
	
	public static void addBooks(Book newvalue, FilterableTreeItem<Book> rootItem) {
		if (!libri.contains(newvalue.getPath())) { 
			FilterableTreeItem<Book> item = new FilterableTreeItem<Book>(newvalue);
			rootItem.getInternalChildren().add(item);
			libri.add(newvalue.getPath());
		} 
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Book Already Exist!"); alert.setHeaderText("The book: " + newvalue.getTitle() + " is already present in your library!"); alert.showAndWait();
		}
	}
	
	public static void addCategory(FilterableTreeItem<Book> rootItem) {
		rootItem.getInternalChildren().add(new FilterableTreeItem<Book>(new Book("Inserisci nome")));
	}
	
	private static void removeBook(FilterableTreeItem<Book> Item) {
		libri.remove(Item.getValue().getPath());
		((MainController) SceneHandler.getInstance().getMainLoader().getController()).checkDeleted(Item.getValue());
		//((FilterableTreeItem<Book>) Item.getParent()).getInternalChildren().remove(Item);
	}
	
	private static void removeCategory(FilterableTreeItem<Book> Item) {
		for(TreeItem<Book> entry : Item.getInternalChildren()) {
			removeEntry((FilterableTreeItem<Book>) entry);
		}
	}
	
	private static void removeEntry(FilterableTreeItem<Book> Item) {
		if(Item.getValue().getPath() == null) {
			removeCategory(Item);
		} else {
			removeBook(Item);
		}
	}
	
	public static void callremoveEntry(FilterableTreeItem<Book> Item) {
		removeEntry(Item);
		((FilterableTreeItem<Book>) Item.getParent()).getInternalChildren().remove(Item);
	}
	
	public static void changeTheme(String file) {
		SceneHandler.getInstance().getMainScene().getStylesheets().clear();
		SceneHandler.getInstance().getMainScene().getStylesheets().add(new File(file).toURI().toString());
	}
}
