package utilspackage;

public class TreeCategory extends FilterableTreeItem<Book>{
	public TreeCategory(String label) {
		super(new Book(label));
	}
}
