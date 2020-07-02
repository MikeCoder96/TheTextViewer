package utilspackage;

import java.awt.Desktop;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

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
		this.getItems().add(editName);
		
		if (object.getItem().getPath() != null) {
			MenuItem openexternal = new MenuItem("Open External");
			openexternal.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(Desktop.isDesktopSupported()) {
						new Thread(() -> {
							try {
								Desktop.getDesktop().open(object.getItem().getPath());
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}).start();
					}
				}
			});
			this.getItems().add(openexternal);
		}
		
		MenuItem createCat = new MenuItem("Create category");
		createCat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Utils.addCategory((FilterableTreeItem<Book>)object.getTreeItem().getParent());
			}
		});
		this.getItems().add(createCat);
		
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String title = object.getItem().getTitle();
				FilterableTreeItem<Book> toDelete = (FilterableTreeItem<Book>) object.getTreeItem();
				AtomicBoolean isDeleted = new AtomicBoolean();
				if(object.getItem().getPath() == null) {
					Alert conf = new Alert(AlertType.CONFIRMATION);
					conf.setTitle("Deleting category");
					conf.setHeaderText("You are deleting the category " + title + " and its content, are you sure?");
					conf.showAndWait().ifPresent(rs -> {
					    if (rs == ButtonType.OK) {
					    	isDeleted.set(((FilterableTreeItem<Book>)toDelete.getParent()).getInternalChildren().remove(toDelete));
					    }
					});
				}
				else {
					isDeleted.set(((FilterableTreeItem<Book>)toDelete.getParent()).getInternalChildren().remove(toDelete));
				}
				if (isDeleted.get())
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					if(object.getItem().getPath() == null) {
						alert.setTitle("Category deleted!");
						alert.setHeaderText("The category: " + title + " was deleted!");
					}
					else {
						alert.setTitle("Book Deleted!");
						alert.setHeaderText("The book: " + title + " was deleted!");
					}
					alert.showAndWait();
				}
			}
		});
		this.getItems().add(delete);
	}
}
