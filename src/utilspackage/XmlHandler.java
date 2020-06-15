package utilspackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javafx.scene.control.TreeItem;

/*
 * This class handles local file save and load
 * using xml and xml tools from JDOM
 */

public class XmlHandler {
	
	private static String filename = "local_library.xml";
	private static String LIBRARY = "library";
	private static String CATEGORY = "category";
	private static String CATEGORY_NAME = "name";
	private static String BOOK = "book";
	private static String BOOK_TITLE = "title";
	private static String BOOK_PATH = "path";
	private static Document doc; 
	
	/*
	 * Save current tree structure to
	 * xml file
	 */
	public static void Save(FilterableTreeItem<Book> root) throws FileNotFoundException, IOException {
		doc = new Document();
		doc.setRootElement(new Element(LIBRARY));
		
		for(TreeItem<Book> a : root.getInternalChildren()) {
			if(a instanceof TreeCategory) {
				traverseCategory(a,doc.getRootElement());
			}
			else {
				book(a.getValue(),doc.getRootElement());
			}
		}
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		out.output(doc, new FileOutputStream(filename));
	}
	
	/*
	 * recursively find the categories
	 * with the books inside each one
	 */
	private static void traverseCategory(TreeItem<Book> c, Element root) {
		Element ca = new Element(CATEGORY);
		ca.setAttribute(CATEGORY_NAME, c.getValue().getTitle());
		for(TreeItem<Book> a : c.getChildren()) {
			if(a instanceof TreeCategory) {
				traverseCategory(a,ca);
			}
			else {
				book(a.getValue(), ca);
			}
		}
		root.addContent(ca);
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
	
	/*
	 * Load books and categories
	 * from xml file 
	 */
	public static void Read(FilterableTreeItem<Book> root) throws JDOMException, IOException {
		root.getInternalChildren().clear();
		Document doc = (Document) new SAXBuilder().build(new File(filename));
		Element e = doc.getRootElement();
		if(e.getName().contentEquals(LIBRARY)) {
			for(Element a : e.getChildren()) {
				if(a.getName().contentEquals(CATEGORY)) {
					root.getInternalChildren().add(traverseCategory(a));
				}
				else {
					root.getInternalChildren().add(new FilterableTreeItem<Book>(createBook(a)));
				}
			}
		}
	}
	
	/*
	 * Convert xml book to book object
	 */
	private static Book createBook(Element b) {
		return new Book(b.getAttributeValue(BOOK_TITLE),new File(b.getChildText(BOOK_PATH)));
	}
	
	/*
	 * recursively traverse categories
	 * from xml format
	 */
	
	private static TreeCategory traverseCategory(Element root) {
		TreeCategory rt = new TreeCategory(root.getAttributeValue(CATEGORY_NAME));
		for(Element a : root.getChildren()) {
			if(a.getName().contentEquals(CATEGORY_NAME)) {
				rt.getInternalChildren().add(traverseCategory(a));
			}
			else {
				rt.getInternalChildren().add(new FilterableTreeItem<Book>(createBook(a)));
			}
		}
		return rt;
	}
}
