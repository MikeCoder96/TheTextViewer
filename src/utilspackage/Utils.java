package utilspackage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import mainpackage.SceneHandler;

public class Utils {
	final public static String DEFAULTTHEME = "styles/style_white.css";
	final public static Set<File> libri = new HashSet<File>();
	public static FilterableTreeItem<Book> rootItem;
	
	
	public static void addBooks(Book newvalue, FilterableTreeItem<Book> rootItem) {
		if (!Utils.libri.contains(newvalue.getPath())) { 
			FilterableTreeItem<Book> item = new FilterableTreeItem<Book>(newvalue);
			rootItem.getInternalChildren().add(item);
			Utils.libri.add(newvalue.getPath());
		} 
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Book Already Exist!"); alert.setHeaderText("The book: " + newvalue.getTitle() + " is already present in your library!"); alert.showAndWait();
		}
	}
	
	public static void addCategory(FilterableTreeItem<Book> rootItem) {
		rootItem.getInternalChildren().add(new FilterableTreeItem<Book>(new Book("Inserisci nome")));
	}
	
	public static void changeTheme(String file) {
		SceneHandler.getInstance().getMainScene().getStylesheets().clear();
		SceneHandler.getInstance().getMainScene().getStylesheets().add(file);
	}
}
