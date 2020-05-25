package utilspackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
	final public static String DARKTHEME = "/formpackage/style_dark.css";
	final public static String WHITETHEME = "/formpackage/style_white.css";
	public static HashMap<String, File> books = new HashMap<String, File>();
	
	public static Boolean addBooks(String title, File path) {	
		if (books.containsKey(title))
			return false;
		else
		{
			books.put(title, path);
			return true; 
		}
	}
	
	public static File getBookFromTitle(String title) {
		return books.get(title);
	}
	
	/*public static ArrayList<String> searchBooksFromWords(String text){
		ArrayList<String> tmp = new ArrayList<String>();
		for (String x : books.keySet()) {
			
		}
		return tmp;
	}*/
}
