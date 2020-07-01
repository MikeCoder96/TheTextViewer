package utilspackage;

import java.io.File;
import java.io.Serializable;

public class Book implements Serializable {

	private static final long serialVersionUID = 5309760779341209402L;
	private String title;
	private File path;
	public Book(String label) {
		title = label;
	}
	public Book(String label, File file) {
		title = label;
		path = file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public File getPath() {
		return path;
	}
	public void setPath(File path) {
		this.path = path;
	}
	
}
