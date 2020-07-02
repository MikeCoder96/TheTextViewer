package utilspackage;

import java.awt.Desktop;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;

public class ContextMenuTextCell extends ContextMenu{
	private TreeCell<Book> object;
	
	public ContextMenuTextCell(TreeCell<Book> obj) {
		super();
		object = obj;
		MenuItem editName = new MenuItem("Edit Title");
		editName.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				object.startEdit();
			}
		});
		
		MenuItem openexternal = new MenuItem("Open External");
		openexternal.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
			    Desktop dt = Desktop.getDesktop();
			    try {
					dt.open(object.getItem().getPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String title = object.getItem().getTitle();
				FilterableTreeItem<Book> toDelete = (FilterableTreeItem<Book>) object.getTreeItem();
				Boolean isDeleted = ((FilterableTreeItem<Book>)toDelete.getParent()).getInternalChildren().remove(toDelete);
				if (isDeleted)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Book Deleted!");
					alert.setHeaderText("The book: " + title + " was deleted!");
					//alert.setContentText("I have a great message for you!");
					alert.showAndWait().ifPresent(rs -> {
					    if (rs == ButtonType.OK) {
					        //System.out.println("Pressed OK.");
					    }
					});
				}
			}
		});
		this.getItems().add(editName);
		this.getItems().add(delete);
		if (object.getItem().getPath() != null)
			this.getItems().add(openexternal);
	}
}
