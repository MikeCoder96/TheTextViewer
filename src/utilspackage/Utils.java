package utilspackage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utils {
	final public static String DARKTHEME = "/formpackage/style_dark.css";
	final public static String WHITETHEME = "/formpackage/style_white.css";
	
	final public static Set<File> libri = new HashSet<File>();
	
	public static void addBooks(String Title, File file, FilterableTreeItem<Book> rootItem) {
		if (!Utils.libri.contains(file)) { 
			FilterableTreeItem<Book> item = new FilterableTreeItem<Book>(new Book(Title,file));
			rootItem.getInternalChildren().add(item);
			Utils.libri.add(file);
		} 
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Book Already Exist!"); alert.setHeaderText("The book: " + Title + " is already present in your library!"); alert.showAndWait();
		}
	}
	
	public static void addCategory(FilterableTreeItem<Book> rootItem) {
		TreeCategory prova = new TreeCategory("Inserisci nome");
		rootItem.getInternalChildren().add(prova);
	}
	/*
	 * public static HashMap<String, File> books = new HashMap<String, File>();
	 * 
	 * public static Boolean addBooks(String title, File path) { if
	 * (books.containsKey(title)) return false; else { books.put(title, path);
	 * return true; } }
	 * 
	 * public static File getBookFromTitle(String title) { return books.get(title);
	 * }
	 */	
	/*public static ArrayList<String> searchBooksFromWords(String text){
		ArrayList<String> tmp = new ArrayList<String>();
		for (String x : books.keySet()) {
			
		}
		return tmp;
	}*/
}
