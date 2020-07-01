package utilspackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javafx.scene.control.TreeItem;
import javafx.util.Pair;
import mainpackage.SceneHandler;

/*
 * This class handles local file save and load
 * using xml and xml tools from JDOM
 */

public class XmlHandler {
	
	private static String SETTINGS = "settings.xml";
	private static String SETTINGSROOT = "settings";
	private static String STYLE = "style";
	private static String LOCALSAVE = "local_library.xml";
	private static String LIBRARY = "library";
	private static String CATEGORY = "category";
	private static String CATEGORY_NAME = "name";
	private static String BOOK = "book";
	private static String BOOK_TITLE = "title";
	private static String BOOK_PATH = "path";
	private static Document doc;
	private static XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
	public static List<Pair<Book, FilterableTreeItem<Book>>> nonfoundbooks;
	
	
	public static void callSave(FilterableTreeItem<Book> root) throws FileNotFoundException, IOException {
		doc = new Document();
		doc.setRootElement(new Element(LIBRARY));
		Save(root,doc.getRootElement());
		out.output(doc, new FileOutputStream(LOCALSAVE));
	}
	/*
	 * Save current tree structure to
	 * xml file and recursively traverse
	 * categories
	 */
	public static void Save(FilterableTreeItem<Book> root, Element xmlroot) {
		for(TreeItem<Book> a : root.getInternalChildren()) {
			if(a instanceof TreeCategory) {
				Element ca = new Element(CATEGORY);
				ca.setAttribute(CATEGORY_NAME, a.getValue().getTitle());
				Save((FilterableTreeItem<Book>)a,ca);
				xmlroot.addContent(ca);
			}
			else {
				book(a.getValue(),xmlroot);
			}
		}
	}
	/*
	 * convert a book object
	 * to its xml representation
	 */
	private static void book(Book a, Element root) {
		Element b =  new Element(BOOK);
		b.setAttribute(BOOK_TITLE,a.getTitle());
		b.addContent(new Element(BOOK_PATH).setText(a.getPath().toString()));
		root.addContent(b);
	}
	
	//initialize book loading from file
	public static void callRead(FilterableTreeItem<Book> root) throws JDOMException, IOException {
		root.getInternalChildren().clear();
		doc = (Document) new SAXBuilder().build(new File(LOCALSAVE));
		Element e = doc.getRootElement();
		if(e.getName().contentEquals(LIBRARY)) {
			Read(root,e);
		}
		SceneHandler.getInstance().initBookDialog();
	}
	/*
	 * Load books and categories(recursively)
	 * from xml file 
	 */
	private static void Read(FilterableTreeItem<Book> root, Element xmlroot) throws JDOMException, IOException {
		for(Element a : xmlroot.getChildren()) {
			if(a.getName().contentEquals(CATEGORY)) {
				TreeCategory rt = new TreeCategory(a.getAttributeValue(CATEGORY_NAME));
				root.getInternalChildren().add(rt);
				Read(rt,a);
			}
			else {
				Book tmp = createBook(a);
				if(tmp.getPath().exists())
					root.getInternalChildren().add(new FilterableTreeItem<Book>(tmp));
				else
					notFoundList(tmp, root);
			}
		}
	}
	
	/*
	 * Convert xml book to book object
	 */
	private static Book createBook(Element b) {
		File f = new File(b.getChildText(BOOK_PATH));
		return new Book(b.getAttributeValue(BOOK_TITLE),f);
	}
	
	//handle not found files list
	private static void notFoundList(Book a, FilterableTreeItem<Book> root) {
		if(nonfoundbooks == null)
			nonfoundbooks = new ArrayList<Pair<Book,FilterableTreeItem<Book>>>();
		nonfoundbooks.add(new Pair<Book, FilterableTreeItem<Book>>(a, root));
	}
	
	//load last used settings, based on the file, called on startup
	public static String loadSettings() throws FileNotFoundException, IOException, JDOMException {
		File st = new File(SETTINGS);
		if(st == null || !st.exists()) {
			doc = new Document();
			doc.setRootElement(new Element(SETTINGSROOT));
			doc.getRootElement().setAttribute(STYLE,Utils.PACKAGEDIR + Utils.DEFAULTTHEME);
			out.output(doc, new FileOutputStream(SETTINGS));
		}
		doc = (Document) new SAXBuilder().build(new File(SETTINGS));
		return doc.getRootElement().getAttributeValue(STYLE);
	}
	
	//save current settings, called on program close
	public static void saveSettings() throws JDOMException, IOException {
		doc = (Document) new SAXBuilder().build(new File(SETTINGS));
		doc.getRootElement().setAttribute(STYLE, SceneHandler.getInstance().getMainScene().getStylesheets().get(0));
		out.output(doc, new FileOutputStream(SETTINGS));
	}
}
